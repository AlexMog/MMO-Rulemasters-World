package alexmog.rulemastersworld.effects;

import org.newdawn.slick.Image;

import alexmog.rulemastersworld.datas.DatasManager;
import alexmog.rulemastersworld.entity.Entity;
import alexmog.rulemastersworld.entity.LivingEntity;
import alexmog.rulemastersworld.gamemodes.GameMode;
import alexmog.rulemastersworld.util.Timer;

public class Effect {
    protected String mName;
    protected String mDescription;
    protected GameMode mGameMode;
    protected LivingEntity mEntity;
    protected Timer mTimer;
    protected int mStacks;
    protected int mId;
    protected Image mIcon;
    protected boolean mInfinite;
    
    public Effect(GameMode gameMode, String name, int id, long cooldown, int mIconId, boolean infinite) {
        mGameMode = gameMode;
        mId = id;
        mName = name;
        mTimer = new Timer(cooldown);
        mIcon = (Image) DatasManager.getInstance().getFile("Image.Icon_" + mIconId);
        mInfinite = infinite;
    }
    
    public void setInfinite(boolean infinite) {
        mInfinite = infinite;
    }
    
    public boolean isInfinite() {
        return mInfinite;
    }
    
    public Entity getEntity() {
    	return mEntity;
    }
    
    public String getDescription() {
        return mDescription;
    }
    
    public void setDescription(String description) {
        mDescription = description;
    }
    
    public void setStacks(int stacks) {
        mStacks = stacks;
    }
    
    public Timer getTimer() {
    	return mTimer;
    }
    
    public int getStacks() {
        return mStacks;
    }
    
    public void apply(LivingEntity e) {
        mEntity = e;
    }
    
    public void remove() {}
    
    public void update(int delta) {
        if (!mInfinite) mTimer.update(delta);
    }

    public String getName() {
        return mName;
    }

    public int getId() {
        return mId;
    }
    
    public Image getIcon() {
        return mIcon;
    }
    
    public void setIcon(Image icon) {
        mIcon = icon;
    }
}
