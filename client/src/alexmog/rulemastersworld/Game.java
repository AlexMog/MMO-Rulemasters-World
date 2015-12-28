package alexmog.rulemastersworld;

import java.io.File;
import java.net.URL;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import TWLSlick.TWLStateBasedGame;
import alexmog.rulemastersworld.gamemodes.GameMode;
import alexmog.rulemastersworld.scenes.GameScene;
import alexmog.rulemastersworld.scenes.IngameLoadingScene;
import alexmog.rulemastersworld.scenes.LoadingState;
import alexmog.rulemastersworld.scenes.LoginScene;
import alexmog.rulemastersworld.scenes.MainScene;
import alexmog.rulemastersworld.scenes.OptionsScene;
import alexmog.rulemastersworld.scenes.ServerListScene;

public class Game extends TWLStateBasedGame {
    private GameMode mGameMode = null;
    public static IngameLoadingScene ingameLoadingScene;
    
    public Game(String name, GameMode g) {
        this(name);
        mGameMode = g;
    }

    public Game(String name) {
        super(name);
        mGameMode = new GameMode();
    }
    
    public GameMode getGameMode() {
        return mGameMode;
    }

    @Override
    public void initStatesList(GameContainer arg0) throws SlickException {
        LoadingState s = new LoadingState();
        s.setGameMode(mGameMode);
        ingameLoadingScene = new IngameLoadingScene();
        addState(s);
        addState(new GameScene(mGameMode));
        addState(new MainScene());
        addState(new OptionsScene());
        addState(new LoginScene());
        addState(ingameLoadingScene);
        addState(new ServerListScene());
        enterState(0);
    }

    @Override
    protected URL getThemeURL() {
        try {
            return new File("./res/theme/theme.xml").toURI().toURL();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
