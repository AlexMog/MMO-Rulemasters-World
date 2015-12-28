package alexmog.rulemastersworld.jssandbox;

import org.mozilla.javascript.ClassShutter;

public class SandboxClassShutter implements ClassShutter {
    private static final String[] classVisible = {
        "org.newdawn.slick.geom.",
        "alexmog.rulemastersworld.entity.",
        "alexmog.rulemastersworld.effects."
    };
    
    public boolean visibleToScripts(String fullClassName) {
        for (String s : classVisible) {
            if (fullClassName.startsWith(s)) return true;
        }
        return false;
    }
}
