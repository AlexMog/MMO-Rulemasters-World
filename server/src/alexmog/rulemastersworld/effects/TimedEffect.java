package alexmog.rulemastersworld.effects;

import alexmog.rulemastersworld.gamemodes.GameMode;
import alexmog.rulemastersworld.util.Timer;

public abstract class TimedEffect extends Effect {
    private Timer mTimer;
    protected long mTimerValue;

    public TimedEffect(int id, GameMode gameMode, String name, long l) {
        super(gameMode, name, id);
        mTimer = new Timer(l);
        mTimerValue = l;
    }
    
    public Timer getTimer() {
        return mTimer;
    }
    
    /**
     * Used when the duration of the effect is finished
     */
    public abstract void onTimerFinished();
    
    @Override
    public void update(long delta) {
        if (!mInfinite) {
            mTimer.update(delta);
            if (mTimer.isFinished()) {
                onTimerFinished();
                mEntity.removeEffect(this);
            }
        }
        super.update(delta);
    }
    
    @Override
    public abstract Effect clone();

}
