package alexmog.rulemastersworld;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;

import org.apache.commons.dbcp2.BasicDataSource;
import org.mozilla.javascript.Context;

import alexmog.rulemastersworld.datas.DBManager;
import alexmog.rulemastersworld.datas.DatasManager;
import alexmog.rulemastersworld.jssandbox.SandboxContextFactory;
import alexmog.rulemastersworld.managers.MapInstancesManager;
import alexmog.rulemastersworld.managers.ServiceManager;
import alexmog.rulemastersworld.packets.Network;
import alexmog.rulemastersworld.server.ServerListener;
import alexmog.rulemastersworld.tchat.commands.CommandInterpretator;
import alexmog.rulemastersworld.utils.log.formatters.SimpleFormatter;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class Main {
    public static Server server;
    public static final List<AccountConnection> clientList = new ArrayList<AccountConnection>();
    public static final BasicDataSource accountDb = new BasicDataSource();
    public static final BasicDataSource worldDb = new BasicDataSource();
    public static final BasicDataSource sharedDb = new BasicDataSource();
    public static final Random random = new Random();
    private static Context mContext;
    
    public static Context getJSContext() {
        return mContext;
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        ConsoleHandler ch = new ConsoleHandler();
        ch.setFormatter(new SimpleFormatter());
        FileHandler fh = new FileHandler("logs/%u.%g.log",
                1024 * 1024, 10, false);
        fh.setFormatter(new SimpleFormatter());

        Log.getLogger().getLogger().setUseParentHandlers(false);
        Log.getLogger().getLogger().addHandler(fh);
        Log.getLogger().getLogger().addHandler(ch);
        
        if (args.length >= 1) {
            for (String e : args) {
                if (e.equals("-v")) {
                    Log.set(Log.LEVEL_DEBUG);
                }
            }
        }
        
        Properties configs = new Properties();
        configs.load(new FileInputStream("configs.cfg"));
        
        /*
        if (mapId == 0) {
            System.err.println("No map to load set.");
            System.exit(1);
        }
        */
        
        server = new Server() {
            protected Connection newConnection() {
                return new AccountConnection();
            }
        };
        
        Properties prop = new Properties();
        prop.load(new FileInputStream("db.cfg"));
        try {
            Log.info("Init server...");
            Log.info("Initialisating JS context...");
            SandboxContextFactory contextFactory = new SandboxContextFactory();
            mContext = contextFactory.makeContext();
            contextFactory.enterContext(mContext);
            Log.info("Init Account DB params");
            accountDb.setDriverClassName("com.mysql.jdbc.Driver");
            accountDb.setUrl("jdbc:mysql://" + prop.getProperty("account_server")
                    + "/" + prop.getProperty("account_db"));
            accountDb.setUsername(prop.getProperty("account_user"));
            accountDb.setPassword(prop.getProperty("account_password"));
            Log.info("Init World DB params");
            worldDb.setDriverClassName("com.mysql.jdbc.Driver");
            worldDb.setUrl("jdbc:mysql://" + prop.getProperty("world_server")
                    + "/" + prop.getProperty("world_db"));
            worldDb.setUsername(prop.getProperty("world_user"));
            worldDb.setPassword(prop.getProperty("world_password"));
            Log.info("Init Shared DB params");
            sharedDb.setDriverClassName("com.mysql.jdbc.Driver");
            sharedDb.setUrl("jdbc:mysql://" + prop.getProperty("shared_server")
                    + "/" + prop.getProperty("shared_db"));
            sharedDb.setUsername(prop.getProperty("shared_user"));
            sharedDb.setPassword(prop.getProperty("shared_password"));
            
            Log.info("Connecting to Account DB...");
            accountDb.getConnection().close();
            Log.info("Connected to account DB!");
            Log.info("Connecting to World DB...");
            worldDb.getConnection().close();
            Log.info("Connected to world DB!");
            Log.info("Connecting to Shared DB...");
            sharedDb.getConnection().close();
            Log.info("Connected to Shared DB!");
            
            // Db loads
            DBManager.getInstance().initScripts();
            DBManager.getInstance().initEffectTemplates();
            DBManager.getInstance().initEntityTemplates();
            DBManager.getInstance().initEffects();
            DBManager.getInstance().initSkills();
            
            // Initialisation of datas
            DatasManager.getInstance().initScripts();
        
            Log.info("Init game...");
            MapInstancesManager.getInstance().init();
            
            Log.info("Network registeries...");
            Network.register(server);
            
            Log.info("Init finished. Starting game logic...");
            MapInstancesManager.getInstance().start();
            
            Log.info("Starting services...");
            ServiceManager.init();
            
            Log.info("Starting server listeners.");
            server.addListener(new ServerListener());
            server.bind(Network.port);
            server.start();
            
            Log.info("Server ready.");
        } catch (Exception e) {
            Log.error("Server init", e);
            System.exit(1);
        }
//        System.exit(0);
        try (
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(isr);
            ) {
            CommandInterpretator interpretator = new CommandInterpretator();
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    int ind = line.indexOf(' ');
                    if (ind < 0) {
                        ind = line.length();
                    }
                    interpretator.interpretateConsole(line.substring(0, ind),
                            line.substring(line.indexOf(' ') + 1));
                } catch (Exception e) {
                    Log.info("ConsoleCommand", e);
                }
            }
        }
    }
    
    public static void sendToAllAuthenticated(Object o) {
        synchronized(clientList) {
            for (AccountConnection a : clientList) {
                try {
                    a.sendTCP(o);
                } catch (Exception e) {
                    Log.error("SendToAllAuthenticated", e);
                }
            }
        }
    }

}
