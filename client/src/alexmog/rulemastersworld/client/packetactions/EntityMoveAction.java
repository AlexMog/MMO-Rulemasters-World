package alexmog.rulemastersworld.client.packetactions;

import com.esotericsoftware.kryonet.Client;

import alexmog.rulemastersworld.packets.EntityMovePacket;
import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.client.PacketAction;
import alexmog.rulemastersworld.entity.Entity;

public class EntityMoveAction implements PacketAction {

    public void run(Object packet, Client client) {
        EntityMovePacket p = (EntityMovePacket)packet;
        Entity e = Main.game.getGameMode().getEntitiesManager().getEntityById(p.id);
        if (e != null) {
            e.setLastAngle(p.angle);
            e.setAnimationId(p.animation);
            e.getPosition().x = p.x;
            e.getPosition().y = p.y;
            e.setSpeed(p.velocity);
        }
    }

}
