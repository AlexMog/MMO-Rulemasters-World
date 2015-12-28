package alexmog.rulemastersworld.datas;

import java.util.ArrayList;
import java.util.List;
import org.mozilla.javascript.ScriptableObject;

import alexmog.rulemastersworld.Main;

public class ScriptBean {
    private int mId, mAuthorId;
    private String mScript, mName, mDescription;
    private ScriptableObject mScope;
    private boolean mTemplate;
    private String mType;
    private List<CustomVarBean> mCustomVars = new ArrayList<CustomVarBean>();
    
    public ScriptBean(int id, String script, String name, String description,
            boolean template, int authorId, String type) {
        mId = id;
        mScript = script;
        mName = name;
        mDescription = description;
        mTemplate = template;
        mAuthorId = authorId;
        mType = type;
    }
    
    public void addCustomVar(CustomVarBean bean) {
        mCustomVars.add(bean);
    }
    
    public List<CustomVarBean> getCustomVars() {
        return mCustomVars;
    }
    
    public int getAuthorId() {
        return mAuthorId;
    }
    
    public boolean isTemplate() {
        return mTemplate;
    }
    
    public String getType() {
        return mType;
    }
    
    public void init() throws Exception {
        ScriptableObject prototype = Main.getJSContext().initStandardObjects();
        prototype.setParentScope(null);
        mScope = (ScriptableObject) Main.getJSContext().newObject(prototype);
        mScope.setPrototype(prototype);
        Main.getJSContext().evaluateString(mScope, mScript, mName, 1, null);
    }
    
    public String getName() {
        return mName;
    }
    
    public String getDescription() {
        return mDescription;
    }
    
    public ScriptableObject getScope() {
        return mScope;
    }
    
    public int getId() {
        return mId;
    }
}
