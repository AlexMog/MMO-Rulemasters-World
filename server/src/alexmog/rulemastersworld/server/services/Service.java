package alexmog.rulemastersworld.server.services;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.esotericsoftware.minlog.Log;

public class Service implements IService {
    private Deque<Runnable> mActionMaps = new LinkedList<Runnable>();
    private Lock mLock = new ReentrantLock();
    private Condition mCondVar = mLock.newCondition();
    private boolean mRunning = true;
    
    public void addAction(Runnable r) {
        mLock.lock();
        try {
            mActionMaps.push(r);
            mCondVar.signalAll();
        } finally {
            mLock.unlock();
        }
    }
    
    public void stopRun() {
        mLock.lock();
        try {
            mRunning = false;
            mCondVar.signalAll();
        } finally {
            mLock.unlock();
        }
    }

    public void run() {
        while (mRunning) {
            mLock.lock();
            try {
            	if (mActionMaps.isEmpty()) {
            		mCondVar.await();
            	}
                while (mRunning && !mActionMaps.isEmpty()) {
                    mActionMaps.pop().run();
                }
            } catch (InterruptedException e) {
                Log.error("Service", e);
            } finally {
                mLock.unlock();
            }
        }
        
    }
}
