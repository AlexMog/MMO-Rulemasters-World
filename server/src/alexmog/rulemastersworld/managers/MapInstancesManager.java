package alexmog.rulemastersworld.managers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.minlog.Log;
import com.google.gson.Gson;

import alexmog.rulemastersworld.map.MapInstance;

public class MapInstancesManager {
    private static MapInstancesManager instance = new MapInstancesManager();
    private Map<Integer, MapInstance> mMapInstances = new HashMap<Integer, MapInstance>();
    
    public static MapInstancesManager getInstance() {
        return instance;
    }
    
    public int getInstanceNumber() {
        return mMapInstances.size();
    }
    
    public Map<Integer, MapInstance> getMapInstances() {
        return mMapInstances;
    }
    
    private class WorldBean {
        public String name, serverFile, clientFile;
        public int id;
    }
    
    public void init() throws Exception {
        Gson gson = new Gson();
        try (FileReader f = new FileReader("worlds.json");
            BufferedReader br = new BufferedReader(f);) {
            WorldBean[] worldBeans = gson.fromJson(br, WorldBean[].class);
            for (WorldBean bean : worldBeans) {
                MapInstance instance = new MapInstance(bean.id, bean.name, bean.serverFile, bean.clientFile);
                try {
                    Log.info("Initialisating map '" + bean.name + "'(" + bean.id + "'...");
                    instance.init();
                    mMapInstances.put(bean.id, instance);
                } catch (Exception e) {
                    Log.info("Error on initialisation of the map '" + bean.name +"'(" + bean.id +"). Do not add it to the map instances list.", e);
                }
            }
        }
        if (mMapInstances.get(0) == null) {
            throw new Exception("No default map instance found.");
        }
    }
    
    public MapInstance getMapInstance(int id) {
        return mMapInstances.get(id);
    }
    
    public void stop() {
        Log.info("Stopping map instances...");
        for (MapInstance i : mMapInstances.values()) {
            i.stop();
        }
        Log.info("Map instances stopped.");
    }
    
    public void start() {
        Log.info("Starting the maps instances...");
        for (MapInstance i : mMapInstances.values()) {
            Log.info("Starting map instance '" + i.getName() + "'...");
            i.start();
        }
        Log.info("Map instances started.");
    }
}
