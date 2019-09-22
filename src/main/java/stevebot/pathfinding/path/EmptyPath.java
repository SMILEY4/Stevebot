package stevebot.pathfinding.path;

import stevebot.pathfinding.nodes.Node;

import java.util.Collections;
import java.util.List;

public class EmptyPath implements Path {


	@Override
	public double getCost() {
		return Integer.MAX_VALUE;
	}




	@Override
	public List<Node> getNodes() {
		return Collections.emptyList();
	}




	@Override
	public boolean reachedGoal() {
		return false;
	}




	@Override
	public Node getFirstNode() {
		return null;
	}




	@Override
	public Node getLastNode() {
		return null;
	}


}
