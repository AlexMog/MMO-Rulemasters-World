package alexmog.rulemastersworld.datas;

public class EffectTemplate {
    private int mId, mScriptId;
    private String mName, mDescription;
    
    public EffectTemplate(int id, int scriptId, String name, String description) {
        mId = id;
        mScriptId = scriptId;
        mName = name;
        mDescription = description;
    }
    
    public int getId() {
        return mId;
    }
    
    public int getScriptId() {
        return mScriptId;
    }
    
    public String getName() {
        return mName;
    }
    
    public String getDescription() {
        return mDescription;
    }
    
}
