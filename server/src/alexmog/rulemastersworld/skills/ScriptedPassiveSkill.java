package alexmog.rulemastersworld.skills;

import alexmog.rulemastersworld.datas.SkillBean;
import alexmog.rulemastersworld.scripting.ScriptHelper;
import alexmog.rulemastersworld.scripting.ScriptedObject;

public class ScriptedPassiveSkill extends PassiveSkill implements ScriptedObject {
    private static final String[] FUNC_NAMES = {
        "onUpdate"  // 0
    };
    private ScriptHelper mScriptHelper;
    
    public ScriptedPassiveSkill(int id, SkillBean bean) {
        super(id, bean.getName(), bean.getDescription());
        mScriptHelper = new ScriptHelper(bean.getScriptId(), FUNC_NAMES, bean);
    }

    @Override
    public void update(int delta) {
        mScriptHelper.callFunction(mCaster.getGameMode(), this, 0, delta);
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
