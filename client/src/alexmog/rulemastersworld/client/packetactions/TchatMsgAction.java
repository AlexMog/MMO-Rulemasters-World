package alexmog.rulemastersworld.client.packetactions;

import com.esotericsoftware.kryonet.Client;

import alexmog.rulemastersworld.packets.tchat.TchatMsgPacket;
import alexmog.rulemastersworld.client.PacketAction;
import alexmog.rulemastersworld.scenes.GameScene;

public class TchatMsgAction implements PacketAction {

    public void run(Object packet, Client client) {
        TchatMsgPacket p = (TchatMsgPacket) packet;
        GameScene.chat.appendRow("default", p.username + ": " + p.msg);
    }

}
