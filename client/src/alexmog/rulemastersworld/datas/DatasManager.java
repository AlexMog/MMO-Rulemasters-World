package alexmog.rulemastersworld.datas;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.newdawn.slick.SlickException;

import alexmog.rulemastersworld.packets.EntityPacket;

public class DatasManager {
	private final static String MAP_NAME = "__MAP__";
	
    private static DatasManager instance = new DatasManager();
    private Map<String, Object> mFiles = new HashMap<String, Object>();
    private Queue<Object> mActions = new LinkedList<Object>();
    
    private DatasManager() {}
    
    public void addAction(Object action) {
        synchronized(mActions) {
            mActions.add(action);
        }
    }
    
    public Queue<Object> getActionsList() {
    	return mActions;
    }
    
    public Object getNextAction() {
        synchronized(mActions) {
            if (mActions.size() > 0) {
                return mActions.poll();
            }
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
	public void sortActions() {
    	Collections.sort((List<Object>) mActions, new Comparator<Object>() {

			public int compare(Object o1, Object o2) {
				if (o1 instanceof EntityPacket && !(o2 instanceof EntityPacket)) {
					return -1;
				}
				return 0;
			}
		});
    }
    
    public static DatasManager getInstance() {
        return instance;
    }
    
    public void addFile(String name, Object file) {
        mFiles.put(name, file);
    }
    
    public void addMap(String fileName) throws SlickException {
    	this.addFile(MAP_NAME, new alexmog.rulemastersworld.map.Map(fileName));
    }
    
    @SuppressWarnings("unchecked")
	public <T> T getFile(String name) {
        return (T)mFiles.get(name);
    }
    
    public alexmog.rulemastersworld.map.Map getMap() {
    	return (this.getFile(MAP_NAME));
    }
    
    public Map<String, Object> getAllFiles() {
        return mFiles;
    }
}
