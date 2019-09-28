package stevebot.pathfinding;

import net.minecraft.util.math.BlockPos;
import stevebot.Config;
import stevebot.Stevebot;
import stevebot.data.blocks.BlockProvider;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionFactoryProvider;
import stevebot.pathfinding.actions.playeractions.Action;
import stevebot.pathfinding.actions.playeractions.BlockChange;
import stevebot.pathfinding.goal.Goal;
import stevebot.pathfinding.nodes.BestNodesContainer;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.nodes.NodeCache;
import stevebot.pathfinding.path.CompletedPath;
import stevebot.pathfinding.path.EmptyPath;
import stevebot.pathfinding.path.PartialPath;
import stevebot.pathfinding.path.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Pathfinding {


	private static final ActionFactoryProvider actionFactoryProvider = new ActionFactoryProvider();




	/**
	 * Calculates a new path from the given starting position to the given goal
	 *
	 * @param posStart    the start position
	 * @param goal        the goal
	 * @param timeoutInMs the timeout in milliseconds
	 * @return the created path or an {@link EmptyPath}
	 */
	public Path calculatePath(BlockPos posStart, Goal goal, long timeoutInMs) {

		// prepare node cache
		NodeCache.clear();
		Node nodeStart = NodeCache.get(posStart);
		nodeStart.gcost = 0;

		// prepare open set
		final PriorityQueue<Node> openSet = new PriorityQueue<>((o1, o2) -> {
			final int fcostResult = Double.compare(o1.fcost(), o2.fcost());
			if (fcostResult == 0) {
				return Double.compare(o1.hcost, o2.hcost);
			} else {
				return fcostResult;
			}
		});
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

			// get block changes
			Stevebot.get().getBlockProvider().clearBlockChanges();
			collectBlockChanges(current, Stevebot.get().getBlockProvider());

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
						Action action = factory.createAction(current);
						next.gcost = newCost;
						next.hcost = goal.calcHCost(next.pos);
						next.prev = current;
						next.action = action;
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

		Stevebot.get().logNonCritical("Pathfinding completed in " + ((System.currentTimeMillis() - timeStart)) + "ms, considered " + NodeCache.getNodes().size() + " nodes.");

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




	/**
	 * @param timeStart   the timestamp of the start of the calculation
	 * @param timeoutInMs the timeout in milliseconds
	 * @return true, if the timeout was reached
	 */
	private boolean checkForTimeout(long timeStart, long timeoutInMs) {
		return System.currentTimeMillis() > timeStart + timeoutInMs;
	}




	/**
	 * Removes the node with the smallest cost from the given set
	 *
	 * @param set the set
	 * @return the removed node
	 */
	private Node removeLowest(PriorityQueue<Node> set) {
		return set.poll();
	}




	/**
	 * Collects all {@link BlockChange}s that are necessary to get from the starting node to the given node and adds them to the given {@link BlockProvider}.
	 *
	 * @param node          the target node
	 * @param blockProvider the block provider
	 */
	private void collectBlockChanges(Node node, BlockProvider blockProvider) {
		Node current = node;
		while (current.prev != null) {
			Action action = current.action;
			if (action.changedBlocks()) {
				BlockChange[] changes = action.getBlockChanges();
				for (int i = 0; i < changes.length; i++) {
					blockProvider.addBlockChange(changes[i], false);
				}
			}
			current = current.prev;
		}
	}




	/**
	 * Creates/Traces the path from the given starting node to the end node
	 *
	 * @param start       the start node
	 * @param end         the end node
	 * @param reachedGoal true, if the path was completed; false if it didnt reach the goal.
	 * @return
	 */
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

		return path;
	}


}
