package stevebot.pathfinding.actions.playeractions;

import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.execution.PathExecutorImpl;

public abstract class Action {


	private Node from;
	private Node to;
	private double cost;




	/**
	 * @param from the start node of this action
	 * @param to   the destination node of this action
	 * @param cost the cost to get from the start to the destination node with this action
	 */
	protected Action(Node from, Node to, double cost) {
		this.from = from;
		this.to = to;
		this.cost = cost;
	}




	/**
	 * @return the cost of this action
	 */
	public double getCost() {
		return cost;
	}




	/**
	 * @return the starting node of this action
	 */
	public Node getFrom() {
		return from;
	}




	/**
	 * @return the destination node of this action
	 */
	public Node getTo() {
		return to;
	}




	/**
	 * Resets this action.
	 */
	public void resetAction() {
	}




	public boolean changedBlocks() {
		return false;
	}




	public BlockChange[] getBlockChanges() {
		return BlockChange.EMPTY;
	}




	/**
	 * Updates this action.
	 *
	 * @param fistTick true on the first update of this action
	 * @return the resulting {@link PathExecutorImpl.StateFollow} of the update
	 */
	public abstract PathExecutorImpl.StateFollow tick(boolean fistTick);


}
