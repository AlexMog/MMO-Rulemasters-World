package alexmog.rulemastersworld.managers;

import alexmog.rulemastersworld.Game;
import alexmog.rulemastersworld.gamemodes.GameMode;

public abstract class Manager {
    protected GameMode mGameMode;
    
    
    public Manager(GameMode gameMode) {
        mGameMode = gameMode;
    }
    
    public abstract void update(Game game, long delta);
}
