package alexmog.rulemastersworld.events;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Input;

import alexmog.rulemastersworld.skills.Skill;

public class EventsManager {
	private static EventsManager sInstance = null;
	public static EventsManager getInstance() {
		if (sInstance == null) {
			sInstance = new EventsManager();
		}
		return sInstance;
	}
	
	
	
    private Input mInput;
	private final int BUTTONS_NUMBER = 3;
	private MouseButton mMouseButtons[] = new MouseButton[BUTTONS_NUMBER];
    private int[] mSpellKeys = {Input.KEY_1, Input.KEY_2, Input.KEY_3, Input.KEY_4, Input.KEY_5, Input.KEY_6,
            Input.KEY_7, Input.KEY_8, Input.KEY_9};
    private Map<Integer, Runnable> mSpellLaunch = new HashMap<Integer, Runnable>();
    private int mLastKeySpellUsed = 0;
	
	public EventsManager() {
		for (int i = 0; i < BUTTONS_NUMBER; ++i) {
			mMouseButtons[i] = new MouseButton();
		}
	}
	
	public int getKeySpell(int keyPos) {
	    return mSpellKeys[keyPos];
	}
	
	public void addSpell(final Skill s) {
	    if (mLastKeySpellUsed < mSpellKeys.length) {
	        mSpellLaunch.put(mSpellKeys[mLastKeySpellUsed], new Runnable() {
	            private Skill mSkill = s;
                public void run() {
                    mSkill.select();
                }
            });
	        s.setKeySpell(mSpellKeys[mLastKeySpellUsed]);
	        mLastKeySpellUsed++;
	    }
	}
	
	public void setInput(Input input) {
		mInput = input;
	}
	
	public MouseButton getMouseButton(int id) {
		return (mMouseButtons[id]);
	}
	
	public boolean isKeyPressed(int code) {
		return (mInput.isKeyPressed(code));
	}
	
	public boolean isKeyDown(int code) {
		return (mInput.isKeyDown(code));
	}
	
	public int getAbsoluteMouseX() {
		return (mInput.getAbsoluteMouseX());
	}
	
	public int getAbsoluteMouseY() {
		return (mInput.getAbsoluteMouseY());
	}
	
	public void onGameKeyPressed(int code, char c) {
	    //TODO
	}
	
	public void onGameKeyReleased(int code, char c) {
	    if (code == Input.KEY_ESCAPE) {
	        if (Skill.getSelectedSkill() != null) {
	            Skill.unselectSpell();
	        } else {
	            //TODO show menu
	        }
	        return;
	    }
	    Runnable action = mSpellLaunch.get(code);
	    if (action != null) {
	        action.run();
	    }
	}
	
	public void update(int delta) {
		MouseButton button;
		
		for (int i = 0; i < BUTTONS_NUMBER; ++i) {
			button = mMouseButtons[i];
			button.update(delta);
			if (mInput.isMouseButtonDown(i) && !button.isPressed()) {
				button.click();
			} else if (!mInput.isMouseButtonDown(i) && button.isPressed()) {
				button.release();
			}
		}
	}
}
