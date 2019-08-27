package stevebot.pathfinding;

import modtools.rendering.Color;
import modtools.rendering.Renderable;
import modtools.rendering.renderables.PathRenderObject;
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
