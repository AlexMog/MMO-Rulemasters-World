package alexmog.rulemastersworld.skills;

import alexmog.rulemastersworld.entity.LivingEntity;
import alexmog.rulemastersworld.gamemodes.GameMode;
import alexmog.rulemastersworld.packets.skills.TargetType;

public class InvokeSkill extends ActivableSkill {
    private final LivingEntity mEntityToSpawn;

    public InvokeSkill(int id, String name,
            String description, LivingEntity toSpawn) {
        super(id, name, description);
        mEntityToSpawn = toSpawn;
        mIconId = 1;
        mTargetType = TargetType.Zone;
    }

    @Override
    public void onActivation(float x, float y) {
    	LivingEntity e = (LivingEntity) mEntityToSpawn.clone();
    	e.getPosition().x = x;
    	e.getPosition().y = y;
    	mCaster.getGameMode().getEntityManager().addEntity(e);
    }

	@Override
	public void onActivation(LivingEntity target) {
		// Not used there
	}

}
