package alexmog.rulemastersworld.server;

import alexmog.rulemastersworld.AccountConnection;
import alexmog.rulemastersworld.Game;
import alexmog.rulemastersworld.MyEntry;
import alexmog.rulemastersworld.packets.DisconnectedPacket;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

public class ServerListener extends Listener {
    @Override
    public void connected(Connection c) {
    }
    
    @Override
    public void received(Connection c, Object packet) {
        AccountConnection ac = (AccountConnection)c;
        if (ac.getPlayer() != null && ac.getPlayer().getMapInstance() != null) {
            synchronized(ac.getPlayer().getMapInstance().getGameInstance().getActions()) {
                ac.getPlayer().getMapInstance().getGameInstance().getActions().push(new MyEntry((AccountConnection)c, packet));
            }
        } else {
            Game.mInterpretator.onPacketReceived(c, packet);
        }
    }
    
    @Override
    public void disconnected(Connection c) {
        AccountConnection ac = (AccountConnection)c;
        if (ac.getPlayer() != null && ac.getPlayer().getMapInstance() != null) {
            synchronized(ac.getPlayer().getMapInstance().getGameInstance().getActions()) {
                ac.getPlayer().getMapInstance().getGameInstance().getActions().push(new MyEntry((AccountConnection)c, new DisconnectedPacket()));
            }
        } else {
            Log.info("The user is not on a map. So he wasn't connected. CQFD.");
        }
    }
}
