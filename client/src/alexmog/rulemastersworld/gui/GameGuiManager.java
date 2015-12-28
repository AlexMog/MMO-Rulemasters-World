package alexmog.rulemastersworld.gui;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.effects.Effect;
import alexmog.rulemastersworld.graphic.EffectRender;
import alexmog.rulemastersworld.graphic.SpellInBarRender;
import alexmog.rulemastersworld.skills.Skill;

public class GameGuiManager {
	private Map<Integer, EffectRender> mEffects = new ConcurrentHashMap<Integer, EffectRender>();
	private Map<Integer, SpellInBarRender> mSpellsInBar = new ConcurrentHashMap<Integer, SpellInBarRender>();

    public void update(GameContainer container, StateBasedGame game, int delta) {
    	int i = 0;
    	for (SpellInBarRender r : mSpellsInBar.values()) {
    		r.update(i);
    		++i;
    	}
    }
    
    public void addSpellInBar(Skill s) {
    	mSpellsInBar.put(s.getId(), new SpellInBarRender(s));
    }
    
    public void removeSpellInBar(int id) {
    	mSpellsInBar.remove(id);
    }
    
    public void addEffect(Effect e) {
    	if (e.getEntity().getId() != Main.clientId) return;
    	synchronized(mEffects) {
    		EffectRender r = mEffects.get(e.getId());
    		if (r != null) {
    			r.setEffect(e);
    		} else {
    			mEffects.put(e.getId(), new EffectRender(e));
    		}
    	}
    }
    
    public void init() {
    }
    
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
		int i = 0;
		for (EffectRender r : mEffects.values()) {
			r.render(g, i);
			++i;
		}
		i = 0;
		for (SpellInBarRender r : mSpellsInBar.values()) {
			r.render(g, i);
			++i;
		}
    }
    
    public void removeEffect(Effect e) {
    	if (e.getEntity().getId() == Main.clientId) {
    		mEffects.remove(e.getId());
    	}
    }
    
    public void reset() {
        
    }
}
