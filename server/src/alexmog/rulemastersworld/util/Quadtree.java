package alexmog.rulemastersworld.util;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import alexmog.rulemastersworld.entity.Entity;

public class Quadtree {
    
    private static int MAX_OBJECTS = 10;
    private static int MAX_LEVELS = 5;
    
    private int mLevel;
    private List<Entity> mObjects;
    private Rectangle mBounds;
    private Quadtree[] mNodes;
    
    public Quadtree(int level, Rectangle bounds) {
        mBounds = bounds;
        mLevel = level;
        mObjects = new ArrayList<Entity>();
        mNodes = new Quadtree[4];
    }
    
    public void clear() {
        mObjects.clear();
        for (int i = 0; i < mNodes.length; ++i) {
            if (mNodes[i] != null) {
                mNodes[i].clear();
                mNodes[i] = null;
            }
        }
    }
    
    private void split() {
        int newWidth = (int) (mBounds.getWidth() / 2);
        int newHeight = (int) (mBounds.getHeight() / 2);
        int x = (int)mBounds.getX();
        int y = (int)mBounds.getY();
        
        mNodes[0] = new Quadtree(mLevel + 1, new Rectangle(x + newWidth, y, newWidth, newHeight));
        mNodes[1] = new Quadtree(mLevel + 1, new Rectangle(x, y, newWidth, newHeight));
        mNodes[2] = new Quadtree(mLevel + 1, new Rectangle(x, y + newHeight, newWidth, newHeight));
        mNodes[3] = new Quadtree(mLevel + 1, new Rectangle(x + newWidth, y + newHeight, newWidth, newHeight));
    }

    private int getIndex(Entity entity) {
        Shape rect = (entity.getVision() == null ? entity.getBounds() : entity.getVision());
        int index = -1;
        double verticalMidPoint = mBounds.getX() + (mBounds.getWidth() / 2);
        double horizontalMidPoint = mBounds.getY() + (mBounds.getHeight() / 2);
        
        // Fits the top quadrant
        boolean topQuad = rect.getY() < horizontalMidPoint && rect.getY() + rect.getHeight() < horizontalMidPoint;
        // Fits the bottom quadrant
        boolean botQuad = rect.getY() > horizontalMidPoint;
        
        // Fits the left quadrant
        if (rect.getX() < verticalMidPoint && rect.getX() + rect.getWidth() < verticalMidPoint) {
            if (topQuad) {
                index = 1;
            } else if (botQuad) {
                index = 2;
            }
        // Fits the right quadrant
        } else if (rect.getX() > verticalMidPoint) {
            if (topQuad) {
                index = 0;
            } else if (botQuad) {
                index = 3;
            }
        }
        return index;
    }
    
    public void insert(Entity entity) {
        if (mNodes[0] != null) {
            int index = getIndex(entity);
            
            if (index != -1) {
                mNodes[index].insert(entity);
                return;
            }
        }
        
        mObjects.add(entity);
        
        if (mObjects.size() > MAX_OBJECTS && mLevel < MAX_LEVELS) {
            if (mNodes[0] == null) {
                split();
            }
            
            int i = 0;
            while (i < mObjects.size()) {
                int index = getIndex(mObjects.get(i));
                if (index != -1) {
                    mNodes[index].insert(mObjects.remove(i));
                } else {
                    ++i;
                }
            }
        }
    }
    
    public List<Entity> collideList(List<Entity> returnObjects, Entity entity) {
        int index = getIndex(entity);
        if (index != -1 && mNodes[0] != null) {
            mNodes[index].collideList(returnObjects, entity);
        }
        returnObjects.addAll(mObjects);
        return returnObjects;
    }
}
