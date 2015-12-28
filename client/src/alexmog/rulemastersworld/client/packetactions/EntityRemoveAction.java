package alexmog.rulemastersworld.client.packetactions;

import com.esotericsoftware.kryonet.Client;

import alexmog.rulemastersworld.packets.EntityRemovePacket;
import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.client.PacketAction;

public class EntityRemoveAction implements PacketAction {

    public void run(Object packet, Client client) {
    	Main.game.getGameMode().getEntitiesManager().remEntity(((EntityRemovePacket)packet).id);
    }

}
