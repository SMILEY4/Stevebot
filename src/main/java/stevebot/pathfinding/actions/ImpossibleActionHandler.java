package stevebot.pathfinding.actions;

import stevebot.misc.Direction;
import stevebot.pathfinding.actions.playeractions.Action;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ImpossibleActionHandler {


	private HashMap<Class<? extends Action>, Set<Class<? extends Action>>> mapping = new HashMap<>();

	private HashMap<String, boolean[]> invalidActions = new HashMap<>();




	/**
	 * Registers following relationship: for a given node (and direction):
	 * if "valid" is the type of a valid action, all "impossible" can never be valid actions.
	 *
	 * @param valid      the class of the valid action
	 * @param impossible the classes of the impossible actions
	 */
	public void makesImpossible(Class<? extends Action> valid, Class<? extends Action>... impossible) {
		for (Class<? extends Action> imp : impossible) {
			makesImpossible(valid, imp);
		}
	}




	/**
	 * Registers following relationship: for a given node (and direction):
	 * if "valid" is the type of a valid action, "impossible" can never be a valid action.
	 *
	 * @param valid      the class of the valid action
	 * @param impossible the class of the impossible action
	 */
	public void makesImpossible(Class<? extends Action> valid, Class<? extends Action> impossible) {
		final Set<Class<? extends Action>> set = mapping.computeIfAbsent(valid, k -> new HashSet<>());
		set.add(impossible);
	}




	/**
	 * Adds the given factory to the list of completed actions.
	 *
	 * @param factory the {@link ActionFactory}
	 */
	public void addValid(ActionFactory factory) {
		addValid(factory, factory.getDirection());
	}




	/**
	 * Adds the given factory to the list of completed actions in the given direction.
	 *
	 * @param factory   the {@link ActionFactory}
	 * @param direction the directon of the action
	 */
	public void addValid(ActionFactory factory, Direction direction) {
		addValid(factory.producesAction(), direction);
	}




	/**
	 * Adds the given action to the list of completed actions in the given direction.
	 *
	 * @param validAction the valid action
	 * @param direction   the directon of the action
	 */
	public void addValid(Class<? extends Action> validAction, Direction direction) {
		final Set<Class<? extends Action>> impossibleActions = mapping.get(validAction);
		if (impossibleActions != null) {
			for (Class<? extends Action> action : impossibleActions) {
				final boolean[] invalidDirections = invalidActions.computeIfAbsent(action.getSimpleName(), k -> new boolean[Direction.values().length]);
				invalidDirections[direction.ordinal()] = true;
			}
		}
	}




	/**
	 * Rests everything
	 */
	public void reset() {
		invalidActions.clear();
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
		return isPossible(factory.producesAction(), direction);
	}




	/**
	 * Checks if an action from is possible depending on the previously successful/valid actions
	 *
	 * @param action    the type of the {@link Action}
	 * @param direction the direction of the action
	 * @return whether an action in the given direction is still possible.
	 */
	public boolean isPossible(Class<? extends Action> action, Direction direction) {
		final boolean[] invalidDirections = invalidActions.get(action.getSimpleName());
		if (invalidDirections == null) {
			return true;
		} else {
			return !invalidDirections[direction.ordinal()];
		}
	}


}




