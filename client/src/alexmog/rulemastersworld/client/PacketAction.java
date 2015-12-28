package alexmog.rulemastersworld.client;

import com.esotericsoftware.kryonet.Client;

public interface PacketAction {
    public void run(Object packet, Client client);
}
