package alexmog.rulemastersworld.managers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.esotericsoftware.minlog.Log;

import alexmog.rulemastersworld.entity.Player;

public class PlayerCacheManager {
    private static PlayerCacheManager instance = new PlayerCacheManager();
    private Map<Integer, Player> mCachedPlayers = new ConcurrentHashMap<Integer, Player>();
    
    public static PlayerCacheManager getInstance() {
        return instance;
    }
    
    public void addPlayerToCache(Player p) {
        Log.info("Added player " + p.getName() + " to account cache.");
        mCachedPlayers.put(p.getAccount().getAccountId(), p);
    }
    
    public Player pollPlayerFromCache(int accountId) {
        Player p = mCachedPlayers.remove(accountId);
        if (p != null) Log.info("Pooled player " + p.getName() + " from account cache.");
        return p;
    }

    public Player getPlayerFromCache(int accountId) {
        return mCachedPlayers.get(accountId);
    }
}
