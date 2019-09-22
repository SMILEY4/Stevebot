package stevebot.pathfinding;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.util.math.BlockPos;
import stevebot.rendering.Color;
import stevebot.rendering.Renderable;
import stevebot.rendering.Renderer;

import java.util.ConcurrentModificationException;
import java.util.Map;

public class NodeRenderable implements Renderable {


	private final Map<BlockPos, Node> nodes;




	public NodeRenderable(Map<BlockPos, Node> nodes) {
		this.nodes = nodes;
	}




	@Override
	public void render(Renderer renderer) {
//		renderPaths(renderer);
		renderAABB(renderer);
	}




	private void renderPaths(Renderer renderer) {
		renderer.beginLines(2);
		final Vector3d posNode = new Vector3d();
		final Vector3d posPrev = new Vector3d();
		try {
			for (Node node : nodes.values()) {
				if (node.prev == null) {
					continue;
				}
				BlockPos nodePos = node.pos;
				BlockPos prevPos = node.prev.pos;
				posNode.set(nodePos.getX(), nodePos.getY(), nodePos.getZ()).add(0.5);
				posPrev.set(prevPos.getX(), prevPos.getY(), prevPos.getZ()).add(0.5);
				renderer.drawLineOpen(posNode, posPrev, (node.open ? Color.WHITE : Color.GRAY));
			}
		} catch (ConcurrentModificationException e) {
			// ignore
		}
		renderer.end();
	}




	private void renderAABB(Renderer renderer) {
		renderer.beginBoxes(2);
		final Vector3d posMin = new Vector3d();
		final Vector3d posMax = new Vector3d();
		try {
			for (Node node : nodes.values()) {
				BlockPos nodePos = node.pos;
				posMin.set(nodePos.getX(), nodePos.getY(), nodePos.getZ());
				posMax.set(posMin).add(1);
				renderer.drawBoxOpen(nodePos, (node.open ? Color.WHITE : Color.GRAY));
			}
		} catch (ConcurrentModificationException e) {
			// ignore
		}
		renderer.end();
	}

}