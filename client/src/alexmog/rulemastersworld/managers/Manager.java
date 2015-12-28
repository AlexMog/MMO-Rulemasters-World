package alexmog.rulemastersworld.managers;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Manager {
	public abstract void update(GameContainer container, StateBasedGame game, int delta);
	public abstract void render(GameContainer container, StateBasedGame game, Graphics g);
}
