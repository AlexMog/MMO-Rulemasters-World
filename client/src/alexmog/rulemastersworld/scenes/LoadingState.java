package alexmog.rulemastersworld.scenes;

import java.awt.Font;
import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import alexmog.rulemastersworld.datas.ConfigsManager;
import alexmog.rulemastersworld.datas.DatasManager;
import alexmog.rulemastersworld.datas.ResourcesManager;
import alexmog.rulemastersworld.gamemodes.GameMode;

public class LoadingState extends BackgroundPlanetScene {
    private int mRemaining, mTotal;
    private GameMode mGameMode = null;
    private DeferredResource mResource;
    
    public LoadingState() {
        
    }
    
    public void setGameMode(GameMode g) {
        mGameMode = g;
    }

    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
        ConfigsManager.getInstance().applicateConfigs();
    	LoadingList.setDeferredLoading(true);
        
        DatasManager.getInstance().addFile("LivingEntity.name", new TrueTypeFont(new Font("Verdana", Font.BOLD, 12), true));
        DatasManager.getInstance().addFile("TextEffect.damages", new TrueTypeFont(new Font("Verdana", Font.BOLD, 12), true));
        DatasManager.getInstance().addFile("TextEffect.status", new TrueTypeFont(new Font("Verdana", Font.BOLD, 14), true));
        
        DatasManager.getInstance().addFile("MouseCursor.default", new Image("res/images/cursors/default.png"));
        
        DatasManager.getInstance().addMap("test.tmx");
        try {
            ResourcesManager.getInstance().loadResources();
        } catch (Exception e) {
            throw new SlickException(e.getMessage());
        }
        super.init(container, game);
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException {
    	super.render(container, game, g);
        g.drawString("Loading...", 200, 200);
        g.drawString((mTotal - mRemaining) + "/" + mTotal, 200, 220);
    }

    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException {
    	super.update(container, game, delta);
        if (mResource != null) {
            try {
                mResource.load();
            } catch (IOException e) {
                throw new SlickException("Failed to load: " + mResource.getDescription(), e);
            }
            
            mResource = null;
        }
        
        mRemaining = LoadingList.get().getRemainingResources();
        mTotal = LoadingList.get().getTotalResources();
        if (LoadingList.get().getRemainingResources() > 0) {
            mResource = LoadingList.get().getNext();
        } else {
            ((Music)DatasManager.getInstance().getFile("Music.menu")).loop();
            game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
    }

    @Override
    public int getID() {
        return 0;
    }

}
