package alexmog.rulemastersworld.client.packetactions;

import com.esotericsoftware.kryonet.Client;

import alexmog.rulemastersworld.packets.NewUserConnectedLobbyPacket;
import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.client.PacketAction;
import alexmog.rulemastersworld.graphic.camera.FollowingCamera;

public class NewUserConnectedLobbyAction implements PacketAction {

    public void run(Object packet, Client client) {
        NewUserConnectedLobbyPacket p = (NewUserConnectedLobbyPacket)packet;
        Main.clientId = p.userId;
        if (Main.game.getGameMode().getCamera() != null) {
            if (Main.game.getGameMode().getCamera() instanceof FollowingCamera) {
                ((FollowingCamera)Main.game.getGameMode().getCamera()).setTarget(null);
            }
        }
    }
    
}
