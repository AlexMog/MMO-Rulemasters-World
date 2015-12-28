package alexmog.rulemastersworld.client;

import com.esotericsoftware.kryonet.Client;

import alexmog.rulemastersworld.packets.ConnectPacket;
import alexmog.rulemastersworld.packets.ErrorPacket;
import alexmog.rulemastersworld.packets.NewUserConnectedLobbyPacket;
import alexmog.rulemastersworld.packets.tchat.TchatPrivmsgPacket;
import alexmog.rulemastersworld.client.packetactions.ConnectAction;
import alexmog.rulemastersworld.client.packetactions.ErrorAction;
import alexmog.rulemastersworld.client.packetactions.NewUserConnectedLobbyAction;
import alexmog.rulemastersworld.client.packetactions.TchatPrivmsgAction;

public class HubPacketsInterpretator extends PacketsInterpretator {
    
    public HubPacketsInterpretator(Client client) {
        super(client);
        mPackets.put(ConnectPacket.class, new ConnectAction());
        mPackets.put(ErrorPacket.class, new ErrorAction());
        mPackets.put(NewUserConnectedLobbyPacket.class, new NewUserConnectedLobbyAction());
        mPackets.put(TchatPrivmsgPacket.class, new TchatPrivmsgAction());
    }
}
