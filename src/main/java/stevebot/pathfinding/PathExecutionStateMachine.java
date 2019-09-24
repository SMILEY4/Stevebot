package stevebot.pathfinding;

import stevebot.StateMachine;

public class PathExecutionStateMachine extends StateMachine<PathExecutionStateMachine.ExecutionState, PathExecutionStateMachine.ExecutionTransition> {


	public enum ExecutionState {
		PREPARE_EXECUTION,
		WAITING_FOR_SEGMENT,
		FOLLOWING,
		DONE,
		ERROR
	}






	public enum ExecutionTransition {
		START,
		SEGMENT_CALCULATED,
		REACHED_END_OF_SEGMENT,
		REACHED_END_OF_PATH,
	}




	public PathExecutionStateMachine(ExecutionState startState) {
		defineTransition(ExecutionState.PREPARE_EXECUTION, ExecutionTransition.START, ExecutionState.WAITING_FOR_SEGMENT);
		defineTransition(ExecutionState.WAITING_FOR_SEGMENT, ExecutionTransition.SEGMENT_CALCULATED, ExecutionState.FOLLOWING);
		defineTransition(ExecutionState.FOLLOWING, ExecutionTransition.REACHED_END_OF_SEGMENT, ExecutionState.WAITING_FOR_SEGMENT);
		defineTransition(ExecutionState.FOLLOWING, ExecutionTransition.REACHED_END_OF_PATH, ExecutionState.DONE);
		defineErrorState(ExecutionState.ERROR);
		setState(startState);
	}


}
