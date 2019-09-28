package stevebot.pathfinding.nodes;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blockpos.FastBlockPos;
import stevebot.pathfinding.actions.playeractions.Action;

public class Node {


	private boolean open;
	private FastBlockPos pos;
	private Node prev;
	private Action action; // action required to reach this node
	private double gcost;
	private double hcost;




	public BaseBlockPos getPos() {
		return this.pos;
	}




	public FastBlockPos getPosCopy() {
		return this.pos.copy();
	}




	public void setPos(BaseBlockPos pos) {
		if (this.pos == null) {
			this.pos = new FastBlockPos();
		}
		this.pos.set(pos);
	}




	public Node getPrev() {
		return this.prev;
	}




	public void setPrev(Node node) {
		this.prev = node;
	}




	public Action getAction() {
		return this.action;
	}




	public void setAction(Action action) {
		this.action = action;
	}




	/**
	 * @return true, if this node is open
	 */
	public boolean isOpen() {
		return open;
	}




	/**
	 * Opens this node
	 */
	public void open() {
		this.open = true;
	}




	/**
	 * Closes this node.
	 */
	public void close() {
		this.open = false;
	}




	/**
	 * @param gcost the total cost from the start to this node.
	 */
	public void setGCost(double gcost) {
		this.gcost = gcost;
	}




	/**
	 * @return the g-cost of this node. The total cost from the start to this node.
	 */
	public double gcost() {
		return gcost;
	}




	/**
	 * @param hcost the estimated cost from this node to the goal.
	 */
	public void setHCost(double hcost) {
		this.hcost = hcost;
	}




	/**
	 * @return the h-cost of this node. The estimated cost from this node to the goal.
	 */
	public double hcost() {
		return hcost;
	}




	/**
	 * @return the f-cost of this node ( = gcost + hcost)
	 */
	public double fcost() {
		return gcost + hcost;
	}


}
