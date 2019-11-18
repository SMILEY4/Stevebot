package stevebot.pathfinding.actions;

import stevebot.misc.Direction;
import stevebot.pathfinding.actions.playeractions.Action;

public class ImpossibleActionHandler {


	/**
	 * Registers following relationship: for a given node (and direction):
	 * if the "valid" can create a valid action, the "impossible" can never create a valid action.
	 *
	 * @param valid      the valid TODO
	 * @param impossible the impossible TODO
	 */
	public void makesImpossible(Class<Action> valid, Class<Action> impossible) {
		// TODO
	}




	/**
	 * Adds the given factory to the list of completed actions.
	 *
	 * @param factory the {@link ActionFactory}
	 */
	public void addValidFactory(ActionFactory factory) {
		addValidFactory(factory, factory.getDirection());
	}




	/**
	 * Adds the given factory to the list of completed actions in the given direction.
	 *
	 * @param factory   the {@link ActionFactory}
	 * @param direction the directon of the action
	 */
	public void addValidFactory(ActionFactory factory, Direction direction) {
	}




	/**
	 * Rests everything
	 */
	public void reset() {
	}




	/**
	 * Checks if an action from the given {@link ActionFactory} is possible depending on the previously successful/valid actions
	 *
	 * @param factory the {@link ActionFactory}
	 * @return whether an action from the given factory is still possible.
	 */
	public boolean isPossible(ActionFactory factory) {
		return isPossible(factory, factory.getDirection());
	}




	/**
	 * Checks if an action from the given {@link ActionFactory} is possible depending on the previously successful/valid actions
	 *
	 * @param factory   the {@link ActionFactory}
	 * @param direction the direction of the action
	 * @return whether an action from the given factory in the given direction is still possible.
	 */
	public boolean isPossible(ActionFactory factory, Direction direction) {
		return true;
	}


}




