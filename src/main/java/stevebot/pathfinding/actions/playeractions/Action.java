package stevebot.pathfinding.actions.playeractions;

import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.execution.PathExecutor;

public abstract class Action {


	private Node from;
	private Node to;
	private double cost;




	protected Action(Node from, Node to, double cost) {
		this.from = from;
		this.to = to;
		this.cost = cost;
	}




	public double getCost() {
		return cost;
	}




	public Node getFrom() {
		return from;
	}




	public Node getTo() {
		return to;
	}




	public void resetAction() {
	}




	public abstract PathExecutor.StateFollow tick(boolean fistTick);


}
