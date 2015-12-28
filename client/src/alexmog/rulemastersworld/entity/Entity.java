package alexmog.rulemastersworld.entity;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.gamemodes.GameMode;
import alexmog.rulemastersworld.graphic.Renderable;

public abstract class Entity implements Renderable {
	protected List<Animation[]> mAnimations = new ArrayList<Animation[]>();
	protected Vector2f mPosition, mSize;
	protected boolean mToDelete = false, mMoving = false;
	protected int mDirection = 0;
	protected float mSpeed;
	protected Rectangle mBounds;
	protected int mId;
	protected boolean mBlocking = false;
	protected GameMode mGameMode;
	protected String mName;
	protected String[] mSkinParts;
	protected Circle mVision = null;
    protected double mLastAngle;
	
	public Entity(Vector2f position, Vector2f size, GameMode gameMode) {
		mPosition = position;
		mSize = size;
		mBounds = new Rectangle(position.x, position.y, size.x, size.y);
		if (gameMode != null) {
		    mId = gameMode.getEntitiesManager().generateNewId();
		}
		mGameMode = gameMode;
	}
	
	public void setLastAngle(double angle) {
	    mLastAngle = angle;
	}
	
	public void setAnimationId(int id) {
	    mDirection = id;
	}
	
	public void setId(int id) {
	    mId = id;
	}
	
	public Circle getVision() {
	    return mVision;
	}
	
	public void setVision(float radius) {
	    if (mVision == null) {
	        mVision = new Circle(mPosition.getX(), mPosition.getY(), radius);
	    } else {
	        mVision.setRadius(radius);
	    }
	}
	
	public boolean skinSetted() {
	    return mAnimations.size() > 0;
	}
	
	public void setSkin() {}
	
	public void setSkinParts(String[] parts) {
	    mSkinParts = parts;
	}
	
	public String[] getSkinParts() {
	    return mSkinParts;
	}
	
	public void setMoving(boolean moving) {
		mMoving = moving;
	}
	
	public boolean isMoving() {
		return mMoving;
	}
	
	public void setGameMode(GameMode g) {
	    mGameMode = g;
	}
	
	public void setName(String name) {
	    mName = name;
	}
	
	public String getName() {
	    return mName;
	}
	
	public void setBlocking(boolean blocking) {
		mBlocking = blocking;
	}
	
	public boolean isBlocking() {
		return mBlocking;
	}
	
	public int getId() {
	    return mId;
	}
	
	public void setPosition(Vector2f position) {
		mPosition = position;
	}
	
	public void setSize(Vector2f size) {
		mSize = size;
	}
	
	public void setToDelete(boolean delete) {
		mToDelete = delete;
	}
	
	public boolean isToDelete() {
		return mToDelete;
	}

	public Vector2f getPosition() {
		return mPosition;
	}
	
	public Vector2f getSize() {
		return mSize;
	}
	
	public void update(int delta)
	{
		mBounds.setX(mPosition.x - mSize.x / 2);
		mBounds.setY(mPosition.y - mSize.y / 2);
		if (mVision != null) {
    		mVision.setCenterX(mPosition.x);
    		mVision.setCenterY(mPosition.y);
		}
	}
	
	public void render(Graphics g)
	{
	    for (Animation[] animations : mAnimations) {
    	    if (animations[mDirection] != null) {
    //	        g.drawAnimation(mAnimations[mDirection], mPosition.x - mSize.x / 2, mPosition.y - mSize.y / 2);
    	        animations[mDirection].draw(mPosition.x - mSize.x / 2, mPosition.y - mSize.y / 2, mSize.x, mSize.y);
    	    } else if (animations[0] != null) {
    	        animations[0].draw(mPosition.x - mSize.x / 2, mPosition.y - mSize.y / 2, mSize.x, mSize.y);
    	    }
	    }
	    
	    if (mGameMode.isDebug()) {
		    g.draw(mBounds);
		    if (mVision != null) {
		        g.draw(mVision);
		    }
		}
	}
	
	protected Animation loadAnimation(SpriteSheet spriteSheet, int x, int y, int maxX) {
		Animation ret = new Animation();
		for (;x < maxX; ++x) {
			ret.addFrame(spriteSheet.getSprite(x, y), 100);
		}
		return ret;
	}
	
	public void setSpeed(float speed) {
		mSpeed = speed;
	}
	
	public float getSpeed() {
		return mSpeed;
	}
	
	public Rectangle getBounds() {
	    return mBounds;
	}
	
	public void onCollision(Entity collidedEntity) {}
	
	public void onEntityInVision(Entity e) {}
	
	public void onDelete() {}

    public void init() {}
}
