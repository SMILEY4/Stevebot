package stevebot.pathfinding.actions.playeractions;

import stevebot.player.MTPlayerController;
import net.minecraft.util.math.BlockPos;
import stevebot.Direction;
import stevebot.Stevebot;
import stevebot.pathfinding.BlockUtils;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionUtils;
import stevebot.pathfinding.actions.StatefulAction;

public class ActionWalkJumpDiagonal extends StatefulAction {


	public static ActionWalkJumpDiagonal createValid(Node node, Direction direction) {

		// check from-position
		final BlockPos from = node.pos;
		if (!ActionUtils.canStandAt(from, 3)) {
			return null;
		}

		// check to-position
		final BlockPos to = node.pos.add(direction.dx*2, 0, direction.dz*2);
		if (!ActionUtils.canStandAt(to, 3)) {
			return null;
		}

		// check gap
		if (!ActionUtils.canJumpThrough(from.add(direction.dx, 0, direction.dz))) {
			return null;
		}

		// check diagonal
		Direction[] splitDirection = direction.split();
		final BlockPos p0 = node.pos.add(splitDirection[0].dx, 1, splitDirection[0].dz);
		final BlockPos p1 = node.pos.add(splitDirection[1].dx, 1, splitDirection[1].dz);
		if (!ActionUtils.canJump(p0, p1)) {
			return null;
		}

		return new ActionWalkJumpDiagonal(node, to);
	}




	private static final String STATE_PREPARE = "PREPARE";
	private static final String STATE_JUMP = "JUMP";
	private static final String STATE_LAND = "LAND";




	private ActionWalkJumpDiagonal(Node from, BlockPos to) {
		super(from, Node.get(to), ActionCosts.COST_WALK_JUMP * ActionCosts.COST_MULT_DIAGONAL, STATE_PREPARE, STATE_JUMP, STATE_LAND);
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
