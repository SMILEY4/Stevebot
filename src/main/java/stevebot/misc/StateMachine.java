package stevebot.misc;

import java.util.*;

public class StateMachine<S extends Enum, T extends Enum> {


	private S errorState = null;
	private S current;
	private List<TransitionDefinition> definitions = new ArrayList<>();
	private List<TransitionListener<S, T>> transitionListenersAll = new ArrayList<>();
	private Map<T, List<TransitionListener<S, T>>> transitionListenerMap = new HashMap<>();




	public StateMachine() {
	}




	/**
	 * @param startState the starting state
	 */
	public StateMachine(S startState) {
		setState(startState);
	}




	/**
	 * Adds the given transition listener
	 *
	 * @param listener the listener
	 */
	public void addListener(TransitionListener<S, T> listener) {
		this.transitionListenersAll.add(listener);
	}




	/**
	 * Adds the given transition listener to listen to the given transition.
	 *
	 * @param transition the transition to listen to
	 * @param listener   the listener
	 */
	public void addListener(T transition, TransitionListener<S, T> listener) {
		if (!transitionListenerMap.containsKey(transition)) {
			transitionListenerMap.put(transition, new ArrayList<>());
		}
		transitionListenerMap.get(transition).add(listener);
	}




	/**
	 * Removes a the given listener
	 *
	 * @param listener the listener
	 */
	public void removeListener(TransitionListener<S, T> listener) {
		this.transitionListenersAll.remove(listener);
		transitionListenerMap.values().forEach(list -> list.remove(listener));
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
	 * @param transition the transition between the current state and the target state
	 */
	public void fireTransition(T transition) {
		for (TransitionDefinition definition : definitions) {
			if (definition.transition == transition && definition.start == getState()) {
				setState(definition.target);
				transitionListenersAll.forEach(listener -> listener.onTransition(definition.start, definition.target, transition));
				transitionListenerMap.getOrDefault(transition, Collections.emptyList()).forEach(
						listener -> listener.onTransition(definition.start, definition.target, transition)
				);
				return;
			}
		}
	}




	/**
	 * Transition into the error state
	 */
	public void fireError() {
		setState(errorState);
		transitionListenersAll.forEach(listener -> listener.onTransition(current, errorState, null));
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


