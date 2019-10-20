package stevebot.pathfinding.path;

import stevebot.pathfinding.nodes.Node;
import stevebot.rendering.Renderable;

import java.util.List;

public interface Path {


	/**
	 * Creates a renderable from the given path
	 *
	 * @param path the path
	 * @return the created renderable
	 */
	static Renderable toRenderable(Path path) {
		return new PathRenderable(path);
	}


	/**
	 * @return the total cost of this path
	 */
	double getCost();

	/**
	 * @return whether this path reached the goal
	 */
	boolean reachedGoal();

	/**
	 * @return the list of nodes of this path
	 */
	List<Node> getNodes();

	/**
	 * @return the first node of this path
	 */
	Node getFirstNode();

	/**
	 * @return the last node of this path
	 */
	Node getLastNode();


}
