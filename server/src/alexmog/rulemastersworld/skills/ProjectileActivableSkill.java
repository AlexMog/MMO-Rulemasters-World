package alexmog.rulemastersworld.skills;

import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.effects.Effect;
import alexmog.rulemastersworld.entity.LivingEntity;
import alexmog.rulemastersworld.entity.projectile.ProjectileEntity;
import alexmog.rulemastersworld.gamemodes.GameMode;

public class ProjectileActivableSkill extends ActivableSkill {
    private final ProjectileEntity mProjectile;

    public ProjectileActivableSkill(int id, String name,
            String description, ProjectileEntity projectile) {
        super(id, name, description);
        mProjectile = projectile;
/*        DOTEffect e = new DOTEffect(0, gameMode, "Saignement", 10, 5000, 30000);
        e.setStackable(true);
        e.setDescription("Vous saignez!");
        e.setIconId(0);
        mEffects.add(e);*/
    }

    @Override
    public void onActivation(float x, float y) {
        ProjectileEntity add = (ProjectileEntity) mProjectile.clone();
        add.getPosition().x = mCaster.getPosition().x;
        add.getPosition().y = mCaster.getPosition().y;
        add.setDest(new Vector2f(x, y));
        for (Effect e : mEffects) {
            add.addEffect(e);
        }
        mCaster.getGameMode().getEntityManager().addEntity(add);
    }

	@Override
	public void onActivation(LivingEntity target) {
		onActivation(target.getPosition().x, target.getPosition().y);
	}

}
