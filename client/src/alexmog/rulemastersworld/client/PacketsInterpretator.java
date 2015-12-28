package alexmog.rulemastersworld.client;

import java.util.HashMap;
import java.util.Iterator;

import com.esotericsoftware.kryonet.Client;

import alexmog.rulemastersworld.packets.ConnectPacket;
import alexmog.rulemastersworld.packets.EntityEffectAddPacket;
import alexmog.rulemastersworld.packets.EntityEffectRemovePacket;
import alexmog.rulemastersworld.packets.EntityHpPacket;
import alexmog.rulemastersworld.packets.EntityMovePacket;
import alexmog.rulemastersworld.packets.EntityPacket;
import alexmog.rulemastersworld.packets.EntityRemovePacket;
import alexmog.rulemastersworld.packets.ErrorPacket;
import alexmog.rulemastersworld.packets.NewUserConnectedLobbyPacket;
import alexmog.rulemastersworld.packets.PlayerSpellPacket;
import alexmog.rulemastersworld.packets.PlayerSpellUsedPacket;
import alexmog.rulemastersworld.packets.tchat.TchatMsgPacket;
import alexmog.rulemastersworld.packets.tchat.TchatPrivmsgPacket;
import alexmog.rulemastersworld.client.packetactions.ConnectAction;
import alexmog.rulemastersworld.client.packetactions.EntityAction;
import alexmog.rulemastersworld.client.packetactions.EntityEffectAddAction;
import alexmog.rulemastersworld.client.packetactions.EntityEffectRemoveAction;
import alexmog.rulemastersworld.client.packetactions.EntityHpAction;
import alexmog.rulemastersworld.client.packetactions.EntityMoveAction;
import alexmog.rulemastersworld.client.packetactions.EntityRemoveAction;
import alexmog.rulemastersworld.client.packetactions.ErrorAction;
import alexmog.rulemastersworld.client.packetactions.NewUserConnectedLobbyAction;
import alexmog.rulemastersworld.client.packetactions.PlayerSpellAction;
import alexmog.rulemastersworld.client.packetactions.PlayerSpellUsedAction;
import alexmog.rulemastersworld.client.packetactions.TchatMsgAction;
import alexmog.rulemastersworld.client.packetactions.TchatPrivmsgAction;

@SuppressWarnings("rawtypes")
public class PacketsInterpretator {
    protected HashMap<Class, PacketAction> mPackets = new HashMap<Class, PacketAction>();
    protected Client mClient;
    
    public PacketsInterpretator(Client client) {
        mClient = client;
        mPackets.put(ConnectPacket.class, new ConnectAction());
        mPackets.put(ErrorPacket.class, new ErrorAction());
        mPackets.put(EntityPacket.class, new EntityAction());
        mPackets.put(EntityMovePacket.class, new EntityMoveAction());
        mPackets.put(EntityRemovePacket.class, new EntityRemoveAction());
        mPackets.put(EntityHpPacket.class, new EntityHpAction());
        mPackets.put(EntityEffectAddPacket.class, new EntityEffectAddAction());
        mPackets.put(EntityEffectRemovePacket.class, new EntityEffectRemoveAction());
        mPackets.put(NewUserConnectedLobbyPacket.class, new NewUserConnectedLobbyAction());
        mPackets.put(TchatMsgPacket.class, new TchatMsgAction());
        mPackets.put(TchatPrivmsgPacket.class, new TchatPrivmsgAction());
        mPackets.put(PlayerSpellPacket.class, new PlayerSpellAction());
        mPackets.put(PlayerSpellUsedPacket.class, new PlayerSpellUsedAction());
    }
    
    public boolean onPacketReceived(Object packet) {
        Iterator<Class> it = mPackets.keySet().iterator();
        
        while (it.hasNext()) {
            Class item = it.next();
            if (packet.getClass().isAssignableFrom(item)) {
                mPackets.get(item).run(packet, mClient);
                return true;
            }
        }
        return false;
    }
}
