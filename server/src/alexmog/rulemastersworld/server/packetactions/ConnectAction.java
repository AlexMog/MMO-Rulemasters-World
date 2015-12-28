package alexmog.rulemastersworld.server.packetactions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp2.DelegatingPreparedStatement;
import org.newdawn.slick.geom.Vector2f;

import com.esotericsoftware.minlog.Log;

import alexmog.rulemastersworld.AccountConnection;
import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.entity.Player;
import alexmog.rulemastersworld.managers.MapInstancesManager;
import alexmog.rulemastersworld.managers.PlayerCacheManager;
import alexmog.rulemastersworld.managers.ServiceManager;
import alexmog.rulemastersworld.map.MapInstance;
import alexmog.rulemastersworld.packets.ConnectPacket;
import alexmog.rulemastersworld.packets.ErrorPacket;
import alexmog.rulemastersworld.packets.Network;
import alexmog.rulemastersworld.packets.PlayerSpellPacket;
import alexmog.rulemastersworld.packets.ErrorPacket.ErrorType;
import alexmog.rulemastersworld.server.PacketAction;
import alexmog.rulemastersworld.server.services.Service;
import alexmog.rulemastersworld.skills.ActivableSkill;
import alexmog.rulemastersworld.skills.Skill;

public class ConnectAction implements PacketAction {
    public static final int DEFAULT_SPAWN_X = 200, DEFAULT_SPAWN_Y = 200;
    private static final int DEFAULT_PLAYER_END = 200;

    public void run(final AccountConnection connection, Object packet)  throws Exception {
        final ConnectPacket realPacket = (ConnectPacket)packet;
        ((Service)ServiceManager.getInstance().getService("LoginService")).addAction(new Runnable() {
            
            public void run() {
                if (realPacket.version != Network.version) {
                    ErrorPacket err = new ErrorPacket();
                    err.status = ErrorType.BAD_VERSION;
                    err.message = "You have an outdated version. Please update your game.";
                    connection.sendTCP(err);
                    connection.close();
                    return;
                }
                String username = null;
                String playername = null;
                int posX = DEFAULT_SPAWN_X, posY = DEFAULT_SPAWN_Y, skin_id = 0, hp = 100;
                int lvl = 0;
                int accountId = 0;
                MapInstance mapInstance = null;
                try {
                    try (
                            Connection conn = Main.accountDb.getConnection();
                            DelegatingPreparedStatement statement = (DelegatingPreparedStatement) conn.prepareStatement("SELECT * FROM accounts WHERE token = ?");
                            Connection conn2 = Main.worldDb.getConnection();
                            DelegatingPreparedStatement statement2 = (DelegatingPreparedStatement) conn2.prepareStatement("SELECT * FROM characters WHERE account_id = ?");
                        ) {
                        statement.setString(1, realPacket.token);
                        try (ResultSet result = statement.executeQuery();) {
                            if (result.next()) {
                                playername = username = result.getString("username");
                                lvl = result.getInt("grade");
                                accountId = result.getInt("id");
                                statement2.setInt(1, accountId);
                                try (ResultSet r2 = statement2.executeQuery();) {
                                    if (r2.next()) {
                                        playername = r2.getString("name");
                                        posX = r2.getInt("posx");
                                        posY = r2.getInt("posy");
                                        skin_id = r2.getInt("skin_id");
                                        hp = r2.getInt("hp");
                                        mapInstance = MapInstancesManager.getInstance().getMapInstance(r2.getInt("world"));
                                    } else {
                                        try (DelegatingPreparedStatement statement3 = (DelegatingPreparedStatement)
                                                conn2.prepareStatement("INSERT INTO characters (account_id, name, skin_id, posx, posy, hp) VALUES(?, ?, ?, ?, ?, ?)");) {
                                            statement3.setInt(1, accountId);
                                            statement3.setString(2, username);
                                            statement3.setInt(3, 0);
                                            statement3.setInt(4, DEFAULT_SPAWN_X); // TODO
                                            statement3.setInt(5, DEFAULT_SPAWN_Y); // TODO
                                            statement3.setInt(6, hp);
                                            statement3.executeUpdate();
                                        }
                                    }
                                }
                            } else {
                                throw new SQLException();
                            }
                        }
                    }
                } catch (SQLException e) {
                    Log.error("ConnectAction", e);
                    ErrorPacket err = new ErrorPacket();
                    err.status = ErrorType.SERVER_ERROR;
                    err.message = "Account not found.";
                    connection.sendTCP(err);
                    connection.close();
                    return;
                }
                
                synchronized(Main.clientList) {
                    for (AccountConnection a : Main.clientList) {
                        if (a.getPlayer().getName().equalsIgnoreCase(username)) {
                            ErrorPacket err = new ErrorPacket();
                            err.status = ErrorType.ALREADY_IN_LOBBY;
                            err.message = "You are already connected.";
                            connection.sendTCP(err);
                            connection.close();
                            return;
                        }
                    }
                }
                

                if (mapInstance == null) {
                    mapInstance = MapInstancesManager.getInstance().getMapInstance(0);
                }
                
                Player player = PlayerCacheManager.getInstance().pollPlayerFromCache(accountId);
                if (player == null) {
                    player = new Player(new Vector2f(posX, posY),
                            new Vector2f(64, 64), mapInstance.getGameInstance().getGameMode(), connection.getID());    
                }
                
                AccountConnection client = (AccountConnection)connection;
                player.setPermissionsLvl(lvl);
                player.setMap(mapInstance);
                player.setName(playername);
                player.getStats().setEnd(DEFAULT_PLAYER_END);
                player.setHp(hp);
                player.setSkinId(skin_id);
                client.setPlayer(player);

                client.synchronize();
                client.setConnectedAt(System.currentTimeMillis());
                client.setToken(realPacket.token);
                client.setUsername(username);
                client.setAccountId(accountId);
                Main.clientList.add(client);
                mapInstance.getGameInstance().addAccount(client);
                // TODO items
                for (Skill s : client.getPlayer().getSkills().values()) {
                	PlayerSpellPacket spellPacket = new PlayerSpellPacket();
                	spellPacket.description = s.getDescription();
                	spellPacket.name = s.getName();
                	spellPacket.iconId = s.getIconId();
                	spellPacket.spellId = s.getId();
                	if (s instanceof ActivableSkill) {
                		ActivableSkill tmp = (ActivableSkill)s;
                		spellPacket.cooldown = tmp.getCooldown();
                		spellPacket.targetType = tmp.getTargetType();
                	}
                	spellPacket.range = s.getRange();
                	spellPacket.passive = s.isPassive();
                	// TODO Spell slot
                	client.sendTCP(spellPacket);
                }
                Log.info("User " + username + " connected.");                
            }
        });
    }

    public boolean needLoggedIn() {
        return false;
    }

}
