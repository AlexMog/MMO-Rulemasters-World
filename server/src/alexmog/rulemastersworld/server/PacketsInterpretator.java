package alexmog.rulemastersworld.server;

import java.util.HashMap;
import java.util.Iterator;

import alexmog.rulemastersworld.AccountConnection;
import alexmog.rulemastersworld.packets.ConnectPacket;
import alexmog.rulemastersworld.packets.DisconnectedPacket;
import alexmog.rulemastersworld.packets.ErrorPacket;
import alexmog.rulemastersworld.packets.GotoPosPacket;
import alexmog.rulemastersworld.packets.PlayerUseSpellPacket;
import alexmog.rulemastersworld.packets.ErrorPacket.ErrorType;
import alexmog.rulemastersworld.packets.tchat.TchatCommandPacket;
import alexmog.rulemastersworld.packets.tchat.TchatMsgPacket;
import alexmog.rulemastersworld.packets.tchat.TchatPrivmsgPacket;
import alexmog.rulemastersworld.server.packetactions.ConnectAction;
import alexmog.rulemastersworld.server.packetactions.DisconnectedAction;
import alexmog.rulemastersworld.server.packetactions.GotoPosAction;
import alexmog.rulemastersworld.server.packetactions.PlayerUseSpellAction;
import alexmog.rulemastersworld.server.packetactions.TchatCommandAction;
import alexmog.rulemastersworld.server.packetactions.TchatMsgAction;
import alexmog.rulemastersworld.server.packetactions.TchatPrivmsgAction;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.minlog.Log;

@SuppressWarnings("rawtypes")
public class PacketsInterpretator {
    private HashMap<Class, PacketAction> mPackets = new HashMap<Class, PacketAction>();
    
    public PacketsInterpretator() {
        mPackets.put(ConnectPacket.class, new ConnectAction());
        mPackets.put(GotoPosPacket.class, new GotoPosAction());
        mPackets.put(DisconnectedPacket.class, new DisconnectedAction());
        mPackets.put(TchatMsgPacket.class, new TchatMsgAction());
        mPackets.put(TchatPrivmsgPacket.class, new TchatPrivmsgAction());
        mPackets.put(TchatCommandPacket.class, new TchatCommandAction());
        mPackets.put(PlayerUseSpellPacket.class, new PlayerUseSpellAction());
    }
    
    public boolean onPacketReceived(Connection connection, Object packet) {
        Iterator<Class> it = mPackets.keySet().iterator();
        
        while (it.hasNext()) {
            Class item = it.next();
            try {
                if (packet.getClass().isAssignableFrom(item)) {
                    PacketAction a = mPackets.get(item);
                    AccountConnection c = (AccountConnection)connection;
                    if (!a.needLoggedIn() || (c.getToken() != null && c.getToken().length() > 0)) {
                        a.run(c, packet);
                    } else {
                        ErrorPacket p = new ErrorPacket();
                        p.message = "You are not authenticated.";
                        p.status = ErrorType.NOT_AUTHENTICATED;
                        c.sendTCP(p);
                        c.close();
                    }
                    return true;
                }
            } catch (Exception e) {
                Log.error("PacketInterpretator", e);
            }
        }
        return false;
    }
}
