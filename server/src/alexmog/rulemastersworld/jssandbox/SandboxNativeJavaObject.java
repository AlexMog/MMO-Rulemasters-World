package alexmog.rulemastersworld.jssandbox;


import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Scriptable;

public class SandboxNativeJavaObject extends NativeJavaObject {
    private static final long serialVersionUID = 165057822076928459L;

    public SandboxNativeJavaObject(Scriptable scope, Object javaObject,
                                   Class<?> staticType) {
        super(scope, javaObject, staticType);
    }

    @Override
    public Object get(String name, Scriptable start) {
        if ("getClass".equals(name)) {
            return NOT_FOUND;
        }
        return super.get(name, start);
    }
}