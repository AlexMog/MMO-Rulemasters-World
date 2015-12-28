package alexmog.rulemastersworld.entity.projectile;

import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.entity.Entity;
import alexmog.rulemastersworld.entity.LivingEntity;
import alexmog.rulemastersworld.entity.MouseEntity;
import alexmog.rulemastersworld.gamemodes.GameMode;

public class ProjectileEntity extends Entity {
	protected float mProjectileTimeout;
	private double mRadianAngle = 0;
	private Entity mCreator;
	
	public ProjectileEntity(Entity creator, Vector2f position, Vector2f size, Vector2f pos2, GameMode gameMode) {
		super(new Vector2f(position), size, gameMode);
		mCreator = creator;
//		mAnimations[0] = loadAnimation(GameScene.projectileSpriteSheet, 0, 0, 1);
		mRadianAngle = Math.atan2(pos2.y - position.y, pos2.x - position.x);
		mSpeed = 10;
	}
	
	@Override
	public void update(int delta) {
		mPosition.x += Math.cos(mRadianAngle) * mSpeed * .1 * delta;
		mPosition.y += Math.sin(mRadianAngle) * mSpeed * .1 * delta;
		if (mProjectileTimeout >= 5000) {
			this.mToDelete = true;
		}
		mProjectileTimeout += delta;
        super.update(delta);
	}

	@Override
	public void onCollision(Entity collidedEntity) {
		super.onCollision(collidedEntity);
		if (collidedEntity.getId() != mCreator.getId() && !(collidedEntity instanceof MouseEntity)
				&& !(collidedEntity instanceof ProjectileEntity)) {
			mToDelete = true;
			if (collidedEntity instanceof LivingEntity) {
				LivingEntity e = (LivingEntity) collidedEntity;
				e.setHp(e.getHp() - 1);
			}
		}
	}
}
