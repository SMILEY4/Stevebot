package astarviz;


import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Pathfinding {


	private static final PriorityQueue<Node> openSet = new PriorityQueue<>((o1, o2) -> {
		final int fcostResult = Double.compare(o1.fcost(), o2.fcost());
		if (fcostResult == 0) {
			return Double.compare(o1.hcost, o2.hcost);
		} else {
			return fcostResult;
		}
	});

	public static final double[] COEFFICIENTS = {1.5, 2, 2.5, 3, 4, 5, 10};




	public static Map[] findPath(Map origin) {

		final List<Map> maps = new ArrayList<>();

		Map map = new Map(origin);

		final Node nodeGoal = map.getGoal();
		final Node nodeStart = map.getStart();
		nodeStart.gcost = 0;

		// add start to openset
		openSet.clear();
		openSet.add(nodeStart);

		for (int i = 0; i < COEFFICIENTS.length; i++) {
			map.bestCosts[i] = estimateHCost(nodeStart, nodeGoal);
			map.bestNodes[i] = nodeStart;
		}

		Path bestPath = null;

		while (!openSet.isEmpty()) {

			// get next/current node (and close it)
			Node current = openSet.poll();
			current.processed = true;
			current.open = false;

			// check if reached goal
			if (current.x == nodeGoal.x && current.y == nodeGoal.y) {
				Path path = Path.buildPath(map, nodeStart, nodeGoal);
				if (bestPath == null) {
					bestPath = path;
				} else {
					if (bestPath.cost > path.cost) {
						bestPath = path;
					}
				}
				maps.add(new Map(map, bestPath));
				continue;
			}

			// iterate over possible actions
			for (Action action : Action.getActions(map, current)) {

				// get dest node
				final Node next = action.to;

				// calc new cost
				final double newCost = current.gcost + action.cost;
				if (!next.open && newCost >= next.gcost) {
					continue;
				}

				// open dest node
				if (newCost < next.gcost || !next.open) {
					next.gcost = newCost;
					next.hcost = estimateHCost(next, nodeGoal);
					next.prev = current;
					next.open = true;
					next.processed = true;
					openSet.add(next);

					for (int i = 0; i < COEFFICIENTS.length; i++) {
						double h = next.hcost + next.gcost / COEFFICIENTS[i];
						if (map.bestCosts[i] > h) {
							map.bestCosts[i] = h;
							map.bestNodes[i] = next;
						}
					}

				}

			}

			maps.add(new Map(map, bestPath));

		}

		final Map[] mapsArray = new Map[maps.size()];
		for (int i = 0; i < maps.size(); i++) {
			mapsArray[i] = maps.get(i);
		}
		return mapsArray;
	}




	private static double estimateHCost(Node next, Node goal) {
		final int dx = Math.abs(next.x) - Math.abs(goal.x);
		final int dy = Math.abs(next.y) - Math.abs(goal.y);
		return Math.sqrt(dx * dx + dy * dy);
	}




	static class Action {


		static List<Action> getActions(Map map, Node from) {

			List<Action> actions = new ArrayList<>();

			for (int oy = -1; oy <= 1; oy++) {
				for (int ox = -1; ox <= 1; ox++) {
					if (ox != 0 || oy != 0) {
						final int x = from.x + ox;
						final int y = from.y + oy;
						final Node to = map.getNode(x, y);
						if (!to.isWall && to.x > 0) {
							final double cost = (Math.abs(ox) + Math.abs(oy) == 1) ? 1 : Math.sqrt(2);
							actions.add(new Action(from, to, cost));
						}
					}
				}
			}

			return actions;
		}




		public final Node from;
		public final Node to;
		public final double cost;




		Action(Node from, Node to, double cost) {
			this.from = from;
			this.to = to;
			this.cost = cost;
		}

	}


}
