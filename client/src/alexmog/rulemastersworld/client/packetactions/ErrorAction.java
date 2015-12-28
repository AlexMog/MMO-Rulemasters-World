package alexmog.rulemastersworld.client.packetactions;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Client;

import alexmog.rulemastersworld.packets.ErrorPacket;
import alexmog.rulemastersworld.packets.ErrorPacket.ErrorType;
import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.client.PacketAction;

public class ErrorAction implements PacketAction {

    public void run(Object packet, Client client) {
        ErrorPacket p = (ErrorPacket) packet;
        if (p.status == ErrorType.AUTHENTICATION_FAILED) {
            Main.loginFrame.setStatus("");
            Main.loginFrame.enableLogin();
        }
        JOptionPane.showMessageDialog(null, "Le serveur vous envois un message d'erreur:\n" + p.message, "Server Error", 0);
    }

}
