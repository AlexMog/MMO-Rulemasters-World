package alexmog.rulemastersworld.effects;

import org.newdawn.slick.Graphics;

import alexmog.rulemastersworld.entity.LivingEntity;
import alexmog.rulemastersworld.gamemodes.GameMode;
import alexmog.rulemastersworld.skills.Skill;

public abstract class Effect {
    protected String mName;
    protected GameMode mGameMode;
    protected Skill mSource = null;
    protected LivingEntity mEntity;
    protected int mId;
    protected String mDescription;
    protected int mIconId;
    protected boolean mStackable, mInfinite;
    protected int mStacks = 1;
    
    public Effect(GameMode gameMode, String name, int id) {
        mGameMode = gameMode;
        mId = id;
        mName = name;
    }
    
    public void setInfinite(boolean infinite) {
        mInfinite = infinite;
    }
    
    public boolean isInfinite() {
        return mInfinite;
    }
    
    public abstract Effect clone();
    
    public void setStacks(int stacks) {
        mStacks = stacks;
    }
    
    public int getStacks() {
        return mStacks;
    }
    
    public void setStackable(boolean stackable) {
        mStackable = stackable;
    }
    
    public boolean isStackable() {
        return mStackable;
    }
    
    public void setDescription(String description) {
        mDescription = description;
    }
    
    public String getDescription() {
        return mDescription;
    }
    
    public void setIconId(int iconId) {
        mIconId = iconId;
    }
    
    public int getIconId() {
        return mIconId;
    }
    
    public void setSkill(Skill s) {
        mSource = s;
    }
    
    public Skill getSkill() {
        return mSource;
    }
    
    public void onApply() {}
    
    public void onRemove() {}
    
    public void apply(LivingEntity e) {
        mEntity = e;
        onApply();
    }
    
    public void remove() {
        onRemove();
    }
    
    public LivingEntity getEntity() {
        return mEntity;
    }
    
    public void setEntity(LivingEntity e) {
        mEntity = e;
    }
    
    public void update(long delta) {}
    
    public void render(Graphics g) {}

    public String getName() {
        return mName;
    }

    public int getId() {
        return mId;
    }
}
