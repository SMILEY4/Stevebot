package stevebot;

import java.util.ArrayList;
import java.util.List;

public class StateMachine<S extends Enum, T extends Enum> {


	private S errorState = null;
	private S current;
	private List<TransitionDefinition> definitions = new ArrayList<>();




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


