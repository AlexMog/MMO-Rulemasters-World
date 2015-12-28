package alexmog.rulemastersworld.effects;

import alexmog.rulemastersworld.datas.DatasManager;
import alexmog.rulemastersworld.datas.EffectBean;
import alexmog.rulemastersworld.datas.EffectTemplate;
import alexmog.rulemastersworld.gamemodes.GameMode;
import alexmog.rulemastersworld.scripting.ScriptHelper;

public class ScriptedEffect extends TicEffect {
    private final static String[] FUNC_NAMES = {
        "onApply",          // 0
        "onRemove",         // 1
        "onUpdate",         // 2
        "onTimerFinished",  // 3
        "onTicTimerOut"     // 4
    };
    private EffectBean mBean;
    private ScriptHelper mScriptHelper;

    public ScriptedEffect(GameMode gameMode, EffectBean bean) {
        super(bean.getId(), gameMode, bean.getName(), bean.getTicTime(), bean.getDuration());
        EffectTemplate template = DatasManager.getInstance().getEffectTemplates().get(bean.getTemplateId());
        mScriptHelper = new ScriptHelper(template.getScriptId(), FUNC_NAMES, bean);
        mBean = bean;
        mIconId = bean.getIconId();
        mDescription = bean.getDescription();
        mInfinite = bean.isInfinite();
    }
    
    public Object getCustomVar(String name) {
        return mScriptHelper.getCustomVar(name);
    }
    
    public void setCustomVar(String name, Object value) {
        mScriptHelper.setCustomVar(name, value);;
    }
        
    @Override
    public void onApply() {
        super.onApply();
        mScriptHelper.callFunction(mEntity.getGameMode(), this, 0);
    }
    
    @Override
    public void onRemove() {
        super.onRemove();
        mScriptHelper.callFunction(mEntity.getGameMode(), this, 1);
    }
    
    @Override
    public void update(long delta) {
        super.update(delta);
        mScriptHelper.callFunction(mEntity.getGameMode(), this, 2);
    }

    @Override
    public void onTimerOut() {
        mScriptHelper.callFunction(mEntity.getGameMode(), this, 4);
    }

    @Override
    public void onTimerFinished() {
        mScriptHelper.callFunction(mEntity.getGameMode(), this, 3);
    }

    @Override
    public Effect clone() {
        ScriptedEffect ret = new ScriptedEffect(mGameMode, mBean);
        ret.setIconId(mIconId);
        ret.setDescription(mDescription);
        ret.setStackable(mStackable);
        ret.setStacks(mStacks);
        return ret;
    }
    
}
