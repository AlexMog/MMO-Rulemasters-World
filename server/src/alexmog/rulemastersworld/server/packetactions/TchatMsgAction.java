package alexmog.rulemastersworld.server.packetactions;

import com.esotericsoftware.minlog.Log;

import alexmog.rulemastersworld.AccountConnection;
import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.packets.tchat.TchatMsgPacket;
import alexmog.rulemastersworld.server.PacketAction;

public class TchatMsgAction implements PacketAction {

    public void run(AccountConnection connection, Object packet) throws Exception {
        TchatMsgPacket p = (TchatMsgPacket) packet;
        
        p.username = ((AccountConnection)connection).getPlayer().getName();
        Log.info(p.username + ": " + p.msg);
        Main.sendToAllAuthenticated(p);
    }

    public boolean needLoggedIn() {
        return true;
    }

}
