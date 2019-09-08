package stevebot.pathfinding;

import stevebot.rendering.Color;
import stevebot.rendering.Renderable;
import stevebot.rendering.renderables.PathRenderObject;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class Path {


	public double cost;
	public List<Node> nodes = new ArrayList<>();




	public Renderable toRenderable() {
		BlockPos[] positions = new BlockPos[nodes.size()];
		for (int i = 0; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			positions[i] = node.pos;
		}
		return new PathRenderObject(positions, Color.RED);
	}

}
