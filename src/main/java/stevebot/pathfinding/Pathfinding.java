package stevebot.pathfinding;

import net.minecraft.util.math.BlockPos;
import stevebot.pathfinding.actions.Action;
import stevebot.pathfinding.actions.ActionGenerator;
import stevebot.pathfinding.goal.Goal;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		Path path = null;
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
				Path p = buildPath(nodeStart, current);
				if (path == null || p.cost < path.cost) {
					path = p;
				}
			}


			// check if current path cost is already higher than prev. path cost
			if (path != null && path.cost < current.gcost) {
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

		return path;
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




	private Path buildPath(Node start, Node end) {
		Path path = new Path();
		path.cost = end.gcost;
		Node current = end;
		while (current != start) {
			path.nodes.add(current);
			current = current.prev;
		}
		path.nodes.add(start);
		Collections.reverse(path.nodes);
		return path;
	}


}
