package stevebotOLD.pathfinding.actions;

import net.minecraft.util.math.BlockPos;
import stevebotOLD.pathfinding.BlockUtils;
import stevebotOLD.pathfinding.Node;
import stevebotOLD.pathfinding.PathExecutor;


public class ActionDropDownN extends Action {


	@SuppressWarnings ("Duplicates")
	public static ActionDropDownN createValid(Node node, int x, int z) {
		final BlockPos from = node.pos;
		BlockPos to = node.pos.add(x, 0, z);

		// check if standing on a block
		final BlockPos s0 = from.add(0, -1, 0);
		if (!BlockUtils.canWalkOn(s0)) {
			return null;
		}

		// check destination space
		final BlockPos d1 = to;
		final BlockPos d2 = to.add(0, 1, 0);
		if (!BlockUtils.canWalkThrough(d1) || !BlockUtils.canWalkThrough(d2)) { // can not move into dest blocks
			return null;
		}

		// additional checks if diagonal movement
		if (Action.isDiagonal(from, to)) {

			final BlockPos a1 = from.add(to.getX() - from.getX(), 0, 0);
			final BlockPos a2 = from.add(to.getX() - from.getX(), 1, 0);

			final BlockPos b1 = from.add(0, 0, to.getZ() - from.getZ());
			final BlockPos b2 = from.add(0, 1, to.getZ() - from.getZ());

			final boolean aBlocked = !(BlockUtils.canWalkThrough(a1) && BlockUtils.canWalkThrough(a2));
			final boolean bBlocked = !(BlockUtils.canWalkThrough(b1) && BlockUtils.canWalkThrough(b2));

			if (aBlocked || bBlocked) {
				return null;
			}
		}

		// calc landing pos
		int height = 0;
		to = to.add(0, -1, 0);
		while (BlockUtils.canWalkThrough(to)) {
			to = to.add(0, -1, 0);
			height++;
		}
		to = to.add(0, 1, 0);

		final BlockPos d0 = to.add(0, -1, 0);
		if (height <= 1 || !BlockUtils.canWalkOn(d0) || BlockUtils.isDangerous(d0)) {
			return null;
		}

		return new ActionDropDownN(node, to);
	}




	private final Node from;
	private final Node to;
	private final double cost;




	public ActionDropDownN(Node from, BlockPos to) {
		this.from = from;
		this.to = Node.get(to);
		this.cost = Action.COST_WALKING * (Action.isDiagonal(from.pos, to) ? 1.414 : 1) + Action.COST_FALL_N(from.pos.getY() - to.getY());
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




	private int state = 0;




	@Override
	public PathExecutor.State tick(boolean firstTick) {
//		final PlayerController controller = Stevebot.PLAYER_CONTROLLER;


		if (state == 0) { // prepare fall
//			Stevebot.RENDERER.addObject(new Marker(controller.getPlayerPosition(), this, Color.WHITE));
//			final double distToDrop = controller.getPlayerPosition().distanceTo(new Vec3d(getTo().pos.getX() + 0.5, controller.getPlayerPosition().y, getTo().pos.getZ() + 0.5));
//			final double threshold = isDiagonal(from.pos, to.pos) ?  Math.sqrt(0.38): (0.38);
//			if (distToDrop <= threshold) {
//				state = 1;
//			} else {
//				controller.movement.moveTowardsFreeLook(to.pos, false);
//			}
			return PathExecutor.State.EXEC;


		} else if (state == 1) { // slide/decelerate towards fall
//			Stevebot.RENDERER.addObject(new Marker(controller.getPlayerPosition(), this, Color.BLACK));
//			if(controller.getPlayer().onGround && controller.isPlayerMoving()) {
//				controller.movement.moveTowardsFreeLook(to.pos, true);
//			}
//			if(!controller.getPlayer().onGround) {
//				state = 2;
//			}
			return PathExecutor.State.EXEC;


		} else if (state == 2) { // falling
//			Stevebot.RENDERER.addObject(new Marker(controller.getPlayerPosition(), this, Color.WHITE));
//			if(controller.getPlayer().onGround) {
//				state = 3;
//			}
			return PathExecutor.State.EXEC;


		} else if (state == 3) { // landed, walk to center
//			Stevebot.RENDERER.addObject(new Marker(controller.getPlayerPosition(), this, Color.GRAY));
//			if (controller.movement.moveTowardsFreeLook(to.pos, false)) {
//				return PathExecutor.State.DONE;
//			} else {
//				return PathExecutor.State.EXEC;
//			}

		}

		return PathExecutor.State.EXEC; // should never reach this
	}

}
