package alexmog.rulemastersworld.packets;

import alexmog.rulemastersworld.packets.skills.TargetType;

public class PlayerSpellPacket {
    public String name;
    public int iconId, spellId;
    public String description;
    public int spellSlotId;
    public boolean passive;
    public long cooldown;
    public TargetType targetType;
    public int range;
}
