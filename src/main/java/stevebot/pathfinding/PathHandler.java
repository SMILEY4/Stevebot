package stevebot.pathfinding;

import net.minecraft.util.math.BlockPos;
import stevebot.Stevebot;
import stevebot.pathfinding.goal.Goal;

public class PathHandler {


	private PathExecutor excecutor = null;




	public void createPath(BlockPos from, Goal goal, boolean startFollowing, boolean enableFreelook) {
		if (excecutor == null) {
			excecutor = new PathExecutor(from, goal);
		} else {
			Stevebot.get().log("Can not start new path. Another path is already in progress.");
		}

	}




	public void startFollowing() {
		if (excecutor != null) {
			excecutor.startFollowing();
		}
	}




	public void stopFollowing() {
		if (excecutor != null) {
			excecutor.stopFollowing();
			Stevebot.get().getEventHandler().removeListener(excecutor);
			excecutor = null;
		}
	}


}
