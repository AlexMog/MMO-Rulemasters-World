package alexmog.rulemastersworld.skills;

import alexmog.rulemastersworld.datas.SkillBean;
import alexmog.rulemastersworld.entity.Entity;
import alexmog.rulemastersworld.packets.skills.SkillType;

public class ScriptedSkill {
    private SkillType mType = SkillType.ACTIVABLE;
    private Skill mSkill;
    private final Entity mCaster;
    
    public ScriptedSkill(SkillBean bean, Entity caster) {
        mCaster = caster;
        mType = bean.getPassive() ? SkillType.PASSIVE : SkillType.ACTIVABLE;
        if (mType == SkillType.ACTIVABLE) {
            // TODO ScriptedActivableSkill class
        } else {
            // TODO ScriptedPassiveSkill class
        }
    }
    
    
}
