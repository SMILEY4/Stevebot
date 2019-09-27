package stevebot.pathfinding.path;

import stevebot.pathfinding.nodes.Node;

import java.util.List;

/**
 * A path that successfully reached the goal
 */
public class CompletedPath implements Path {


	private final double cost;
	private final List<Node> nodes;




	public CompletedPath(double cost, List<Node> nodes) {
		this.cost = cost;
		this.nodes = nodes;
	}




	@Override
	public double getCost() {
		return this.cost;
	}




	@Override
	public boolean reachedGoal() {
		return true;
	}




	@Override
	public List<Node> getNodes() {
		return this.nodes;
	}




	@Override
	public Node getFirstNode() {
		return nodes.get(0);
	}




	@Override
	public Node getLastNode() {
		return nodes.get(nodes.size() - 1);
	}


}
