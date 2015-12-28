package alexmog.rulemastersworld.scripting;

public interface ScriptedObject {
    public <T> T getCustomVar(String varName);
    public void setCustomVar(String varName, Object value);
}
