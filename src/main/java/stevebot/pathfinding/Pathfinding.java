package stevebot.pathfinding;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import stevebot.Stevebot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pathfinding {

	/*
	 * https://github.com/leijurv/MineBot/blob/master/mcp918/src/minecraft/minebot/pathfinding/PathFinder.java#L43
	 * https://www.raywenderlich.com/3016-introduction-to-a-pathfinding
	 */


	public static final double MOVE_COST = 1;




	public static Path calculatePath(BlockPos posStart, BlockPos posDest, long timeout) {
		Stevebot.LOGGER.info("Calculate Path from " + posStart.toString() + " to " + posDest.toString());

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
				if (path == null || p.cost <path.cost) {
					path = p;
				}
				Stevebot.LOGGER.info("Possible path found cost=" + path.cost);
			}

			for (Node next : getNeightbors(current)) {
				double newCost = current.gcost + MOVE_COST;
				if (!next.open && newCost >= next.gcost) {
					continue;
				}
				if (newCost < next.gcost || !openSet.contains(next)) {
					if (current.pos.equals(new BlockPos(99, 73, 312))) {
					}
					next.gcost = newCost;
					next.hcost = next.pos.distanceSq(posDest);
					next.prev = current;
					next.open = true;
					openSet.add(next);
				}
			}

		}

		Stevebot.LOGGER.info("Resulting Path with " + (path == null ? "null" : path.nodes.size()) + " nodes.");
		return path == null ? new Path() : path;
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
		return path;
	}




	private static List<Node> getNeightbors(Node current) {
		List<Node> list = new ArrayList<>();

		final BlockPos pos = current.pos;

		BlockPos posN0 = pos.add(-1, 0, 0);
		BlockPos posP0 = pos.add(+1, 0, 0);
		BlockPos pos0N = pos.add(0, 0, -1);
		BlockPos pos0P = pos.add(0, 0, +1);

		if (isBlock(posN0, Blocks.COBBLESTONE)) {
			list.add(Node.get(posN0));
		}
		if (isBlock(posP0, Blocks.COBBLESTONE)) {
			list.add(Node.get(posP0));
		}
		if (isBlock(pos0N, Blocks.COBBLESTONE)) {
			list.add(Node.get(pos0N));
		}
		if (isBlock(pos0P, Blocks.COBBLESTONE)) {
			list.add(Node.get(pos0P));
		}

		return list;
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




	private static Block getBlock(BlockPos pos) {
		return Minecraft.getMinecraft().world.getBlockState(pos).getBlock();
	}




	private static boolean isBlock(BlockPos pos, Block block) {
		return getBlock(pos) == block;
	}


}
