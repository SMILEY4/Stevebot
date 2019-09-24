package stevebot.pathfinding.actions.playeractions;

import net.minecraft.util.math.BlockPos;
import stevebot.Direction;
import stevebot.Stevebot;
import stevebot.pathfinding.BlockUtils;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.PathExecutor;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionUtils;
import stevebot.player.PlayerController;

public class ActionStepUp extends StatefulAction {


	private static final String STATE_SLOW_DOWN = "SLOW_DOWN";
	private static final String STATE_JUMP = "JUMP";




	private ActionStepUp(Node from, Node to, double cost) {
		super(from, to, cost, STATE_SLOW_DOWN, STATE_JUMP);
	}




	@Override
	public PathExecutor.StateFollow tick(boolean firstTick) {

		final PlayerController controller = Stevebot.get().getPlayerController();

		if (controller.utils().getMotionVector().mul(1, 0, 1).length() < 0.075) {
			setState(STATE_JUMP);
		}

		switch (getCurrentState()) {

			case STATE_SLOW_DOWN: {
				boolean slowEnough = controller.movement().slowDown(0.075);
				if (slowEnough) {
					setState(STATE_JUMP);
				} else {
					controller.camera().setLookAt(getTo().pos, true);
				}
				return PathExecutor.StateFollow.EXEC;
			}

			case STATE_JUMP: {
				if (controller.utils().getPlayerBlockPos().equals(getFrom().pos)) {
					controller.input().setJump(false);
				}
				if (controller.movement().moveTowards(getTo().pos, true)) {
					return PathExecutor.StateFollow.DONE;
				} else {
					return PathExecutor.StateFollow.EXEC;
				}
			}

			default: {
				return PathExecutor.StateFollow.FAILED;
			}

		}

	}




	private static abstract class StepUpActionFactory implements ActionFactory {


		ActionStepUp create(Node node, Direction direction) {
			final Result result = direction.diagonal ? checkDiagonal(node, direction) : checkStraight(node, direction);
			return new ActionStepUp(node, result.to, result.estimatedCost);
		}




		Result check(Node node, Direction direction) {
			if (direction.diagonal) {
				return checkDiagonal(node, direction);
			} else {
				return checkStraight(node, direction);
			}
		}




		Result checkStraight(Node node, Direction direction) {

			// check to-position
			final BlockPos to = node.pos.add(direction.dx, 1, direction.dz);
			if (!BlockUtils.isLoaded(to)) {
				return Result.unloaded();
			}
			if (!ActionUtils.canStandAt(to)) {
				return Result.invalid();
			}

			// check from-position
			final BlockPos from = node.pos;
			if (!ActionUtils.canStandAt(from, 3)) {
				return Result.invalid();
			}

			return Result.valid(Node.get(to), ActionCosts.COST_STEP_UP);
		}




		Result checkDiagonal(Node node, Direction direction) {

			// check to-position
			final BlockPos to = node.pos.add(direction.dx, 1, direction.dz);
			if (!BlockUtils.isLoaded(to)) {
				return Result.unloaded();
			}
			if (!ActionUtils.canStandAt(to)) {
				return Result.invalid();
			}

			// check from-position
			final BlockPos from = node.pos;
			if (!ActionUtils.canStandAt(from, 3)) {
				return Result.invalid();
			}

			// check diagonal
			Direction[] splitDirection = direction.split();
			final BlockPos p0 = node.pos.add(splitDirection[0].dx, 1, splitDirection[0].dz);
			final BlockPos p1 = node.pos.add(splitDirection[1].dx, 1, splitDirection[1].dz);

			boolean traversable0 = ActionUtils.canMoveThrough(p0, 3) && BlockUtils.isLoaded(p0);
			boolean traversable1 = ActionUtils.canMoveThrough(p1, 3) && BlockUtils.isLoaded(p1);

			if (ActionUtils.canStandAt(to) && traversable0 && traversable1) {
				return Result.valid(Node.get(to), ActionCosts.COST_STEP_UP * ActionCosts.COST_MULT_DIAGONAL);
			} else {
				return Result.invalid();
			}

		}


	}






	public static class StepUpFactoryNorth extends ActionStepUp.StepUpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.NORTH);
		}

	}






	public static class StepUpFactoryNorthEast extends ActionStepUp.StepUpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH_EAST);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.NORTH_EAST);
		}

	}






	public static class StepUpFactoryEast extends ActionStepUp.StepUpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.EAST);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.EAST);
		}

	}






	public static class StepUpFactorySouthEast extends ActionStepUp.StepUpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH_EAST);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.SOUTH_EAST);
		}

	}






	public static class StepUpFactorySouth extends ActionStepUp.StepUpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.SOUTH);
		}

	}






	public static class StepUpFactorySouthWest extends ActionStepUp.StepUpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH_WEST);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.SOUTH_WEST);
		}

	}






	public static class StepUpFactoryWest extends ActionStepUp.StepUpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.WEST);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.WEST);
		}

	}






	public static class StepUpFactoryNorthWest extends ActionStepUp.StepUpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH_WEST);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.NORTH_WEST);
		}

	}

}
