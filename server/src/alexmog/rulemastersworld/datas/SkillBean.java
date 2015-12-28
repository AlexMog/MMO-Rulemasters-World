package alexmog.rulemastersworld.datas;

import java.util.ArrayList;
import java.util.List;

public class SkillBean extends ScriptableBean {
    private int mId, mIconId, mRange, mScriptId;
    private String mName, mDescription;
    private boolean mPassive;
    private List<Integer> mEffectIds = new ArrayList<Integer>();
    
    public SkillBean(int id, int iconId, int range, int scriptId, String name, String description, boolean passive) {
        mId = id;
        mIconId = iconId;
        mScriptId = scriptId;
        mName = name;
        mDescription = description;
        mPassive = passive;
    }
    
    public int getId() {
        return mId;
    }
    
    public int getIconId() {
        return mIconId;
    }
    
    public int getRange() {
        return mRange;
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
    
    public boolean getPassive() {
        return mPassive;
    }
    
    public List<Integer> getEffectList() {
        return mEffectIds;
    }
}
