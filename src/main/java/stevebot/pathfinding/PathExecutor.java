package stevebot.pathfinding;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import stevebot.Stevebot;
import stevebot.events.GameTickListener;
import stevebot.pathfinding.goal.Goal;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.path.EmptyPath;
import stevebot.pathfinding.path.Path;
import stevebot.pathfinding.path.PathFactory;

public abstract class PathExecutor implements GameTickListener {


	public enum StateFollow {
		EXEC, DONE, FAILED;
	}






	private PathFactory pathFactory;
	private PathExecutionStateMachine stateMachine;

	private int currentIndexFrom = 0;
	private Node currentNodeTo;

	private boolean isExecuting = false;
	private boolean follow = false;
	private boolean fistTick = true;




	public PathExecutor(BlockPos posStart, Goal goal) {
		this.pathFactory = new PathFactory(posStart, goal);
		Stevebot.get().getEventHandler().addListener(this);
	}




	public void start() {
		Stevebot.get().log("Starting Path from " + pathFactory.getPosStart().getX() + " " + pathFactory.getPosStart().getY() + " " + pathFactory.getPosStart().getZ() + " to " + pathFactory.getGoal().goalString());
		stateMachine = new PathExecutionStateMachine(PathExecutionStateMachine.ExecutionState.PREPARE_EXECUTION);
		isExecuting = true;
	}




	private void stop() {
		isExecuting = false;
		Stevebot.get().getEventHandler().removeListener(this);
		onFinished();
	}




	public void startFollowing() {
		follow = true;
	}




	public abstract void onFinished();




	@Override
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (!isExecuting) {
			return;
		}
		switch (stateMachine.getState()) {

			case PREPARE_EXECUTION: {
				pathFactory.prepareNextPath();
				stateMachine.fireTransition(PathExecutionStateMachine.ExecutionTransition.START);
				onClientTick(event);
				break;
			}

			case WAITING_TO_START: {
				if (follow) {
					stateMachine.fireTransition(PathExecutionStateMachine.ExecutionTransition.START_FOLLOW);
					onClientTick(event);
				}
				break;
			}

			case WAITING_FOR_SEGMENT: {
				if (pathFactory.hasPath()) {
					if (pathFactory.getCurrentPath() instanceof EmptyPath || pathFactory.getCurrentPath().getNodes().size() <= 1) {
						stateMachine.fireTransition(PathExecutionStateMachine.ExecutionTransition.REACHED_END_OF_PATH);
					} else {
						currentIndexFrom = 0;
						currentNodeTo = pathFactory.getCurrentPath().getNodes().get(currentIndexFrom + 1);
						stateMachine.fireTransition(PathExecutionStateMachine.ExecutionTransition.SEGMENT_CALCULATED);
					}
					onClientTick(event);
				}
				break;
			}

			case FOLLOWING: {
				final StateFollow state = tick();
				if (state == StateFollow.FAILED) {
					stateMachine.fireError();
					onClientTick(event);
				}
				if (state == StateFollow.DONE) {
					Path path = pathFactory.getCurrentPath();
					if (path.reachedGoal() || path instanceof EmptyPath) {
						pathFactory.removeCurrentPath();
						stateMachine.fireTransition(PathExecutionStateMachine.ExecutionTransition.REACHED_END_OF_PATH);
					} else {
						pathFactory.prepareNextPath();
						pathFactory.removeCurrentPath();
						stateMachine.fireTransition(PathExecutionStateMachine.ExecutionTransition.REACHED_END_OF_SEGMENT);
					}
					onClientTick(event);
				}
				break;
			}

			case DONE: {
				Stevebot.get().log("Done following path.");
				stop();
				break;
			}

			case ERROR: {
				Stevebot.get().log("Failed to follow path.");
				stop();
				break;
			}
		}
	}




	private StateFollow tick() {

		Stevebot.get().getPlayerController().input().stopAll();

		StateFollow actionState = currentNodeTo.action.tick(fistTick);
		fistTick = false;

		if (actionState == StateFollow.FAILED) {
			return StateFollow.FAILED;
		}

		if (actionState == StateFollow.DONE) {
			fistTick = true;
			boolean hasNext = nextAction();
			if (!hasNext) {
				return StateFollow.DONE;
			}
		}

		return StateFollow.EXEC;

	}




	private boolean nextAction() {
		currentIndexFrom++;
		if (currentIndexFrom == pathFactory.getCurrentPath().getNodes().size() - 1) { // next is last node
			currentNodeTo = null;
			return false;
		} else {
			currentNodeTo = pathFactory.getCurrentPath().getNodes().get(currentIndexFrom + 1);
			currentNodeTo.action.resetAction();
			return true;
		}
	}


}
