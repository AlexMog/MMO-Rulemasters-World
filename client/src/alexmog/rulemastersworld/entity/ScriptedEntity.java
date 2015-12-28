package alexmog.rulemastersworld.entity;

import java.util.ArrayList;
import java.util.List;

import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.effects.Effect;
import alexmog.rulemastersworld.gamemodes.GameMode;

public class ScriptedEntity extends LivingEntity {
    private static String[] FUNCTIONS_NAMES = {
        "onUpdate",
        "onDamage",
        "onHeal",
        "onEntityInVision",
        "onEffectAdded",
        "onEffectRemoved",
        "onEffectsCleared",
        "onTaunt",
        "init",
        "onCollision"
    };
    private List<Entity> mVisibleEntities = new ArrayList<Entity>();
    private String mScript;
    private Function mFunctions[] = new Function[FUNCTIONS_NAMES.length];
    private ScriptableObject mScope;
    
    public ScriptedEntity(Vector2f position, Vector2f size, int hp, GameMode gameMode, int maxHp) {
        super(position, size, hp, maxHp, gameMode);
    }

    private void callFunction(int id, Object... objects) {
        if (id < mFunctions.length && mFunctions[id] != null) {
            mFunctions[id].call(mGameMode.getContext(), mScope, mScope, objects);
        }
    }
    
    @Override
    public void onEntityInVision(Entity e) {
        mVisibleEntities.add(e);
        callFunction(3, e);
        super.onEntityInVision(e);
    }
    
    public List<Entity> getVisibleEntities() {
        return mVisibleEntities;
    }
    
    @Override
    public void onCollision(Entity collidedEntity) {
        callFunction(9, collidedEntity);
        super.onCollision(collidedEntity);
    }
    
    @Override
    public void update(int delta) {
        callFunction(0, delta);
        super.update(delta);
        mVisibleEntities.clear(); 
    }
    
    @Override
    public void takeDamages(Entity from, int amount) {
        callFunction(1, from, amount);
        super.takeDamages(from, amount);
    }
    
    @Override
    public void takeHeal(Entity from, int amount) {
        callFunction(2, from, amount);
        super.takeHeal(from, amount);
    }
    
    @Override
    public void addEffect(Effect e) {
        callFunction(4, e);
        super.addEffect(e);
    }
    
    
    
    @Override
    public void removeEffect(Effect e) {
        callFunction(5, e);
        super.removeEffect(e);
    }
    
    @Override
    public void clearEffects() {
        callFunction(6);
        super.clearEffects();
    }
    
    @Override
    public void entityTaunted(LivingEntity e) {
        callFunction(7, e);
        super.entityTaunted(e);
    }

    public void init() {
        try {
            mScope = mGameMode.getContext().initStandardObjects();
            mScope.put("$this", mScope, this);
            mGameMode.getContext().evaluateString(mScope, mScript, "script", 1, null);
            for (int i = 0; i < FUNCTIONS_NAMES.length; ++i) {
                Object fnc = mScope.get(FUNCTIONS_NAMES[i], mScope);
                if (fnc != null && fnc instanceof Scriptable) {
                    mFunctions[i] = (Function)fnc;
                } else {
                    mFunctions[i] = null;
                }
            }
            callFunction(8);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setScript(String s) {
        mScript = s;
    }
}
