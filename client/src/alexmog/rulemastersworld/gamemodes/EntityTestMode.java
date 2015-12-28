package alexmog.rulemastersworld.gamemodes;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import alexmog.rulemastersworld.entity.Entity;

public class EntityTestMode extends GameMode {
    private Entity mEntity;
    
    public EntityTestMode(Entity entity) {
        mEntity = entity;
    }

    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
        mEntity.setPosition(new Vector2f(300, 300));
        mEntity.setGameMode(this);
        mEntitiesManager.addEntity(mEntity);
        super.init(container, game);
    }
}
