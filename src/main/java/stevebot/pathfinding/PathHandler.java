package stevebot.pathfinding;

import stevebot.rendering.Renderable;
import net.minecraft.util.math.BlockPos;
import stevebot.Stevebot;
import stevebot.pathfinding.goal.Goal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PathHandler {


	private ExecutorService service = Executors.newSingleThreadExecutor();

	private Path path;
	private Renderable pathRenderable;

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
				pathRenderable = path.toRenderable();
				Stevebot.get().getRenderer().addRenderable(pathRenderable);
				Stevebot.get().getPlayerController().utils().sendMessage("Path found! " + ((System.currentTimeMillis() - ts) / 1000.0) + "s, nodes=" + path.nodes.size() + ", cost=" + path.cost + ", explored:" + Node.nodeCache.size());
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

}
