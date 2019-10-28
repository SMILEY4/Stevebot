package astarviz;

public class Node {

	public static final double INFINITY = 999999;

	public final int x;
	public final int y;

	public boolean isWall = false;
	public boolean isStart = false;
	public boolean isGoal = false;

	public double gcost = INFINITY;
	public double hcost = INFINITY;
	public boolean open = false;
	public boolean processed = false;
	public Node prev;



	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}




	public Node(Node other) {
		this.x = other.x;
		this.y = other.y;
		this.isWall = other.isWall;
		this.isStart = other.isStart;
		this.isGoal = other.isGoal;
		this.gcost = other.gcost;
		this.hcost = other.hcost;
		this.open = other.open;
		this.processed = other.processed;
	}




	public double fcost() {
		return gcost + hcost;
	}

}
