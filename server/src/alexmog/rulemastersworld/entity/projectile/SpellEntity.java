package alexmog.rulemastersworld.entity.projectile;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.effects.Effect;
import alexmog.rulemastersworld.entity.Entity;
import alexmog.rulemastersworld.entity.LivingEntity;
import alexmog.rulemastersworld.gamemodes.GameMode;

public abstract class SpellEntity extends Entity {
    protected final LivingEntity mCreator;
    protected final List<Effect> mEffects = new ArrayList<Effect>();

    public SpellEntity(LivingEntity creator, Vector2f position, Vector2f size, GameMode gameMode) {
        super(new Vector2f(position), size, gameMode);
        mCreator = creator;
    }
    
    public void addEffect(Effect e) {
        mEffects.add(e.clone());
    }
    
    public abstract SpellEntity clone();

}
