package stevebot.pathfinding;

import stevebot.Stevebot;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.events.EventManager;
import stevebot.pathfinding.execution.PathExecutor;
import stevebot.pathfinding.execution.PathExecutorImpl;
import stevebot.pathfinding.goal.Goal;
import stevebot.rendering.Renderer;

public class PathHandler {


	private PathExecutor excecutor = null;

	private EventManager eventManager;
	private Renderer renderer;




	public PathHandler(EventManager eventManager, Renderer renderer) {
		this.renderer = renderer;
		this.eventManager = eventManager;
	}




	/**
	 * Creates a new path and executor from the given start position to the given goal.
	 *
	 * @param from           the starting position
	 * @param goal           the goal of the resulting path
	 * @param startFollowing true, to immediately start following the path
	 * @param enableFreelook true, to enable freelook when following the path
	 */
	public void createPath(BaseBlockPos from, Goal goal, boolean startFollowing, boolean enableFreelook) {
		if (excecutor == null) {
			excecutor = new PathExecutorImpl(from, goal, renderer);
			eventManager.addListener(excecutor.getTickListener());
			excecutor.setPathListener(() -> {
				eventManager.removeListener(excecutor.getTickListener());
				excecutor = null;
			});
			excecutor.start();
			if (startFollowing) {
				excecutor.startFollowing(enableFreelook);
			}
		} else {
			Stevebot.log("Can not start new path. Another path is already in progress.");
		}
	}




	/**
	 * Start following the created path.
	 */
	public void startFollowing() {
		if (excecutor != null) {
			excecutor.startFollowing(false);
		}
	}




	/**
	 * Stop the current path
	 */
	public void cancelPath() {
		if (excecutor != null) {
			excecutor.stop();
		}
	}


}
