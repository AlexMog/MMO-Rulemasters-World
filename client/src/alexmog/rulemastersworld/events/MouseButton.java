package alexmog.rulemastersworld.events;

public class MouseButton {
	private final int COOLDOWN_TIME = 200; // ms
	private boolean mPressed = false;
	private int mCooldown = 0;
	
	
	protected void click() {
		mPressed = true;
		mCooldown = 0;
	}
	
	protected void release() {
		mPressed = false;
	}
	
	protected void update(int delta) {
		mCooldown += delta;
		if (mCooldown >= COOLDOWN_TIME) {
			mCooldown = 0;
		}
	}
	
	
	public boolean isPressed() {
		return mPressed;
	}
	
	public boolean isJustClicked() {
		return (mPressed && mCooldown == 0);
	}
}
