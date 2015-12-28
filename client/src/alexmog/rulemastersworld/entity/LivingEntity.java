package alexmog.rulemastersworld.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.datas.DatasManager;
import alexmog.rulemastersworld.effects.Effect;
import alexmog.rulemastersworld.frames.entityeditor.SkinCreator;
import alexmog.rulemastersworld.gamemodes.GameMode;
import alexmog.rulemastersworld.graphic.effect.TextEffect;
import alexmog.rulemastersworld.skills.Skill;

public class LivingEntity extends BasicIAEntity {
	protected int mHp;
	protected Map<Integer, Effect> mEffects = new ConcurrentHashMap<Integer, Effect>();
	protected boolean mStunned = false;
	protected boolean mSilenced = false;
	protected Faction mFaction = Faction.NEUTRAL;
	protected List<Skill> mSkills = new ArrayList<Skill>();
	protected TrueTypeFont mNameFont;
	protected int mMaxHp;
	
	public LivingEntity(Vector2f position, Vector2f size,
			int life, int maxHp, GameMode gameMode) {
		super(position, size, gameMode);
		mHp = life;
		mMaxHp = maxHp;
	}
	
	@Override
	public void init() {
	    mNameFont = DatasManager.getInstance().getFile("LivingEntity.name");
	    super.init();
	}
	
	public List<Skill> getSkills() {
	    return mSkills;
	}
	
	public void addSkill(Skill s) {
	    mSkills.add(s);
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
	
	public void setSkin() {
	    // TODO remake this code?
	    for (String skin : mSkinParts) {
	        SpriteSheet s = DatasManager.getInstance().getFile(SkinCreator.IMAGES_PATH.substring(1) + skin);
	        Animation[] anim = new Animation[4];
            anim[0] = loadAnimation(s, 0, 0, 3); // Haut
            anim[1] = loadAnimation(s, 0, 1, 3); // Gauche
            anim[2] = loadAnimation(s, 0, 2, 3); // Droite
            anim[3] = loadAnimation(s, 0, 3, 3); // Bas
            mAnimations.add(anim);
	    }
	}
	
	public void addEffect(Effect e) {
	    this.mEffects.put(e.getId(), e);
	    e.apply(this);
	    
		this.mGameMode.getEffectsManager().addEffect(new TextEffect("status", "ADDED: " + e.getName() + " (" + e.getStacks() + ")", Color.yellow, mPosition, TextEffect.STATUS_DURATION));
		mGameMode.mGuiManager.addEffect(e);
	}
	
	public boolean hasEffect(int i) {
	    return mEffects.containsKey(i);
	}
	
	public void onRemoveEffect(Effect e) {}
	
	public void removeEffect(int id) {
	    if (hasEffect(id)) {
	    	Effect e = mEffects.get(id);
    	    onRemoveEffect(e);
    	    mEffects.remove(id);
    	    e.remove();
    		this.mGameMode.getEffectsManager().addEffect(new TextEffect("status", "REMOVED: " + e.getName(), Color.yellow, mPosition, TextEffect.STATUS_DURATION));
            Main.game.getGameMode().mGuiManager.removeEffect(e);
	    }
	}
	
	public void removeEffect(Effect e) {
	    removeEffect(e.getId());
	}
	
	public void clearEffects() {
	    for (Entry<Integer, Effect> e : mEffects.entrySet()) {
            e.getValue().remove();
        }
	    mEffects.clear();
	}
	
	public Map<Integer, Effect> getEffectsMap() {
	    return mEffects;
	}
	
	public Set<Effect> getEffects() {
	    return new HashSet<Effect>(mEffects.values());
	}
	
	@Override
	public void update(int delta) {
		// Update effects
		for (Entry<Integer, Effect> e : mEffects.entrySet()) {
		    e.getValue().update(delta);
		}
		
//		if (mHp <= 0) {
//			this.mToDelete = true;
//		}
        super.update(delta);
	}
	
	@Override
	public void render(Graphics g) {
	    super.render(g);
/*	    float x = mPosition.x - mSize.x / 2;
	    float y = mPosition.y - 35 - mSize.y / 2;
	    if (mName != null) {
	        mNameFont.drawString(mPosition.x - mNameFont.getWidth(mName) / 2, y - 25, mName);
	    }
	    g.drawRect(x, y, mSize.x, 20);
	    g.setColor(getHp() > mMaxHp * 50 / 100 ? Color.green :
	    	getHp() > mMaxHp * 20 / 100 ? Color.orange : Color.red);
	    float s = ((mSize.x * mHp) / mMaxHp) - 1;
	    g.fillRect(x + 1, y + 1, (s > 0 ? s : 0), 19);
        g.setColor(Color.white);*/
	}
	
	public void setHp(int life) {
		int diff = mHp - life;
		
		if (diff > 0) {
			this.mGameMode.getEffectsManager().addEffect(new TextEffect("damages", "" + diff, Color.red, mPosition, TextEffect.DAMAGES_DURATION));
		} else if (diff < 0) {
			this.mGameMode.getEffectsManager().addEffect(new TextEffect("damages", "+" + diff, Color.green, mPosition, TextEffect.DAMAGES_DURATION));
		}
		
		mHp = life;
	}
	
	public int getHp() {
		return mHp;
	}
	
	public void takeDamages(Entity from, int amount) {
	    setHp(getHp() - amount);
	}
	
	public void takeHeal(Entity from, int amount) {
	    setHp(getHp() + amount);
	}
}
