package stevebot.pathfinding.actions;

import stevebot.pathfinding.Node;

public abstract class StatefulAction extends Action {


	private final String[] states;
	private int currentState;




	protected StatefulAction(Node from, Node to, double cost, String... states) {
		super(from, to, cost);
		this.states = states;
	}




	public String[] getStates() {
		return this.states;
	}




	public String getCurrentState() {
		if (states == null) {
			return null;
		} else {
			return states[currentState];
		}
	}




	public boolean setState(String next) {
		for (int i = 0; i < states.length; i++) {
			if (states[i].equalsIgnoreCase(next)) {
				currentState = i;
				return true;
			}
		}
		return false;
	}




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
