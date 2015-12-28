package alexmog.rulemastersworld.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.packets.GotoPosPacket;
import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.datas.DatasManager;
import alexmog.rulemastersworld.events.EventsManager;
import alexmog.rulemastersworld.gamemodes.GameMode;
import alexmog.rulemastersworld.map.Map;
import alexmog.rulemastersworld.scenes.GameScene;
import alexmog.rulemastersworld.skills.Skill;

public class MouseEntity extends Entity {
	private LivingEntity mCollidedEntity = null;
	private Circle mSpellRange = new Circle(0, 0, 0);
	private Image mMouseCursorNormal, mCurrentCursor;
	
    public MouseEntity(Vector2f position, Vector2f size, GameMode gameMode) {
        super(position, size, gameMode);
    }
    
    public void setCursorFromSkill(Skill skill) {
        mSpellRange.setRadius(skill.getRange() * Map.access().getTiledMap().getTileHeight());
    }
    
    public void resetDefaultCursor() {
        mCurrentCursor = mMouseCursorNormal;
    }
    
    @Override
    public void init() {
        mMouseCursorNormal = (Image)DatasManager.getInstance().getFile("MouseCursor.default");
        resetDefaultCursor();
        super.init();
    }

    @Override
    public void update(int delta) {
    	EventsManager eventsManager = EventsManager.getInstance();
    	
        mPosition.x = eventsManager.getAbsoluteMouseX();
        mPosition.y = eventsManager.getAbsoluteMouseY();
        mPosition.sub(mGameMode.getCamera().getAbsoluteLookedPosition());
    	if (eventsManager.getMouseButton(1).isJustClicked()) {
    	    GotoPosPacket p = new GotoPosPacket();
    	    p.x = mPosition.x;
    	    p.y = mPosition.y;
    	    Main.client.sendTCP(p);
    	}

    	if (eventsManager.getMouseButton(0).isJustClicked() && eventsManager.getAbsoluteMouseY() <= 720) {
    		Skill s = Skill.getSelectedSkill();
    		if (s != null) {
    			if (s.getNextUsage() <= 0) {
    				if (s.use(mCollidedEntity, mPosition)) {
            			s.unselect();
    				}
    			} else {
    				// TODO add an error message
    				GameScene.chat.appendRow("default", "ERROR: Skill not ready.");
    			}
    		}
    	}
    	mCollidedEntity = null;
        super.update(delta);
    }
    
    @Override
    public void render(Graphics g) {
    }
    
    public void renderEnd(Graphics g) {
        super.render(g);
        Skill s = Skill.getSelectedSkill();
        if (s != null) {
            Entity playerEntity = mGameMode.getEntitiesManager().getPlayerEntity();// TODO NOT OPTIMIZED!!
            mSpellRange.setCenterX(playerEntity.getPosition().x);
            mSpellRange.setCenterY(playerEntity.getPosition().y);
            g.setColor(Color.red);
            g.draw(mSpellRange);
            g.setColor(Color.white);
        }
        if (mCurrentCursor != null) {
            mCurrentCursor.draw(mPosition.x,
                    mPosition.y);
        }
    }
    
    @Override
    public void onCollision(Entity collidedEntity) {
    	if (collidedEntity instanceof LivingEntity) {
    		mCollidedEntity = (LivingEntity) collidedEntity;
    	}
        super.onCollision(collidedEntity);
    }
}
