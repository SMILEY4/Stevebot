package stevebot.pathfinding.path;

import net.minecraft.util.math.BlockPos;
import stevebot.pathfinding.Node;
import stevebot.rendering.Color;
import stevebot.rendering.Renderable;
import stevebot.rendering.renderables.PathRenderObject;

import java.util.List;

public interface Path {


	static Renderable toRenderable(Path path) {
		BlockPos[] positions = new BlockPos[path.getNodes().size()];
		for (int i = 0; i < path.getNodes().size(); i++) {
			Node node = path.getNodes().get(i);
			positions[i] = node.pos;
		}
		return new PathRenderObject(positions, Color.RED);
	}



	double getCost();

	List<Node> getNodes();

	boolean reachedGoal();

}
