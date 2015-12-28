package alexmog.rulemastersworld.map;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import alexmog.rulemastersworld.datas.DatasManager;

public class Map {
	private final TiledMap	mTiledMap;
	
	public static Map access() {
		return (DatasManager.getInstance().getMap());
	}
	
	public TiledMap getTiledMap() {
	    return mTiledMap;
	}
	
	public Map(String path) throws SlickException {
		mTiledMap = new TiledMap("./" + path);
	}

	public void renderBackground(Graphics g) {
	    if (mTiledMap.getLayerCount() > 0) {
	        mTiledMap.render(0, 0, 0);
	    }
	    if (mTiledMap.getLayerCount() > 1) {
            mTiledMap.render(0, 0, 1);
        }
	}
	
	public void renderForeground(Graphics g) {
	    for (int i = 2; i < mTiledMap.getLayerCount(); ++i) {
	        mTiledMap.render(0, 0, i);
	    }
	}
}
