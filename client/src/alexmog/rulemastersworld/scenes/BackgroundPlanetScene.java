package alexmog.rulemastersworld.scenes;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.particles.effects.FireEmitter;
import org.newdawn.slick.state.StateBasedGame;

import TWLSlick.BasicTWLGameState;
import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.datas.DatasManager;

public abstract class BackgroundPlanetScene extends BasicTWLGameState {
    private StateBasedGame mGame;
    private ParticleSystem mParticleSystem;
    private Image mBd1, mBd2;
    
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
        mGame = game;
        Image image = new Image("res/effects/particle.tga", true);
        mParticleSystem = new ParticleSystem(image);
        mBd1 = (Image)DatasManager.getInstance().getFile("Image.menubackground");
        mBd2 = (Image)DatasManager.getInstance().getFile("Image.menuplanet");
        mParticleSystem.addEmitter(new FireEmitter(500, 400, 580));
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException {
        mBd1.draw(0, 0, Main.WIDTH, Main.HEIGHT);
        mParticleSystem.render();
        mBd2.draw(0, 0, Main.WIDTH, Main.HEIGHT);
        mBd2.setCenterOfRotation(Main.WIDTH / 2 - 5, Main.HEIGHT / 2 + 14);
        mBd2.rotate(0.1f);
    }

    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException {
        mParticleSystem.update(delta);
    }
    
    public StateBasedGame getGame() {
        return mGame;
    }

    @Override
    public abstract int getID();
}
