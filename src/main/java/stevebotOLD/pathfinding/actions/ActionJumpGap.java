package stevebotOLD.pathfinding.actions;

import net.minecraft.util.math.BlockPos;
import stevebotOLD.pathfinding.BlockUtils;
import stevebotOLD.pathfinding.Node;
import stevebotOLD.pathfinding.PathExecutor;


public class ActionJumpGap extends Action {


	public static ActionJumpGap createValid(Node node, int x, int z) {
		final BlockPos from = node.pos;
		final BlockPos to = node.pos.add(x, 0, z);


		// check start position
		final BlockPos s0 = from.add(0, -1, 0); // standing on = walk on
		final BlockPos s3 = from.add(0, +2, 0); // above = walk through

		if (!BlockUtils.canWalkOn(s0) || !BlockUtils.canWalkThrough(s3)) {
			return null;
		}


		// check gap
		final BlockPos g0 = from.add(x / 2, -1, z / 2); // gap ground = !walk on
		final BlockPos g1 = from.add(x / 2, 0, z / 2); // feet = walk through
		final BlockPos g2 = from.add(x / 2, +1, z / 2); // head = walk through
		final BlockPos g3 = from.add(x / 2, +2, z / 2); // above = walk through

		if (BlockUtils.canWalkOn(g0) || !BlockUtils.canWalkThrough(g1) || !BlockUtils.canWalkThrough(g2) || !BlockUtils.canWalkThrough(g3)) {
			return null;
		}


		// check destination
		final BlockPos d0 = from.add(x, -1, z); // landing on = walk on
		final BlockPos d1 = from.add(x, 0, z); // feet = walk through
		final BlockPos d2 = from.add(x, +1, z); // head = walk through
		final BlockPos d3 = from.add(x, +2, z); // above = walk through

		if (!BlockUtils.canWalkOn(d0) || !BlockUtils.canWalkThrough(d1) || !BlockUtils.canWalkThrough(d2) || !BlockUtils.canWalkThrough(d3)) {
			return null;
		}


		// valid movement -> create action
		return new ActionJumpGap(node, to);
	}




	private final Node from;
	private final Node to;
	private final double cost;




	public ActionJumpGap(Node from, int dx, int dz) {
		this(from, from.pos.add(dx, 0, dz));
	}




	public ActionJumpGap(Node from, BlockPos to) {
		this.from = from;
		this.to = Node.get(to);
		this.cost = Action.COST_WALKING * 2; // TODO
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
//		final PlayerController controller = Stevebot.PLAYER_CONTROLLER;

//		if (controller.getPlayer().onGround && controller.getPlayerBlockPos().equals(from.pos)) {
//			controller.setJump();
//			Stevebot.RENDERER.addObject(new Marker(controller.getPlayerPosition(), this, Color.BLACK));
//		} else {
//			Stevebot.RENDERER.addObject(new Marker(controller.getPlayerPosition(), this, Color.WHITE));
//
//		}
//
//		controller.setLookAt(to.pos.add(0, 1, 0));
//		if (controller.movement.moveTowardsFreeLook(to.pos, true)) {
//			return PathExecutor.State.DONE;
//		} else {
//			return PathExecutor.State.EXEC;
//		}

		return PathExecutor.State.FAILED;

	}

}
