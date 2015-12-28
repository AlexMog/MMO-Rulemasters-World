package alexmog.rulemastersworld.packets;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import alexmog.rulemastersworld.packets.hub.CreateGamePacket;
import alexmog.rulemastersworld.packets.hub.GameLobbyJoin;
import alexmog.rulemastersworld.packets.hub.GameLobbyNumberPacket;
import alexmog.rulemastersworld.packets.hub.GameLobbyPacket;
import alexmog.rulemastersworld.packets.tchat.TchatPrivmsgPacket;

public class HubNetwork {
    public static final int port = 4242;
//    public static final int udpPort = 4244;
    public static final String host = "labs-epimars.eu";
    public static int version = 0015;
    
    public static void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(ConnectPacket.class);
        kryo.register(NewUserConnectedLobbyPacket.class);
        kryo.register(TchatPrivmsgPacket.class);
        kryo.register(ErrorPacket.class);
        kryo.register(ErrorPacket.ErrorType.class);
        // Game lobby packets
        kryo.register(GameLobbyJoin.class);
        kryo.register(CreateGamePacket.class);
        kryo.register(GameLobbyNumberPacket.class);
        kryo.register(GameLobbyPacket.class);
    }
}
