package alexmog.rulemastersworld.scenes;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.esotericsoftware.minlog.Log;

import TWLSlick.RootPane;
import alexmog.rulemastersworld.datas.Translator;
import alexmog.rulemastersworld.scenes.serverlist.ServerListWidget;
import alexmog.rulemastersworld.util.HttpUtils;

public class ServerListScene extends BackgroundPlanetScene {
    private ServerListWidget mWidget;
    private boolean mNeedUpdate = false;

    @Override
    protected RootPane createRootPane() {
        RootPane rp = super.createRootPane();
        mWidget = new ServerListWidget(this);
        rp.add(mWidget);
        
        return rp;
    }
    
    @Override
    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException {
        super.enter(container, game);
        mWidget.reset();
        new Thread(new Runnable() {
            
            public void run() {
/*                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }*/
                Log.info("Loading server list...");
                try {
                    for (ServerListWidget.ServerContainer s : HttpUtils.getServersList()) {
                        mWidget.addServer(s.name, s.ip, s.port);
                    }
                    mWidget.setStatus(Translator.getInstance().getTranslated("ServersScreen.serverslist"));
                    mWidget.addReturnButton();
                } catch (IOException e) {
                    Log.info("Error on getting list", e);
                    mWidget.setStatus("Error.");
                    mWidget.addReturnButton();
                }
                mNeedUpdate = true;
            }
        }).start();
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException {
        if (mNeedUpdate) {
            mWidget.update();
            mNeedUpdate = false;
        }
        super.update(container, game, delta);
    }
    
    @Override
    public int getID() {
        return 6;
    }

    public void reset() {
        if (mWidget != null) {
            mWidget.reset();
        }
    }

}
