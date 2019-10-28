package astarviz;

import com.ruegnerlukas.simplemath.vectors.vec2.Vector2i;

import java.util.Random;

public class Map {


	private static Random random = new Random();

	public final int width;
	public final int height;
	public final Node[][] nodes;

	private Vector2i posStart = null;
	private Vector2i posGoal = null;

	public Path path = null;

	public Node[] bestNodes = new Node[Pathfinding.COEFFICIENTS.length];
	public double[] bestCosts = new double[Pathfinding.COEFFICIENTS.length];

	private final Node NODE_WALL = new Node(-10, -10);




	public Map(int width, int height) {
		this.width = width;
		this.height = height;
		this.nodes = new Node[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				final Node node = new Node(x, y);
				node.isWall = random.nextBoolean();
				nodes[x][y] = node;
			}
		}
		this.NODE_WALL.isWall = true;
	}




	public Map(Map other) {
		this(other, null);
	}




	public Map(Map other, Path path) {
		this.width = other.width;
		this.height = other.height;
		this.nodes = new Node[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				nodes[x][y] = new Node(other.nodes[x][y]);
			}
		}
		this.setStart(other.posStart);
		this.setGoal(other.posGoal);
		this.path = path;
		for (int i = 0; i < Pathfinding.COEFFICIENTS.length; i++) {
			if (other.bestNodes[i] == null) {
				this.bestNodes[i] = null;
			} else {
				this.bestNodes[i] = getNode(other.bestNodes[i].x, other.bestNodes[i].y);
			}
			this.bestCosts[i] = other.bestCosts[i];
		}
	}




	public void clear() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				nodes[x][y].isWall = false;
				nodes[x][y].isGoal = false;
				nodes[x][y].isStart = false;
				nodes[x][y].gcost = Node.INFINITY;
				nodes[x][y].hcost = Node.INFINITY;
				nodes[x][y].open = false;
				nodes[x][y].processed = false;
				nodes[x][y].prev = null;
				bestNodes = new Node[Pathfinding.COEFFICIENTS.length];
				bestCosts = new double[Pathfinding.COEFFICIENTS.length];
			}
		}
	}




	public void setStart(Vector2i pos) {
		if (posStart == null) {
			posStart = new Vector2i(pos);
		}
		nodes[posStart.x][posStart.y].isStart = false;
		posStart.set(pos);
		nodes[pos.x][pos.y].isStart = true;
		nodes[pos.x][pos.y].isGoal = false;
		nodes[pos.x][pos.y].isWall = false;
	}




	public Node getStart() {
		return nodes[posStart.x][posStart.y];
	}




	public void setGoal(Vector2i pos) {
		if (posGoal == null) {
			posGoal = new Vector2i(pos);
		}
		nodes[posGoal.x][posGoal.y].isGoal = false;
		posGoal.set(pos);
		nodes[pos.x][pos.y].isGoal = true;
		nodes[pos.x][pos.y].isStart = false;
		nodes[pos.x][pos.y].isWall = false;
	}




	public Node getGoal() {
		return nodes[posGoal.x][posGoal.y];
	}




	public void setWall(Vector2i pos) {
		nodes[pos.x][pos.y].isWall = !nodes[pos.x][pos.y].isWall;
	}




	public Node getNode(int x, int y) {
		if (0 <= x && x < width && 0 <= y && y < height) {
			return nodes[x][y];
		} else {
			return NODE_WALL;
		}
	}

}
