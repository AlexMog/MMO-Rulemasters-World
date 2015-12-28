package alexmog.rulemastersworld.entity;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.datas.EntityTemplate;
import alexmog.rulemastersworld.datas.TemplateConverter;
import alexmog.rulemastersworld.effects.Effect;
import alexmog.rulemastersworld.gamemodes.GameMode;
import alexmog.rulemastersworld.scripting.ScriptHelper;
import alexmog.rulemastersworld.scripting.ScriptedObject;

public class ScriptedEntity extends LivingEntity implements ScriptedObject {
    private static final String[] FUNCTIONS_NAMES = {
        "onUpdate",         // 0
        "onDamage",         // 1
        "onHeal",           // 2
        "onEntityInVision", // 3
        "onEffectAdded",    // 4
        "onEffectRemoved",  // 5
        "onEffectsCleared", // 6
        "onTaunt",          // 7
        "init",             // 8
        "onCollision"       // 9
    };
    private List<Entity> mVisibleEntities = new ArrayList<Entity>();
    private ScriptHelper mScriptHelper;
    
    public ScriptedEntity(Vector2f position, Vector2f size, GameMode gameMode, EntityTemplate template) {
        super(position, size, gameMode);
        mStats = TemplateConverter.convertToStats(template);
        mScriptHelper = new ScriptHelper(template.getScriptId(), FUNCTIONS_NAMES, template);
        setName(template.getName());
        setSpeed(template.getSpeed());
        setSkinId(template.getSkinId());
        mUseAStar = template.isUseAstar();
        mHp = mStats.getMaxHp();
    }
    
    @Override
    public <T> T getCustomVar(String varName) {
        return mScriptHelper.getCustomVar(varName);
    }
    
    @Override
    public void setCustomVar(String varName, Object value) {
        mScriptHelper.setCustomVar(varName, value);
    }

    @Override
    public void onEntityInVision(Entity e) {
        mVisibleEntities.add(e);
        mScriptHelper.callFunction(this.getGameMode(), this, 3, e);
        super.onEntityInVision(e);
    }
    
    public List<Entity> getVisibleEntities() {
        return mVisibleEntities;
    }
    
    @Override
    public void onCollision(Entity collidedEntity) {
        mScriptHelper.callFunction(this.getGameMode(), this, 9, collidedEntity);
        super.onCollision(collidedEntity);
    }
    
    @Override
    public void update(long delta) {
        mScriptHelper.callFunction(this.getGameMode(), this, 0, delta);
        super.update(delta);
        mVisibleEntities.clear(); 
    }
    
    @Override
    public void takeDamages(Entity from, int amount) {
        mScriptHelper.callFunction(this.getGameMode(), this, 1, from, amount);
        super.takeDamages(from, amount);
    }
    
    @Override
    public void takeHeal(Entity from, int amount) {
        mScriptHelper.callFunction(this.getGameMode(), this, 2, from, amount);
        super.takeHeal(from, amount);
    }
    
    @Override
    public void addEffect(Effect e) {
        mScriptHelper.callFunction(this.getGameMode(), this, 4, e);
        super.addEffect(e);
    }
    
    
    
    @Override
    public void removeEffect(Effect e) {
        mScriptHelper.callFunction(this.getGameMode(), this, 5, e);
        super.removeEffect(e);
    }
    
    @Override
    public void clearEffects() {
        mScriptHelper.callFunction(this.getGameMode(), this, 6);
        super.clearEffects();
    }
    
    @Override
    public void entityTaunted(LivingEntity e) {
        mScriptHelper.callFunction(this.getGameMode(), this, 7, e);
        super.entityTaunted(e);
    }

    public void init() {
        mScriptHelper.callFunction(this.getGameMode(), this, 8);
    }
}
