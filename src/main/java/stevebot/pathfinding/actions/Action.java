package stevebot.pathfinding.actions;

import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;

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




	public abstract PathExecutor.State tick(boolean fistTick);


}
