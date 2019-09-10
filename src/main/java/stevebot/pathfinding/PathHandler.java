package stevebot.pathfinding;

import net.minecraft.util.math.BlockPos;
import stevebot.Stevebot;
import stevebot.pathfinding.goal.Goal;
import stevebot.pathfinding.path.Path;
import stevebot.pathfinding.path.PathRenderable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PathHandler {


	private ExecutorService service = Executors.newSingleThreadExecutor();

	private Path path;
	private PathRenderable pathRenderable;

	private PathExecutor excecutor = null;




	public void calculatePath(BlockPos from, Goal goal, long timeout) {
		service.submit(() -> {
			Stevebot.get().getPlayerController().utils().sendMessage("Calculating Path from " + from + " to " + goal.goalString() + "  (timeout@" + (timeout / 1000.0) + ")");
			Stevebot.get().getRenderer().removeRenderable(pathRenderable);

			long ts = System.currentTimeMillis();

			Pathfinding pathfinder = new Pathfinding();
			path = pathfinder.calculatePath(from, goal, timeout);

			if (path == null) {
				Stevebot.get().getPlayerController().utils().sendMessage("No Path found! " + ((System.currentTimeMillis() - ts) / 1000.0) + "s, nodes:" + Node.nodeCache.size());
			} else {
				pathRenderable = new PathRenderable(path);
				Stevebot.get().getRenderer().addRenderable(pathRenderable);
				Stevebot.get().getPlayerController().utils().sendMessage("Path found! " + ((System.currentTimeMillis() - ts) / 1000.0) + "s, nodes=" + path.getNodes().size() + ", cost=" + path.getCost() + ", explored:" + Node.nodeCache.size());
			}

		});
	}




	public void startFollowLastPath() {
		stopFollowing();
		if (path != null) {
			excecutor = new PathExecutor(path);
			excecutor.start();
		}
	}




	public void stopFollowing() {
		if (excecutor != null) {
			excecutor.stop();
			Stevebot.get().getEventHandler().removeListener(excecutor);
		}
	}




	public void setPathRenderableStyle(PathRenderable.Style style) {
		if (pathRenderable != null) {
			pathRenderable.setStyle(style);
		}
	}

}
