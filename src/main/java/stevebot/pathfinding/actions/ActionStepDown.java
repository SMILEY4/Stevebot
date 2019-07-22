package stevebot.pathfinding.actions;

import net.minecraft.util.math.BlockPos;
import stevebot.Stevebot;
import stevebot.pathfinding.BlockUtils;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;
import stevebot.player.PlayerController;
import stevebot.rendering.Color;
import stevebot.rendering.Marker;


public class ActionStepDown extends Action {


	@SuppressWarnings ("Duplicates")
	public static ActionStepDown createValid(Node node, int x, int z) {
		final BlockPos from = node.pos;
		final BlockPos to = node.pos.add(x, -1, z);

		// check if standing on a block
		final BlockPos s0 = from.add(0, -1, 0);
		if (!BlockUtils.canWalkOn(s0)) {
			return null;
		}

		// check destination pos
		final BlockPos d0 = to.add(0, -1, 0);
		final BlockPos d1 = to;
		final BlockPos d2 = to.add(0, 1, 0);
		final BlockPos d3 = to.add(0, 1, 0);
		if (!BlockUtils.canWalkOn(d0)) { // can not stand on dest block
			return null;
		}
		if (!BlockUtils.canWalkThrough(d1) || !BlockUtils.canWalkThrough(d2)) { // can not move into dest blocks
			return null;
		}
		if (!BlockUtils.canWalkThrough(d3)) { // dest has block above
			return null;
		}


		// additional checks if diagonal movement
		if(Action.isDiagonal(from, to)) {

			final BlockPos a1 = from.add(to.getX() - from.getX(), 0, 0);
			final BlockPos a2 = from.add(to.getX() - from.getX(), 1, 0);

			final BlockPos b1 = from.add(0, 0, to.getZ() - from.getZ());
			final BlockPos b2 = from.add(0, 1, to.getZ() - from.getZ());

			final boolean aBlocked = !(BlockUtils.canWalkThrough(a1) && BlockUtils.canWalkThrough(a2));
			final boolean bBlocked = !(BlockUtils.canWalkThrough(b1) && BlockUtils.canWalkThrough(b2));

			if(aBlocked || bBlocked) {
				return null;
			}

		}

		return new ActionStepDown(node, x, z);
	}




	private final Node from;
	private final Node to;
	private final double cost;




	public ActionStepDown(Node from, int dx, int dz) {
		this(from, from.pos.add(dx, -1, dz));
	}




	public ActionStepDown(Node from, BlockPos to) {
		this.from = from;
		this.to = Node.get(to);
		this.cost = Action.COST_WALKING * (Action.isDiagonal(from.pos, to) ? 1.414 : 1);
	}




	@Override
	public double getCost() {
		return this.cost;
	}




	@Override
	public Node getFrom() {
		return this.from;
	}




	@Override
	public Node getTo() {
		return this.to;
	}




	@Override
	public PathExecutor.State tick(boolean firstTick) {
		final PlayerController controller = Stevebot.PLAYER_CONTROLLER;

		Stevebot.RENDERER.addObject(new Marker(controller.getPlayerPosition(), null, Color.YELLOW));

		if (controller.movement.moveTowardsFreeLook(to.pos, true)) {
			return PathExecutor.State.DONE;
		} else {
			return PathExecutor.State.EXEC;
		}
	}

}
