package alexmog.rulemastersworld.graphic.camera;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.gamemodes.GameMode;

public abstract class Camera {
	private GameContainer mContainer;
	
	public Camera(GameContainer container) {
		mContainer = container;
	}
	
	
	public abstract void update(GameMode game, GameContainer container, int delta);
	public abstract Vector2f getLookedPosition();
	
	public Vector2f getAbsoluteLookedPosition() {
		Vector2f position = this.getLookedPosition().copy();
		
		position.x = -position.x + mContainer.getWidth() / 2;
		position.y = -position.y + mContainer.getHeight() / 2;
		
		return position;
	}
	
	public void translate(Graphics g) {
		Vector2f position = this.getAbsoluteLookedPosition();
		g.translate(position.x, position.y);
	}
	
	public void untranslate(Graphics g) {
		Vector2f position = this.getAbsoluteLookedPosition();
		g.translate(-position.x, -position.y);
	}
}
