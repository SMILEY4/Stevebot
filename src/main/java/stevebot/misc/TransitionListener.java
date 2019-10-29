package stevebot.misc;

public interface TransitionListener<S extends Enum, T extends Enum> {


	/**
	 * Called after the state transitioned to a new one
	 *
	 * @param previous   the previous state
	 * @param next       the next state
	 * @param transition the transition between the states
	 */
	void onTransition(S previous, S next, T transition);

}
