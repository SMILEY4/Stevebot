package stevebot.pathfinding.nodes;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.rendering.Color;
import stevebot.rendering.Renderable;
import stevebot.rendering.Renderer;

import java.util.ConcurrentModificationException;
import java.util.Map;

public class NodeRenderable implements Renderable {


	private final Map<BaseBlockPos, Node> nodes;




	public NodeRenderable(Map<BaseBlockPos, Node> nodes) {
		this.nodes = nodes;
	}




	@Override
	public void render(Renderer renderer) {
		renderAABB(renderer);
	}




	private void renderPaths(Renderer renderer) {
		renderer.beginLines(2);
		final Vector3d posNode = new Vector3d();
		final Vector3d posPrev = new Vector3d();
		try {
			for (Node node : nodes.values()) {
				if (node.getPrev() == null) {
					continue;
				}
				BaseBlockPos nodePos = node.getPos();
				BaseBlockPos prevPos = node.getPrev().getPos();
				posNode.set(nodePos.getX(), nodePos.getY(), nodePos.getZ()).add(0.5);
				posPrev.set(prevPos.getX(), prevPos.getY(), prevPos.getZ()).add(0.5);
				renderer.drawLineOpen(posNode, posPrev, (node.isOpen() ? Color.WHITE : Color.GRAY));
			}
		} catch (ConcurrentModificationException e) {
			// ignore
		}
		renderer.end();
	}




	private void renderAABB(Renderer renderer) {
		renderer.beginBoxes(2);
		final Vector3d pos = new Vector3d();
		try {
			for (Node node : nodes.values()) {
				BaseBlockPos nodePos = node.getPos();
				pos.set(nodePos.getX(), nodePos.getY(), nodePos.getZ());
				renderer.drawBoxOpen(pos, (node.isOpen() ? Color.WHITE : Color.GRAY));
			}
		} catch (ConcurrentModificationException e) {
			// ignore
		}
		renderer.end();
	}

}