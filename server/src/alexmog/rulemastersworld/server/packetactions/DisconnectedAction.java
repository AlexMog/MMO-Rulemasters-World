package alexmog.rulemastersworld.server.packetactions;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.DelegatingPreparedStatement;

import com.esotericsoftware.minlog.Log;

import alexmog.rulemastersworld.AccountConnection;
import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.entity.Player;
import alexmog.rulemastersworld.managers.PlayerCacheManager;
import alexmog.rulemastersworld.managers.ServiceManager;
import alexmog.rulemastersworld.server.PacketAction;
import alexmog.rulemastersworld.server.services.Service;

public class DisconnectedAction implements PacketAction {

    public void run(AccountConnection connection, Object packet) throws Exception {
        final AccountConnection ac = (AccountConnection)connection;
        final Player p = ac.getPlayer();
        if (p != null) {
            Log.info("User " + p.getName() + " disconnected.");
            ((Service)ServiceManager.getInstance().getService("DisconnectService")).addAction(new Runnable() {
                @Override
                public void run() {
                    try (
                            Connection con = Main.worldDb.getConnection();
                            DelegatingPreparedStatement st = (DelegatingPreparedStatement) con
                                    .prepareStatement("UPDATE characters SET posx = ?, posy = ?, hp = ?, world = ? WHERE account_id = ?");
                    ) {
                        st.setInt(1, (int)p.getPosition().x);
                        st.setInt(2, (int)p.getPosition().y);
                        st.setInt(3, p.getHp());
                        st.setInt(4, p.getMapInstance().getId());
                        st.setInt(5, ac.getAccountId());
                        st.executeUpdate();
                    } catch (SQLException e) {
                        Log.info("DisconnectSave", e);
                    }
                    try (
                        Connection con = Main.accountDb.getConnection();
                        DelegatingPreparedStatement st = (DelegatingPreparedStatement) con
                                .prepareStatement("UPDATE accounts SET time_played = time_played + ? WHERE id = ?");
                    ) {
                        st.setLong(1, System.currentTimeMillis() - ac.getConnectedAt());
                        st.setInt(2, ac.getAccountId());
                        st.executeUpdate();
                    } catch (SQLException e) {
                        Log.info("DisconnectAccountSave", e);
                    }
                }
            });;
        }
        PlayerCacheManager.getInstance().addPlayerToCache(p);
        ac.getPlayer().getMapInstance().getGameInstance().remAccount(ac);
        p.setAccount(null);
        synchronized(Main.clientList) {
            Main.clientList.remove(connection);
        }
    }

    public boolean needLoggedIn() {
        return true;
    }

}
