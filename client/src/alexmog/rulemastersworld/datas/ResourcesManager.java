package alexmog.rulemastersworld.datas;

import java.io.FileInputStream;
import java.util.Map.Entry;
import java.util.Properties;

import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import com.esotericsoftware.minlog.Log;

public class ResourcesManager {
    private static final String RESOURCES_PATH = "res/resourceslist.cfg";
    private Properties mProperties = new Properties();
    private static final ResourcesManager instance = new ResourcesManager();
    
    private ResourcesManager() {}
    
    public static ResourcesManager getInstance() {
        return instance;
    }
    
    public void loadResources() throws Exception {
        FileInputStream s = null;
        try {
            s = new FileInputStream(RESOURCES_PATH);
            mProperties.load(s);
            setResources();
        } finally {
            s.close();
        }
    }
    
    private void setResources() throws SlickException {
        for (Entry<Object, Object> e : mProperties.entrySet()) {
            String key = (String)e.getKey();
            String value = (String)e.getValue();
            
            if (key.startsWith("Music")) {
                DatasManager.getInstance().addFile(key,
                        new Music(ConfigsManager.getInstance().getProperties().getProperty("Resources.musicdir") + value));                
            } else if (key.startsWith("Sound")) {
                DatasManager.getInstance().addFile(key,
                        new Sound(ConfigsManager.getInstance().getProperties().getProperty("Resources.sounddir") + value));
            } else if (key.startsWith("Image")) {
                Image img = new Image(ConfigsManager.getInstance().getProperties().getProperty("Resources.imagesdir") + value);
                DatasManager.getInstance().addFile(key,
                        img);
            }
            
            Log.info("Added file to load: Key(" + key + ") Value(" + value + ")");
        }
    }
}
