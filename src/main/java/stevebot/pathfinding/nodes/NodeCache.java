package stevebot.pathfinding.nodes;

import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;

public class NodeCache {


	public static Map<BlockPos, Node> nodes = new HashMap<>();




	public static Map<BlockPos, Node> getNodes() {
		return nodes;
	}




	public static void clear() {
		nodes.clear();
	}




	public static Node get(BlockPos pos) {
		Node n = nodes.get(pos);
		if (n == null) {
			return create(pos, null);
		} else {
			return n;
		}
	}




	private static Node create(BlockPos pos, Node prev) {
		return create(pos, prev, 1000000);
	}




	private static Node create(BlockPos pos, Node prev, double cost) {
		Node node = nodes.get(pos);
		if (node == null) {
			node = new Node();
			node.pos = pos;
			node.prev = prev;
			node.gcost = cost;
			node.open = true;
			nodes.put(pos, node);
		}
		return node;
	}


}
