package stevebot.pathfinding;

import net.minecraft.util.math.BlockPos;
import stevebot.Stevebot;
import stevebot.pathfinding.actions.Action;
import stevebot.pathfinding.actions.ActionGenerator;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pathfinding {


	public static Path calculatePath(BlockPos posStart, BlockPos posDest, long timeout) {
		Node.nodeCache.clear();
		Node nodeStart = Node.get(posStart);
		nodeStart.gcost = 0;

		final Set<Node> openSet = new HashSet<>();
		openSet.add(nodeStart);

		Path path = null;

		long timeEnd = System.currentTimeMillis() + timeout;
		while (!openSet.isEmpty()) {

			if (System.currentTimeMillis() > timeEnd) {
				Stevebot.LOGGER.info("Timeout");
				break;
			}

			Node current = removeLowest(openSet);
			current.close();
			if (current.pos.equals(posDest)) {
				Path p = buildPath(nodeStart, current);
				if (path == null || p.cost < path.cost) {
					path = p;
				}
				Stevebot.LOGGER.info("Possible path found cost=" + path.cost);
			}


			if (path != null && path.cost < current.gcost) {
				continue;
			}

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
					next.hcost = calcHCost(next.pos, posDest);
					next.prev = current;
					next.action = action;
					next.open = true;
					openSet.add(next);
				}

			}

		}

		return path == null ? new Path() : path;
	}




	private static double calcHCost(BlockPos from, BlockPos to) {
		return Math.sqrt(from.distanceSq(to));
	}




	private static Node removeLowest(Set<Node> set) {
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




	private static Path buildPath(Node start, Node end) {
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
