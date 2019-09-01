package stevebot.pathfinding.actions;

import modtools.player.MTPlayerController;
import net.minecraft.util.math.BlockPos;
import stevebot.Direction;
import stevebot.Stevebot;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;

public class ActionStepUpStraight extends Action {


	public static ActionStepUpStraight createValid(Node node, Direction direction) {

		final BlockPos from = node.pos;
		if (!ActionUtils.canStandAt(from, 3)) {
			return null;
		}

		final BlockPos to = node.pos.add(direction.dx, 1, direction.dz);
		if (ActionUtils.canStandAt(to)) {
			return new ActionStepUpStraight(node, to);
		} else {
			return null;
		}

	}




	private final Node from;
	private final Node to;
	private final double cost;




	public ActionStepUpStraight(Node from, BlockPos to) {
		this.from = from;
		this.to = Node.get(to);
		this.cost = ActionCosts.COST_STEP_UP;
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




	private int currentState = 0;
	private final int SLOW_DOWN = 0;
	private final int JUMP = 1;




	@SuppressWarnings ("Duplicates")
	@Override
	public PathExecutor.State tick(boolean firstTick) {

		final MTPlayerController controller = Stevebot.get().getPlayerController();

		if (controller.getMotionVector().mul(1, 0, 1).length() < 0.075) {
			currentState = JUMP;
		}

		switch (currentState) {

			case SLOW_DOWN: {
				boolean slowEnough = controller.getMovement().slowDown(0.075);
				if (slowEnough) {
					currentState = JUMP;
				} else {
					controller.getCamera().setLookAt(to.pos, true);
				}
				return PathExecutor.State.EXEC;
			}

			case JUMP: {
				if (controller.getPlayerBlockPos().equals(from.pos)) {
					controller.setJump(false);
				}
				if (controller.getMovement().moveTowards(to.pos, true)) {
					return PathExecutor.State.DONE;
				} else {
					return PathExecutor.State.EXEC;
				}
			}

		}

		return PathExecutor.State.EXEC;
	}




	@Override
	public void resetAction() {
		currentState = 0;
	}

}