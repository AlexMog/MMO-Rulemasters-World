package alexmog.rulemastersworld.client.packetactions;

import com.esotericsoftware.kryonet.Client;

import alexmog.rulemastersworld.packets.EntityEffectAddPacket;
import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.client.PacketAction;
import alexmog.rulemastersworld.effects.Effect;
import alexmog.rulemastersworld.entity.LivingEntity;

public class EntityEffectAddAction implements PacketAction {

    public void run(Object packet, Client client) {
        EntityEffectAddPacket p = (EntityEffectAddPacket)packet;
        LivingEntity e = (LivingEntity)Main.game.getGameMode().getEntitiesManager().getEntityById(p.entityId);
        if (e != null) {
            Effect eff = new Effect(Main.game.getGameMode(), p.name, p.id, p.cooldown, p.iconId, p.infinite);
            eff.setStacks(p.stacks);
            eff.setDescription(p.description);
            e.addEffect(eff);
        }
    }

}
