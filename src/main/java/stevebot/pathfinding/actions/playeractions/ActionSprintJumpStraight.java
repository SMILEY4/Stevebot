package stevebot.pathfinding.actions.playeractions;

import stevebot.player.PlayerController;
import net.minecraft.util.math.BlockPos;
import stevebot.Direction;
import stevebot.Stevebot;
import stevebot.pathfinding.BlockUtils;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionUtils;
import stevebot.pathfinding.actions.StatefulAction;

public class ActionSprintJumpStraight extends StatefulAction {


	public static ActionSprintJumpStraight createValid(Node node, Direction direction) {

		// check from-position
		final BlockPos from = node.pos;
		if (ActionUtils.canJumpAt(from)) {
			return null;
		}

		// check to-position
		final BlockPos to = from.add(direction.dx*4, 0, direction.dz*4);
		if (!ActionUtils.canJumpAt(to)) {
			return null;
		}

		// check gap
		for (int i = 0; i < 3; i++) {
			final BlockPos gap = from.add(direction.dx * (i + 1), +0, direction.dz * (i + 1));
			if (i == 2) {
				if (ActionUtils.canJump(gap)) {
					return null;
				}
			} else {
				if (ActionUtils.canJumpThrough(gap)) {
					return null;
				}
			}
		}

		return new ActionSprintJumpStraight(node, to);
	}




	private static final String STATE_PREPARE = "PREPARE";
	private static final String STATE_JUMP = "JUMP";
	private static final String STATE_LAND = "LAND";




	private ActionSprintJumpStraight(Node from, BlockPos to) {
		super(from, Node.get(to), ActionCosts.COST_SPRINT_JUMP, STATE_PREPARE, STATE_JUMP, STATE_LAND);
	}




	@Override
	public PathExecutor.State tick(boolean firstTick) {

		PlayerController controller = Stevebot.get().getPlayerController();

		switch (getCurrentState()) {
			case STATE_PREPARE: {
				controller.camera().setLookAt(getTo().pos, true);
				boolean slowEnough = controller.movement().slowDown(0.055);
				if (slowEnough) {
					nextState();
				}
				return PathExecutor.State.EXEC;
			}

			case STATE_JUMP: {
				controller.movement().moveTowards(getTo().pos, true);
				controller.input().setSprint();
				final double distToEdge = BlockUtils.distToCenter(controller.utils().getPlayerPosition());
				if (distToEdge > 0.4) {
					controller.input().setJump(false);
				}
				if (controller.getPlayer().onGround && controller.utils().getPlayerBlockPos().equals(getTo().pos)) {
					nextState();
				}
				return PathExecutor.State.EXEC;
			}


			case STATE_LAND: {
				if (controller.movement().moveTowards(getTo().pos, true)) {
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
