package alexmog.rulemastersworld.entity;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.effects.Effect;
import alexmog.rulemastersworld.effects.TicEffect;
import alexmog.rulemastersworld.effects.TimedEffect;
import alexmog.rulemastersworld.gamemodes.GameMode;
import alexmog.rulemastersworld.packets.EntityEffectAddPacket;
import alexmog.rulemastersworld.packets.EntityEffectRemovePacket;
import alexmog.rulemastersworld.packets.EntityHpPacket;
import alexmog.rulemastersworld.skills.Skill;

public class LivingEntity extends BasicAIEntity {
	protected int mHp;
	protected Map<Integer, Effect> mEffects = new ConcurrentHashMap<Integer, Effect>();
	protected boolean mStunned = false;
	protected boolean mSilenced = false;
	protected Faction mFaction = Faction.NEUTRAL;
	protected Map<Integer, Skill> mSkills = new ConcurrentHashMap<Integer, Skill>();
	
	public LivingEntity(Vector2f position, Vector2f size, GameMode gameMode) {
		super(position, size, gameMode);
	}
	
	public Map<Integer, Skill> getSkills() {
	    return mSkills;
	}
	
	public void addSkill(Skill s) {
	    mSkills.put(s.getId(), s);
	}
	
	public void setFaction(Faction f) {
	    mFaction = f;
	}
	
	public Faction getFaction() {
	    return mFaction;
	}
	
	public boolean isStunned() {
	    return mStunned;
	}
	
	public boolean isSilenced() {
	    return mSilenced;
	}
	
	public void setStunned(boolean stunned) {
	    mStunned = stunned;
	}
	
	public void setSilenced(boolean silenced) {
	    mSilenced = silenced;
	}
	
	public void addEffect(Effect e) {
	    if (hasEffect(e.getId()) && e.isStackable()) {
	        Effect tmp = mEffects.get(e.getId());
	        if (e instanceof TicEffect) {
	            ((TicEffect)e).setTicTimer(((TicEffect)tmp).getTicTimer());
	        }
	        e.setStacks(tmp.getStacks() + 1);
	    }
	    this.mEffects.put(e.getId(), e);
	    e.apply(this);

	    EntityEffectAddPacket p = new EntityEffectAddPacket();
	    p.entityId = mId;
	    if (e instanceof TimedEffect) {
	        p.cooldown = ((TimedEffect)e).getTimer().remaning();
	    }
	    p.infinite = e.isInfinite();
	    p.stacks = e.getStacks();
	    p.iconId = e.getIconId();
	    p.id = e.getId();
	    p.name = e.getName();
	    p.description = e.getDescription();
	    mGameMode.getGameInstance().sendToAllPlayers(p);
	}
	
	public boolean hasEffect(int i) {
	    return mEffects.containsKey(i);
	}
	
	public void onRemoveEffect(Effect e) {
        EntityEffectRemovePacket p = new EntityEffectRemovePacket();
        p.entityId = mId;
        p.id = e.getId();
        mGameMode.getGameInstance().sendToAllPlayers(p);
	}
	
	public void removeEffect(int id) {
	    if (hasEffect(id)) {
    	    onRemoveEffect(mEffects.get(id));
    	    Effect e = mEffects.remove(id);
    	    e.remove();
	    }
	}
	
	public void removeEffect(Effect e) {
	    if (hasEffect(e.getId())) {
	        onRemoveEffect(e);
	        mEffects.remove(e.getId()).remove();
	    }
	}
	
	public void clearEffects() {
	    for (Entry<Integer, Effect> e : mEffects.entrySet()) {
            removeEffect(e.getKey());
        }
	}
	
	public Map<Integer, Effect> getEffectsMap() {
	    return mEffects;
	}
	
	public Set<Effect> getEffects() {
	    return new HashSet<Effect>(mEffects.values());
	}
	
	@Override
	public void update(long delta) {
		// Update effects
		for (Entry<Integer, Effect> e : mEffects.entrySet()) {
		    e.getValue().update(delta);
		}
		// Update skills
		for (Skill s : mSkills.values()) {
		    s.update(delta);
		}
		
		if (mHp <= 0) {
			this.mToDelete = true;
		}
		
        super.update(delta);
	}
	
	public void setHp(int life) {
		setHpOffline(life);
		EntityHpPacket p = new EntityHpPacket();
		p.id = mId;
		p.value = mHp;
		mGameMode.getGameInstance().sendToAllPlayers(p);
	}
	
	public void setHpOffline(int life) {
		mHp = life;
		if (mHp > mStats.getMaxHp()) {
			mHp = mStats.getMaxHp();
		} else if (mHp < 0) {
			mHp = 0;
		}
	}
	
	public int getHp() {
		return mHp;
	}
	
	public void takeDamages(Entity from, int amount) {
	    setHp(getHp() - amount);
	}
	
	// TODO Probably useless.
	public void takeHeal(Entity from, int amount) {
	    setHp(getHp() + amount);
	}
	
	public Entity clone() {
	    LivingEntity ret = new LivingEntity(new Vector2f(mPosition), new Vector2f(mSize), mGameMode);
	    ret.setStats(this);
	    ret.setSpeed(mSpeed);
	    ret.setSkinId(mSkinId);
	    return ret;
	}
}
