package alexmog.rulemastersworld;

import java.io.File;
import java.util.Locale;
import java.util.logging.FileHandler;

import org.newdawn.slick.AppGameContainer;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

import alexmog.rulemastersworld.packets.Network;
import alexmog.rulemastersworld.utils.log.formatters.SimpleFormatter;
import alexmog.rulemastersworld.client.ClientListener;
import alexmog.rulemastersworld.datas.ConfigsManager;
import alexmog.rulemastersworld.datas.Translator;
import alexmog.rulemastersworld.frames.CrashcatchedFrame;
import alexmog.rulemastersworld.frames.LoginFrame;

public class Main {
    /* Too many public statics, but I'm a lazy boy \o/ */
    public static final CrashcatchedFrame crashReporter = new CrashcatchedFrame();
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int FPS = 60;
    public static Client client = null;
    public static final Client hubClient = null;
    public static final LoginFrame loginFrame = new LoginFrame();
    public static final Game game = new Game("Rulemasters World");
    public static int clientId;
    public static int gameId = 1;
    public static AppGameContainer app;
    
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(LoginFrame.uncaughtExceptionHandler);
        try {
            app = new AppGameContainer(Main.game);
	        Main.client = new Client();
	        Network.register(Main.client);
	        Main.client.addListener(new ClientListener(Main.client));
	        
	        ConfigsManager.getInstance().loadConfigs();
	        Translator.getInstance().initBundle(new Locale(ConfigsManager
	                .getInstance().getProperties().getProperty("Translator.language")),
	                ConfigsManager.getInstance().getProperties().getProperty("Translator.directory"));
	        
	        File f = new File(System.getProperty("user.home") + "/rmw/logs/");
	        if (!f.isDirectory()) {
	            f.mkdirs();
	        }
	        FileHandler fh = new FileHandler(System.getProperty("user.home") + "/rmw/logs/%u.%g.log",
	                1024 * 1024, 10, false);
//	        Log.set(Log.LEVEL_DEBUG);
	        fh.setFormatter(new SimpleFormatter());
	        Log.getLogger().getLogger().addHandler(fh);
        
            app.setDisplayMode(Main.WIDTH, Main.HEIGHT, false);
            app.setTargetFrameRate(Main.FPS);
            app.setShowFPS(true);
            app.setAlwaysRender(true);
            app.setUpdateOnlyWhenVisible(false);
            app.start();
//            app.destroy();
        } catch (Exception e) {
            LoginFrame.uncaughtExceptionHandler.uncaughtException(null, e);
        }
    }

}
