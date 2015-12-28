package alexmog.rulemastersworld.map;

import im.bci.tmxloader.TmxLayer;
import im.bci.tmxloader.TmxLoader;
import im.bci.tmxloader.TmxMap;
import im.bci.tmxloader.TmxTileInstance;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.esotericsoftware.minlog.Log;

public class Map {
    private MapElement[] mMapElements;
    private TmxMap mTmxMap;
    private List<Vector2f> mPlayerSpawnList = new ArrayList<Vector2f>();
    
    public List<Vector2f> getPlayerSpawnList() {
        return mPlayerSpawnList;
    }
    
    public Map(File mapFile) throws IOException {
        TmxLoader loader = new TmxLoader();
        mTmxMap = new TmxMap();
        loader.parseTmx(mTmxMap, loadText(mapFile));
        loader.decode(mTmxMap);
        mMapElements = new MapElement[mTmxMap.getHeight() * mTmxMap.getWidth()];
        for (TmxLayer layer : mTmxMap.getLayers()) {
            for (int x = 0; x < mTmxMap.getWidth(); ++x) {
                for (int y = 0; y < mTmxMap.getHeight(); ++y) {
                    TmxTileInstance tile = layer.getTileAt(x, y);
                    if (tile != null) {
                        MapElement element = getMapElement(x, y);
                        if (element == null) {
                            element = new MapElement(x, y, mTmxMap.getTilewidth(), mTmxMap.getTileheight());
                            setMapElement(x, y, element);
                        }
                        if (!tile.getProperty("blocking", "false").equals("false")) {
                            element.setBlocking(true);
                        } else if (!tile.getProperty("player_spawn", "false").equals("false")) {
                            element.setSpawn(true);
                            mPlayerSpawnList.add(new Vector2f(x * mTmxMap.getTilewidth(), y * mTmxMap.getTileheight()));
                        }
                        
                        Log.debug("MapElementProperty (" + x + ", " + y + ") Blocking: " + element.isBlocking()
                                + ", Spawn: " + element.isSpawn());
                        // TODO add more properties
                    }
                }
            }
        }
    }
    
    public void getTiles(int startX, int startY, int endX, int endY,
            List<MapElement> tiles) {
        tiles.clear();
        for (int y = startY; y <= endY; ++y) {
            for (int x = startX; x <= endX; ++x) {
                MapElement element = getMapElement(x, y);
                if (element != null && element.isBlocking()) {
                    tiles.add(element);
                }
            }
        }
    }
    
    public boolean isSomethingBlocking(Rectangle rectangle) {
    	int x = (int)(rectangle.getX() / mTmxMap.getTilewidth());
    	int y = (int)(rectangle.getY() / mTmxMap.getTileheight());
    	int endX = (int)((rectangle.getX() + rectangle.getWidth()) / mTmxMap.getTilewidth());
    	int endY = (int)((rectangle.getY() + rectangle.getHeight()) / mTmxMap.getTileheight());
    	int mapX = mTmxMap.getWidth();
    	int mapY = mTmxMap.getHeight();
    	
//    	System.out.println(mapX + ", " + mapY);
    	if (x >= mapX || y >= mapY) {
    	    return true;
    	}
    	
    	for (; x < endX; ++x) {
        	for (; y < endY; ++y) {
        	    MapElement m = getMapElement(x, y);
        		if (m != null && m.isBlocking()) {
        			return true;
        		}
        	}
    	}
    	
    	return false;
    }
    
    public TmxMap getTmxMap() {
        return mTmxMap;
    }
    
    private String loadText(File f) throws IOException {
        InputStream is = new FileInputStream(f);
        @SuppressWarnings("resource")
        Scanner s = new Scanner(is, "UTF-8").useDelimiter("\\Z");
        return s.next();
    }
    
/*    public Map(int id, int size_x, int size_y) {
        mId = id;
        mSize_x = size_x;
        mSize_y = size_y;
    }
    
    public void init() throws Exception {
        Log.info("Initializating map elements...");
        map = new MapElement[mSize_x * mSize_y];
        PreparedStatement statement = (PreparedStatement) Main.database
                .prepareStatement("SELECT * FROM map_elements WHERE map_id = ?");
        statement.setInt(1, mId);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            int posX = result.getInt("pos_x");
            int posY = result.getInt("pos_y");
            if (posX < mSize_x && posY < mSize_y) {
                setCase(posX, posY, new MapElement(result.getBoolean("blocking")));
            }
        }
        result.close();
        statement.close();
        Log.info("Initialisating map entities...");
        statement = (PreparedStatement) Main.database
                .prepareStatement("SELECT * FROM map_entities WHERE map_id = ?");
        statement.setInt(1, mId);
        result = statement.executeQuery();
        while (result.next()) {
            String type = result.getString("entity_type");
            int posX = result.getInt("pos_x");
            int posY = result.getInt("pos_y");
            if (type.equals("gameobject")) {
                // TODO do it...
            } else if (type.equals("monster")) {
                // TODO finish it
                int id = result.getInt("entity_id");
                Main.game.getGameMode().getEntityManager().addEntity(EntityFactory.createScriptedEntity(id, posX, posY));
            } else if (type.equals("spawn")) {
                // TODO do it...
            }
        }
        result.close();
        statement.close();
    }*/
    
    public void setMapElement(int x, int y, MapElement value) {
       mMapElements[mTmxMap.getHeight() * y + x] = value;
    }
    
    public MapElement getMapElement(int x, int y) {
        if (x >= 0 && y >= 0 && x < mTmxMap.getWidth() && y < mTmxMap.getHeight()) {
            return mMapElements[mTmxMap.getHeight() * y + x];
        }
        return null;
    }
}
