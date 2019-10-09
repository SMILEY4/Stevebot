package stevebot.pathfinding.nodes;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blockpos.FastBlockPos;
import stevebot.pathfinding.actions.playeractions.Action;

import java.util.PriorityQueue;

public class Node {


	private boolean open;

	private FastBlockPos pos;
	private Node prev;
	private Action action; // action required to reach this node
	private double gcost;
	private double hcost;




	/**
	 * @return the {@link BaseBlockPos} of this node
	 */
	public BaseBlockPos getPos() {
		return this.pos;
	}




	/**
	 *
	 * @return a copy of the position of this node
	 */
	public FastBlockPos getPosCopy() {
		return this.pos.copy();
	}




	/**
	 * @param pos the new position of this node
	 */
	public void setPos(BaseBlockPos pos) {
		if (this.pos == null) {
			this.pos = new FastBlockPos();
		}
		this.pos.set(pos);
	}




	/**
	 * @return the previous node of this node
	 */
	public Node getPrev() {
		return this.prev;
	}




	/**
	 *
	 * @param node the new previous node
	 */
	public void setPrev(Node node) {
		this.prev = node;
	}




	/**
	 *
	 * @return the action required to get from the previous node to this node
	 */
	public Action getAction() {
		return this.action;
	}




	/**
	 * Set the action required to get from the previous node to this node.
	 * @param action the {@link Action}
	 */
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
	 * Opens this node and adds it to the given set.
	 *
	 * @param openSet the set (of open nodes).
	 */
	public void open(PriorityQueue<Node> openSet) {
		this.open = true;
		if (openSet != null) {
			openSet.add(this);
		}
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
