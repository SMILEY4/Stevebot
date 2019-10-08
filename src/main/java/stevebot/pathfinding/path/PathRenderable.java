package stevebot.pathfinding.path;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import stevebot.misc.Config;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.rendering.Color;
import stevebot.rendering.Renderable;
import stevebot.rendering.Renderer;

public class PathRenderable implements Renderable {


	public enum PathStyle {
		SOLID,
		PATH_ID,
		ACTION_TYPE,
		ACTION_ID,
		ACTION_COST;




		public static PathStyle fromString(String str) {
			switch (str.toLowerCase()) {
				case "solid":
					return SOLID;
				case "pathid":
					return PATH_ID;
				case "actiontype":
					return ACTION_TYPE;
				case "actionid":
					return ACTION_ID;
				case "actioncost":
					return ACTION_COST;
				default:
					return null;
			}
		}

	}






	private final Path path;




	public PathRenderable(Path path) {
		this.path = path;
	}




	@Override
	public void render(Renderer renderer) {
		renderer.beginLineStrip(DEFAULT_LINE_WIDTH);

		double minCost = ActionCosts.COST_INFINITE;
		double maxCost = 0;
		for (int i = 0; i < path.getNodes().size() - 1; i++) {
			final double cost = path.getNodes().get(i).getAction().getCost();
			minCost = Math.min(minCost, cost);
			maxCost = Math.max(maxCost, cost);
		}

		final Vector3d p0 = new Vector3d();
		final Vector3d p1 = new Vector3d();

		for (int i = 0; i < path.getNodes().size() - 1; i++) {
			final Node node0 = path.getNodes().get(i);
			final Node node1 = path.getNodes().get(i + 1);
			p0.set(node0.getPos().getX() + 0.5, node0.getPos().getY() + 0.5, node0.getPos().getZ() + 0.5);
			p1.set(node1.getPos().getX() + 0.5, node1.getPos().getY() + 0.5, node1.getPos().getZ() + 0.5);
			renderer.drawLineOpen(p0, p1, getColor(node0, node1, node1.getAction().getCost(), minCost, maxCost));
		}

		renderer.end();
	}




	private Color getColor(Node from, Node to, double cost, double minCost, double maxCost) {
		PathStyle style = Config.getPathStyle();
		switch (style) {
			case SOLID:
				return Color.RED;
			case PATH_ID:
				return Color.random(path.hashCode());
			case ACTION_TYPE:
				return Color.random(to.getAction().getClass().hashCode());
			case ACTION_ID:
				return Color.random(to.getAction().hashCode());
			case ACTION_COST:
				return Color.mix(Color.GREEN, Color.RED, (float) ((cost - minCost) / (maxCost - minCost)));
			default:
				return Color.BLACK;
		}

	}


}
