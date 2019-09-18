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




	public PathFactory(BlockPos posStart, Goal goal) {
		this.posStart = posStart;
		this.goal = goal;
	}




	public boolean hasPath() {
		synchronized (pathQueue) {
			return !pathQueue.isEmpty();
		}
	}




	public Path getCurrentPath() {
		synchronized (pathQueue) {
			if (hasPath()) {
				return pathQueue.get(0);
			} else {
				return null;
			}
		}
	}




	public boolean isCurrentLastSegment() {
		synchronized (pathQueue) {
			return pathQueue.size() == 1;
		}
	}




	public Path getLastPath() {
		synchronized (pathQueue) {
			if (hasPath()) {
				return pathQueue.get(pathQueue.size() - 1);
			} else {
				return null;
			}
		}
	}




	public boolean prepareNextPath() {
		if (preparingPath) {
			return false;
		}
		preparingPath = true;
		Stevebot.get().getLogger().info("Preparing next path segment: " + !(hasPath() && getCurrentPath().reachedGoal()));
		if (hasPath()) {
			if (getCurrentPath().reachedGoal()) {
				preparingPath = false;
				return true;
			} else {
				final Path prevPath = getLastPath();
				executorService.submit(() -> {
					Path path = pathfinding.calculatePath(prevPath.getLastNode().pos, goal, Config.getPathfindingTimeout() * 1000);
					synchronized (pathQueue) {
						pathQueue.add(path);
						preparingPath = false;
					}
				});
				return false;
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
			return false;
		}
	}




	public void removeCurrentPath() {
		if (hasPath()) {
			synchronized (pathQueue) {
				pathQueue.remove(0);
			}
		}
	}


}
