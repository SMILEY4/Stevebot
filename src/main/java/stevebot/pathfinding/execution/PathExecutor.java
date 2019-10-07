package stevebot.pathfinding.execution;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import stevebot.StateMachineListener;
import stevebot.Stevebot;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.events.EventListener;
import stevebot.pathfinding.goal.Goal;
import stevebot.pathfinding.path.EmptyPath;
import stevebot.pathfinding.path.Path;
import stevebot.pathfinding.path.PathFactory;
import stevebot.rendering.Color;
import stevebot.rendering.Renderable;
import stevebot.rendering.renderables.BoxRenderObject;
import stevebot.rendering.renderables.DynPointCollectionRenderObject;

import static stevebot.pathfinding.execution.PathExecutionStateMachine.ExecutionState;
import static stevebot.pathfinding.execution.PathExecutionStateMachine.ExecutionTransition;

public abstract class PathExecutor implements StateMachineListener<ExecutionState, ExecutionTransition> {


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
	private Renderable startRenderable;
	private Renderable goalRenderable;
	private DynPointCollectionRenderObject pathTraceRenderable = new DynPointCollectionRenderObject(3);

	private final EventListener<TickEvent.ClientTickEvent> clientTickListener = new EventListener<TickEvent.ClientTickEvent>() {
		@Override
		public Class<TickEvent.ClientTickEvent> getEventClass() {
			return TickEvent.ClientTickEvent.class;
		}




		@Override
		public void onEvent(TickEvent.ClientTickEvent event) {
			onClientTick();
		}

	};




	public PathExecutor(BaseBlockPos posStart, Goal goal) {
		this.pathFactory = new PathFactory(posStart, goal);
		this.stateMachine.addListener(this);
		this.goalRenderable = goal.createRenderable();
		this.startRenderable = new BoxRenderObject(posStart.copyAsMCBlockPos(), 3, Color.YELLOW);
		Stevebot.get().getRenderer().addRenderable(goalRenderable);
		Stevebot.get().getRenderer().addRenderable(startRenderable);
		Stevebot.get().getRenderer().addRenderable(pathTraceRenderable);
		Stevebot.get().getEventHandler().addListener(clientTickListener);
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
		Stevebot.get().getEventHandler().removeListener(clientTickListener);
		Stevebot.get().getRenderer().removeRenderable(goalRenderable);
		Stevebot.get().getRenderer().removeRenderable(startRenderable);
		Stevebot.get().getRenderer().removeRenderable(pathRenderable);
		Stevebot.get().getRenderer().removeRenderable(pathTraceRenderable);
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




	public void onClientTick() {
		if (!isExecuting) {
			return;
		}
		switch (stateMachine.getState()) {

			case PREPARE_EXECUTION: {
				pathFactory.prepareNextPath();
				stateMachine.fireTransition(ExecutionTransition.START);
				onClientTick();
				break;
			}

			case WAITING_TO_START: {
				if (follow) {
					stateMachine.fireTransition(ExecutionTransition.START_FOLLOW);
					onClientTick();
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
					onClientTick();
				} else {
					Stevebot.get().getPlayerController().input().stopAll();
				}
				break;
			}

			case FOLLOWING: {
				final StateFollow state = tick();
				if (state == StateFollow.FAILED) {
					stateMachine.fireError();
					onClientTick();
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
					onClientTick();
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

		pathTraceRenderable.addPoint(Stevebot.get().getPlayerController().utils().getPlayerPosition(), Color.MAGENTA);

		Stevebot.get().getPlayerController().input().stopAll();

		StateFollow actionState = crawler.getCurrentNodeTo().getAction().tick(fistTick);
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
