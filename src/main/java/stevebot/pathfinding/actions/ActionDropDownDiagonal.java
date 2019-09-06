package stevebot.pathfinding.actions;

import modtools.player.MTPlayerController;
import net.minecraft.util.math.BlockPos;
import stevebot.Direction;
import stevebot.Stevebot;
import stevebot.pathfinding.BlockUtils;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;

public class ActionDropDownDiagonal extends StatefulAction {


	public static ActionDropDownDiagonal createValid(Node node, Direction direction) {

		final BlockPos from = node.pos;
		if (!BlockUtils.canWalkOn(from.add(0, -1, 0))) {
			return null;
		}

		final BlockPos to = node.pos.add(direction.dx, -1, direction.dz);
		final BlockPos toUp = to.add(0, 1, 0);
		if (!BlockUtils.canWalkThrough(to) || !BlockUtils.canWalkThrough(toUp)) { // can not move into dest blocks
			return null;
		}

		Direction[] splitDirection = direction.split();
		final BlockPos p0 = node.pos.add(splitDirection[0].dx, 0, splitDirection[0].dz);
		final BlockPos p1 = node.pos.add(splitDirection[1].dx, 0, splitDirection[1].dz);

		boolean traversable0 = BlockUtils.canWalkThrough(p0) && BlockUtils.canWalkThrough(p0.add(0, 1, 0));
		boolean traversable1 = BlockUtils.canWalkThrough(p1) && BlockUtils.canWalkThrough(p1.add(0, 1, 0));

		if (!traversable0 || !traversable1) {
			return null;
		}

		final ActionFall fall = ActionFall.createValid(Node.get(to));
		if (fall == null) {
			return null;
		} else {
			return new ActionDropDownDiagonal(node, fall, direction);
		}
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