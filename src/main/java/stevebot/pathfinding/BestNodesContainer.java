package stevebot.pathfinding;

import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.goal.Goal;

public class BestNodesContainer {


	private final int capacity;
	private final double modifierDamper;

	private final double[] modifiers;
	private final double[] costs;
	private final Node[] nodes;




	public BestNodesContainer(int capacity) {
		this(capacity, 0.4);
	}




	public BestNodesContainer(int capacity, double modifierDamper) {
		this.capacity = capacity;
		this.modifierDamper = modifierDamper;

		modifiers = new double[capacity];
		costs = new double[capacity];
		nodes = new Node[capacity];

		for (int i = 0; i < capacity; i++) {
			costs[i] = ActionCosts.COST_INFINITE;
			modifiers[i] = Math.pow((modifierDamper * (i + 1)), 2) + (1.0 - modifierDamper * modifierDamper);
		}
	}




//	public boolean update(Node node) {
//		for (int i = 0; i < capacity; i++) {
//			final double costNode = node.fcost() / modifiers[i];
//			final double costBest = costs[i];
//			if (costNode < costBest) {
//				costs[i] = costNode;
//				nodes[i] = node;
//				return true;
//			}
//		}
//		return false;
//	}


	public boolean update(Node node, Goal goal) {
		for (int i = 0; i < capacity; i++) {
			final double costNode = node.fcost() * goal.calcHCost(node.pos);
			final double costBest = costs[i];
			if (costNode < costBest) {
				costs[i] = costNode;
				nodes[i] = node;
				return true;
			}
		}
		return false;
	}


	public Node getBest() {
		for (int i = 0; i < capacity; i++) {
			Node node = nodes[i];
			if (node != null) {
				return node;
			}
		}
		return null;
	}


}
