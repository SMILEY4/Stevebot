package stevebot.core.pathfinding.nodes;

import java.util.ConcurrentModificationException;
import java.util.Map;
import stevebot.core.data.blockpos.BaseBlockPos;
import stevebot.core.math.vectors.vec3.Vector3d;
import stevebot.core.rendering.Color;
import stevebot.core.rendering.Renderable;
import stevebot.core.rendering.Renderer;

public class NodeRenderable implements Renderable {


    private final Map<BaseBlockPos, Node> nodes;


    public NodeRenderable(Map<BaseBlockPos, Node> nodes) {
        this.nodes = nodes;
    }


    @Override
    public void render(Renderer renderer) {
        renderAABB(renderer);
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