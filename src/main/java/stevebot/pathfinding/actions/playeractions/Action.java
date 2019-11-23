package stevebot.pathfinding.actions.playeractions;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.modification.Modification;
import stevebot.misc.ProcState;
import stevebot.pathfinding.actions.ActionObserver;
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
	 * Called when the action finished with done or failed
	 *
	 * @param state the state with which this action finished
	 */
	public void onActionFinished(ProcState state) {
		ActionObserver.endAction(this.getActionNameExp(), state == ProcState.FAILED);
	}




	/**
	 * Check if the given position is on the path of this action.
	 *
	 * @param position the position to check
	 * @return true, if the position is on the path
	 */
	public abstract boolean isOnPath(BaseBlockPos position);




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




	/**
	 * @return the expanded name of this action
	 */
	public String getActionNameExp() {
		return getActionName();
	}


}
