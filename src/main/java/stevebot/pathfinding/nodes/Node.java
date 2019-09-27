package stevebot.pathfinding.nodes;

import net.minecraft.util.math.BlockPos;
import stevebot.pathfinding.actions.playeractions.Action;

public class Node {


	public boolean open;
	public BlockPos pos;
	public Node prev;
	public Action action; // action required to reach this node

	/**
	 * The cost from the start to this node.
	 */
	public double gcost;

	/**
	 * The estimated cost from this node to the goal.
	 */
	public double hcost;




	/**
	 * @return the f-cost of this node ( = gcost + hcost)
	 */
	public double fcost() {
		return gcost + hcost;
	}




	/**
	 * Closes this node.
	 */
	public void close() {
		this.open = false;
	}


}
