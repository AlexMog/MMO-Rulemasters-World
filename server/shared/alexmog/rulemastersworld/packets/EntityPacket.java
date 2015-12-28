package alexmog.rulemastersworld.packets;

import alexmog.rulemastersworld.statsystem.EntityStats;

public class EntityPacket {
    public int id, animation, skinId, actualHp, actualMana;
    public EntityStats stats;
    public float x, y, height, width;
    public String name;
    public boolean player, living;
}
