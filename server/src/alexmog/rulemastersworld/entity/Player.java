package alexmog.rulemastersworld.entity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.AccountConnection;
import alexmog.rulemastersworld.gamemodes.GameMode;
import alexmog.rulemastersworld.items.Item;
import alexmog.rulemastersworld.map.MapInstance;
import alexmog.rulemastersworld.maths.MathsHelper;
import alexmog.rulemastersworld.packets.EntityMovePacket;
import alexmog.rulemastersworld.server.packetactions.ConnectAction;

public class Player extends LivingEntity {
	private Entity mTarget;
	private AccountConnection mConnection;
	private int mSocketId;
	private int mPermissionsLvl = 0;
	private Map<Integer, Item> mInventory = new ConcurrentHashMap<Integer, Item>();
    private MapInstance mMap;
    private AccountConnection mAccount;
	
	public Player(Vector2f position, Vector2f size, GameMode gameMode, int socketId) {
		super(position, size, gameMode);
		mSpeed = 1.5f;
		mMoving = false;
		mSocketId = socketId;
		// TODO REMOVE
		mStats.setInt(10); // To test the effect intel
		// END REMOVE
/*        ProjectileEntity e = new ProjectileEntity(this, mPosition, new Vector2f(25, 25), gameMode);
        e.setSkinId(2);
        ProjectileActivableSkill s = new ProjectileActivableSkill(0, mGameMode, "Fireball", "Send a fireball on your enemies!", e);
        s.setCaster(this);
        addSkill(s);
        RandomEntity toSpawn = new RandomEntity(new Vector2f(500, 500), new Vector2f(64, 64),
                10, mGameMode);
        toSpawn.setEnd(20);
        toSpawn.setSpeed(1.5f);
        toSpawn.setSkinId(1);
        InvokeSkill s2 = new InvokeSkill(1, mGameMode, "Random Invoke", "Invoke the 'random' creature.", toSpawn);
        s2.setCaster(this);
        addSkill(s2);
        HealingSpell s3 = new HealingSpell(2, mGameMode, "Healing spell", "Add a heal effect on an entity");
        s3.setCaster(this);
        addSkill(s3);*/
        mUseAStar = false;
	}
	
	@Override
	public GameMode getGameMode() {
	    return mMap.getGameInstance().getGameMode();
	}
	
	@Override
	public void goTo(float x, float y) {
	    moveTo(x, y);
	}
	
	public void setMap(MapInstance map) {
        if (mMap != null) {
            mMap.getGameInstance().remAccount(mAccount);
        }
        mMap = map;
        mMap.getGameInstance().addAccount(mAccount);
    }
    
    public MapInstance getMapInstance() {
        return mMap;
    }
	
	public int getPermissionsLvl() {
	    return mPermissionsLvl;
	}
	
	public void setPermissionsLvl(int lvl) {
	    mPermissionsLvl = lvl;
	}
	
	public int getSocketId() {
	    return mSocketId;
	}
	
	public AccountConnection getConnection() {
	    return mConnection;
	}
	
	@Override
	public void update(long delta) {
	    if (mTarget != null && mTarget.isToDelete()) {
	        mTarget = null;
	    }
	    saveLastPos();
        super.update(delta);
        if (mToDelete && mHp <= 0) {
            List<Vector2f> spawns = mGameMode.getGameInstance().getMap().getPlayerSpawnList();
            
            Vector2f spawn = new Vector2f(ConnectAction.DEFAULT_SPAWN_X,
                    ConnectAction.DEFAULT_SPAWN_Y);
            double bestDistance = -1;
            for (Vector2f s : spawns) {
                double distance = MathsHelper.getDistance(s, mPosition);
                if (bestDistance == -1) {
                    bestDistance = distance;
                    spawn = s;
                } else if (distance < bestDistance) {
                    bestDistance = distance;
                    spawn = s;
                }
            }
            
            mPosition.x = spawn.x;
            mPosition.y = spawn.y;
            EntityMovePacket p = new EntityMovePacket();
            p.angle = mAngle;
            p.animation = mDirection + 4;
            p.id = mId;
            p.velocity = 0;
            p.x = mPosition.x;
            p.y = mPosition.y;
            mGameMode.getGameInstance().sendToAllPlayers(p);
            clearEffects();
            setHp(mStats.getMaxHp());
            mToDelete = false;
        }
	}
	
	public void setTarget(Entity e) {
	    mTarget = e;
	}

    public void setAccount(AccountConnection accountConnection) {
        mAccount = accountConnection;
    }
    
    public AccountConnection getAccount() {
        return mAccount;
    }
}
