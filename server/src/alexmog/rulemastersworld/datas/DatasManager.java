package alexmog.rulemastersworld.datas;

import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.minlog.Log;

import alexmog.rulemastersworld.map.MapInstance;

public class DatasManager {
    private static DatasManager instance = new DatasManager();
    private Map<Integer, ScriptBean> mScripts = new HashMap<Integer, ScriptBean>();
    private Map<Integer, EntityTemplate> mEntityTemplates = new HashMap<Integer, EntityTemplate>();
    private Map<Integer, EffectTemplate> mEffectTemplates = new HashMap<Integer, EffectTemplate>();
    private Map<Integer, EffectBean> mEffectBeans = new HashMap<Integer, EffectBean>();
    private Map<Integer, SkillBean> mSkillBeans = new HashMap<Integer, SkillBean>();
    private Map<String, MapInstance> mMaps = new HashMap<String, MapInstance>();
    
    public static DatasManager getInstance() {
        return instance;
    }
    
    private DatasManager() {}

    public Map<Integer, SkillBean> getSkillBeans() {
        return mSkillBeans;
    }
    
    public Map<String, MapInstance> getMaps() {
        return mMaps;
    }
    
    public Map<Integer, ScriptBean> getScripts() {
        return mScripts;
    }
    
    public Map<Integer, EntityTemplate> getEntityTemplates() {
        return mEntityTemplates;
    }
    
    public Map<Integer, EffectTemplate> getEffectTemplates() {
        return mEffectTemplates;
    }
    
    public Map<Integer, EffectBean> getEffectBeans() {
        return mEffectBeans;
    }
    
    public void initScripts() {
        Log.info("Initialisating Scripts...");
        for (ScriptBean s : mScripts.values()) {
            try {
                Log.info("Initialisation of the script '" + s.getName() + "'(" + s.getId() + ")...");
                s.init();
            } catch (Exception e) {
                Log.info("Cannot load this script", e);
            }
        }
        Log.info("Script initialisation done.");
    }
}
