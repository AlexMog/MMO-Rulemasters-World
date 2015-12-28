package alexmog.rulemastersworld.jssandbox;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;

public class SandboxContextFactory extends ContextFactory {
    @Override
    public Context makeContext() {
        Context context = super.makeContext();
        context.setWrapFactory(new SandboxWrapFactory());
        context.setClassShutter(new SandboxClassShutter());
        return context;
    }
}
