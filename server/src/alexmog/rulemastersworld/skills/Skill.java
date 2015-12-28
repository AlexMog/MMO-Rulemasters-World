package alexmog.rulemastersworld.skills;

import java.util.ArrayList;
import java.util.List;

import alexmog.rulemastersworld.effects.Effect;
import alexmog.rulemastersworld.entity.LivingEntity;

public abstract class Skill {
    protected String mName;
    protected String mDescription;
    protected final boolean mPassive;
    protected int mIconId;
    protected int mRange = 10;
    protected List<Effect> mEffects = new ArrayList<Effect>();
    protected LivingEntity mCaster = null;
    private int mId;
    
    public Skill(int id, String name, String description, boolean passive) {
        mName = name;
        mDescription = description;
        mPassive = passive;
        mId = id;
    }
    
    public String getName() {
    	return mName;
    }
    
    public void setCaster(LivingEntity caster) {
        mCaster = caster;
    }
    
    public LivingEntity getCaster() {
        return mCaster;
    }
    
    public void applyTo(LivingEntity e) {
        for (Effect eff : mEffects) {
            e.addEffect(eff);
        }
    }
    
    public List<Effect> getEffectsList() {
        return mEffects;
    }
    
    public boolean isPassive() {
        return mPassive;
    }
    
    public int getIconId() {
        return mIconId;
    }
    
    public void setIcon(int icon) {
        mIconId = icon;
    }
    
    public int getRange() {
        return mRange;
    }
    
    public void setRange(int range) {
        mRange = range;
    }
    
    public void setDescription(String description) {
        mDescription = description;
    }
    
    public String getDescription() {
        return mDescription;
    }

    public void update(long delta) {}

	public int getId() {
		return mId;
	}
}
