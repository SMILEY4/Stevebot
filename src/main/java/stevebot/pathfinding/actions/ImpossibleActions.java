package stevebot.pathfinding.actions;

import stevebot.misc.Direction;

public class ImpossibleActions {


	/**
	 * Adds the given factory to the list of completed actions in the given direction.
	 *
	 * @param factory   the {@link ActionFactory}
	 * @param direction the directon of the action
	 */
	public void addCompletedFactory(ActionFactory factory, Direction direction) {
	}




	/**
	 * Adds the given factory to the list of completed actions.
	 *
	 * @param factory the {@link ActionFactory}
	 */
	public void addCompletedFactory(ActionFactory factory) {
	}




	/**
	 * Rests everything
	 */
	public void reset() {
	}




	/**
	 * @param factory the {@link ActionFactory}
	 * @return whether an action from the given factory is still possible.
	 */
	public boolean isPossible(ActionFactory factory) {
		return true;
	}




	/**
	 * @param factory   the {@link ActionFactory}
	 * @param direction the direction of the action
	 * @return whether an action from the given factory in the given direction is still possible.
	 */
	public boolean isPossible(ActionFactory factory, Direction direction) {
		return true;
	}


}
