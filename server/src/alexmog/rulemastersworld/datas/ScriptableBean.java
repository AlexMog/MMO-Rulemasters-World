package alexmog.rulemastersworld.datas;

import java.util.HashMap;
import java.util.Map;

public class ScriptableBean {
    private Map<String, Object> mCustomVars = new HashMap<String, Object>();

    public Map<String, Object> getCustomVars() {
        return mCustomVars;
    }
    
    public void addCustomVar(String key, Object value) {
        mCustomVars.put(key, value);
    }
}
