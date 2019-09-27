package stevebot.pathfinding.execution;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import stevebot.StateMachineListener;
import stevebot.Stevebot;
import stevebot.events.GameTickListener;
import stevebot.pathfinding.goal.Goal;
import stevebot.pathfinding.path.EmptyPath;
import stevebot.pathfinding.path.Path;
import stevebot.pathfinding.path.PathFactory;
import stevebot.rendering.Renderable;

import static stevebot.pathfinding.execution.PathExecutionStateMachine.ExecutionState;
import static stevebot.pathfinding.execution.PathExecutionStateMachine.ExecutionTransition;

public abstract class PathExecutor implements GameTickListener, StateMachineListener<ExecutionState, ExecutionTransition> {


	public enum StateFollow {
		EXEC, DONE, FAILED;
	}






	private PathFactory pathFactory;
	private PathExecutionStateMachine stateMachine = new PathExecutionStateMachine();
	private PathCrawler crawler = new PathCrawler();

	private boolean isExecuting = false;
	private boolean follow = false;
	private boolean fistTick = true;

	private Renderable pathRenderable;
	private Renderable goalRenderable;




	public PathExecutor(BlockPos posStart, Goal goal) {
		this.pathFactory = new PathFactory(posStart, goal);
		this.stateMachine.addListener(this);
		this.goalRenderable = goal.createRenderable();
		Stevebot.get().getRenderer().addRenderable(goalRenderable);
		Stevebot.get().getEventHandler().addListener(this);
	}




	/**
	 * Starts execution the specified path.
	 */
	public void start() {
		Stevebot.get().log("Starting Path from " + pathFactory.getPosStart().getX() + " " + pathFactory.getPosStart().getY() + " " + pathFactory.getPosStart().getZ() + " to " + pathFactory.getGoal().goalString());
		isExecuting = true;
	}




	/**
	 * Stops the execution the specified path. It can not be restarted.
	 */
	public void stop() {
		isExecuting = false;
		Stevebot.get().getEventHandler().removeListener(this);
		Stevebot.get().getRenderer().removeRenderable(goalRenderable);
		Stevebot.get().getRenderer().removeRenderable(pathRenderable);
		onFinished();
	}




	/**
	 * Start following the path.
	 */
	public void startFollowing() {
		follow = true;
	}




	/**
	 * Called when the path is completed, failed or stopped
	 */
	public abstract void onFinished();




	@Override
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (!isExecuting) {
			return;
		}
		switch (stateMachine.getState()) {

			case PREPARE_EXECUTION: {
				pathFactory.prepareNextPath();
				stateMachine.fireTransition(ExecutionTransition.START);
				onClientTick(event);
				break;
			}

			case WAITING_TO_START: {
				if (follow) {
					stateMachine.fireTransition(ExecutionTransition.START_FOLLOW);
					onClientTick(event);
				}
				break;
			}

			case WAITING_FOR_SEGMENT: {
				if (pathFactory.hasPath()) {
					if (pathFactory.getCurrentPath() instanceof EmptyPath || pathFactory.getCurrentPath().getNodes().size() <= 1) {
						stateMachine.fireTransition(ExecutionTransition.REACHED_END_OF_PATH);
					} else {
						crawler.startPath(pathFactory.getCurrentPath());
						stateMachine.fireTransition(ExecutionTransition.SEGMENT_CALCULATED);
					}
					onClientTick(event);
				} else {
					Stevebot.get().getPlayerController().input().stopAll();
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
						stateMachine.fireTransition(ExecutionTransition.REACHED_END_OF_PATH);
					} else {
						pathFactory.prepareNextPath();
						pathFactory.removeCurrentPath();
						stateMachine.fireTransition(ExecutionTransition.REACHED_END_OF_SEGMENT);
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




	@Override
	public void onTransition(ExecutionState start, ExecutionState target, ExecutionTransition transition) {
		if (transition == ExecutionTransition.SEGMENT_CALCULATED) {
			Stevebot.get().getRenderer().removeRenderable(pathRenderable);
			Path path = pathFactory.getLastPath();
			pathRenderable = Path.toRenderable(path);
			Stevebot.get().getRenderer().addRenderable(pathRenderable);
		}
	}




	/**
	 * Update the current action. If the action is completed the {@link PathCrawler} will step to the next action.
	 *
	 * @return the resulting {@link StateFollow} of this tick
	 */
	private StateFollow tick() {

		Stevebot.get().getPlayerController().input().stopAll();

		StateFollow actionState = crawler.getCurrentNodeTo().action.tick(fistTick);
		fistTick = false;

		if (actionState == StateFollow.FAILED) {
			return StateFollow.FAILED;
		}

		if (actionState == StateFollow.DONE) {
			fistTick = true;
			boolean hasNext = crawler.nextAction();
			if (!hasNext) {
				return StateFollow.DONE;
			}
		}

		return StateFollow.EXEC;
	}


}
