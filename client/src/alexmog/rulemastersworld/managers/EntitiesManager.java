package alexmog.rulemastersworld.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.datas.DatasManager;
import alexmog.rulemastersworld.entity.Entity;
import alexmog.rulemastersworld.entity.MouseEntity;
import alexmog.rulemastersworld.entity.OnlineEntity;
import alexmog.rulemastersworld.util.Quadtree;

public class EntitiesManager extends Manager {
	private List<Entity> mEntitiesToAdd = new ArrayList<Entity>();
	private Map<Integer, Entity> mEntities = new HashMap<Integer, Entity>();
	private Quadtree mCollisionTree = new Quadtree(0, new Rectangle(0, 0, 10, 10));
	private List<Entity> mCollidedObjects = new ArrayList<Entity>();
	private MouseEntity mMouseEntity = null;
	private int mActualId = 0;
	private int mCollisionsCheckNumber = 0;
	private List<Integer> mEntitiesToRemove = new ArrayList<Integer>();
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
	    synchronized(mEntities) {
		    synchronized(mEntitiesToAdd) {
	            for (Entity e : mEntitiesToAdd) {
	                try {
	                    e.init();
	                    mEntities.put(e.getId(), e);
	                } catch (Exception ex) {
	                    ex.printStackTrace();
	                }
	            }
	            mEntitiesToAdd.clear();
	        }
		    synchronized(mEntitiesToRemove) {
		    	while (!mEntitiesToRemove.isEmpty()) {
		    		mEntities.remove(mEntitiesToRemove.remove(0));
		    	}
		    }
	    
    	    mCollisionsCheckNumber = 0;
    	    // Can be optimized?
    	    mCollisionTree.clear();
            Entity[] entities = (Entity[]) mEntities.values().toArray(new Entity[0]);
    	    for (int i = 0; i < entities.length; ++i) {
    	        mCollisionTree.insert(entities[i]);
    	    }
    	    
    	    for (int i = 0; i < entities.length;) {
    	        Entity e = entities[i];
    	        try {
        	        // Entity update
        	        e.update(delta);
        	        
        	        // Collision detection
        	        mCollidedObjects.clear();
                    mCollisionTree.collideList(mCollidedObjects, e);
                    for (int x = 0; x < mCollidedObjects.size(); ++x) {
                        Entity cEntity = mCollidedObjects.get(x);
                        if (e.getId() != cEntity.getId()) {
                            if (e.getVision() == null
                                    || e.getVision().getRadius() <= 0 || e.getVision().intersects(cEntity.getBounds())) {
                                if (!e.getBounds().intersects(cEntity.getBounds())) {
                                    e.onEntityInVision(cEntity);
                                } else {
                                    e.onCollision(cEntity);
                                    cEntity.onCollision(e);
                                }
                            }
                        }
                        mCollisionsCheckNumber += 1;
                    }
    	        } catch (Exception ex) {
    	            ex.printStackTrace();
    	        }
    	        
//    			if (e.isToDelete()) {
//    			    remEntity(e);
//    			} else {
    			    ++i;
//    			}
    		}
	    }
	}
	
	public void reset() {
	    mActualId = 0;
	    mEntitiesToAdd.clear();
	    mEntities.clear();
	    mCollisionTree = new Quadtree(0, new Rectangle(0, 0, 
	            DatasManager.getInstance().getMap().getTiledMap().getWidth() * DatasManager.getInstance().getMap().getTiledMap().getTileWidth(),
	            DatasManager.getInstance().getMap().getTiledMap().getHeight() * DatasManager.getInstance().getMap().getTiledMap().getTileHeight()));
	    mCollidedObjects.clear();
	    mMouseEntity = null;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
	    synchronized(mEntities) {
			for (Entity e : mEntities.values()) {
			    if (!e.skinSetted()) {
			        e.setSkin();
			    }
				e.render(g);
			}
	    }
	    if (Main.game.getGameMode().isDebug()) {
			    g.setColor(Color.red);
		    drawQuadTree(mCollisionTree, g);
		    g.setColor(Color.white);
		}
	}
	
	private void drawQuadTree(Quadtree q, Graphics g) {
	    if (q != null) {
    	    if (q.getBounds() != null) {
    	        g.draw(q.getBounds());
    	    }
    	    if (q.getNodes() != null) {
        	    for (Quadtree q2 : q.getNodes()) {
        	        drawQuadTree(q2, g);
        	    }
    	    }
	    }
	}
	
	public void addEntity(Entity e) {
	    synchronized(mEntitiesToAdd) {
	        mEntitiesToAdd.add(e);
	    }
	}
	
	public void addEntity(MouseEntity e) {
		mMouseEntity = e;
		this.addEntity((Entity)e);
	}
	
	public void remEntity(int id) {
	    synchronized (mEntitiesToRemove) {
	        mEntitiesToRemove.add(id);
        }
	}
	
	public void remEntity(Entity e) {
	    remEntity(e.getId());
	}
	
	public Entity getEntityById(int id) {
	    Entity e = null;
	    synchronized(mEntities) {
    	    e = mEntities.get(id);
	    }
	    if (e == null) {
	        synchronized(mEntitiesToAdd) {
	            for (Entity en : mEntitiesToAdd) {
	                if (en.getId() == id) {
	                    e = en;
	                }
	            }
	        }
	    }
	    return e;
	}
	
	public MouseEntity getMouseEntity() {
		return mMouseEntity;
	}

    public int generateNewId() {
		mActualId += 1;
    	return mActualId;
    }
    
    public int getEntityCount() {
        return mEntities.size();
    }
    
    public int getCollisionsCheckNumber() {
        return mCollisionsCheckNumber;
    }
    
    public Entity[] getEntityTab() {
        return (Entity[]) mEntities.values().toArray(new Entity[0]);
    }
    
    public OnlineEntity getPlayerEntity() {
    	return (OnlineEntity)this.getEntityById(Main.clientId);
    }

}
