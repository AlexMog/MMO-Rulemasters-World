package alexmog.rulemastersworld.graphic.effect;

import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.graphic.Renderable;

public abstract class Effect implements Renderable {
	protected Vector2f mPosition;
	protected boolean mToDelete = false;
	
	public Effect(Vector2f position) {
		mPosition = new Vector2f(position);
	}
	
	
	public boolean isToDelete() {
		return mToDelete;
	}
	
	public abstract void update(int delta);
}
