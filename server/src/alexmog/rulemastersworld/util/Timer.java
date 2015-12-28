package alexmog.rulemastersworld.util;

public class Timer {
    private long mTimer;
    
    public Timer(long l) {
    	mTimer = l;
    }

    public void update(long elapsedTime) {
    	mTimer -= elapsedTime;
    }
    
    public long remaning() {
        return mTimer;
    }
    
    public boolean isFinished() {
    	return (mTimer <= 0);
    }
}
