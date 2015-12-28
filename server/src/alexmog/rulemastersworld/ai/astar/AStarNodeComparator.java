package alexmog.rulemastersworld.ai.astar;

import java.util.Comparator;

import org.newdawn.slick.geom.Vector2f;

public class AStarNodeComparator implements Comparator<AStarNode> {
	private Vector2f mTarget;
	
	public void setTarget(Vector2f target) {
		mTarget = target;
	}

	public int compare(AStarNode node1, AStarNode node2) {
		return (int)(node1.getPosition().distance(mTarget) - node2.getPosition().distance(mTarget));
	}

}
