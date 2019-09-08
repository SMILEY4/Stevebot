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

public class ActionDropDownDiagonal extends StatefulAction {


	public static ActionDropDownDiagonal createValid(Node node, Direction direction) {

		// check from-position
		final BlockPos from = node.pos;
		if (!ActionUtils.canStandAt(from)) {
			return null;
		}

		// check to-position horizontal
		final BlockPos to = node.pos.add(direction.dx, -1, direction.dz);
		if(!ActionUtils.canMoveThrough(to)) {
			return null;
		}

		// check diagonal blocks
		Direction[] splitDirection = direction.split();
		final BlockPos p0 = node.pos.add(splitDirection[0].dx, 0, splitDirection[0].dz);
		final BlockPos p1 = node.pos.add(splitDirection[1].dx, 0, splitDirection[1].dz);
		if(!ActionUtils.canMoveThroughAll(p0, p1)) {
			return null;
		}

		// check+create fall
		final ActionFall fall = ActionFall.createValid(Node.get(to));
		if (fall == null) {
			return null;
		}

		return new ActionDropDownDiagonal(node, fall, direction);
	}




	private static final String STATE_PREPARE_1 = "PREPARE 1";
	private static final String STATE_PREPARE_2 = "PREPARE 2";
	private static final String STATE_FALL = "FALL";
	private static final String STATE_FINISH = "FINISH";

	private final ActionFall fall;
	private final Direction direction;




	private ActionDropDownDiagonal(Node from, ActionFall fall, Direction direction) {
		super(from, fall.getTo(), ActionCosts.COST_WALKING * ActionCosts.COST_MULT_DIAGONAL + fall.getCost(), STATE_PREPARE_1, STATE_PREPARE_2, STATE_FALL, STATE_FINISH);
		this.fall = fall;
		this.direction = direction;
	}




	@Override
	public void resetAction() {
		fall.resetAction();
		super.resetAction();
	}




	@Override
	public PathExecutor.State tick(boolean firstTick) {
		final MTPlayerController controller = Stevebot.get().getPlayerController();


		switch (getCurrentState()) {

			case STATE_PREPARE_1: {
				final double distToEdge = BlockUtils.distToEdge(controller.getPlayerPosition(), direction);
				if (distToEdge <= 0.4) {
					nextState();
				} else {
					controller.getMovement().moveTowards(getTo().pos, true);
				}
				return PathExecutor.State.EXEC;
			}

			case STATE_PREPARE_2: {
				if (controller.getPlayer().onGround && !controller.isPlayerMoving(0.0001, false)) {
					controller.getMovement().moveTowards(getTo().pos, true);
				}
				if (!controller.getPlayer().onGround) {
					nextState();
				}
				return PathExecutor.State.EXEC;
			}

			case STATE_FALL: {
				if (controller.getPlayer().onGround) {
					nextState();
				}
				return PathExecutor.State.EXEC;
			}

			case STATE_FINISH: {
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