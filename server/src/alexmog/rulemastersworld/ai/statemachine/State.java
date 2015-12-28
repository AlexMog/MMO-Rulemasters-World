package alexmog.rulemastersworld.ai.statemachine;

import alexmog.rulemastersworld.entity.LivingEntity;

public abstract class State {
    protected final LivingEntity mEntity;
    
    public State(LivingEntity e) {
        mEntity = e;
    }
    
    public void enter() {}
    
    public void quit() {}
    
    public abstract void update(long delta);
}
