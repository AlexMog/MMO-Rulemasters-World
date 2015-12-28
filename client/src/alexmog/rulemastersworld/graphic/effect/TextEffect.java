package alexmog.rulemastersworld.graphic.effect;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.maths.MathsHelper;
import alexmog.rulemastersworld.datas.DatasManager;

public class TextEffect extends TimedEffect {
	public static final int DAMAGES_DURATION = 800;
	public static final int STATUS_DURATION = 1200;
	private static final float MOVEMENT_SPEED = 0.1f;
	
	
	private MathsHelper mMathsHelper = MathsHelper.getInstance();
	private final String mType;
	private final String mText;
	private final Color mColor;
	
	public TextEffect(String type, String text, Color color, Vector2f position, int duration) {
		super(position, duration);
		mType = type;
		mText = text;
		mColor = color;
	}

	
	public void render(Graphics g) {
		TrueTypeFont font = DatasManager.getInstance().getFile("TextEffect." + mType);
		
		float posX = mPosition.x - font.getWidth(mText) / 2;
		font.drawString(posX, mPosition.y, mText, mColor);
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		mPosition.y -= MOVEMENT_SPEED * delta;
		mPosition.x += mMathsHelper.cos((double)mTimer.remaning() / 100.0d);
	}
}
