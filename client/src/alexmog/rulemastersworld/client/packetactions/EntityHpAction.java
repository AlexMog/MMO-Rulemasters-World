package alexmog.rulemastersworld.client.packetactions;

import com.esotericsoftware.kryonet.Client;

import alexmog.rulemastersworld.packets.EntityHpPacket;
import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.client.PacketAction;
import alexmog.rulemastersworld.entity.LivingEntity;

public class EntityHpAction implements PacketAction {

    public void run(Object packet, Client client) {
        EntityHpPacket p = (EntityHpPacket)packet;
        LivingEntity e = (LivingEntity)Main.game.getGameMode().getEntitiesManager().getEntityById(p.id);
        if (e != null) {
            e.setHp(p.value);
        }
    }

}
