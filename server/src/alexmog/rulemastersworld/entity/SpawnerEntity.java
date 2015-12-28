package alexmog.rulemastersworld.entity;

import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.gamemodes.GameMode;

public class SpawnerEntity extends Entity {
    private long SPAWN_UPDATE = 60000;
    private long mTimer = SPAWN_UPDATE;
    private LivingEntity mToSpawn;

    public SpawnerEntity(Vector2f position, Vector2f size, GameMode gameMode, LivingEntity toSpawn, long spawnTimer) {
        super(position, size, gameMode);
        mToSpawn = toSpawn;
    }
    
    @Override
    public void update(long delta) {
        if (mTimer >= SPAWN_UPDATE) {
            mTimer = 0;
            mGameMode.getEntityManager().addEntity(mToSpawn.clone());
        }
        mTimer += delta;
        super.update(delta);
    }
    
}
