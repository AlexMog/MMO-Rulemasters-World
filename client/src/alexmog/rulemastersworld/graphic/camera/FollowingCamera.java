package alexmog.rulemastersworld.graphic.camera;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.entity.Entity;
import alexmog.rulemastersworld.gamemodes.GameMode;

public class FollowingCamera extends Camera {
	private static final float FREE_SQUARE_RATIO = 0.05f;
	
	private Entity mTarget;
	private Vector2f mCurrentPosition;
	
	public FollowingCamera(GameContainer container, Entity target) {
		super(container);
		mTarget = target;
		mCurrentPosition = target.getPosition().copy();
	}
	
	
	public void setTarget(Entity target) {
		mTarget = target;
	}

	@Override
	public void update(GameMode game, GameContainer container, int delta) {
	    if (mTarget == null) {
	        mTarget = Main.game.getGameMode().getEntitiesManager().getPlayerEntity();
            return;
	    }
		Vector2f targetPos = mTarget.getPosition();
		float diffX = mCurrentPosition.x - targetPos.x;
		float diffY = mCurrentPosition.y - targetPos.y;
		float maxX = container.getWidth() / 2 * FREE_SQUARE_RATIO;
		float maxY = container.getHeight() / 2 * FREE_SQUARE_RATIO;
		
		if (diffX > maxX) {
			mCurrentPosition.x = targetPos.x + maxX;
		} else if (diffX < -maxX) {
			mCurrentPosition.x = targetPos.x - maxX;
		}
		if (diffY > maxY) {
			mCurrentPosition.y = targetPos.y + maxY;
		} else if (diffY < -maxY) {
			mCurrentPosition.y = targetPos.y - maxY;
		}
	}

	@Override
	public Vector2f getLookedPosition() {
		return (mCurrentPosition);
	}

}
