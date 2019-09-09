package stevebot.pathfinding.path;

import stevebot.pathfinding.Node;

import java.util.List;

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
	public List<Node> getNodes() {
		return this.nodes;
	}




	@Override
	public boolean reachedGoal() {
		return true;
	}

}
