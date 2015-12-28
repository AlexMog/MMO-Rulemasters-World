package alexmog.rulemastersworld.entity;

import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.gamemodes.GameMode;
import alexmog.rulemastersworld.skills.Skill;

public class Player extends LivingEntity {
	private Entity mTarget;
	private Skill mSelectedSkill = null;
	
	public Player(Vector2f position, Vector2f size, int hp, GameMode gameMode, int maxHp) {
		super(position, size, hp, maxHp, gameMode);
		mSkinParts = new String[1];
		mSkinParts[0] = "body/Female/WhiteElf.png";
		mSpeed = 1.5f;
		mMoving = false;
		mName = "Player";
	}
	
	public Skill getSelectedSkill() {
		return mSelectedSkill;
	}
	
	@Override
	public void update(int delta) {
	    if (mTarget != null && mTarget.isToDelete()) {
	        mTarget = null;
	    }
	    saveLastPos();
				
        super.update(delta);
	}
	
	public void setTarget(Entity e) {
	    mTarget = e;
	}
}
