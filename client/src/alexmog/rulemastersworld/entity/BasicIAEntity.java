package alexmog.rulemastersworld.entity;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.gamemodes.GameMode;

public class BasicIAEntity extends StatsEntity {
    protected Vector2f mLastPosition = new Vector2f();
    protected Vector2f mGoTo = new Vector2f();
    protected Entity mTarget;
    protected List<Vector2f> mWaypoints = new ArrayList<Vector2f>();
    protected boolean mRotateWaypoints = false;
    protected int mWaypointPos = 0;

    public BasicIAEntity(Vector2f position, Vector2f size, GameMode gameMode) {
        super(position, size, gameMode);
    }
    
    public void setRotateWaypoints(boolean b) {
        mRotateWaypoints = b;
    }
    
    public boolean isRotateWaypoints() {
        return mRotateWaypoints;
    }

    @Override
    public void onCollision(Entity collidedEntity) {
        super.onCollision(collidedEntity);
        if (mMoving && collidedEntity.isBlocking()) {
            mPosition.x = mLastPosition.x;
            mPosition.y = mLastPosition.y;
        }
    }
    
    public void clearWaypoints() {
        mWaypoints.clear();
    }
    
    public void addWaypoint(Vector2f waypoint) {
        mWaypoints.add(waypoint);
    }
    
    public void removeWaypoint(int id) {
        mWaypoints.remove(id);
    }
    
    public void removeWaypoint(Vector2f waypoint) {
        mWaypoints.remove(waypoint);
    }
    
    public void saveLastPos() {
        mLastPosition.x = mPosition.x;
        mLastPosition.y = mPosition.y;
    }
    
    public void goTo(float x, float y) {
        mGoTo.x = x;
        mGoTo.y = y;
        mLastAngle = Math.atan2(y - mPosition.y, x - mPosition.x);
        mMoving = true;
    }
    
    @Override
    public void update(int delta) {
        if (!mMoving && mWaypoints.size() > 0) {
            if (!mRotateWaypoints) {
                goTo(mWaypoints.get(mWaypoints.size() - 1));
                mWaypoints.remove(mWaypoints.size() - 1);
            } else {
                goTo(mWaypoints.get(mWaypointPos));
                mWaypointPos++;
                if (mWaypointPos >= mWaypoints.size()) {
                    mWaypointPos = 0;
                }
            }
        }
        
        if (mPosition.x <= mGoTo.x && mPosition.x >= mGoTo.x - 10
                && mPosition.y <= mGoTo.y + 10 && mPosition.y >= mGoTo.y - 10) {
            mMoving = false;
        }
        
        if (mMoving) {
            if (mLastAngle >= -2.5f && mLastAngle <= -0.5f) {
                mDirection = 3;
            } else if (mLastAngle >= 0.9f && mLastAngle <= 2.19f) {
                mDirection = 0;
            } else if (mLastAngle >= -0.5f && mLastAngle <= 2.19f) {
                mDirection = 2;
            } else {
                mDirection = 1;
            }
        }
        
        if (mMoving) {
            mPosition.x += Math.cos(mLastAngle) * mSpeed * .1 * delta;
            mPosition.y += Math.sin(mLastAngle) * mSpeed * .1 * delta;
        }
        super.update(delta);
    }
    
    private void goTo(Vector2f pos) {
        goTo(pos.x, pos.y);
    }

    @Override
    public void onEntityInVision(Entity e) {}
    
    public void entityTaunted(LivingEntity e) {
        mTarget = e;
    }
}
