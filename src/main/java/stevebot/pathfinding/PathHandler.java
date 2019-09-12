package stevebot.pathfinding;

import net.minecraft.util.math.BlockPos;
import stevebot.Stevebot;
import stevebot.pathfinding.goal.Goal;

public class PathHandler {


	private PathExecutor excecutor = null;




	public void createPath(BlockPos from, Goal goal, long timeout) {
		excecutor = new PathExecutor(from, goal);
	}




	public void startFollowLastPath() {
		if (excecutor != null) {
			excecutor.startFollowing();
		}
	}




	public void stopFollowing() {
		if (excecutor != null) {
			excecutor.stopFollowing();
			Stevebot.get().getEventHandler().removeListener(excecutor);
		}
	}


}
