package stevebot.pathfinding.actions;

import modtools.player.MTPlayerController;
import net.minecraft.util.math.BlockPos;
import stevebot.Direction;
import stevebot.Stevebot;
import stevebot.pathfinding.BlockUtils;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;

public class ActionWalkJumpStraight extends StatefulAction {


	public static ActionWalkJumpStraight createValid(Node node, int x, int z, Direction direction) {
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
		return new ActionWalkJumpStraight(node, to);
	}




	private static final String STATE_PREPARE = "PREPARE";
	private static final String STATE_JUMP = "JUMP";
	private static final String STATE_LAND = "LAND";




	private ActionWalkJumpStraight(Node from, BlockPos to) {
		super(from, Node.get(to), ActionCosts.COST_WALK_JUMP, STATE_PREPARE, STATE_JUMP, STATE_LAND);
	}




	@Override
	public PathExecutor.State tick(boolean firstTick) {

		MTPlayerController controller = Stevebot.get().getPlayerController();

		switch (getCurrentState()) {
			case STATE_PREPARE: {
				controller.getCamera().setLookAt(getTo().pos, true);
				boolean slowEnough = controller.getMovement().slowDown(0.055);
				if (slowEnough) {
					nextState();
				}
				return PathExecutor.State.EXEC;
			}

			case STATE_JUMP: {
				controller.getMovement().moveTowards(getTo().pos, true);
				final double distToEdge = BlockUtils.distToCenter(controller.getPlayerPosition());
				if (distToEdge > 0.4) {
					controller.setJump(false);
				}
				if (controller.getPlayer().onGround && controller.getPlayerBlockPos().equals(getTo().pos)) {
					nextState();
				}
				return PathExecutor.State.EXEC;
			}

			case STATE_LAND: {
				if (controller.getMovement().moveTowards(getTo().pos, true)) {
					return PathExecutor.State.DONE;
				} else {
					return PathExecutor.State.EXEC;
				}
			}

			default: {
				return PathExecutor.State.FAILED;
			}

		}

	}


}
