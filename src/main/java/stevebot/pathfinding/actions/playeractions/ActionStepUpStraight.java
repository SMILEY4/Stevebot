package stevebot.pathfinding.actions.playeractions;

import stevebot.player.MTPlayerController;
import net.minecraft.util.math.BlockPos;
import stevebot.Direction;
import stevebot.Stevebot;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionUtils;
import stevebot.pathfinding.actions.StatefulAction;

public class ActionStepUpStraight extends StatefulAction {


	public static ActionStepUpStraight createValid(Node node, Direction direction) {

		// check from-position
		final BlockPos from = node.pos;
		if (!ActionUtils.canStandAt(from, 3)) {
			return null;
		}

		// check to-position
		final BlockPos to = node.pos.add(direction.dx, 1, direction.dz);
		if (!ActionUtils.canStandAt(to)) {
			return null;
		}

		return new ActionStepUpStraight(node, to);
	}




	private static final String STATE_SLOW_DOWN = "SLOW_DOWN";
	private static final String STATE_JUMP = "JUMP";




	private ActionStepUpStraight(Node from, BlockPos to) {
		super(from, Node.get(to), ActionCosts.COST_STEP_UP, STATE_SLOW_DOWN, STATE_JUMP);
	}




	@Override
	public PathExecutor.State tick(boolean firstTick) {

		final MTPlayerController controller = Stevebot.get().getPlayerController();

		if (controller.getMotionVector().mul(1, 0, 1).length() < 0.075) {
			setState(STATE_JUMP);
		}

		switch (getCurrentState()) {

			case STATE_SLOW_DOWN: {
				boolean slowEnough = controller.getMovement().slowDown(0.075);
				if (slowEnough) {
					setState(STATE_JUMP);
				} else {
					controller.getCamera().setLookAt(getTo().pos, true);
				}
				return PathExecutor.State.EXEC;
			}

			case STATE_JUMP: {
				if (controller.getPlayerBlockPos().equals(getFrom().pos)) {
					controller.setJump(false);
				}
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