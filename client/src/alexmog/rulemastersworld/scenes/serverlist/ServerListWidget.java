package alexmog.rulemastersworld.scenes.serverlist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import alexmog.rulemastersworld.packets.ConnectPacket;
import alexmog.rulemastersworld.packets.Network;
import alexmog.rulemastersworld.Game;
import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.datas.Translator;
import alexmog.rulemastersworld.frames.LoginFrame;
import alexmog.rulemastersworld.scenes.ServerListScene;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.DialogLayout;
import de.matthiasmann.twl.Label;

public class ServerListWidget extends DialogLayout {
    private final Label lblStatus;
    private final ServerListScene mScene;
    private Button mReturnButton;
    private Group mHorizontal, mVertical;
    private List<ServerContainer> mServers = new ArrayList<ServerContainer>();

    public class ServerContainer {
        public String name;
        public String ip;
        public int port;
    }
    
    public ServerListWidget(ServerListScene loginScene) {
        mScene = loginScene;
        setTheme("loginscenewidget");
        
        lblStatus = new Label("");
        
        mReturnButton = new Button(Translator.getInstance().getTranslated("ServersScreen.returntomenu"));
        mReturnButton.addCallback(new Runnable() {
            
            public void run() {
                mScene.getGame().enterState(4);
            }
        });
        
        mHorizontal = createParallelGroup();
        mVertical = createSequentialGroup();
        setHorizontalGroup(mHorizontal);
        setVerticalGroup(mVertical);
        reset();
    }

    @Override
    protected void layout() {
        // login panel is centered
        adjustSize();
        setPosition((getParent().getWidth() - getWidth()) / 2,
                (getParent().getHeight() - getHeight()) / 2);
    }

    public void setStatus(String status) {
        lblStatus.setText(status);
    }

    public void addReturnButton() {
        mReturnButton.setEnabled(true);
    }
    
    public void removeReturnButton() {
        mReturnButton.setEnabled(false);
    }
    
    public void addServer(String name, String ip, int port) {
        ServerContainer s = new ServerContainer();
        s.ip = ip;
        s.port = port;
        s.name = name;
        mServers.add(s);
    }
    
    public void reset() {
        mServers.clear();
        removeReturnButton();
        lblStatus.setText(Translator.getInstance().getTranslated("ServersScreen.loading"));
        update();
    }

    public void update() {
        
        mHorizontal.clear(true);
        mVertical.clear(true);
        mHorizontal.addWidget(lblStatus);
        
        mVertical.addWidget(lblStatus);

        for (final ServerContainer s : mServers) {
            Button b = new Button(s.name);
            
            b.addCallback(new Runnable() {
                private int port = s.port;
                private String ip = s.ip;
                
                public void run() {
                    Network.host = ip;
                    Network.port = port;
                    connect();
                }
            });
            
            mHorizontal.addWidget(b);
            mVertical.addWidget(b);
        }
        
        mHorizontal.addWidget(mReturnButton);
        mVertical.addWidget(mReturnButton);
        lblStatus.getParent().invalidateLayout();
        invalidateLayout();
    }
    
    private void connect() {
        mScene.getGame().enterState(5);

        new Thread(new Runnable() {
            
            public void run() {
                Game.ingameLoadingScene.reset();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    if (Main.client.isConnected()) {
                        Main.client.close();
                        Main.client.stop();
                    }
                    Main.client.start();
                    Main.client.connect(5000, Network.host, Network.port);
                } catch (IOException e1) {
                    e1.printStackTrace();
                    Game.ingameLoadingScene.setStatus(Translator.getInstance().getTranslated("ServersScreen.cannotconnect"));
                    Game.ingameLoadingScene.addReturnButton();
                    return;
                }
                ConnectPacket p = new ConnectPacket();
                p.version = Network.version;
                p.token = LoginFrame.token;
                Main.client.sendTCP(p);
                Game.ingameLoadingScene.setLoaded(true);
            }
        }).start();
    }

}
