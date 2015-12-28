package alexmog.rulemastersworld.datas;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.newdawn.slick.SlickException;

import alexmog.rulemastersworld.Main;

public class ConfigsManager {
    private static final String CONFIGS_PATH = "res/configs.cfg";
    private Properties mProperties = new Properties();
    private static final ConfigsManager instance = new ConfigsManager();
    
    private ConfigsManager() {}
    
    public static ConfigsManager getInstance() {
        return instance;
    }

    public void loadConfigs() throws Exception {
        FileInputStream s = null;
        try {
            s = new FileInputStream(CONFIGS_PATH);
            mProperties.load(s);
        } finally {
            s.close();
        }
    }
    
    public Properties getProperties() {
        return mProperties;
    }
    
    public void applicateConfigs() throws SlickException {
        Main.app.setMusicVolume((float)Integer.parseInt(mProperties.getProperty("SoundManager.Music.volume")) / 100);
        Main.app.setSoundVolume((float)Integer.parseInt(mProperties.getProperty("SoundManager.Sound.volume")) / 100);
        Main.app.setShowFPS(Boolean.parseBoolean(mProperties.getProperty("UIManager.showfps")));
        Main.app.setTargetFrameRate(Integer.parseInt(mProperties.getProperty("GraphicManager.maxfps")));
        Main.app.setVSync(Boolean.parseBoolean(mProperties.getProperty("GraphicManager.vsync")));
        Main.app.setFullscreen(Boolean.parseBoolean(mProperties.getProperty("GraphicManager.fullscreen")));
        Main.game.getGameMode().setDebug(Boolean.parseBoolean(mProperties.getProperty("UIManager.showdebug")));
    }
    
    public void saveConfigs() throws Exception {
        FileOutputStream o = null;
        try {
            o = new FileOutputStream(CONFIGS_PATH);
            mProperties.store(o, "Game configs");
            applicateConfigs();
        } finally {
            o.close();
        }
    }
    
    public static ConfigType getConfigType(String value) {
    	if (value.startsWith("KEY_")) {
    		return ConfigType.Key;
    	}
    	if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
    		return ConfigType.Boolean;
    	}
    	try {
    		Integer.parseInt(value);
    		return ConfigType.Integer;
    	} catch(Exception e) {}
    	return ConfigType.String;
    }
}
