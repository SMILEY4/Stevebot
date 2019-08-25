package stevebotOLD.pathfinding;

import net.minecraft.util.math.BlockPos;
import stevebotOLD.pathfinding.actions.Action;

import java.util.HashMap;
import java.util.Map;

public class Node {


	public static Map<BlockPos, Node> nodeCache = new HashMap<>();




	public static Node get(BlockPos pos) {
		Node n = nodeCache.get(pos);
		if (n == null) {
			return create(pos, null);
		} else {
			return n;
		}
	}




	public static Node create(BlockPos pos, Node prev) {
		return create(pos, prev, 1000000);
	}




	public static Node create(BlockPos pos, Node prev, double cost) {
		Node node = nodeCache.get(pos);
		if (node == null) {
			node = new Node();
			node.pos = pos;
			node.prev = prev;
			node.gcost = cost;
			node.open = true;
			nodeCache.put(pos, node);
//			Stevebot.RENDERER.nodes.add(node.pos);
		}
		return node;
	}




	public boolean open;
	public BlockPos pos;
	public Node prev;
	public Action action; // action required to reach this node

	public double gcost; // dist to starting nodes
	public double hcost; // dist to destination nodes




	public double fcost() {
		return gcost + hcost;
	}




	public void close() {
		this.open = false;
	}


}
