package stevebot.pathfinding.nodes;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.goal.Goal;

/**
 * A container holding the estimated best nodes to the goal. If the path could not reach the goal, the best node from this container is the next best option.
 */
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
			costs[i] = ActionCosts.get().COST_INFINITE;
			modifiers[i] = Math.pow((modifierDamper * (i + 1)), 2) + (1.0 - modifierDamper * modifierDamper);
		}
	}




	/**
	 * Updates the given node in the set. If the node is not yet in this collection it wll be added. Required when the cost of a node changes.
	 *
	 * @param node the node
	 * @param goal the goal of the path
	 * @return true, if it was added to the best nodes
	 */
	public boolean update(BaseBlockPos start, Node node, Goal goal) {
		final double costNode = node.hcost();
		for (int i = 0; i < capacity; i++) {
			if (costNode < costs[i]) {
				costs[i] = costNode;
				nodes[i] = node;
				return true;
			}
		}
		return false;
	}




	/**
	 * @return the estimated best node to the goal
	 */
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
