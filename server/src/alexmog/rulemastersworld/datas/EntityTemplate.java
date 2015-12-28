package alexmog.rulemastersworld.datas;

public class EntityTemplate extends ScriptableBean {
    private int mId, mStr, mAgi, mInt, mEnd, mArmor;
    private float mSpeed;
    private String mName, mDescription;
    private int mScriptId, mSkinId;
    private boolean mUseAstar;
    
    public EntityTemplate(int id, String name, String description, int str, int agi, int intel, int end, int armor,
            float speed, int scriptId, int skinId, boolean useAstar) {
        mId = id;
        mStr = str;
        mAgi = agi;
        mInt = intel;
        mEnd = end;
        mArmor = armor;
        mSpeed = speed;
        mName = name;
        mDescription = description;
        mScriptId = scriptId;
        mSkinId = skinId;
        mUseAstar = useAstar;
    }
    
    public boolean isUseAstar() {
        return mUseAstar;
    }
    
    public int getId() {
        return mId;
    }
    
    public int getStr() {
        return mStr;
    }
    
    public int getInt() {
        return mInt;
    }
    
    public int getAgi() {
        return mAgi;
    }
    
    public int getEnd() {
        return mEnd;
    }
    
    public int getArmor() {
        return mArmor;
    }
    
    public float getSpeed() {
        return mSpeed;
    }
    
    public String getName() {
        return mName;
    }
    
    public String getDescription() {
        return mDescription;
    }
    
    public int getScriptId() {
        return mScriptId;
    }

    public int getSkinId() {
        return mSkinId;
    }
}
