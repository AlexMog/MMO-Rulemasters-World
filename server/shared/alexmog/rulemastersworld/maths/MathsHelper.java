package alexmog.rulemastersworld.maths;

import org.newdawn.slick.geom.Vector2f;


public class MathsHelper {
	private static MathsHelper sInstance = new MathsHelper();
	
	public static MathsHelper getInstance() {
		return sInstance;
	}
	
	
	private final int PRECALC_NUMBERS = 10000;
	private float mCos[] = new float[PRECALC_NUMBERS];
	private float mSin[] = new float[PRECALC_NUMBERS];
	private float mTan[] = new float[PRECALC_NUMBERS];
	
	
	private MathsHelper() {
		double angle;
		
		for (int i = 0; i < PRECALC_NUMBERS; ++i) {
			angle = arrayPositionToAngle(i);
			mCos[i] = (float)Math.cos(angle);
			mSin[i] = (float)Math.cos(angle);
			mTan[i] = (float)Math.cos(angle);
		}
	}
	
	private double arrayPositionToAngle(int position) {
		return (Math.PI * 2.0d * position / PRECALC_NUMBERS);
	}
	
	private int angleToArrayPosition(double angle) {
		return (int)(angle / Math.PI / 2.0d * PRECALC_NUMBERS);
	}
	
	
	public double aroundAngle(double angle) {
		angle %= Math.PI * 2.0d;
		if (angle < 0) {
			angle += Math.PI * 2.0d;
		}
		return (angle);
	}
	
	public double cos(double angle) {
//		System.out.println(angle + " become " + aroundAngle(angle) + " and result is " + mCos[angleToArrayPosition(aroundAngle(angle))] + " vs " + Math.cos(angle));
		angle = aroundAngle(angle);
		return (mCos[angleToArrayPosition(angle)]);
	}
	
	public double sin(double angle) {
//		System.out.println(angle + " become " + aroundAngle(angle) + " and result is " + mSin[angleToArrayPosition(aroundAngle(angle))] + " vs " + Math.sin(angle));
		angle = aroundAngle(angle);
		return (mSin[angleToArrayPosition(angle)]);
	}
	
	public double tan(double angle) {
		angle = aroundAngle(angle);
		return (mTan[angleToArrayPosition(angle)]);
	}
	
	
	public float around(float n, float around) {
		return (n - n % around);
	}
	
	public static double getDistance(Vector2f p1, Vector2f p2) {
	    return getDistance(p1.x, p1.y, p2.x, p2.y);
	}
	
	public static double getDistance(float x, float y, float x2, float y2) {
	    return Math.sqrt(Math.pow(x - x2, 2) + Math.pow(y - y2, 2));
	}
}
