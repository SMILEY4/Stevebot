package stevebot;

import java.util.ArrayList;
import java.util.List;

public class StateMachine<S extends Enum, T extends Enum> {


	private S errorState = null;
	private S current;
	private List<TransitionDefinition> definitions = new ArrayList<>();
	private List<StateMachineListener<S, T>> listeners = new ArrayList<>();




	public void addListener(StateMachineListener<S, T> listener) {
		this.listeners.add(listener);
	}




	public void removeListener(StateMachineListener<S, T> listener) {
		this.listeners.remove(listener);
	}




	public void defineTransition(S start, T transition, S target) {
		definitions.add(new TransitionDefinition(start, target, transition));
	}




	public void defineErrorState(S errorState) {
		this.errorState = errorState;
	}




	public void fireTransition(T transition) {
		for (TransitionDefinition definition : definitions) {
			if (definition.transition == transition && definition.start == getState()) {
				setState(definition.target);
				listeners.forEach(listener -> listener.onTransition(definition.start, definition.target, transition));
				return;
			}
		}
	}




	public void fireError() {
		setState(errorState);
	}




	public void setState(S state) {
		this.current = state;
	}




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


