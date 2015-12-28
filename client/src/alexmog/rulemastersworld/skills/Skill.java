package alexmog.rulemastersworld.skills;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.maths.MathsHelper;
import alexmog.rulemastersworld.packets.PlayerUseSpellPacket;
import alexmog.rulemastersworld.packets.skills.TargetType;
import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.datas.DatasManager;
import alexmog.rulemastersworld.entity.LivingEntity;
import alexmog.rulemastersworld.gamemodes.GameMode;
import alexmog.rulemastersworld.map.Map;
import alexmog.rulemastersworld.scenes.GameScene;

public class Skill {
    private static Skill sSelected = null;
    
    public static Skill getSelectedSkill() {
    	return sSelected;
    }
    
    
    protected GameMode mGameMode;
    protected String mName;
    protected long mCooldown;
    protected String mDescription;
    protected Image mIcon;
    protected int mId, mSpellSlot, mRange;
    protected boolean mPassive;
    protected long mNextUsage = 0;
    protected TargetType mTargetType;
    protected int mKeySpell;
    
    public Skill(int id, GameMode gameMode, String name, String description, long cooldown, int iconId,
    				boolean passive, TargetType target, int range) {
        mName = name;
        mGameMode = gameMode;
        mDescription = description;
        mCooldown = cooldown;
        mIcon = (Image) DatasManager.getInstance().getFile("Image.Icon_" + iconId);
        mPassive = passive;
        mId = id;
        mTargetType = target;
        mRange = range;
    }
    
    public int getKeySpell() {
        return mKeySpell;
    }
    
    public void setKeySpell(int keySpell) {
        mKeySpell = keySpell;
    }
    
    public int getRange() {
        return mRange;
    }
    
    public TargetType getTargetType() {
    	return mTargetType;
    }
    
    public boolean use(LivingEntity entity, Vector2f pos) {
    	if (mNextUsage <= 0) {
    		PlayerUseSpellPacket p = new PlayerUseSpellPacket();
    		p.entityId = -1;
    		Vector2f pos2 = new Vector2f();
    		if (mTargetType == TargetType.Entity && entity != null) {
    			p.entityId = entity.getId();
    			pos2.x = entity.getPosition().x;
    			pos2.y = entity.getPosition().y;
    		} else if (mTargetType == TargetType.Zone && pos != null) {
    			p.posX = pos.x;
    			p.posY = pos.y;
    			pos2.x = pos.x;
    			pos2.y = pos.y;
    		} else { // TODO add more possibilities
    		    GameScene.chat.appendRow("default", "ERROR: Bad entity selected");
    			return false;
    		}
    		if (MathsHelper.getDistance(Main.game.getGameMode().getEntitiesManager().getPlayerEntity().getPosition(), pos2)
    		        > mRange * Map.access().getTiledMap().getTileHeight()) {
    		    GameScene.chat.appendRow("default", "ERROR: Not in range.");
    		    return false;
    		}
    		p.spellid = mId;
    		Main.client.sendTCP(p);
    		mNextUsage = mCooldown;
    	}
    	return true;
    }
    
    public boolean use(LivingEntity entity) {
    	return use(entity, null);
    }
    
    public boolean use(Vector2f pos) {
    	return use(null, pos);
    }
    
    public void update(long delta) {
    	if (mNextUsage > 0) {
    		mNextUsage -= delta;
    	}
    	if (mNextUsage < 0) {
    		mNextUsage = 0;
    	}
    }
    
    public long getNextUsage() {
    	return mNextUsage;
    }
    
    public void select() {
    	sSelected = this;
    	mGameMode.getEntitiesManager().getMouseEntity().setCursorFromSkill(this);
    }
    
    public void unselect() {
    	sSelected = null;
    	mGameMode.getEntitiesManager().getMouseEntity().resetDefaultCursor();
    }
    
    public static void unselectSpell() {
        sSelected = null;
        Main.game.getGameMode().getEntitiesManager().getMouseEntity().resetDefaultCursor();
    }

	public int getId() {
		return mId;
	}
	
	public String getName() {
		return mName;
	}
	
	public String getDescription() {
		return mDescription;
	}
	
	public long getCooldown() {
		return mCooldown;
	}
	
	public Image getIcon() {
		return mIcon;
	}
	
	public boolean isPassive() {
		return mPassive;
	}
}
