package alexmog.rulemastersworld.server.services;

import com.esotericsoftware.minlog.Log;

public abstract class TimerService implements IService {
    private boolean mRunning = true;
    private final long mSleepTime;
    private long mCurrFrameTicks;
    private long mPrevFrameTicks;
    
    public TimerService(long sleepTime) {
        mSleepTime = sleepTime;
    }
    
    public abstract void onTimerOut();
    
    @Override
    public void run() {
        mCurrFrameTicks = System.currentTimeMillis();
        while (mRunning) {
            mPrevFrameTicks = mCurrFrameTicks;
            mCurrFrameTicks = System.currentTimeMillis();
            long mDiff = mCurrFrameTicks - mPrevFrameTicks;
            
            if (mDiff > 5000) {
                onTimerOut();
            }
            
            if (mDiff < mSleepTime) {
                try {
                    Thread.sleep(mSleepTime - mDiff);
                } catch (InterruptedException e) {
                    Log.error("TimerSleep", e);
                }
            }
        }
    }

    @Override
    public synchronized void stopRun() {
        mRunning = false;
    }

}
