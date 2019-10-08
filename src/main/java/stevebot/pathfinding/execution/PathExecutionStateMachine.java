package stevebot.pathfinding.execution;

import stevebot.misc.StateMachine;

public class PathExecutionStateMachine extends StateMachine<PathExecutionStateMachine.ExecutionState, PathExecutionStateMachine.ExecutionTransition> {


	public enum ExecutionState {
		PREPARE_EXECUTION,
		WAITING_TO_START,
		WAITING_FOR_SEGMENT,
		FOLLOWING,
		DONE,
		ERROR
	}






	public enum ExecutionTransition {
		START,
		START_FOLLOW,
		SEGMENT_CALCULATED,
		REACHED_END_OF_SEGMENT,
		REACHED_END_OF_PATH,
	}




	public PathExecutionStateMachine() {
		defineTransition(ExecutionState.PREPARE_EXECUTION, ExecutionTransition.START, ExecutionState.WAITING_TO_START);
		defineTransition(ExecutionState.WAITING_TO_START, ExecutionTransition.START_FOLLOW, ExecutionState.WAITING_FOR_SEGMENT);
		defineTransition(ExecutionState.WAITING_FOR_SEGMENT, ExecutionTransition.SEGMENT_CALCULATED, ExecutionState.FOLLOWING);
		defineTransition(ExecutionState.WAITING_FOR_SEGMENT, ExecutionTransition.REACHED_END_OF_PATH, ExecutionState.DONE);
		defineTransition(ExecutionState.FOLLOWING, ExecutionTransition.REACHED_END_OF_SEGMENT, ExecutionState.WAITING_FOR_SEGMENT);
		defineTransition(ExecutionState.FOLLOWING, ExecutionTransition.REACHED_END_OF_PATH, ExecutionState.DONE);
		defineErrorState(ExecutionState.ERROR);
		setState(ExecutionState.PREPARE_EXECUTION);
	}


}
