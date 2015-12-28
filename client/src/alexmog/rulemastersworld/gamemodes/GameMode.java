package alexmog.rulemastersworld.gamemodes;

import java.util.Random;

import org.mozilla.javascript.Context;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.entity.MouseEntity;
import alexmog.rulemastersworld.events.EventsManager;
import alexmog.rulemastersworld.graphic.camera.Camera;
import alexmog.rulemastersworld.graphic.camera.MoronicCamera;
import alexmog.rulemastersworld.gui.GameGuiManager;
import alexmog.rulemastersworld.managers.EffectsManager;
import alexmog.rulemastersworld.managers.EntitiesManager;
import alexmog.rulemastersworld.managers.PlayerSkillsManager;
import alexmog.rulemastersworld.map.Map;
import alexmog.rulemastersworld.scenes.GameScene;

public class GameMode {
    protected EntitiesManager mEntitiesManager = new EntitiesManager();
    protected EffectsManager mEffectsManager = new EffectsManager();
    public GameGuiManager mGuiManager = new GameGuiManager();
    protected PlayerSkillsManager mPlayerSkillsManager = new PlayerSkillsManager();
    public Random mRand = new Random();
    private Context mContext;
    private Camera mCamera;
    private int mPingTimeout = 0;
    private boolean mDebug = true;
    private GameContainer mGameContainer;
    private MouseEntity mMouse;
    
    public void setDebug(boolean debug) {
        mDebug = debug;
    }
    
    public GameMode() {
    }
    
    public void reset() {
        mEntitiesManager.reset();
        mEffectsManager.reset();
        mGuiManager.reset();
        mPlayerSkillsManager.reset();
        MouseEntity mouse = new MouseEntity(new Vector2f(), new Vector2f(5, 5), this);
        mouse.setId(-1);
        mMouse = mouse;
        mEntitiesManager.addEntity(mouse);
    }
    
    public EntitiesManager getEntitiesManager() {
        return mEntitiesManager;
    }
    
    public EffectsManager getEffectsManager() {
        return mEffectsManager;
    }
    
    public PlayerSkillsManager getPlayerSkillsManager() {
    	return mPlayerSkillsManager;
    }
    
    public GameGuiManager getGameGuiManager() {
    	return mGuiManager;
    }
    
    public Context getContext() {
        return mContext;
    }
    
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
        EventsManager.getInstance().setInput(container.getInput());
//        p = new Player(new Vector2f(50, 50), new Vector2f(32, 48), 5, this);
//        mEntityManager.addEntity(p);
//        p.setEnd(1);
//        addEntitySkin("body/Female/WhiteElf.png"); // TEMPORARY
        mCamera = new MoronicCamera(container, 0, 0);
        
/*        for (String skin : mEntitySkins) {
            String imgP = SkinCreator.IMAGES_PATH.substring(1) + skin;
            System.out.println("Image to load: " + imgP);
            Image img = new Image(imgP);
            DatasManager.getInstance().addFile(imgP, new SpriteSheet(img, img.getWidth() / 3, img.getHeight() / 4));
        }*/
        mGuiManager.init();
    }
    
    public void setCamera(Camera camera) {
    	mCamera = camera;
    }
    
    public Camera getCamera() {
    	return mCamera;
    }
    
    public void update(GameContainer container, StateBasedGame game, int delta) {
        mGameContainer = container;
    	mCamera.update(this, container, delta);
        mEntitiesManager.update(container, game, delta);
        mEffectsManager.update(container, game, delta);
        EventsManager.getInstance().update(delta);
        mGuiManager.update(container, game, delta);
        mPlayerSkillsManager.update(container, game, delta);
        if (mPingTimeout > 2000) {
            Main.client.updateReturnTripTime();
            GameScene.ping.addPoint(Main.client.getReturnTripTime());
            mPingTimeout = 0;
        }
        
        if (!Main.client.isConnected()) {
            game.enterState(1);
        }
        mPingTimeout += delta;
    }
    
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
    	mCamera.translate(g);
    	{
    		Map.access().renderBackground(g);
            mEntitiesManager.render(container, game, g);
            Map.access().renderForeground(g);
            mEffectsManager.render(container, game, g);
    	}
    	mMouse.renderEnd(g);
    	mCamera.untranslate(g);
        g.drawString("Entities: " + mEntitiesManager.getEntityCount(), 10, 25);
        g.drawString("Collisions checked: " + mEntitiesManager.getCollisionsCheckNumber(), 10, 72);
        //g.drawString("Connected: " + Main.client.isConnected(), 10, 84);
        g.drawString("Ping: " + Main.client.getReturnTripTime(), 10, 84);
        mGuiManager.render(container, game, g);
    }
    
    public boolean isDebug() {
        return mDebug;
    }

    public GameContainer getContainer() {
        return mGameContainer;
    }

}
