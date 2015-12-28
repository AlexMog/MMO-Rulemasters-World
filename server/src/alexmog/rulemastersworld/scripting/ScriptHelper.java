package alexmog.rulemastersworld.scripting;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import com.esotericsoftware.minlog.Log;

import alexmog.rulemastersworld.datas.CustomVarBean;
import alexmog.rulemastersworld.datas.DatasManager;
import alexmog.rulemastersworld.datas.ScriptBean;
import alexmog.rulemastersworld.datas.ScriptableBean;
import alexmog.rulemastersworld.gamemodes.GameMode;

public class ScriptHelper {
    private ScriptableObject mScope;
    private Function[] mFunctions;
    private Map<String, Object> mCustomVars = new HashMap<String, Object>();
    private final String[] mFunctionNames;
    private int mScriptId;

    public ScriptHelper(int scriptId, String[] func_names, ScriptableBean bean) {
       ScriptBean script = DatasManager.getInstance().getScripts().get(scriptId);
       mScope = script.getScope();
       mFunctions = new Function[func_names.length];
       mFunctionNames = func_names;
       mScriptId = scriptId;
       try {
           for (int i = 0; i < func_names.length; ++i) {
               Object fnc = mScope.get(func_names[i], mScope);
               if (fnc != null && fnc instanceof Scriptable) {
                   mFunctions[i] = (Function)fnc;
               } else {
                   mFunctions[i] = null;
               }
           }
       } catch(Exception e) {
           Log.error("ScriptedEffectInit", e);
       }

       // Set custom default vars
       for (CustomVarBean var : script.getCustomVars()) {
           setCustomVar(var.getName(), var.getValue());
       }
       
       // Set custom vars from EffectBean this time
       for (Entry<String, Object> var : bean.getCustomVars().entrySet()) {
           setCustomVar(var.getKey(), var.getValue());
       }
    }
    
    // Every functions will get on first arg the element element.
    public void callFunction(GameMode gameMode, Object caller, int id, Object... objects) {
        if (mScope == null) return;
        if (id < mFunctions.length && mFunctions[id] != null) {
            Object[] obj_array = new Object[objects.length + 1];
            obj_array[0] = caller;
            System.arraycopy(objects, 0, obj_array, 1, objects.length);
            for (Entry<String, Object> e : mCustomVars.entrySet()) {
                mFunctions[id].put(e.getKey(), mScope, e.getValue());
            }
            try {
                mFunctions[id].call(gameMode.getContext(), mScope, mScope, obj_array);
            } catch (Exception e) {
                Log.error("A scripted function caused an error. Deactivating it.", e);
                Log.error("Function : " + mFunctionNames[id] + " object: " + caller + " script_id: " + mScriptId);
                mFunctions[id] = null;
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getCustomVar(String varName) {
        try {
            return (T)mCustomVars.get(varName);
        } catch (Exception e) {
            return null;
        }
    }
    
    public void setCustomVar(String varName, Object value) {
        mCustomVars.put(varName, value);
    }
}
