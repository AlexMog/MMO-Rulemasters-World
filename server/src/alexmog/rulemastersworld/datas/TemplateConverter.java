package alexmog.rulemastersworld.datas;

import alexmog.rulemastersworld.statsystem.EntityStats;

public class TemplateConverter {
    public static EntityStats convertToStats(EntityTemplate template) {
        EntityStats ret = new EntityStats();
        ret.setEnd(template.getEnd());
        ret.setAgi(template.getAgi());
        ret.setInt(template.getInt());
        ret.setArmor(template.getArmor());
        ret.setStr(template.getStr());
        return ret;
    }
}
