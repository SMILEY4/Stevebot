package stevebot.pathfinding;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.util.math.BlockPos;
import stevebot.Stevebot;
import stevebot.pathfinding.goal.ExactGoal;
import stevebot.pathfinding.goal.Goal;
import stevebot.pathfinding.goal.XZGoal;
import stevebot.pathfinding.path.EmptyPath;
import stevebot.pathfinding.path.Path;
import stevebot.pathfinding.path.PathRenderable;
import stevebot.rendering.Color;
import stevebot.rendering.Renderable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PathHandler {


	private ExecutorService service = Executors.newSingleThreadExecutor();
	private Path path;
	private PathExecutor excecutor = null;

	private Renderable goalRenderable = null;
	private PathRenderable pathRenderable;
	private Renderable cachedChunkRenderable = null;




	public void calculatePath(BlockPos from, Goal goal, long timeout) {


		service.submit(() -> {
			Stevebot.get().getPlayerController().utils().sendMessage("Calculating Path from " + from + " to " + goal.goalString() + "  (timeout@" + (timeout / 1000.0) + ")");
			Stevebot.get().getRenderer().removeRenderable(pathRenderable);

			Stevebot.get().getRenderer().removeRenderable(goalRenderable);
			goalRenderable = renderer -> {
				renderer.beginBoxes(3);
				if (goal instanceof ExactGoal) {
					renderer.drawBoxOpen(((ExactGoal) goal).pos, Color.GREEN);
				} else {
					XZGoal xzGoal = (XZGoal) goal;
					renderer.drawBoxOpen(new Vector3d(xzGoal.x, 0, xzGoal.z), new Vector3d(xzGoal.x+1, 255, xzGoal.z+1), Color.GREEN);
				}
				renderer.end();
			};
			Stevebot.get().getRenderer().addRenderable(goalRenderable);


			long ts = System.currentTimeMillis();

			Pathfinding pathfinder = new Pathfinding();
			path = pathfinder.calculatePath(from, goal, timeout);

			if (path instanceof EmptyPath) {
				Stevebot.get().getPlayerController().utils().sendMessage("No Path found! " + ((System.currentTimeMillis() - ts) / 1000.0) + "s, nodes:" + Node.nodeCache.size());
			} else {
				pathRenderable = new PathRenderable(path);
				Stevebot.get().getRenderer().addRenderable(pathRenderable);
				Stevebot.get().getPlayerController().utils().sendMessage("Path found! (reachedGoal=" + path.reachedGoal() + ") " + ((System.currentTimeMillis() - ts) / 1000.0) + "s, nodes=" + path.getNodes().size() + ", cost=" + path.getCost() + ", explored:" + Node.nodeCache.size());
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




	public void showCachedChunks(boolean show) {
		if (show) {
			if (cachedChunkRenderable == null) {
				cachedChunkRenderable = Stevebot.get().getBlockProvider().getBlockCache().getChunkCache().getChunkCacheRenderable();
				Stevebot.get().getRenderer().addRenderable(cachedChunkRenderable);
			}
		} else {
			if (cachedChunkRenderable != null) {
				Stevebot.get().getRenderer().removeRenderable(cachedChunkRenderable);
				cachedChunkRenderable = null;
			}
		}
	}

}
