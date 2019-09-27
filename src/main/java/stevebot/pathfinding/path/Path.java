package stevebot.pathfinding.path;

import net.minecraft.util.math.BlockPos;
import stevebot.pathfinding.nodes.Node;
import stevebot.rendering.Color;
import stevebot.rendering.Renderable;
import stevebot.rendering.renderables.PathRenderObject;

import java.util.List;

public interface Path {


	/**
	 * Creates a renderable from the given path
	 *
	 * @param path the path
	 * @return the created renderable
	 */
	static Renderable toRenderable(Path path) {
		BlockPos[] positions = new BlockPos[path.getNodes().size()];
		for (int i = 0; i < path.getNodes().size(); i++) {
			Node node = path.getNodes().get(i);
			positions[i] = node.pos;
		}
		return new PathRenderObject(positions, Color.RED);
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
