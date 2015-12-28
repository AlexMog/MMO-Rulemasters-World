package alexmog.rulemastersworld.entity;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.gamemodes.GameMode;
import alexmog.rulemastersworld.maths.MathsHelper;

public abstract class Entity {
	protected MathsHelper mMathsHelper = MathsHelper.getInstance();
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
	protected int mSkinId;
    protected double mAngle;
	
	public Entity(Vector2f position, Vector2f size, GameMode gameMode) {
		mPosition = position;
		mSize = size;
		mBounds = new Rectangle(position.x, position.y, size.x, size.y);
		if (gameMode != null) {
		    mId = gameMode.getEntityManager().generateNewId();
		}
		mGameMode = gameMode;
	}
	
	public void setLastAngle(double angle) {
	    mAngle = angle;
	}
	
	public double getLastAngle() {
	    return mAngle;
	}
	
	public void setId(int id) {
	    mId = id;
	}
	
	public void setSkinId(int id) {
	    mSkinId = id;
	}
	
	public int getSkinId() {
	    return mSkinId;
	}
	
	public GameMode getGameMode() {
	    return mGameMode;
	}
	
	public int getAnimationId() {
	    return mDirection;
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
	
	public void update(long delta)
	{
		mBounds.setX(mPosition.x - mSize.x / 2);
		mBounds.setY(mPosition.y - mSize.y / 2);
		if (mVision != null) {
    		mVision.setCenterX(mPosition.x);
    		mVision.setCenterY(mPosition.y);
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
