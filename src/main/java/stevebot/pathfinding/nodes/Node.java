package stevebot.pathfinding.nodes;

import net.minecraft.util.math.BlockPos;
import stevebot.pathfinding.actions.playeractions.Action;

public class Node {


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
