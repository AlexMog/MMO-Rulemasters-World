package alexmog.rulemastersworld.packets;

import alexmog.rulemastersworld.datacontainers.TchatCommands;
import alexmog.rulemastersworld.packets.connect.ConnectQueuePlace;
import alexmog.rulemastersworld.packets.skills.TargetType;
import alexmog.rulemastersworld.packets.tchat.TchatCommandPacket;
import alexmog.rulemastersworld.packets.tchat.TchatMsgPacket;
import alexmog.rulemastersworld.statsystem.EntityStats;
import alexmog.rulemastersworld.statsystem.ObjectStats;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
    public static int port = 4243;
    public static final int udpPort = 4244;
    public static String host = "localhost";
    public static int version = 21;
    
    public static void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(ConnectPacket.class);
        kryo.register(NewUserConnectedLobbyPacket.class);
        kryo.register(ErrorPacket.class);
        kryo.register(ErrorPacket.ErrorType.class);
        kryo.register(EntityMovePacket.class);
        kryo.register(EntityPacket.class);
        kryo.register(EntityRemovePacket.class);
        kryo.register(GotoPosPacket.class);
        kryo.register(EntityHpPacket.class);
        kryo.register(EntityEffectAddPacket.class);
        kryo.register(EntityEffectRemovePacket.class);
        kryo.register(TchatMsgPacket.class);
        kryo.register(TchatCommandPacket.class);
        kryo.register(TchatCommands.class);
        kryo.register(ConnectQueuePlace.class);
        kryo.register(PlayerSpellPacket.class);
        kryo.register(TargetType.class);
        kryo.register(PlayerUseSpellPacket.class);
        kryo.register(ChangeMapPacket.class);
        kryo.register(PlayerSpellUsedPacket.class);
        kryo.register(ObjectStats.class);
        kryo.register(EntityStats.class);
    }
}
