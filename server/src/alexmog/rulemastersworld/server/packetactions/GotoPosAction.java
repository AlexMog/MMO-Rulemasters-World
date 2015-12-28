package alexmog.rulemastersworld.server.packetactions;

import alexmog.rulemastersworld.AccountConnection;
import alexmog.rulemastersworld.packets.GotoPosPacket;
import alexmog.rulemastersworld.server.PacketAction;

public class GotoPosAction implements PacketAction {

    public void run(AccountConnection connection, Object packet) throws Exception {
        GotoPosPacket p = (GotoPosPacket)packet;
        ((AccountConnection)connection).getPlayer().goTo(p.x, p.y);
    }

    public boolean needLoggedIn() {
        return true;
    }

}
