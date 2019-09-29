package stevebot;

import java.util.ArrayList;
import java.util.List;

public class StateMachine<S extends Enum, T extends Enum> {


	private S errorState = null;
	private S current;
	private List<TransitionDefinition> definitions = new ArrayList<>();
	private List<StateMachineListener<S, T>> listeners = new ArrayList<>();




	public StateMachine() {
	}




	/**
	 * @param startState the starting state
	 */
	public StateMachine(S startState) {
		setState(startState);
	}




	/**
	 * Adds the given listener
	 *
	 * @param listener the listener
	 */
	public void addListener(StateMachineListener<S, T> listener) {
		this.listeners.add(listener);
	}




	/**
	 * Removes a the given listener
	 *
	 * @param listener the listener
	 */
	public void removeListener(StateMachineListener<S, T> listener) {
		this.listeners.remove(listener);
	}




	/**
	 * Defines a new transition between the given states
	 *
	 * @param start      the start state
	 * @param transition the transition between the states
	 * @param target     the end state
	 */
	public void defineTransition(S start, T transition, S target) {
		definitions.add(new TransitionDefinition(start, target, transition));
	}




	/**
	 * Defines a state as an error state.
	 *
	 * @param errorState the error state
	 */
	public void defineErrorState(S errorState) {
		this.errorState = errorState;
	}




	/**
	 * Transition to the next state (if possible) depending on the given transition and the current state.
	 *
	 * @param transition the transition between the curren state and the target state
	 */
	public void fireTransition(T transition) {
		for (TransitionDefinition definition : definitions) {
			if (definition.transition == transition && definition.start == getState()) {
				setState(definition.target);
				listeners.forEach(listener -> listener.onTransition(definition.start, definition.target, transition));
				return;
			}
		}
	}




	/**
	 * Transition into the error state
	 */
	public void fireError() {
		setState(errorState);
		listeners.forEach(listener -> listener.onTransition(current, errorState, null));
	}




	/**
	 * Sets the current state to the given state. This will not call the listeners.
	 *
	 * @param state the new current state
	 */
	public void setState(S state) {
		this.current = state;
	}




	/**
	 * @return the current state
	 */
	public S getState() {
		return current;
	}




	class TransitionDefinition {


		final S start;
		final S target;
		final T transition;




		TransitionDefinition(S start, S target, T transition) {
			this.start = start;
			this.target = target;
			this.transition = transition;
		}


	}


}


