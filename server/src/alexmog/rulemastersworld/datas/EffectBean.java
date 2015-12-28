package alexmog.rulemastersworld.datas;


public class EffectBean extends ScriptableBean {
    private int mId, mTemplateId, mIconId, mTicTime;
    private long mDuration;
    private String mName, mDescription;
    private boolean mStackable, mInfinite;
    
    public EffectBean(int id, int templateId,
            long duration, String name, String description, boolean isStackable,
            int iconId, int ticTime, boolean infinite) {
        mId = id;
        mTemplateId = templateId;
        mDuration = duration;
        mName = name;
        mDescription = description;
        mStackable = isStackable;
        mIconId = iconId;
        mTicTime = ticTime;
        mInfinite = infinite;
    }
    
    public boolean isInfinite() {
        return mInfinite;
    }
    
    public int getId() {
        return mId;
    }
    
    public int getTemplateId() {
        return mTemplateId;
    }
    
    public long getDuration() {
        return mDuration;
    }
    
    public String getName() {
        return mName;
    }
    
    public String getDescription() {
        return mDescription;
    }
    
    public boolean isStackable() {
        return mStackable;
    }


    public int getIconId() {
        return mIconId;
    }


    public int getTicTime() {
        return mTicTime;
    }
}
