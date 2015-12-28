package alexmog.rulemastersworld.scenes;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import TWLSlick.BasicTWLGameState;
import TWLSlick.RootPane;
import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.client.PacketsInterpretator;
import alexmog.rulemastersworld.datas.DatasManager;
import alexmog.rulemastersworld.events.EventsManager;
import alexmog.rulemastersworld.gamemodes.GameMode;
import alexmog.rulemastersworld.scenes.debug.CollisionsGraph;
import alexmog.rulemastersworld.scenes.debug.EntityGraph;
import alexmog.rulemastersworld.scenes.debug.MsGraph;
import alexmog.rulemastersworld.scenes.debug.PingGraph;
import alexmog.rulemastersworld.scenes.debug.RamGraph;
import alexmog.rulemastersworld.scenes.gamescene.ChatFrame;

public class GameScene extends BasicTWLGameState {
	private GameMode mGameMode;
	private PacketsInterpretator mInterpretator = new PacketsInterpretator(Main.client);
	// Used for debug mode
	private Runtime mRuntime = Runtime.getRuntime();
	private int mMb = 1024 * 1024;
	public static ChatFrame chat;
	public static PingGraph ping;
	
	public GameScene(GameMode mode) {
	    mGameMode = mode;
	}
	
	@Override
	public void keyReleased(int key, char c) {
	    EventsManager.getInstance().onGameKeyReleased(key, c);
	    super.keyReleased(key, c);
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game)
	        throws SlickException {
	    mGameMode.reset();
//	    container.setMouseGrabbed(true);
	    super.enter(container, game);
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game)
	        throws SlickException {
//	    container.setMouseGrabbed(false);
	    super.leave(container, game);
	}
	
	@Override
	protected RootPane createRootPane() {
	    RootPane rp = super.createRootPane();
	    chat = new ChatFrame();
	    ping = new PingGraph();
	    rp.add(new MsGraph());
	    rp.add(new RamGraph());
	    rp.add(new EntityGraph());
	    rp.add(new CollisionsGraph());
	    rp.add(ping);
	    rp.add(chat);
	    
	    return rp;
	}
	
	@Override
	public void keyPressed(int key, char c) {
	    EventsManager.getInstance().onGameKeyPressed(key, c);
	    super.keyPressed(key, c);
	}
	
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
        mGameMode.init(container, game);
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException {
//    	mCamera.render(container, game, g);
        mGameMode.render(container, game, g);
    	g.drawString("Used memory: " + ((mRuntime.totalMemory() - mRuntime.freeMemory()) / mMb), 10, 36);
    	g.drawString("Free memory: " + (mRuntime.freeMemory() / mMb), 10, 48);
    	g.drawString("Total memory: " + (mRuntime.totalMemory() / mMb), 10, 60);
    }

    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException {
        // Execute getted packets
        Object action;
//        DatasManager.getInstance().sortActions();
//        System.out.println(DatasManager.getInstance().getActionsList().size());
        while ((action = DatasManager.getInstance().getNextAction()) != null) {
            try {
                mInterpretator.onPacketReceived(action);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        mGameMode.update(container, game, delta);
        // Seems to reduce input lags
//        GL11.glGetError();          // this call will burn the time between vsyncs
//        Display.processMessages();  // process new native messages since Display.update();
//        Mouse.poll();               // now update Mouse events
//        Keyboard.poll();            // and Keyboard too
    }

    @Override
    public int getID() {
        return 2;
    }

}
