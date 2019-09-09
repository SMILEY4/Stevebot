package stevebot.pathfinding;

import net.minecraft.util.math.BlockPos;
import stevebot.pathfinding.actions.Action;
import stevebot.pathfinding.actions.ActionGenerator;
import stevebot.pathfinding.goal.Goal;
import stevebot.pathfinding.path.CompletedPath;
import stevebot.pathfinding.path.EmptyPath;
import stevebot.pathfinding.path.PartialPath;
import stevebot.pathfinding.path.Path;

import java.util.*;

public class Pathfinding {


	private long timeStart;




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
		timeStart = System.currentTimeMillis();

		// calculate path
		while (!openSet.isEmpty()) {

			// timeout
			if (checkForTimeout(timeoutInMs)) {
				break;
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

			// check if current path cost is already higher than prev. path cost
			if (bestPath.getCost() < current.gcost) {
				continue;
			}

			// process actions
			List<Action> actions = ActionGenerator.getActions(current);
			for (int i = 0, n = actions.size(); i < n; i++) {
				final Action action = actions.get(i);
				final Node next = action.getTo();

				final double newCost = current.gcost + action.getCost();
				if (!next.open && newCost >= next.gcost) {
					continue;
				}
				if (newCost < next.gcost || !openSet.contains(next)) {
					next.gcost = newCost;
					next.hcost = goal.calcHCost(next.pos);
					next.prev = current;
					next.action = action;
					next.open = true;
					openSet.add(next);
				}

			}

		}

		return bestPath;
	}




	private boolean checkForTimeout(long timeoutInMs) {
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




	private Path buildPath(Node start, Node end, boolean reachedGoal) {

		List<Node> nodes = new ArrayList<>();
		Node current = end;
		while (current != start) {
			nodes.add(current);
			current = current.prev;
		}
		Collections.reverse(nodes);

		if (reachedGoal) {
			return new CompletedPath(end.gcost, nodes);
		} else {
			return new PartialPath(end.gcost, nodes);
		}

	}


}
