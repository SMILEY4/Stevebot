package stevebot.pathfinding.actions.playeractions;

import stevebot.data.modification.Modification;
import stevebot.misc.ProcState;
import stevebot.pathfinding.nodes.Node;

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




	/**
	 * Updates this action.
	 *
	 * @param firstTick true on the first update of this action
	 * @return the resulting {@link ProcState} of the update
	 */
	public abstract ProcState tick(boolean firstTick);




	/**
	 * @return whether this action modified any blocks
	 */
	public boolean hasModifications() {
		return false;
	}




	/**
	 * @return the {@link Modification}s this action made
	 */
	public Modification[] getModifications() {
		return Modification.EMPTY;
	}




	/**
	 * @return the unique name of this action
	 */
	public abstract String getActionName();


}
