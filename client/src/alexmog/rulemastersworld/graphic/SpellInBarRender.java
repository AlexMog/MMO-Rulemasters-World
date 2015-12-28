package alexmog.rulemastersworld.graphic;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import alexmog.rulemastersworld.events.EventsManager;
import alexmog.rulemastersworld.skills.Skill;

public class SpellInBarRender {
	private static final float xPos = 980, yPos = 710, width = 40, height = 40;
	private Skill mSkill;
	private Rectangle mDescRect = new Rectangle(0, 0, 400, 50);
	private Rectangle mBounds = new Rectangle(0, 0, width, height);
	private float x;

	public SpellInBarRender(Skill skill) {
		mSkill = skill;
		mDescRect.setX(620);
	    mDescRect.setY(yPos - 55);
	    mDescRect.setWidth(400);
		mBounds.setY(yPos);
	}
	
	public void update(int order) {
		x = (xPos - ((width + 3) * order));
		mBounds.setX(x);
		if (Skill.getSelectedSkill() != mSkill
				&& EventsManager.getInstance().getMouseButton(0).isJustClicked()
				&& mBounds.contains(EventsManager.getInstance().getAbsoluteMouseX(), 
						EventsManager.getInstance().getAbsoluteMouseY())) {
			mSkill.select();
		}
	}
	
	public void render(Graphics g, int order) {
		mSkill.getIcon().draw(x, yPos, width, height);
        g.drawString("" + (mSkill.getNextUsage() / 1000), x, yPos);
        g.drawString(Input.getKeyName(mSkill.getKeySpell()), x, yPos + 20);
		EventsManager e = EventsManager.getInstance();
		if (e.getAbsoluteMouseX() >= x && e.getAbsoluteMouseX() <= x + width
		        && e.getAbsoluteMouseY() >= yPos && e.getAbsoluteMouseY() <= yPos + height) {
		    g.setColor(Color.black);
		    g.fill(mDescRect);
            g.setColor(Color.white);
		    g.drawString(mSkill.getName(), mDescRect.getX(), mDescRect.getY() + 5);
		    if (mSkill.getDescription() != null) {
		        g.drawString(mSkill.getDescription(), mDescRect.getX(), mDescRect.getY() + 20);
		    }
		}
	}
	
	public void setSkill(Skill e) {
		mSkill = e;
	}
	
	public Skill getSkill() {
		return mSkill;
	}
	
}
