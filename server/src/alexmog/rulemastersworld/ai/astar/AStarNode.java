package alexmog.rulemastersworld.ai.astar;

import org.newdawn.slick.geom.Vector2f;

public class AStarNode {
	private final AStarNode mParent;
	private final Vector2f mPosition;
	
	public AStarNode(AStarNode parent, Vector2f position) {
		mParent = parent;
		mPosition = position.copy();
	}
	
	public Vector2f getPosition() {
		return mPosition;
	}
	
	public AStarNode getParent() {
		return mParent;
	}
}
