package alexmog.rulemastersworld.datas;

public class CustomVarBean {
    private final String mName;
    private final Object mValue;
    private final CustomVarType mType;
    public CustomVarBean(String name, Object value, CustomVarType type) {
        mName = name;
        mValue = value;
        mType = type;
    }
    
    public String getName() {
        return mName;
    }
    
    public Object getValue() {
        return mValue;
    }
    
    public CustomVarType getType() {
        return mType;
    }
}
