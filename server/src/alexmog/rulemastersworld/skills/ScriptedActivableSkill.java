package alexmog.rulemastersworld.skills;

import alexmog.rulemastersworld.datas.SkillBean;
import alexmog.rulemastersworld.entity.LivingEntity;
import alexmog.rulemastersworld.scripting.ScriptHelper;
import alexmog.rulemastersworld.scripting.ScriptedObject;

public class ScriptedActivableSkill extends ActivableSkill implements ScriptedObject {
    private static final String[] FUNC_NAMES = {
        "onActivationPosition", // 0
        "onActivationEntity"    // 1
    };
    private ScriptHelper mScriptHelper;
    
    public ScriptedActivableSkill(int id, SkillBean bean) {
        super(id, bean.getName(), bean.getDescription());
        mScriptHelper = new ScriptHelper(bean.getScriptId(), FUNC_NAMES, bean);
    }

    @Override
    public void onActivation(float x, float y) {
        mScriptHelper.callFunction(mCaster.getGameMode(), this, 0, x, y);
    }

    @Override
    public void onActivation(LivingEntity target) {
        mScriptHelper.callFunction(mCaster.getGameMode(), this, 1, target);
    }

    @Override
    public <T> T getCustomVar(String varName) {
        return mScriptHelper.getCustomVar(varName);
    }

    @Override
    public void setCustomVar(String varName, Object value) {
        mScriptHelper.setCustomVar(varName, value);
    }
}
