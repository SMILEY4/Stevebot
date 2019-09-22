package stevebot.pathfinding;

import net.minecraft.util.math.BlockPos;
import stevebot.Config;
import stevebot.Stevebot;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionFactoryProvider;
import stevebot.pathfinding.goal.Goal;
import stevebot.pathfinding.nodes.BestNodesContainer;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.path.CompletedPath;
import stevebot.pathfinding.path.EmptyPath;
import stevebot.pathfinding.path.PartialPath;
import stevebot.pathfinding.path.Path;
import stevebot.rendering.Renderable;

import java.util.*;

public class Pathfinding {


	private static final ActionFactoryProvider actionFactoryProvider = new ActionFactoryProvider();




	public Path calculatePath(BlockPos posStart, Goal goal, long timeoutInMs) {

		// prepare node cache
		Node.nodeCache.clear();
		Node nodeStart = Node.get(posStart);
		nodeStart.gcost = 0;

		// prepare open set
		final Set<Node> openSet = new HashSet<>();
		openSet.add(nodeStart);

		// prepare misc
		Path bestPath = new EmptyPath();
		long timeStart = System.currentTimeMillis();
		int nUnloadedHits = 0;
		BestNodesContainer bestNodes = new BestNodesContainer(20);
		int nWorseThanBest = 0;

		// calculate path
		while (!openSet.isEmpty() && nUnloadedHits < 400) {

			// timeout
			if (checkForTimeout(timeStart, timeoutInMs)) {
				Stevebot.get().getPlayerController().utils().sendMessage("Timeout");
				break;
			}

			// slowdown
			if (Config.getPathfindingSlowdown() > 0) {
				try {
					Thread.sleep(Config.getPathfindingSlowdown());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			// get next node
			Node current = removeLowest(openSet);
			current.close();

			// check if reached goal
			if (goal.reached(current.pos)) {
				Path currentPath = buildPath(nodeStart, current, true);
				if (currentPath.getCost() < bestPath.getCost()) {
					bestPath = currentPath;
				}
				continue;
			}

			// check if current path cost is already higher than prev. path cost / the best node
			if (bestPath.getCost() < current.gcost) {
				continue;
			}
			if (bestNodes.getBest() != null && bestNodes.getBest().gcost < current.gcost) {
				nWorseThanBest++;
			} else {
				nWorseThanBest = 0;
			}

			if (!bestPath.reachedGoal() && nWorseThanBest > 1000) {
				break;
			}

			// process actions
			boolean hitUnloaded = false;
			List<ActionFactory> factories = actionFactoryProvider.getAllFactories();
			for (int i = 0, n = factories.size(); i < n; i++) {
				ActionFactory factory = factories.get(i);

				ActionFactory.Result result = factory.check(current);
				if (result.type == ActionFactory.ResultType.INVALID) {
					continue;
				}
				if (result.type == ActionFactory.ResultType.UNLOADED) {
					hitUnloaded = true;
					continue;
				}
				if (result.type == ActionFactory.ResultType.VALID) {
					final Node next = result.to;

					final double newCost = current.gcost + result.estimatedCost;
					if (!next.open && newCost >= next.gcost) {
						continue;
					}
					if (newCost < next.gcost || !openSet.contains(next)) {
						next.gcost = newCost;
						next.hcost = goal.calcHCost(next.pos);
						next.prev = current;
						next.action = factory.createAction(current);
						next.open = true;
						openSet.add(next);
						bestNodes.update(next, goal);
					}
				}

			}

			if (hitUnloaded) {
				nUnloadedHits++;
			}

		}

		if (bestPath.reachedGoal()) {
			return bestPath;
		} else {
			Node bestNode = bestNodes.getBest();
			if (bestNode == null) {
				return new EmptyPath();
			} else {
				return buildPath(nodeStart, bestNode, false);
			}
		}

	}




	private boolean checkForTimeout(long timeStart, long timeoutInMs) {
		return System.currentTimeMillis() > timeStart + timeoutInMs;
	}




	private Node removeLowest(Set<Node> set) {
		Node bestNode = null;
		for (Node node : set) {
			if (bestNode == null
					|| bestNode.fcost() > node.fcost()
					|| (bestNode.fcost() == node.fcost() && bestNode.hcost > node.hcost)) {
				bestNode = node;
			}
		}
		set.remove(bestNode);
		return bestNode;
	}




	private Renderable lastPathRenderable = null;




	private Path buildPath(Node start, Node end, boolean reachedGoal) {

		List<Node> nodes = new ArrayList<>();
		Node current = end;
		while (current != start) {
			nodes.add(current);
			current = current.prev;
		}
		Collections.reverse(nodes);

		Path path;
		if (reachedGoal) {
			path = new CompletedPath(end.gcost, nodes);
		} else {
			path = new PartialPath(end.gcost, nodes);
		}

//		Stevebot.get().getRenderer().removeRenderable(lastPathRenderable);
		lastPathRenderable = Path.toRenderable(path);
		Stevebot.get().getRenderer().addRenderable(lastPathRenderable);

		return path;
	}


}
