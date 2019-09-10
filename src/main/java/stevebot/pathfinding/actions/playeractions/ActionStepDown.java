package stevebot.pathfinding.actions.playeractions;

import net.minecraft.util.math.BlockPos;
import stevebot.Direction;
import stevebot.Stevebot;
import stevebot.pathfinding.BlockUtils;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionUtils;

public class ActionStepDown extends Action {


	private ActionStepDown(Node from, Node to, double cost) {
		super(from, to, cost);
	}




	@Override
	public PathExecutor.State tick(boolean firstTick) {
		if (Stevebot.get().getPlayerController().movement().moveTowards(getTo().pos, true)) {
			return PathExecutor.State.DONE;
		} else {
			return PathExecutor.State.EXEC;
		}
	}




	private static abstract class StepDownActionFactory implements ActionFactory {


		ActionStepDown create(Node node, Direction direction) {
			final ActionFactory.Result result = direction.diagonal ? checkDiagonal(node, direction) : checkStraight(node, direction);
			return new ActionStepDown(node, result.to, result.estimatedCost);
		}




		Result check(Node node, Direction direction) {
			if (direction.diagonal) {
				return checkDiagonal(node, direction);
			} else {
				return checkStraight(node, direction);
			}
		}




		Result checkStraight(Node node, Direction direction) {

			// check from-position
			final BlockPos from = node.pos;
			if (!ActionUtils.canStandAt(from)) {
				return Result.invalid();
			}

			// check to-position
			final BlockPos to = node.pos.add(direction.dx, -1, direction.dz);
			if (!ActionUtils.canStandAt(to, 3)) {
				return Result.invalid();
			}

			return Result.valid(Node.get(to), ActionCosts.COST_STEP_DOWN);
		}




		Result checkDiagonal(Node node, Direction direction) {

			// check from-position
			final BlockPos from = node.pos;
			if (!ActionUtils.canStandAt(from)) {
				return Result.invalid();
			}

			// check to-position
			final BlockPos to = node.pos.add(direction.dx, -1, direction.dz);
			if (!ActionUtils.canStandAt(to, 3)) {
				return Result.invalid();
			}

			// check diagonal
			Direction[] splitDirection = direction.split();
			final BlockPos p0 = node.pos.add(splitDirection[0].dx, 0, splitDirection[0].dz);
			final BlockPos p1 = node.pos.add(splitDirection[1].dx, 0, splitDirection[1].dz);

			boolean traversable0 = ActionUtils.canMoveThrough(p0);
			boolean traversable1 = ActionUtils.canMoveThrough(p0);

			boolean avoid0 = BlockUtils.avoidTouching(p0) || BlockUtils.avoidTouching(p0.add(0, 1, 0));
			boolean avoid1 = BlockUtils.avoidTouching(p1) || BlockUtils.avoidTouching(p1.add(0, 1, 0));

			if (ActionUtils.canStandAt(to) && (traversable0 || traversable1)) {
				if ((traversable0 && avoid1) || (traversable1 && avoid0)) {
					return Result.invalid();
				} else {
					return Result.valid(Node.get(to), ActionCosts.COST_STEP_DOWN * ActionCosts.COST_MULT_DIAGONAL * ((!traversable0 || !traversable1) ? ActionCosts.COST_MULT_TOUCHING : 1));
				}
			} else {
				return Result.invalid();
			}

		}


	}






	public static class StepDownFactoryNorth extends ActionStepDown.StepDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.NORTH);
		}

	}






	public static class StepDownFactoryNorthEast extends ActionStepDown.StepDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH_EAST);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.NORTH_EAST);
		}

	}






	public static class StepDownFactoryEast extends ActionStepDown.StepDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.EAST);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.EAST);
		}

	}






	public static class StepDownFactorySouthEast extends ActionStepDown.StepDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH_EAST);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.SOUTH_EAST);
		}

	}






	public static class StepDownFactorySouth extends ActionStepDown.StepDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.SOUTH);
		}

	}






	public static class StepDownFactorySouthWest extends ActionStepDown.StepDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH_WEST);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.SOUTH_WEST);
		}

	}






	public static class StepDownFactoryWest extends ActionStepDown.StepDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.WEST);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.WEST);
		}

	}






	public static class StepDownFactoryNorthWest extends ActionStepDown.StepDownActionFactory {


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