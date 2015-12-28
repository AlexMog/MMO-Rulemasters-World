package alexmog.rulemastersworld.managers;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Rectangle;

import com.esotericsoftware.minlog.Log;

import alexmog.rulemastersworld.AccountConnection;
import alexmog.rulemastersworld.Game;
import alexmog.rulemastersworld.effects.Effect;
import alexmog.rulemastersworld.effects.TimedEffect;
import alexmog.rulemastersworld.entity.Entity;
import alexmog.rulemastersworld.entity.LivingEntity;
import alexmog.rulemastersworld.entity.Player;
import alexmog.rulemastersworld.gamemodes.GameMode;
import alexmog.rulemastersworld.packets.EntityEffectAddPacket;
import alexmog.rulemastersworld.packets.EntityMovePacket;
import alexmog.rulemastersworld.packets.EntityPacket;
import alexmog.rulemastersworld.packets.EntityRemovePacket;
import alexmog.rulemastersworld.util.Quadtree;

public class EntityManager extends Manager {
	public EntityManager(GameMode gameMode) {
        super(gameMode);
        mCollisionTree = new Quadtree(0, new Rectangle(0, 0, 
	        gameMode.getGameInstance().getMap().getTmxMap().getWidth() * gameMode.getGameInstance().getMap().getTmxMap().getTilewidth(),
	        gameMode.getGameInstance().getMap().getTmxMap().getHeight() * gameMode.getGameInstance().getMap().getTmxMap().getTileheight()));
    }

    private List<Entity> mEntitiesToAdd = new ArrayList<Entity>();
	private List<Entity> mEntities = new ArrayList<Entity>();
	private Quadtree mCollisionTree; 
	private List<Entity> mCollidedObjects = new ArrayList<Entity>();
	private int mActualId = 0;
	private int mCollisionsCheckNumber = 0;
	
	public void updateEntity(Entity e, long delta) {
	    try {
            // Entity update
            e.update(delta);
            
            // Collision detection
            mCollidedObjects.clear();
            mCollisionTree.collideList(mCollidedObjects, e);
            for (int x = 0; x < mCollidedObjects.size(); ++x) {
                Entity cEntity = mCollidedObjects.get(x);
                if (e.getId() != cEntity.getId()) {
                    if (e.getVision() != null
                            && e.getVision().getRadius() > 0) {
                    	if (e.getVision().intersects(cEntity.getBounds())) {
	                        if (!e.getBounds().intersects(cEntity.getBounds())) {
	                            e.onEntityInVision(cEntity);
	                        } else {
	                            e.onCollision(cEntity);
	                            cEntity.onCollision(e);
	                        }
	                    }
                    } else if (e.getBounds().intersects(cEntity.getBounds())) {
                        e.onCollision(cEntity);
                        cEntity.onCollision(e);                    	
                    }
                }
                mCollisionsCheckNumber += 1;
            }
        } catch (Exception ex) {
            Log.error("EntityManagerUpdate", ex);
        }
	}
	
	@Override
	public void update(Game game, long delta) {
	    synchronized(mEntities) {
	        try {
    	        synchronized(mEntitiesToAdd) {
            	    for (Entity e : mEntitiesToAdd) {
                        try {
                            e.init();
                            mEntities.add(e);
                            sendEntityDatas(e);
                        } catch (Exception ex) {
                            Log.error("EntityManagerUpdate", ex);
                        }
                    }
                    mEntitiesToAdd.clear();
    	        }
        	    mCollisionsCheckNumber = 0;
        	    // Can be optimized?
        	    mCollisionTree.clear();
        	    for (int i = 0; i < mEntities.size(); ++i) {
        	        mCollisionTree.insert(mEntities.get(i));
        	    }
        	    
        	    for (int i = 0; i < mEntities.size();) {
        	        Entity e = mEntities.get(i);
        	        updateEntity(e, delta);
        			if (e.isToDelete()) {
                        EntityRemovePacket p = new EntityRemovePacket();
                        p.id = e.getId();
        			    delEntity(e);
        			    mGameMode.getGameInstance().sendToAllPlayers(p);
        			} else {
        			    ++i;
        			}
        		}
	        } catch (Exception e) {
	            Log.error("EntityLoopException", e);
	        }
        }
	}
	
	public void sendEntityDatas(Entity e) {
	    sendEntityDatas(null, e);
	}
	
	public void sendEntityDatas(AccountConnection ac, Entity e) {
	    EntityPacket p = new EntityPacket();
        p.living = false;
        p.player = false;
        LivingEntity ent = null;
        if (e instanceof LivingEntity) {
            ent = (LivingEntity) e;
            p.actualHp = ent.getHp();
            p.actualMana = 0; // TODO
            p.animation = ent.getAnimationId();
            p.id = ent.getId();
            p.stats = ent.getStats();
            p.name = ent.getName();
            p.skinId = ent.getSkinId();
            p.x = ent.getPosition().x;
            p.y = ent.getPosition().y;
            p.width = ent.getSize().x;
            p.height = ent.getSize().y;
            if (e instanceof Player) {
                p.player = true;
            }
            p.living = true;
        } else {
            p.animation = e.getAnimationId();
            p.x = e.getPosition().x;
            p.y = e.getPosition().y;
            p.width = e.getSize().x;
            p.height = e.getSize().y;
            p.id = e.getId();
            p.skinId = e.getSkinId();
        }
        if (ac == null) {
            mGameMode.getGameInstance().sendToAllPlayers(p);
        } else {
            ac.sendTCP(p);
        }
        if (p.living && ent != null) {
            for (Effect eff : ent.getEffects()) {
                EntityEffectAddPacket packet = new EntityEffectAddPacket();
                packet.entityId = ent.getId();
                if (eff instanceof TimedEffect) {
                    packet.cooldown = ((TimedEffect)eff).getTimer().remaning();
                }
                packet.stacks = eff.getStacks();
                packet.iconId = eff.getIconId();
                packet.id = e.getId();
                packet.name = e.getName();
                packet.description = eff.getDescription();
                if (ac == null) {
                    mGameMode.getGameInstance().sendToAllPlayers(packet);
                } else {
                    ac.sendTCP(packet);
                }
            }
        }
        if (e.isMoving()) {
            EntityMovePacket mp = new EntityMovePacket();
            mp.angle = e.getLastAngle();
            mp.animation = e.getAnimationId();
            mp.id = e.getId();
            mp.velocity = e.getSpeed();
            mp.x = e.getPosition().x;
            mp.y = e.getPosition().y;
            if (ac == null) {
                mGameMode.getGameInstance().sendToAllPlayers(mp);                    
            } else {
                ac.sendTCP(mp);
            }
        }
   	}
	
	public void addEntity(Entity e) {
//	    Log.info("Entity added: " + e.getId());
	    synchronized(mEntitiesToAdd) {
	        mEntitiesToAdd.add(e);
	    }
	}
	
	public void remEntity(Entity e) {
//		mEntities.remove(e);
	    if (e != null) {
	        e.setToDelete(true);
	    }
	}
	
	private void delEntity(Entity e) {
	    synchronized(mEntities) {
	        mEntities.remove(e);
	    }
	}
	
	public Entity getEntityById(int id) {
	    synchronized(mEntities) {
    	    for (Entity e : mEntities) {
    	        if (e.getId() == id) {
    	            return e;
    	        }
    	    }
	    }
	    return null;
	}

    public int generateNewId() {
	    mActualId += 1;
    	return mActualId;
    }
    
    public int getEntityCount() {
        synchronized(mEntities) {
            return mEntities.size();
        }
    }
    
    public int getCollisionsCheckNumber() {
        return mCollisionsCheckNumber;
    }
    
    public List<Entity> getEntityList() {
        return mEntities;
    }

}
