package alexmog.rulemastersworld.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.esotericsoftware.minlog.Log;

import alexmog.rulemastersworld.server.services.ConnectedStatusService;
import alexmog.rulemastersworld.server.services.IService;
import alexmog.rulemastersworld.server.services.Service;

public class ServiceManager {
    private static ServiceManager instance;
    private Map<String, IService> mServices = new HashMap<String, IService>();
    private List<Thread> mServiceThreads = new ArrayList<Thread>();
    
    private ServiceManager() {
        createServices();
        startServices();
    }
    
    public static ServiceManager getInstance() {
        return instance;
    }
    
    public static void init() {
        instance = new ServiceManager();
    }
    
    private void createServices() {
        mServices.put("LoginService", new Service());
        mServices.put("DisconnectService", new Service());
        mServices.put("ConnectionUpdateService", new ConnectedStatusService(30000));
    }
    
    private void startServices() {
        for (Entry<String, IService> e : mServices.entrySet()) {
            Log.info("Starting service " + e.getKey() + "...");
            Thread t = new Thread(e.getValue(), e.getKey() + "Thread");
            mServiceThreads.add(t);
            t.start();
        }
    }
    
    public IService getService(String service) {
        return mServices.get(service);
    }
    
    public void stopServices() {
        for (IService e : mServices.values()) {
            e.stopRun();
        }
    }
}
