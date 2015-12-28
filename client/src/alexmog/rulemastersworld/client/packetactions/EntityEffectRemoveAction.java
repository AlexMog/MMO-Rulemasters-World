package alexmog.rulemastersworld.client.packetactions;

import com.esotericsoftware.kryonet.Client;

import alexmog.rulemastersworld.packets.EntityEffectRemovePacket;
import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.client.PacketAction;
import alexmog.rulemastersworld.entity.LivingEntity;

public class EntityEffectRemoveAction implements PacketAction {

    public void run(Object packet, Client client) {
        EntityEffectRemovePacket p = (EntityEffectRemovePacket)packet;
        LivingEntity e = (LivingEntity)Main.game.getGameMode().getEntitiesManager().getEntityById(p.entityId);
        if (e != null) {
            e.removeEffect(p.id);
        }
    }

}
