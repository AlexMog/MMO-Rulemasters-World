package alexmog.rulemastersworld.entity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.datas.DatasManager;
import alexmog.rulemastersworld.gamemodes.GameMode;

public class OnlineEntity extends LivingEntity {
    private final boolean mLiving;

    public OnlineEntity(Vector2f position, Vector2f size, GameMode gameMode, int hp, int maxHp,
            boolean living) {
        super(position, size, hp, maxHp, gameMode);
        this.setAnimationId(4);
        mLiving = living;
    }
    
    @Override
    public void update(int delta) {
        mPosition.x += Math.cos(mLastAngle) * mSpeed * .1 * delta;
        mPosition.y += Math.sin(mLastAngle) * mSpeed * .1 * delta;
        super.update(delta);
    }
    
    /* This thing is creepy & dirty */
    public void setSkin() {
        for (String skin : mSkinParts) {
            Image img = (Image) DatasManager.getInstance().getFile(skin);
            SpriteSheet s = new SpriteSheet(img, img.getWidth() / 3, img.getHeight() / 4);
            Animation[] anim = new Animation[8];
            anim[0] = loadAnimation(s, 0, 0, 3); // Haut
            anim[1] = loadAnimation(s, 0, 1, 3); // Gauche
            anim[2] = loadAnimation(s, 0, 2, 3); // Droite
            anim[3] = loadAnimation(s, 0, 3, 3); // Bas
            anim[4] = loadAnimation(s, 1, 0, 2); // Haut qui bouge pas
            anim[5] = loadAnimation(s, 1, 1, 2); // Gauche qui bouge pas
            anim[6] = loadAnimation(s, 1, 2, 2); // Droite qui bouge pas
            anim[7] = loadAnimation(s, 1, 3, 2); // Bas qui bouge pas
            mAnimations.add(anim);
        }
    }
    
    @Override
    public void render(Graphics g) {
        super.render(g);
        if (mLiving) {
            float x = mPosition.x - mSize.x / 2;
            float y = mPosition.y - 35 - mSize.y / 2;
            if (mName != null) {
                mNameFont.drawString(mPosition.x - mNameFont.getWidth(mName) / 2, y - 25, mName);
            }
            g.drawRect(x, y, mSize.x, 20);
            g.setColor(getHp() > mMaxHp * 50 / 100 ? Color.green :
                getHp() > mMaxHp * 20 / 100 ? Color.orange : Color.red);
            float s = ((mSize.x * mHp) / mMaxHp) - 1;
            g.fillRect(x + 1, y + 1, (s > 0 ? s : 0), 19);
            g.setColor(Color.white);
        }
    }

}
