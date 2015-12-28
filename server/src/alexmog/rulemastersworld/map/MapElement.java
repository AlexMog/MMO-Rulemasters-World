package alexmog.rulemastersworld.map;

import org.newdawn.slick.geom.Rectangle;

public class MapElement {
    private boolean mBlocking = false;
    private boolean mSpawn = false;
    private Rectangle mRect;
    
    public MapElement(int posX, int posY, int width, int height) {
        mRect = new Rectangle(posX * width, posY * height, width, height);
    }
    
    public Rectangle getRectangle() {
        return mRect;
    }
    
    public void setBlocking(boolean blocking) {
        mBlocking = blocking;
    }
    
    public boolean isBlocking() {
        return mBlocking;
    }
    
    public boolean isSpawn() {
        return mSpawn;
    }
    
    public void setSpawn(boolean spawn) {
        mSpawn = spawn;
    }
}
