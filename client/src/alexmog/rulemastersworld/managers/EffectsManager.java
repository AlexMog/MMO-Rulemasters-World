package alexmog.rulemastersworld.managers;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import alexmog.rulemastersworld.graphic.effect.Effect;

public class EffectsManager extends Manager {
	List<Effect> mEffects = new ArrayList<Effect>();
	
	
	public void addEffect(Effect effect) {
		synchronized(mEffects) {
			mEffects.add(effect);
		}
	}
	
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		synchronized(mEffects) {
			for (Effect e : mEffects) {
				e.update(delta);
			}
			
			for (int i = 0; i < mEffects.size();) {
				Effect e = mEffects.get(i);
				if (e.isToDelete()) {
					mEffects.remove(i);
				} else {
					++i;
				}
			}
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		synchronized(mEffects) {
			for (Effect e : mEffects) {
				e.render(g);
			}
		}
	}


    public void reset() {
        mEffects.clear();
    }
}
