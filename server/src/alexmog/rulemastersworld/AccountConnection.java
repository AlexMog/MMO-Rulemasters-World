package alexmog.rulemastersworld;

import java.util.List;

import com.esotericsoftware.kryonet.Connection;

import alexmog.rulemastersworld.entity.Entity;
import alexmog.rulemastersworld.entity.Player;

public class AccountConnection extends Connection {
    private Player mPlayer;
    private String mToken = null;
    private String mUsername;
    private int mAccountId;
    private long mConnectedAt;
    
    public void setPlayer(Player p) {
        p.setAccount(this);
        mPlayer = p;
    }
    
    public void setConnectedAt(long at) {
        mConnectedAt = at;
    }
    
    public long getConnectedAt() {
        return mConnectedAt;
    }
    
    public void setUsername(String username) {
        mUsername = username;
    }
    
    public String getUsername() {
        return mUsername;
    }
    
    public void setToken(String token) {
        mToken = token;
    }
    
    public String getToken() {
        return mToken;
    }
    
    public Player getPlayer() {
        return mPlayer;
    }
    
    public void synchronize() {
        List<Entity> entities = mPlayer.getMapInstance().getGameInstance().getGameMode().getEntityManager().getEntityList();
        synchronized(entities) {
            for (Entity e : entities) {
                mPlayer.getMapInstance().getGameInstance().getGameMode().getEntityManager().sendEntityDatas(this, e);
            }
        }
    }
    
    public void setAccountId(int id) {
        mAccountId = id;
    }

    public int getAccountId() {
        return mAccountId;
    }
}
