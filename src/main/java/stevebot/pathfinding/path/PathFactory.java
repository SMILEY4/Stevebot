package stevebot.pathfinding.path;

import net.minecraft.util.math.BlockPos;
import stevebot.Config;
import stevebot.Stevebot;
import stevebot.pathfinding.Pathfinding;
import stevebot.pathfinding.goal.Goal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PathFactory {


	private final ExecutorService executorService = Executors.newSingleThreadExecutor();
	private final Pathfinding pathfinding = new Pathfinding();

	private final List<Path> pathQueue = new ArrayList<>();
	private volatile boolean preparingPath = false;

	private final BlockPos posStart;
	private final Goal goal;




	/**
	 * @param posStart the start position of the path
	 * @param goal     the goal of the path
	 */
	public PathFactory(BlockPos posStart, Goal goal) {
		this.posStart = posStart;
		this.goal = goal;
	}




	/**
	 * @return true, if a path(-segment) is ready.
	 */
	public boolean hasPath() {
		synchronized (pathQueue) {
			return !pathQueue.isEmpty();
		}
	}




	/**
	 * @return the current/oldest path(-segment)
	 */
	public Path getCurrentPath() {
		synchronized (pathQueue) {
			if (hasPath()) {
				return pathQueue.get(0);
			} else {
				return null;
			}
		}
	}




	/**
	 * @return the last/newest calculated path(-segment)
	 */
	public Path getLastPath() {
		synchronized (pathQueue) {
			if (hasPath()) {
				return pathQueue.get(pathQueue.size() - 1);
			} else {
				return null;
			}
		}
	}




	/**
	 * Start calculating the next path-segment
	 *
	 * @return
	 */
	public void prepareNextPath() {
		if (preparingPath) {
			return;
		}
		preparingPath = true;
		Stevebot.get().getLogger().info("Preparing path segment");
		if (hasPath()) {
			if (getCurrentPath().reachedGoal()) {
				preparingPath = false;
			} else {
				final Path prevPath = getLastPath();
				executorService.submit(() -> {
					Path path = pathfinding.calculatePath(prevPath.getLastNode().pos, goal, Config.getPathfindingTimeout() * 1000);
					synchronized (pathQueue) {
						pathQueue.add(path);
						preparingPath = false;
					}
				});
			}

		} else {
			executorService.submit(() -> {
				Path path = pathfinding.calculatePath(posStart, goal, Config.getPathfindingTimeout() * 1000);
				if (!(path instanceof EmptyPath)) {
					synchronized (pathQueue) {
						pathQueue.add(path);
						preparingPath = false;
					}
				}
			});
		}
	}




	/**
	 * Removes the current/oldest path
	 */
	public void removeCurrentPath() {
		if (hasPath()) {
			synchronized (pathQueue) {
				pathQueue.remove(0);
			}
		}
	}




	/**
	 * @return the starting position of the complete path
	 */
	public BlockPos getPosStart() {
		return posStart;
	}




	/**
	 * @return the goal of the complete path
	 */
	public Goal getGoal() {
		return goal;
	}


}
