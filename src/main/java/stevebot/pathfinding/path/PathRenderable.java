package stevebot.pathfinding.path;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.rendering.Color;
import stevebot.rendering.Renderable;
import stevebot.rendering.Renderer;

public class PathRenderable implements Renderable {


	public enum Style {
		SOLID,
		COST_HEATMAP,
		ACTION_ID,
		ACTION_TYPE
	}






	private final Path path;
	private Style style = Style.SOLID;




	public PathRenderable(Path path) {
		this.path = path;
	}




	public void setStyle(Style style) {
		this.style = style;
	}




	@Override
	public void render(Renderer renderer) {
		renderer.beginLineStrip(DEFAULT_LINE_WIDTH);

		double minCost = ActionCosts.COST_INFINITE;
		double maxCost = 0;
		for (int i = 0; i < path.getNodes().size() - 1; i++) {
			final double cost = path.getNodes().get(i).action.getCost();
			minCost = Math.min(minCost, cost);
			maxCost = Math.max(maxCost, cost);
		}

		final Vector3d p0 = new Vector3d();
		final Vector3d p1 = new Vector3d();

		for (int i = 0; i < path.getNodes().size() - 1; i++) {
			final Node node0 = path.getNodes().get(i);
			final Node node1 = path.getNodes().get(i + 1);
			p0.set(node0.pos.getX() + 0.5, node0.pos.getY() + 0.5, node0.pos.getZ() + 0.5);
			p1.set(node1.pos.getX() + 0.5, node1.pos.getY() + 0.5, node1.pos.getZ() + 0.5);
			renderer.drawLineOpen(p0, p1, getColor(node0, node1, node1.action.getCost(), minCost, maxCost));
		}

		renderer.end();
	}




	private Color getColor(Node from, Node to, double cost, double minCost, double maxCost) {
		switch (style) {
			case SOLID:
				return Color.RED;
			case COST_HEATMAP:
				return Color.mix(Color.GREEN, Color.RED, (float) ((cost - minCost) / (maxCost-minCost)));
			case ACTION_ID:
				return Color.random(to.action.hashCode());
			case ACTION_TYPE:
				return Color.random(to.action.getClass().hashCode());
			default:
				return Color.BLACK;
		}

	}


}
