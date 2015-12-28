package alexmog.rulemastersworld.entity;

import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.entity.projectile.ProjectileEntity;
import alexmog.rulemastersworld.gamemodes.GameMode;
import alexmog.rulemastersworld.skills.ActivableSkill;
import alexmog.rulemastersworld.skills.ProjectileActivableSkill;

public class RandomEntity extends LivingEntity {

    public RandomEntity(Vector2f position, Vector2f size, int life,
            GameMode gameMode) {
        super(position, size, gameMode);
        ProjectileEntity e = new ProjectileEntity(this, mPosition, new Vector2f(25, 25), gameMode);
        e.setSkinId(2);
        ProjectileActivableSkill s = new ProjectileActivableSkill(0, "Saignement", "Vous perdez votre sang", e);
        s.setCaster(this);
        addSkill(s);
        mStats.setEnd(10);
        mSpeed = 1.5f;
        setSkinId(1);
    }
    
    @Override
    public void update(long delta) {
        if (!mMoving) {
//            System.out.println("Initial pos: " + mPosition.x + ", " + mPosition.y);
        	float posX = Main.random.nextInt(mGameMode.getGameInstance().getMap().getTmxMap().getWidth())
                    * mGameMode.getGameInstance().getMap().getTmxMap().getTilewidth();
            float posY = Main.random.nextInt(mGameMode.getGameInstance().getMap().getTmxMap().getHeight())
                    * mGameMode.getGameInstance().getMap().getTmxMap().getTileheight();
            goTo(posX, posY);
//            System.out.println("GOING TO " + posX + " " + posY);
            ((ActivableSkill)mSkills.get(0)).activate(new Vector2f(300, 300));
        }
        super.update(delta);
    }

    @Override
    public Entity clone() {
        RandomEntity ret = new RandomEntity(new Vector2f(mPosition), new Vector2f(mSize), mStats.getMaxHp(), mGameMode);
        ret.setStats(this);
        ret.setSpeed(mSpeed);
        ret.setSkinId(mSkinId);
        return ret;
    }
}
