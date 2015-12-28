package alexmog.rulemastersworld.graphic.camera;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.entity.Entity;
import alexmog.rulemastersworld.gamemodes.GameMode;

public class MoronicCamera extends Camera {
	private final Vector2f mStaredPoint;
	
	public MoronicCamera(GameContainer container, float x, float y) {
		super(container);
		mStaredPoint = new Vector2f(x, y);
	}
	
	public MoronicCamera(GameContainer container, Vector2f point) {
		super(container);
		mStaredPoint = new Vector2f(point);
	}

	
	@Override
	public Vector2f getLookedPosition() {
		return mStaredPoint;
	}

	@Override
	public void update(GameMode game, GameContainer container, int delta) {
		Entity player = game.getEntitiesManager().getPlayerEntity();
		
		if (player != null) {
			game.setCamera(new FollowingCamera(container, player));
		}
	}
}
