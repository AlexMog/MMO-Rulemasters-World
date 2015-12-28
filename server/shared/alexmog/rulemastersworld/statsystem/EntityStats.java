package alexmog.rulemastersworld.statsystem;

public class EntityStats extends ObjectStats {
    protected int mFinalStr, mFinalAgi, mFinalInt, mFinalEnd, mFinalArmor,
            mFinalHp, mFinalMana;

    public void setStats(EntityStats template) {
        setEnd(template.getEnd());
        setAgi(template.getAgi());
        setInt(template.getInt());
        setArmor(template.getArmor());
        setStr(template.getStr());
    }
    
    
}
