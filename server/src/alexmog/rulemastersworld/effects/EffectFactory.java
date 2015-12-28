package alexmog.rulemastersworld.effects;

import alexmog.rulemastersworld.datas.DatasManager;
import alexmog.rulemastersworld.datas.EffectBean;
import alexmog.rulemastersworld.gamemodes.GameMode;

public class EffectFactory {
    public static EffectBean getEffectBean(int id) {
        return getEffectBean(null, id);
    }
    
    public static EffectBean getEffectBean(String name) {
        return getEffectBean(name, -1);
    }
    
    private static EffectBean getEffectBean(String name, int id) {
        for (EffectBean e : DatasManager.getInstance().getEffectBeans().values()) {
            if ((name != null && e.getName().equalsIgnoreCase(name))
                    || id == e.getId()) {
                return e;
            }
        }
        return null;
    }
    
    public static ScriptedEffect createEffect(GameMode gameMode, String name) {
        return createEffect(gameMode, name, -1);
    }

    public static ScriptedEffect createEffect(GameMode gameMode, int id) {
        return createEffect(gameMode, null, id);
    }
    
    private static ScriptedEffect createEffect(GameMode gameMode, String name, 
            int id) {
        EffectBean bean = null;
        if (name == null) {
            bean = getEffectBean(id);
        } else {
            bean = getEffectBean(name);
        }
        if (bean == null) {
            return null;
        }
        return new ScriptedEffect(gameMode, bean);
    }

}
