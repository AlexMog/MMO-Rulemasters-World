package alexmog.rulemastersworld.skills;

import alexmog.rulemastersworld.effects.Effect;
import alexmog.rulemastersworld.entity.LivingEntity;
import alexmog.rulemastersworld.gamemodes.GameMode;

public class InstantSkill extends ActivableSkill {

	public InstantSkill(int id, String name, String description) {
		super(id, name, description);
	}

	@Override
	public void onActivation(float x, float y) {
		// None unused
	}

	@Override
	public void onActivation(LivingEntity target) {
		for (Effect e : mEffects) {
			target.addEffect(e.clone());
		}
	}

}
