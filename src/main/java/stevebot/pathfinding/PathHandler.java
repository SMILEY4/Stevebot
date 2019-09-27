package stevebot.pathfinding;

import net.minecraft.util.math.BlockPos;
import stevebot.Stevebot;
import stevebot.pathfinding.execution.PathExecutor;
import stevebot.pathfinding.goal.Goal;
import stevebot.player.Camera;

public class PathHandler {


	private PathExecutor excecutor = null;




	/**
	 * Creates a new path and executor from the given start position to the given goal.
	 *
	 * @param from           the starting position
	 * @param goal           the goal of the resulting path
	 * @param startFollowing true, to immediately start following the path
	 * @param enableFreelook true, to enable freelook when following the path
	 */
	public void createPath(BlockPos from, Goal goal, boolean startFollowing, boolean enableFreelook) {
		if (excecutor == null) {
			excecutor = new PathExecutor(from, goal) {
				@Override
				public void onFinished() {
					excecutor = null;
				}
			};
			excecutor.start();
			if (startFollowing) {
				excecutor.startFollowing();
				if (enableFreelook) {
					Stevebot.get().getPlayerController().camera().setState(Camera.CameraState.FREELOOK);
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
