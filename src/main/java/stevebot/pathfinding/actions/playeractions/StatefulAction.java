package stevebot.pathfinding.actions.playeractions;

import stevebot.pathfinding.nodes.Node;

public abstract class StatefulAction extends Action {


	private final String[] states;
	private int currentState;




	/**
	 * @param from   the start node of this action
	 * @param to     the destination node of this action
	 * @param cost   the cost to get from the start to the destination node with this action
	 * @param states the list of possible/valid states
	 */
	protected StatefulAction(Node from, Node to, double cost, String... states) {
		super(from, to, cost);
		this.states = states;
	}




	/**
	 * @return the possible/valid states
	 */
	public String[] getStates() {
		return this.states;
	}




	/**
	 * @return the current state (or null)
	 */
	public String getCurrentState() {
		if (states == null) {
			return null;
		} else {
			return states[currentState];
		}
	}




	/**
	 * Sets the state to the given state
	 *
	 * @param next the new state
	 * @return false, if the new state is not a valid state
	 */
	public boolean setState(String next) {
		for (int i = 0; i < states.length; i++) {
			if (states[i].equalsIgnoreCase(next)) {
				currentState = i;
				return true;
			}
		}
		return false;
	}




	/**
	 * Transition to the next state in the list of valid states.
	 *
	 * @return
	 */
	public boolean nextState() {
		if (currentState + 1 >= states.length) {
			return false;
		} else {
			currentState++;
			return true;
		}
	}




	@Override
	public void resetAction() {
		currentState = 0;
	}


}
