package alexmog.rulemastersworld.client.packetactions;

import org.newdawn.slick.geom.Vector2f;

import com.esotericsoftware.kryonet.Client;

import alexmog.rulemastersworld.packets.EntityPacket;
import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.client.PacketAction;
import alexmog.rulemastersworld.entity.OnlineEntity;

public class EntityAction implements PacketAction {

    public void run(Object packet, Client client) {
        EntityPacket p = (EntityPacket)packet;
        OnlineEntity e = new OnlineEntity(new Vector2f(p.x, p.y), new Vector2f(p.width, p.height), Main.game.getGameMode(),
                p.actualHp, p.stats.getMaxHp(), p.living);
        e.setName(p.name);
        e.setToDelete(false);
        e.setSkinParts(new String[] {"Image.Skin_" + p.skinId});
        e.setId(p.id);
        Main.game.getGameMode().getEntitiesManager().addEntity(e);
    }

}
