package stevebot.pathfinding.path;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import stevebot.data.modification.BlockBreakModification;
import stevebot.data.modification.BlockPlaceModification;
import stevebot.data.modification.Modification;
import stevebot.misc.Config;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.nodes.Node;
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
			final Node from = path.getNodes().get(i);
			final Node to = path.getNodes().get(i + 1);
			p0.set(from.getPos().getX() + 0.5, from.getPos().getY() + 0.5, from.getPos().getZ() + 0.5);
			p1.set(to.getPos().getX() + 0.5, to.getPos().getY() + 0.5, to.getPos().getZ() + 0.5);
			renderer.drawLineOpen(p0, p1, getColor(from, to, to.getAction().getCost(), minCost, maxCost));
		}

		renderer.end();

		renderer.beginBoxes(DEFAULT_LINE_WIDTH);

		for (int i = 0; i < path.getNodes().size() - 1; i++) {
			final Node to = path.getNodes().get(i + 1);
			for (Modification mod : to.getAction().getModifications()) {
				if (mod instanceof BlockBreakModification) {
					BlockBreakModification breakMod = (BlockBreakModification) mod;
					renderer.drawBoxOpen(new Vector3d(breakMod.getPosition().getX(), breakMod.getPosition().getY(), breakMod.getPosition().getZ()), Color.RED);
				}
				if (mod instanceof BlockPlaceModification) {
					BlockPlaceModification placeMod = (BlockPlaceModification) mod;
					renderer.drawBoxOpen(new Vector3d(placeMod.getPosition().getX(), placeMod.getPosition().getY(), placeMod.getPosition().getZ()), Color.YELLOW);
				}
			}
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
