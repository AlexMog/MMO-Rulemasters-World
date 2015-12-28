package alexmog.rulemastersworld.client;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.datas.DatasManager;

public class ClientListener extends Listener {
    private Client mClient;
    
    public ClientListener(Client client) {
        mClient = client;
    }
    
    @Override
    public void connected(Connection connection) {
        Main.loginFrame.setStatus("Authentification en cours...");
/*        ConnectPacket packet = new ConnectPacket();
        packet.username = mUsername;
        packet.password = mPassword;
        mClient.sendTCP(packet);*/
    }
    
    @Override
    public void received(Connection connection, Object packet) {
        DatasManager.getInstance().addAction(packet);
    }
    
    @Override
    public void disconnected(Connection connection) {
        JOptionPane.showMessageDialog(null, "Connection perdue avec le serveur.", "Network Error", 0);
    }
}
