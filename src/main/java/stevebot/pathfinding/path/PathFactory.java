package stevebot.pathfinding.path;

import stevebot.Stevebot;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.misc.Config;
import stevebot.pathfinding.Pathfinding;
import stevebot.pathfinding.goal.Goal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PathFactory {


	private static final boolean MULTITHREAD = true;

	private final ExecutorService executorService = Executors.newSingleThreadExecutor();
	private final Pathfinding pathfinding = new Pathfinding();

	private final List<Path> pathQueue = new ArrayList<>();
	private volatile boolean preparingPath = false;

	private final BaseBlockPos posStart;
	private final Goal goal;




	/**
	 * @param posStart the start position of the path
	 * @param goal     the goal of the path
	 */
	public PathFactory(BaseBlockPos posStart, Goal goal) {
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
	 */
	public void prepareNextPath() {
		if (preparingPath) {
			return;
		}
		preparingPath = true;
		Stevebot.getLogger().info("Preparing path segment");
		if (hasPath()) {
			if (getCurrentPath().reachedGoal()) {
				preparingPath = false;
			} else {
				final Path prevPath = getLastPath();
				if (MULTITHREAD) {
					executorService.submit(() -> {
						Path path = pathfinding.calculatePath(prevPath.getLastNode().getPos(), goal, (long) (Config.getPathfindingTimeout() * 1000)).finalPath;
						synchronized (pathQueue) {
							pathQueue.add(path);
							preparingPath = false;
						}
					});
				} else {
					Path path = pathfinding.calculatePath(prevPath.getLastNode().getPos(), goal, (long) (Config.getPathfindingTimeout() * 1000)).finalPath;
					synchronized (pathQueue) {
						pathQueue.add(path);
						preparingPath = false;
					}
				}
			}

		} else {
			if (MULTITHREAD) {
				executorService.submit(() -> {
					Path path = pathfinding.calculatePath(posStart, goal, (long) (Config.getPathfindingTimeout() * 1000)).finalPath;
					if (!(path instanceof EmptyPath)) {
						synchronized (pathQueue) {
							pathQueue.add(path);
							preparingPath = false;
						}
					}
				});
			} else {
				Path path = pathfinding.calculatePath(posStart, goal, (long) (Config.getPathfindingTimeout() * 1000)).finalPath;
				if (!(path instanceof EmptyPath)) {
					synchronized (pathQueue) {
						pathQueue.add(path);
						preparingPath = false;
					}
				}
			}
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
	public BaseBlockPos getPosStart() {
		return posStart;
	}




	/**
	 * @return the goal of the complete path
	 */
	public Goal getGoal() {
		return goal;
	}


}
