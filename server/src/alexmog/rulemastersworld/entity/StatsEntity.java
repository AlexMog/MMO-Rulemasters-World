package alexmog.rulemastersworld.entity;

import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.gamemodes.GameMode;
import alexmog.rulemastersworld.statsystem.EntityStats;

public class StatsEntity extends Entity {
    protected EntityStats mStats = new EntityStats();
    
    public StatsEntity(Vector2f position, Vector2f size, GameMode gameMode) {
        super(position, size, gameMode);
    }
    
    public void setStats(EntityStats e) {
        mStats.setStats(e);
    }
    
    public void setStats(StatsEntity e) {
        mStats.setStats(e.getStats());
    }
    
    public EntityStats getStats() {
        return mStats;
    }
}
