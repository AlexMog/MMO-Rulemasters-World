package alexmog.rulemastersworld.server.packetactions;

import alexmog.rulemastersworld.AccountConnection;
import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.packets.tchat.TchatMsgPacket;
import alexmog.rulemastersworld.packets.tchat.TchatPrivmsgPacket;
import alexmog.rulemastersworld.server.PacketAction;

public class TchatPrivmsgAction implements PacketAction {

    public void run(AccountConnection connection, Object packet) throws Exception {
        TchatPrivmsgPacket p = (TchatPrivmsgPacket)packet;
        
        if (p.destinator == null)
            return;
        p.username = ((AccountConnection)connection).getPlayer().getName();
        synchronized(Main.clientList) {
            for (AccountConnection a : Main.clientList) {
                if (a.getPlayer().getName().equalsIgnoreCase(p.destinator)) {
                    a.sendTCP(p);
                    return;
                }
            }
        }
        TchatMsgPacket ret = new TchatMsgPacket();
        ret.username = "Server";
        ret.msg = "User not found.";
        connection.sendTCP(ret);
    }

    public boolean needLoggedIn() {
        return true;
    }

}
