package stevebot.pathfinding;

import stevebot.Stevebot;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blocks.BlockProvider;
import stevebot.data.blocks.BlockUtils;
import stevebot.data.modification.Modification;
import stevebot.data.player.PlayerSnapshot;
import stevebot.misc.Config;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionFactoryProvider;
import stevebot.pathfinding.actions.playeractions.Action;
import stevebot.pathfinding.goal.Goal;
import stevebot.pathfinding.nodes.BestNodesContainer;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.nodes.NodeCache;
import stevebot.pathfinding.path.CompletedPath;
import stevebot.pathfinding.path.EmptyPath;
import stevebot.pathfinding.path.PartialPath;
import stevebot.pathfinding.path.Path;
import stevebot.player.PlayerUtils;

import java.util.*;

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
	public Path calculatePath(BaseBlockPos posStart, Goal goal, long timeoutInMs) {

		// prepare node cache
		NodeCache.clear();
		Node nodeStart = NodeCache.get(posStart);
		nodeStart.setGCost(0);

		// prepare open set
		final PriorityQueue<Node> openSet = new PriorityQueue<>((o1, o2) -> {
			final int fcostResult = Double.compare(o1.fcost(), o2.fcost());
			if (fcostResult == 0) {
				return Double.compare(o1.hcost(), o2.hcost());
			} else {
				return fcostResult;
			}
		});
		openSet.add(nodeStart);

		// prepare misc
		Path bestPath = new EmptyPath();
		BestNodesContainer bestNodes = new BestNodesContainer(20);
		long timeStart = System.currentTimeMillis();
		int nUnloadedHits = 0;
		int nWorseThanBest = 0;
		int nBetterPathFound = 0;
		long timeLast = System.currentTimeMillis();
		PlayerSnapshot baseSnapshot = PlayerUtils.createSnapshot();

		// calculate path until...
		//	- open set is empty
		//	- we hit too many unloaded chunks (goal is most likely in an unloaded chunk)
		//	- we already found enough paths (no need to improve on existing paths)
		while (!openSet.isEmpty() && nUnloadedHits < 400 && nBetterPathFound < 2) {

			// timeout
			if (checkForTimeout(timeStart, timeoutInMs)) {
				Stevebot.logNonCritical("Timeout");
				break;
			}

			// status report
			if (System.currentTimeMillis() - timeLast > 2 * 1000) {
				timeLast = System.currentTimeMillis();
				Stevebot.logNonCritical("Searching... " + ((System.currentTimeMillis() - timeStart)) + "ms, considered " + NodeCache.getNodes().size() + " nodes.");
			}

			// slowdown
			if (Config.getPathfindingSlowdown() > 0) {
				try {
					Thread.sleep(Config.getPathfindingSlowdown());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			// get next/current node (and close it)
			Node current = removeLowest(openSet);

			// check if reached goal
			//    -> build path start-current and check if path is better than the already found path (if one exists)
			if (goal.reached(current.getPos())) {
				Path currentPath = buildPath(nodeStart, current, true);
				if (currentPath.getCost() < bestPath.getCost()) {
					Stevebot.logNonCritical("Found possible path: " + ((System.currentTimeMillis() - timeStart)) + "ms, cost: " + currentPath.getCost());
					nBetterPathFound++;
					bestPath = currentPath;
				}
				continue;
			}

			// check if cost of the current node is already higher than prev. path cost -> if yes, ignore current node
			if (bestPath.getCost() < current.gcost()) {
				continue;
			}

			// check if cost of current node is is already higher than best node (if one exists)
			// 		-> yes, ignore current node and increment counter
			// 		->	no, reset counter
			if (bestNodes.getBest() != null && bestNodes.getBest().gcost() < current.gcost()) {
				nWorseThanBest++;
			} else {
				nWorseThanBest = 0;
			}

			// detects when goal is not reachable (when the last x nodes in a row had a worse score than the best node so far)
			if (!bestPath.reachedGoal() && nWorseThanBest > 500) {
				break;
			}

			// collect changes
			BlockUtils.getBlockProvider().clearBlockChanges();
			PlayerSnapshot snapshot = new PlayerSnapshot(baseSnapshot);
			collectChanges(current, BlockUtils.getBlockProvider(), snapshot);
			PlayerUtils.setActiveSnapshot(snapshot);

			// process actions
			boolean hitUnloaded = false;
			Set<Class<? extends ActionFactory>> impossibleFactories = new HashSet<>();
			List<ActionFactory> factories = actionFactoryProvider.getAllFactories();

			// iterate over every registered action
			for (int i = 0, n = factories.size(); i < n; i++) {
				ActionFactory factory = factories.get(i);
				System.out.println(factory.getClass().getSimpleName());

				// continue if prev processed actions make this action impossible
				if (impossibleFactories.contains(factory.getClass())) {
					continue;
				}

				// check if action is valid
				ActionFactory.Result result = factory.check(current);

				// action is invalid
				if (result.type == ActionFactory.ResultType.INVALID) {
					continue;
				}

				// action hit an unloaded chunk
				if (result.type == ActionFactory.ResultType.UNLOADED) {
					hitUnloaded = true;
					continue;
				}

				// action is valid
				if (result.type == ActionFactory.ResultType.VALID) {

					// add actions to list that are impossible when this action is valid
					List<Class<? extends ActionFactory>> impList = factory.makesImpossible(result.direction);
					if (impList != null) {
						impossibleFactories.addAll(impList);
					}

					// get destination node of action
					final Node next = result.to;

					// calculate cost of dest. node; ignore action when  it does not improve the already existing cost
					final double newCost = current.gcost() + result.estimatedCost;
					if (!next.isOpen() && newCost >= next.gcost()) {
						continue;
					}

					// open dest node and set new costs
					if (newCost < next.gcost() || !next.isOpen()) {

						// if the closed dest. node already has a score from a prev. action, check if the improvement is enough to justify opening it again
						if (next.gcost() < ActionCosts.COST_INFINITE - 10 && !next.isOpen()) {
							double improvement = next.gcost() - newCost;
							if (improvement < 1) {
								continue;
							}
						}

						// create action and setup dest. node
						Action action = factory.createAction(current, result);
						next.setGCost(newCost);
						next.setHCost(goal.calcHCost(next.getPos()));
						next.setPrev(current);
						next.setAction(action);
						next.open(openSet);
						bestNodes.update(posStart, next, goal);
					}
				}

			}

			// count hits in unloaded chunks
			if (hitUnloaded) {
				nUnloadedHits++;
			}

		}


		Stevebot.logNonCritical("Pathfinding completed in " + ((System.currentTimeMillis() - timeStart)) + "ms, considered " + NodeCache.getNodes().size() + " nodes.");

		if (bestPath.reachedGoal()) {
			// a valid path was found -> return that path
			return bestPath;
		} else {
			// no path was found (timeout, goal in unloaded chunks, ...) -> find best node and return path start-bestnode (or empty path if none exists)
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
		Node node = set.poll();
		node.close();
		return node;
	}




	/**
	 * Collects all {@link Modification}s that are necessary to get from
	 * the starting node to the given node and adds them to the given {@link BlockProvider}.
	 *
	 * @param node          the target node
	 * @param blockProvider the block provider
	 * @param snapshot      the player inventory snapshot
	 */
	private void collectChanges(Node node, BlockProvider blockProvider, PlayerSnapshot snapshot) {
		// TODO optimize this
		// idea ???
		// when opening node n -> check if prev node has changes in history or if action to reach n changed blocks
		// 							-> if true -> set flag "changesInHistory"
		// when collecting changes -> check for flag -> if false, do nothing
		Node current = node;
		while (current.getPrev() != null) {
			Action action = current.getAction();
			if (action.hasModifications()) {
				Modification[] modifications = action.getModifications();
				for (int i = 0; i < modifications.length; i++) {
					blockProvider.addModification(modifications[i], false);
					snapshot.applyModification(modifications[i]);
				}
			}
			current = current.getPrev();
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
			current = current.getPrev();
		}
		nodes.add(start);
		Collections.reverse(nodes);

		Path path;
		if (reachedGoal) {
			path = new CompletedPath(end.gcost(), nodes);
		} else {
			path = new PartialPath(end.gcost(), nodes);
		}

		return path;
	}


}
