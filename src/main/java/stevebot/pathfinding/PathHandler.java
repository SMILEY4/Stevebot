package stevebot.pathfinding;

import stevebot.Stevebot;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.events.EventManager;
import stevebot.pathfinding.execution.PathExecutor;
import stevebot.pathfinding.goal.Goal;
import stevebot.player.Camera;
import stevebot.player.PlayerController;
import stevebot.rendering.Renderer;

public class PathHandler {


	private PathExecutor excecutor = null;

	private EventManager eventManager;
	private Renderer renderer;
	private PlayerController playerController;




	public PathHandler(EventManager eventManager, Renderer renderer, PlayerController playerController) {
		this.renderer = renderer;
		this.eventManager = eventManager;
		this.playerController = playerController;
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
			excecutor = new PathExecutor(from, goal, eventManager, renderer, playerController) {
				@Override
				public void onFinished() {
					excecutor = null;
				}
			};
			excecutor.start();
			if (startFollowing) {
				excecutor.startFollowing();
				if (enableFreelook) {
					playerController.camera().setState(Camera.CameraState.FREELOOK);
				}
			}
		} else {
			Stevebot.get().log("Can not start new path. Another path is already in progress.");
		}
	}




	/**
	 * Start following the created path.
	 */
	public void startFollowing() {
		if (excecutor != null) {
			excecutor.startFollowing();
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
