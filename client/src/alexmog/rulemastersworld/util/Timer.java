package alexmog.rulemastersworld.util;

public class Timer {
    private long mTimer;
    
    public Timer(long cooldown) {
    	mTimer = cooldown;
    }

    public void update(int elapsedTime) {
    	mTimer -= elapsedTime;
    }
    
    public void setRemaining(int time) {
    	mTimer = time;
    }
    
    public long remaning() {
        return mTimer;
    }
    
    public boolean isFinished() {
    	return (mTimer <= 0);
    }
}
