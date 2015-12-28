package alexmog.rulemastersworld.ai.astar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.ai.EntityReflexion;
import alexmog.rulemastersworld.entity.BasicAIEntity;
import alexmog.rulemastersworld.map.Map;
import alexmog.rulemastersworld.maths.MathsHelper;

public class AStar extends EntityReflexion {
	private MathsHelper mMathsHelper = MathsHelper.getInstance();
	private final Vector2f mMovements[] = new Vector2f[4];
	private final Map mMap;
	private Vector2f mPositionToStudy = new Vector2f();
	private AStarNodeComparator mComparator = new AStarNodeComparator();
	private PriorityQueue<AStarNode> mNodes;
	private List<AStarNode> mVisitedNodes = new ArrayList<AStarNode>();
	
	public AStar(BasicAIEntity entity, Map map) {
		super(entity);

		mNodes = new PriorityQueue<AStarNode>(mComparator);
		mMap = map;
		
		mMovements[0] = new Vector2f(0, -map.getTmxMap().getTileheight());
		mMovements[1] = new Vector2f(0, map.getTmxMap().getTileheight());
		mMovements[2] = new Vector2f(map.getTmxMap().getTilewidth(), 0);
		mMovements[3] = new Vector2f(-map.getTmxMap().getTilewidth(), 0);
	}
	
	
	
	private boolean nodeIsVisisted(AStarNode node) {
		for (AStarNode visitedNode : mVisitedNodes) {
			if (visitedNode.getPosition().equals(node.getPosition())) {
				return true;
			}
		}
		
		return false;
	}
	
	private AStarNode searchPath(Vector2f dest, int availableIterations) {
		AStarNode node = mNodes.poll();
		
		if (node != null && availableIterations > 0) {
			for (Vector2f movement : mMovements) {
				mPositionToStudy.x = node.getPosition().x + movement.x;
				mPositionToStudy.y = node.getPosition().y + movement.y;
				Rectangle rect = new Rectangle(mPositionToStudy.x, mPositionToStudy.y, mEntity.getBounds().getWidth(), mEntity.getBounds().getHeight());
				if (!mMap.isSomethingBlocking(rect)) {
					AStarNode newNode = new AStarNode(node, mPositionToStudy);
					if (newNode.getPosition().equals(dest)) {
						return newNode;
					}
					if (!nodeIsVisisted(newNode)) {
						mVisitedNodes.add(newNode);
						mNodes.add(newNode);
					}
				}
			}
			return (this.searchPath(dest, availableIterations - 1));
		}
		
		return null;
	}
	
	private void translateNodesToPath(AStarNode node, List<Vector2f> path) {
		if (node.getParent() != null) {
			node.getPosition().x += mEntity.getBounds().getWidth() / 2;
			node.getPosition().y += mEntity.getBounds().getHeight() / 2;
			path.add(node.getPosition());
			this.translateNodesToPath(node.getParent(), path);
		}
	}

	public List<Vector2f> calculatePath(Vector2f dest) {
		List<Vector2f> path = null;
		Vector2f src = new Vector2f(mEntity.getBounds().getX(), mEntity.getBounds().getY());
		AStarNode start;
		AStarNode end;
		float width, height;
		boolean found;
		
		width = mEntity.getBounds().getWidth();
		height = mEntity.getBounds().getHeight();
		src.x = mMathsHelper.around(src.x, width);
		src.y = mMathsHelper.around(src.y, height);
		dest.x = mMathsHelper.around(dest.x, width);
		dest.y = mMathsHelper.around(dest.y, height);
		
		mComparator.setTarget(dest);
		
		start = new AStarNode(null, src);
		mNodes.clear();
		mNodes.add(start);
		mVisitedNodes.clear();
		mVisitedNodes.add(start);
		
		end = this.searchPath(dest, 200);
		found = (end != null);
		
		if (found) {
			path = new ArrayList<Vector2f>(); // TODO reserve size
			this.translateNodesToPath(end, path);
			Collections.reverse(path);
		}
		
		return path;
	}
}
