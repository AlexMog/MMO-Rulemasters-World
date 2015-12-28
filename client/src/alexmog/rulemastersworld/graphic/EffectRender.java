package alexmog.rulemastersworld.graphic;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import alexmog.rulemastersworld.effects.Effect;
import alexmog.rulemastersworld.events.EventsManager;

public class EffectRender {
	private static final float xPos = 980, yPos = 20, width = 40, height = 40;
	private Effect mEffect;
	private Rectangle mDescRect = new Rectangle(0, 0, 400, 50);

	public EffectRender(Effect effect) {
		mEffect = effect;
	}
	
	public void render(Graphics g, int order) {
		float x = (xPos - ((width + 3) * order));
		mEffect.getIcon().draw(x, yPos, width, height);
		g.drawString("" + mEffect.getStacks(), x, yPos);
		g.drawString((mEffect.isInfinite() ? "Inf." : "" + (mEffect.getTimer().remaning() / 1000)), x, yPos + 20);
		EventsManager e = EventsManager.getInstance();
		if (e.getAbsoluteMouseX() >= x && e.getAbsoluteMouseX() <= x + width
		        && e.getAbsoluteMouseY() >= yPos && e.getAbsoluteMouseY() <= yPos + height) {
		    mDescRect.setX(620);
		    mDescRect.setY(height + 25);
		    mDescRect.setWidth(400);
            g.setColor(Color.black);
		    g.fill(mDescRect);
            g.setColor(Color.white);
		    g.drawString(mEffect.getName(), mDescRect.getX(), mDescRect.getY() + 5);
		    if (mEffect.getDescription() != null) {
		        g.drawString(mEffect.getDescription(), mDescRect.getX(), mDescRect.getY() + 20);
		    }
		}
	}
	
	public void setEffect(Effect e) {
		mEffect = e;
	}
	
	public Effect getEffect() {
		return mEffect;
	}
	
}
