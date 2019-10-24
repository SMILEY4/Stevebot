package astarviz;

public class Node {


	public boolean isWall = false;
	public boolean isStart = false;
	public boolean isGoal = false;

	public double gcost = -1;
	public double hcost = -1;

	public int x;
	public int y;



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
	}




	public double fcost() {
		return gcost + hcost;
	}

}
