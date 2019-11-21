package stevebot.pathfinding.nodes;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.pathfinding.actions.ActionCosts;

import java.util.HashMap;
import java.util.Map;

public class NodeCache {


	private static Map<BaseBlockPos, Node> nodes = new HashMap<>();




	/**
	 * @return the map with all cached nodes
	 */
	public static Map<BaseBlockPos, Node> getNodes() {
		return nodes;
	}




	/**
	 * Clears this node cache
	 */
	public static void clear() {
		nodes.clear();
	}




	/**
	 * Returns the node at the given position. If the node is not yet cached it will be saved in the cache.
	 *
	 * @param pos the position of the node
	 * @return the node at the given position.
	 */
	public static Node get(BaseBlockPos pos) {
		Node n = nodes.get(pos);
		if (n == null) {
			return create(pos, null);
		} else {
			return n;
		}
	}




	/**
	 * Creates a new node
	 *
	 * @param pos  the position of the node
	 * @param prev the previous node
	 * @return the created node
	 */
	private static Node create(BaseBlockPos pos, Node prev) {
		return create(pos, prev, ActionCosts.get().COST_INFINITE);
	}




	/**
	 * Creates a new node
	 *
	 * @param pos  the position of the node
	 * @param prev the previous node
	 * @param cost the (g-)cost of the node
	 * @return the created node
	 */
	private static Node create(BaseBlockPos pos, Node prev, double cost) {
		Node node = nodes.get(pos);
		if (node == null) {
			node = new Node();
			node.setPos(pos);
			node.setPrev(prev);
			node.setGCost(cost);
			node.open(null);
			nodes.put(pos, node);
		}
		return node;
	}


}
