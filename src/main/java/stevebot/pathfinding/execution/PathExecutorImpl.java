package stevebot.pathfinding.execution;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import stevebot.Stevebot;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.events.EventListener;
import stevebot.misc.Config;
import stevebot.misc.ProcState;
import stevebot.misc.StateMachineListener;
import stevebot.pathfinding.goal.Goal;
import stevebot.pathfinding.path.EmptyPath;
import stevebot.pathfinding.path.Path;
import stevebot.pathfinding.path.PathFactory;
import stevebot.player.PlayerCameraImpl;
import stevebot.player.PlayerUtils;
import stevebot.rendering.Color;
import stevebot.rendering.Renderable;
import stevebot.rendering.Renderer;
import stevebot.rendering.renderables.BoxRenderObject;
import stevebot.rendering.renderables.DynPointCollectionRenderObject;

import static stevebot.pathfinding.execution.PathExecutionStateMachine.ExecutionState;
import static stevebot.pathfinding.execution.PathExecutionStateMachine.ExecutionTransition;

public class PathExecutorImpl implements StateMachineListener<ExecutionState, ExecutionTransition>, PathExecutor {


	private PathFactory pathFactory;
	private PathExecutionStateMachine stateMachine = new PathExecutionStateMachine();
	private PathCrawler crawler = new PathCrawler();

	private boolean isExecuting = false;
	private boolean follow = false;
	private boolean firstTick = true;

	private Renderer renderer;
	private Renderable pathRenderable;
	private Renderable startRenderable;
	private Renderable goalRenderable;
	private DynPointCollectionRenderObject pathTraceRenderable = new DynPointCollectionRenderObject(3);

	private PathExecutionListener pathListener;

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




	public PathExecutorImpl(BaseBlockPos posStart, Goal goal, Renderer renderer) {
		this.pathFactory = new PathFactory(posStart, goal);
		this.stateMachine.addListener(this);
		this.goalRenderable = goal.createRenderable();
		this.startRenderable = new BoxRenderObject(posStart.copyAsMCBlockPos(), 3, Color.YELLOW);
		this.renderer = renderer;
		renderer.addRenderable(goalRenderable);
		renderer.addRenderable(startRenderable);
		renderer.addRenderable(pathTraceRenderable);
	}




	@Override
	public EventListener getTickListener() {
		return clientTickListener;
	}




	@Override
	public void setPathListener(PathExecutionListener listener) {
		this.pathListener = listener;
	}




	@Override
	public void start() {
		Stevebot.log("Starting Path from " + pathFactory.getPosStart().getX() + " " + pathFactory.getPosStart().getY() + " " + pathFactory.getPosStart().getZ() + " to " + pathFactory.getGoal().goalString());
		isExecuting = true;
	}




	@Override
	public void stop() {
		PlayerUtils.getInput().stopAll();
		isExecuting = false;
		if(!Config.isKeepPathRenderable()) {
			renderer.removeRenderable(goalRenderable);
			renderer.removeRenderable(startRenderable);
			renderer.removeRenderable(pathRenderable);
			renderer.removeRenderable(pathTraceRenderable);
		}
		onFinished();
	}




	@Override
	public void startFollowing(boolean enableFreelook) {
		if (enableFreelook) {
			PlayerUtils.getCamera().setState(PlayerCameraImpl.CameraState.FREELOOK);
		}
		follow = true;
	}




	/**
	 * Called when the path is completed, failed or stopped
	 */
	public void onFinished() {
		if (pathListener != null) {
			pathListener.onFinished();
		}
	}




	private void onClientTick() {
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
					PlayerUtils.getInput().stopAll();
				}
				break;
			}

			case FOLLOWING: {
				final ProcState state = tick();
				if (state == ProcState.FAILED) {
					stateMachine.fireError();
					onClientTick();
				}
				if (state == ProcState.DONE) {
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
				Stevebot.log("Done following path.");
				stop();
				break;
			}

			case ERROR: {
				Stevebot.log("Failed to follow path.");
				stop();
				break;
			}
		}
	}




	@Override
	public void onTransition(ExecutionState start, ExecutionState target, ExecutionTransition transition) {
		if (transition == ExecutionTransition.SEGMENT_CALCULATED) {
			renderer.removeRenderable(pathRenderable);
			Path path = pathFactory.getLastPath();
			pathRenderable = Path.toRenderable(path);
			renderer.addRenderable(pathRenderable);
		}
	}




	/**
	 * Update the current action. If the action is completed the {@link PathCrawler} will step to the next action.
	 *
	 * @return the resulting {@link ProcState} of this tick
	 */
	private ProcState tick() {

		pathTraceRenderable.addPoint(PlayerUtils.getPlayerPosition(), Color.MAGENTA);

		PlayerUtils.getInput().stopAll();

		if (firstTick) {
			crawler.getCurrentNodeTo().getAction().resetAction();
		}
		ProcState actionState = crawler.getCurrentNodeTo().getAction().tick(firstTick);
		firstTick = false;

		if (actionState == ProcState.FAILED) {
			return ProcState.FAILED;
		}

		if (actionState == ProcState.DONE) {
			firstTick = true;
			boolean hasNext = crawler.nextAction();
			if (!hasNext) {
				return ProcState.DONE;
			}
		}

		return ProcState.EXECUTING;
	}


}
