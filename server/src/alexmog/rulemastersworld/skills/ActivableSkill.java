package alexmog.rulemastersworld.skills;

import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.entity.LivingEntity;
import alexmog.rulemastersworld.gamemodes.GameMode;
import alexmog.rulemastersworld.packets.skills.TargetType;

public abstract class ActivableSkill extends Skill {
    protected long mCooldown = 10000;
    protected long mUsedCooldown = 0;
    protected long mCastTime = 0;
    protected boolean mCanTouchAllies, mCanTouchEnemies, mCanTouchSelf;
    protected TargetType mTargetType = TargetType.Entity;

    public ActivableSkill(int id, String name, String description) {
        super(id, name, description, false);
    }
    
    @Override
    public void update(long delta) {
        mUsedCooldown -= delta;
    }
    
    public boolean isCooledDown() {
        return mUsedCooldown <= 0;
    }
    
    public final void activate(Vector2f location) {
    	activate(location.x, location.y);
    }
    
    public final void activate(float x, float y) {
        if (mUsedCooldown > 0) return;
        mUsedCooldown = mCooldown;
        onActivation(x, y);
    }
    
    public abstract void onActivation(float x, float y);
    
    public void activate(LivingEntity target) {
        onActivation(target);
    }
    
    public abstract void onActivation(LivingEntity target);
    
    public long getCooldown() {
        return mCooldown;
    }
    
    public void setCooldown(long cooldown) {
        mCooldown = cooldown;
    }
    
    public void setCastTime(long castTime) {
        mCastTime = castTime;
    }
    
    public long getCastTime() {
        return mCastTime;
    }
    
    public boolean canTouchAllies() {
        return mCanTouchAllies;
    }
    
    public boolean canTouchEnemies() {
        return mCanTouchEnemies;
    }
    
    public boolean canTouchSelf() {
        return mCanTouchSelf;
    }
    
    public void setTouchAllies(boolean b) {
        mCanTouchAllies = b;
    }
    
    public void setTouchEnemies(boolean b) {
        mCanTouchEnemies = b;
    }
    
    public void setTouchSelf(boolean b) {
        mCanTouchSelf = b;
    }

	public TargetType getTargetType() {
		return mTargetType;
	}
	
	public void setTargetType(TargetType t) {
		mTargetType = t;
	}
}
