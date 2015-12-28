package alexmog.rulemastersworld.gamemodes;

import java.util.Random;

import org.mozilla.javascript.Context;

import alexmog.rulemastersworld.Game;
import alexmog.rulemastersworld.managers.EntityManager;

public class GameMode {
    protected EntityManager mEntityManager;
    public static Random mRand = new Random();
    private Game mGameInstance;
    
    public GameMode(Game gameInstance) {
        mGameInstance = gameInstance;
    }
    
    public Game getGameInstance() {
        return mGameInstance;
    }
    
    public EntityManager getEntityManager() {
        return mEntityManager;
    }
    
    public Context getContext() {
        return mGameInstance.getJSContext();
    }
    
    public void init() {
        mEntityManager = new EntityManager(this);
/*        RandomEntity toSpawn = new RandomEntity(new Vector2f(500, 500), new Vector2f(64, 64),
                10, this);
        toSpawn.setEnd(20);
        toSpawn.setSpeed(1.5f);
        toSpawn.setSkinId(1);
        SpawnerEntity spawner = new SpawnerEntity(new Vector2f(500, 500), new Vector2f(32, 32),
                this, toSpawn, 60000);
        spawner.setSkinId(1);
        mEntityManager.addEntity(spawner);*/
    }
    
    public void update(Game game, long delta) {
        mEntityManager.update(game, delta);
    }

}
