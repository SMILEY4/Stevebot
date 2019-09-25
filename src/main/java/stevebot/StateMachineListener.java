package stevebot;

public interface StateMachineListener<S extends Enum, T extends Enum> {


	void onTransition(S start, S target, T transition);

}
