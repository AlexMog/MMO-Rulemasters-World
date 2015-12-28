package alexmog.rulemastersworld.map;

import java.io.File;

import com.esotericsoftware.minlog.Log;

import alexmog.rulemastersworld.Game;
import alexmog.rulemastersworld.gamemodes.GameMode;

public class MapInstance {
    private Map mMap;
    private String mMapPath, mMapName;
    private Game mGameInstance;
    private Thread mThread;
    private String mClientFilePath;
    private int mId;
    
    public MapInstance(int id, String mapName, String mapPath, String clientFile) {
        mMapPath = mapPath;
        mMapName = mapName;
        mClientFilePath = clientFile;
        mId = id;
    }
    
    public String getClientFilePath() {
        return mClientFilePath;
    }
    
    public String getName() {
        return mMapName;
    }
    
    public void init() throws Exception {
        mMap = new Map(new File(mMapPath));
        mGameInstance = new Game(this, mMap);
        mGameInstance.setGameMode(new GameMode(mGameInstance)); // TODO add the choice to change game mode
    }
    
    public Map getMap() {
        return mMap;
    }
    
    public Game getGameInstance() {
        return mGameInstance;
    }
    
    public void start() {
        (mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mGameInstance.init();
                    mGameInstance.start();
                } catch (Exception e) {
                    Log.error("GameInstanceStart", e);
                }
            }
        }, mMapName + "Thread")).start();
    }

    public void stop() {
        if (mThread != null) {
            mGameInstance.setRunning(false);
            try {
                mThread.join();
            } catch (InterruptedException e) {
                Log.info("Thread" + getName(), e);
            }
        }
    }

    public int getId() {
        return mId;
    }
}
