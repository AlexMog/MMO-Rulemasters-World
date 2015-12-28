package alexmog.rulemastersworld.managers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.events.EventsManager;
import alexmog.rulemastersworld.skills.Skill;

public class PlayerSkillsManager extends Manager {
	private Map<Integer, Skill> mSkills = new ConcurrentHashMap<Integer, Skill>();
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		for (Skill s : mSkills.values()) {
			s.update(delta);
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		// Not used there
	}
	
	public void addSkill(Skill s) {
		mSkills.put(s.getId(), s);
		Main.game.getGameMode().getGameGuiManager().addSpellInBar(s);
		EventsManager.getInstance().addSpell(s);
	}
	
	public Skill getSkill(int id) {
	    return mSkills.get(id);
	}
	
	public void removeSkill(Skill s) {
		removeSkill(s.getId());
	}
	
	public void removeSkill(int id) {
		mSkills.remove(id);
		Main.game.getGameMode().getGameGuiManager().removeSpellInBar(id);
	}

	public void reset() {
		mSkills.clear();
	}

}
