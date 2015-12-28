package alexmog.rulemastersworld;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.mozilla.javascript.Context;

import com.esotericsoftware.minlog.Log;

import alexmog.rulemastersworld.entity.Player;
import alexmog.rulemastersworld.gamemodes.GameMode;
import alexmog.rulemastersworld.jssandbox.SandboxContextFactory;
import alexmog.rulemastersworld.map.Map;
import alexmog.rulemastersworld.map.MapInstance;
import alexmog.rulemastersworld.packets.NewUserConnectedLobbyPacket;
import alexmog.rulemastersworld.server.PacketsInterpretator;

public class Game {
    private boolean mRunning = true;
    private long mPrevFrameTicks, mCurrFrameTicks, mWait = 1000 / 60, mDiff;
    private GameMode mGameMode;
    public static PacketsInterpretator mInterpretator = new PacketsInterpretator();
    private Map mMap;
    private Deque<MyEntry> mActions = new LinkedList<MyEntry>();
    private List<AccountConnection> mAccountList = new ArrayList<AccountConnection>();
    private MapInstance mMapInstance;
    private Context mContext;

    public Context getJSContext() {
        return mContext;
    }
    
    public Game(MapInstance mapInstance, Map map) {
        mMap = map;
        mMapInstance = mapInstance;
    }
    
    public void setGameMode(GameMode gameMode) {
        mGameMode = gameMode;
    }
    
    public void addAccount(AccountConnection account) {
        if (account == null) return;
        Log.info("Added account '" + account.getUsername() + "' to world '" + mMapInstance.getName() + "'(" + mMapInstance.getId() + ")");
        if (!mAccountList.contains(account)) {
            mAccountList.add(account);
            Player player = account.getPlayer();
            if (mGameMode.getEntityManager().getEntityById(player.getId()) != null) {
                player.setId(mGameMode.getEntityManager().generateNewId());
            }
            player.setToDelete(false);
            player.setGameMode(mGameMode);
            mGameMode.getEntityManager().addEntity(player);
            NewUserConnectedLobbyPacket p = new NewUserConnectedLobbyPacket();
            p.userId = account.getPlayer().getId();
            account.sendTCP(p);
        }
    }
    
    public void remAccount(AccountConnection account) {
        if (account == null) return;
        Log.info("Removed account '" + account.getUsername() + "' from world '" + mMapInstance.getName() + "'(" + mMapInstance.getId() + ")");
        mGameMode.getEntityManager().remEntity(account.getPlayer());
        mAccountList.remove(account);
    }
    
    public void sendToAllPlayers(Object o) {
        synchronized(mAccountList) {
            for (AccountConnection a : mAccountList) {
                try {
                    a.sendTCP(o);
                } catch (Exception e) {
                    Log.error("SendToAllPlayers", e);
                }
            }
        }
    }
    
    public Map getMap() {
        return mMap;
    }
    
    public Deque<MyEntry> getActions() {
        return mActions;
    }
    
    public void setRunning(boolean running) {
        mRunning = running;
    }
    
    public void init() throws Exception {
        mGameMode.init();
        Log.info("Initialisating JS context...");
        SandboxContextFactory contextFactory = new SandboxContextFactory();
        mContext = contextFactory.makeContext();
        contextFactory.enterContext(mContext);

    }
    
    public GameMode getGameMode() {
        return mGameMode;
    }
    
    public void update(long delta) {
        mGameMode.update(this, delta);
    }
    
    public void start() {
        mCurrFrameTicks = System.currentTimeMillis();
        while (mRunning) {
            mPrevFrameTicks = mCurrFrameTicks;
            mCurrFrameTicks = System.currentTimeMillis();
            
            try {
                // Executing packets received
                synchronized(mActions) {
                    while (!mActions.isEmpty()) {
                        MyEntry e = mActions.pop();
                        try {
                            mInterpretator.onPacketReceived(e.getConnection(), e.getPacket());
                        } catch (Exception ex) {
                            Log.error("PacketInterpretator", ex);
                        }
                    }
                }
                
                mDiff = mCurrFrameTicks - mPrevFrameTicks;
                
                if (mDiff != 0) {
                    try {
                        update(mDiff);
                    } catch (Exception ex) {
                        Log.error("GameUpdate", ex);
                    }
                }
                
                if (mDiff < mWait) {
                    try {
                        Thread.sleep(mWait - mDiff);
                    } catch (InterruptedException e) {
                        Log.error("GameSleep", e);
                    }
                }
            } catch (Exception e) {
                Log.error("Game", e);
            }
        }
    }
}
