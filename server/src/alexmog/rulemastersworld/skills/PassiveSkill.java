package alexmog.rulemastersworld.skills;

public abstract class PassiveSkill extends Skill {

    public PassiveSkill(int id, String name, String description) {
        super(id, name, description, true);
    }
    
    public abstract void update(int delta);
}
