package alexmog.rulemastersworld.effects;

import alexmog.rulemastersworld.gamemodes.GameMode;

public abstract class TicEffect extends TimedEffect {
    protected int mTicTimer = 0;
    protected int mTicMax = 0;
    
    public TicEffect(int id, GameMode gameMode, String name, int ticMax, long l) {
        super(id, gameMode, name, l);
        mTicMax = ticMax;
    }
    
    /**
     * Used when the tic timer is out
     */
    public abstract void onTimerOut();
    
    public void setTicTimer(int ticTimer) {
        mTicTimer = ticTimer;
    }
    
    @Override
    public void update(long delta) {
        if (mTicTimer >= mTicMax) {
            onTimerOut();
            mTicTimer = 0;
        }
        mTicTimer += delta;
        super.update(delta);
    }

    public int getTicTimer() {
        return mTicTimer;
    }
}
