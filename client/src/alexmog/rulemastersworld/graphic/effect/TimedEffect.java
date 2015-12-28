package alexmog.rulemastersworld.graphic.effect;

import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.util.Timer;

public abstract class TimedEffect extends Effect {
	protected Timer mTimer;
	
	public TimedEffect(Vector2f position, int time) {
		super(position);
		mTimer = new Timer(time);
	}
	

	@Override
	public void update(int delta) {
		mTimer.update(delta);
		if (mTimer.isFinished()) {
			mToDelete = true;
		}
	}

}
