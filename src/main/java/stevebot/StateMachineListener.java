package stevebot;

public interface StateMachineListener<S extends Enum, T extends Enum> {


	/**
	 * Called when the state transitions to a new one
	 *
	 * @param start      the start state
	 * @param target     the next state
	 * @param transition the transition between the states
	 */
	void onTransition(S start, S target, T transition);

}
