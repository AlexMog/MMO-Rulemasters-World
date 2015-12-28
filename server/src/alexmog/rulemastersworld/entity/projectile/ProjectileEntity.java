package alexmog.rulemastersworld.entity.projectile;

import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.effects.Effect;
import alexmog.rulemastersworld.entity.Entity;
import alexmog.rulemastersworld.entity.LivingEntity;
import alexmog.rulemastersworld.gamemodes.GameMode;

public class ProjectileEntity extends SpellEntity {
	protected float mProjectileTimeout;
	private boolean mFirstUpdate = false;
	
	public ProjectileEntity(LivingEntity creator, Vector2f position, Vector2f size, GameMode gameMode) {
		super(creator, position, size, gameMode);
		mSpeed = 2.0f;
	}
	
	public void setDest(Vector2f dest) {
	       mAngle = Math.atan2(dest.y - mPosition.y, dest.x - mPosition.x);
	       mMoving = true;
	}
	
	@Override
	public void update(long delta) {
	    mFirstUpdate = true;
		mPosition.x += Math.cos(mAngle) * mSpeed * .1 * delta;
        mPosition.y += Math.sin(mAngle) * mSpeed * .1 * delta;
		if (mProjectileTimeout >= 8000) {
			this.mToDelete = true;
		}
		mProjectileTimeout += delta;
        super.update(delta);
	}

	@Override
	public void onCollision(Entity collidedEntity) {
        if (!mFirstUpdate) return;
		super.onCollision(collidedEntity);
		if (collidedEntity.getId() != mCreator.getId()
				&& !(collidedEntity instanceof SpellEntity)) {
			mToDelete = true;
			if (collidedEntity instanceof LivingEntity) {
				LivingEntity e = (LivingEntity) collidedEntity;
				for (Effect ef : mEffects) {
				    e.addEffect(ef);
				}
			}
		}
	}

    @Override
    public SpellEntity clone() {
        ProjectileEntity e = new ProjectileEntity(mCreator, new Vector2f(mPosition), new Vector2f(mSize), mGameMode);
        e.setSkinId(mSkinId);
        return e;
    }
}
