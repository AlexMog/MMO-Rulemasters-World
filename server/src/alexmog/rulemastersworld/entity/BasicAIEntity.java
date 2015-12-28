package alexmog.rulemastersworld.entity;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.ai.astar.AStar;
import alexmog.rulemastersworld.gamemodes.GameMode;
import alexmog.rulemastersworld.packets.EntityMovePacket;

public class BasicAIEntity extends StatsEntity {
    protected Vector2f mLastPosition = new Vector2f();
    protected Vector2f mDestination = new Vector2f();
    protected Entity mTarget;
    protected List<Vector2f> mPath = new ArrayList<Vector2f>();
    protected Vector2f mVelocity = new Vector2f();
    protected AStar mAStar;
    protected Vector2f mDiff = new Vector2f();
    protected boolean mUseAStar = true;

    public BasicAIEntity(Vector2f position, Vector2f size, GameMode gameMode) {
        super(position, size, gameMode);
        mAStar = new AStar(this, gameMode.getGameInstance().getMap());
    }

    @Override
    public void onCollision(Entity collidedEntity) {
        super.onCollision(collidedEntity);
        if (mMoving && collidedEntity.isBlocking()) {
            mPosition.x = mLastPosition.x;
            mPosition.y = mLastPosition.y;
        }
    }
    
    public Vector2f getDestination() {
        return mDestination;
    }
    
    public void saveLastPos() {
        mLastPosition.x = mPosition.x;
        mLastPosition.y = mPosition.y;
    }
    
    public void moveTo(float x, float y) {
        mMoving = true;
        mDestination.x = x;
        mDestination.y = y;
    	mDiff.x = x - mPosition.x;
    	mDiff.y = y - mPosition.y;
    	
        mAngle = Math.atan2(y - mPosition.y, x - mPosition.x);
        if (mAngle >= -2.5f && mAngle <= -0.5f) {
            mDirection = 3;
        } else if (mAngle >= 0.9f && mAngle <= 2.19f) {
            mDirection = 0;
        } else if (mAngle >= -0.5f && mAngle <= 2.19f) {
            mDirection = 2;
        } else {
            mDirection = 1;
        }
        
        EntityMovePacket p = new EntityMovePacket();
        p.angle = mAngle;
        p.animation = mDirection;
        p.id = mId;
        p.velocity = mSpeed;
        p.x = mPosition.x;
        p.y = mPosition.y;
        mGameMode.getGameInstance().sendToAllPlayers(p);
    }
    
    public void moveTo(Vector2f dest) {
        moveTo(dest.x, dest.y);
    }
    
    private void nextMovement() {
    	mDestination = mPath.get(0);
    	mPath.remove(0);
    	
    	this.moveTo(mDestination);
    }
    
    public void goTo(float x, float y) {
    	mPath = mAStar.calculatePath(new Vector2f(x, y));

        if (mPath != null && !mMoving) {
        	this.nextMovement();
        }
    }
    
    private boolean destinationReached() {
    	float diffX = mDestination.x - mPosition.x;
    	float diffY = mDestination.y - mPosition.y;
    	
    	return ((mDiff.x == 0 || diffX == 0 || diffX < 0 != mDiff.x < 0)
    	        && (mDiff.y == 0 || diffY == 0 || diffY < 0 != mDiff.y < 0));
    }
    
    @Override
    public void update(long delta) {
        if (mMoving && destinationReached()) {
            mMoving = false;
            if (mUseAStar && mPath != null && !mPath.isEmpty()) {
                mPosition = mDestination;
            	this.nextMovement();
            }
            if (!mMoving){
                EntityMovePacket p = new EntityMovePacket();
                p.angle = mAngle;
                p.animation = mDirection + 4;
                p.id = mId;
                p.velocity = 0;
                p.x = mPosition.x;
                p.y = mPosition.y;
                mGameMode.getGameInstance().sendToAllPlayers(p);
            }
        }
        
        if (mMoving) {
            mVelocity.x = (float) (Math.cos(mAngle) * mSpeed * .1 * delta);
            mVelocity.y = (float) (Math.sin(mAngle) * mSpeed * .1 * delta);

            boolean updateGoTo = false;
            mPosition.x += mVelocity.x;
            mPosition.y += mVelocity.y;
            if (mPosition.x < 0) {
                mPosition.x = 0;
                updateGoTo = true;
            }
            if (mPosition.y < 0) {
                mPosition.y = 0;
                updateGoTo = true;
            }
            if (mPosition.x >= mGameMode.getGameInstance().getMap().getTmxMap().getWidth()
                    * mGameMode.getGameInstance().getMap().getTmxMap().getTilewidth()) {
                mPosition.x = mGameMode.getGameInstance().getMap().getTmxMap().getWidth()
                        * mGameMode.getGameInstance().getMap().getTmxMap().getTilewidth();
                updateGoTo = true;
            }
            if (mPosition.y >= mGameMode.getGameInstance().getMap().getTmxMap().getHeight()
                    * mGameMode.getGameInstance().getMap().getTmxMap().getTileheight()) {
                mPosition.y = mGameMode.getGameInstance().getMap().getTmxMap().getHeight()
                        * mGameMode.getGameInstance().getMap().getTmxMap().getTileheight();
                updateGoTo = true;
            }
            if (updateGoTo) {
                mDestination.x = mPosition.x;
                mDestination.y = mPosition.y;
            }
        }
        
        super.update(delta);
    }
    
    /*
    public void verifyMapCollisions(long delta) {
        int startX, startY, endX, endY;
        boolean updateGoTo = false;
        
        if (mVelocity.x > 0) {
            startX = endX = (int) ((mPosition.x + mSize.x + mVelocity.x) / Main.map.getTmxMap().getTilewidth());
        } else {
            startX = endX = (int) ((mPosition.x - mSize.x + mVelocity.x) / Main.map.getTmxMap().getTilewidth());
        }
        
        startY = (int) (mPosition.y / Main.map.getTmxMap().getTileheight());
        endY = (int) ((mPosition.y + mSize.y) / Main.map.getTmxMap().getTileheight());
        Main.map.getTiles(startX, startY, endX, endY, mCollided);
        mBounds.setX(mBounds.getX() + mVelocity.x);
        for (MapElement e : mCollided) {
            if (mBounds.intersects(e.getRectangle())) {
                mVelocity.x = 0;
                updateGoTo = true;
                break;
            }
        }
        mBounds.setX(mPosition.x - mSize.x / 2);
        
        if (mVelocity.y > 0) {
            startY = endY = (int) ((mPosition.y + mSize.y + mVelocity.y) / Main.map.getTmxMap().getTileheight());
        } else {
            startY = endY = (int) ((mPosition.y - mSize.y + mVelocity.y) / Main.map.getTmxMap().getTileheight());
        }
        
        startX = (int) (mPosition.x / Main.map.getTmxMap().getTilewidth());
        endX = (int) ((mPosition.x + mSize.x) / Main.map.getTmxMap().getTilewidth());
        Main.map.getTiles(startX, startY, endX, endY, mCollided);
        mBounds.setY(mBounds.getY() + mVelocity.y);
        for (MapElement e : mCollided) {
            if (mBounds.intersects(e.getRectangle())) {
                mVelocity.y = 0;
                updateGoTo = true;
                break;
            }
        }
        mBounds.setY(mPosition.y - mSize.y / 2);
        mPosition.x += mVelocity.x;
        mPosition.y += mVelocity.y;
        if (mPosition.x < 0) {
            mPosition.x = 0;
            updateGoTo = true;
        }
        if (mPosition.y < 0) {
            mPosition.y = 0;
            updateGoTo = true;
        }
        if (updateGoTo) {
            mGoTo.x = mPosition.x;
            mGoTo.y = mPosition.y;
        }
        
        Log.info(mPosition.x / Main.map.getTmxMap().getTileheight() + ", " + mPosition.y / Main.map.getTmxMap().getTileheight());
    }
    */

    @Override
    public void onEntityInVision(Entity e) {}
    
    public void entityTaunted(LivingEntity e) {
        mTarget = e;
    }
}
