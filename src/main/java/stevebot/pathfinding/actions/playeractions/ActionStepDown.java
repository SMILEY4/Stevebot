package stevebot.pathfinding.actions.playeractions;

import stevebot.BlockUtils;
import stevebot.Direction;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blockpos.FastBlockPos;
import stevebot.Stevebot;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionUtils;
import stevebot.pathfinding.execution.PathExecutor;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.nodes.NodeCache;

public class ActionStepDown extends Action {


	private ActionStepDown(Node from, Node to, double cost) {
		super(from, to, cost);
	}




	@Override
	public PathExecutor.StateFollow tick(boolean firstTick) {
		if (Stevebot.get().getPlayerController().movement().moveTowards(getTo().getPos(), true)) {
			return PathExecutor.StateFollow.DONE;
		} else {
			return PathExecutor.StateFollow.EXEC;
		}
	}




	private static abstract class StepDownActionFactory implements ActionFactory {


		ActionStepDown create(Node node, Direction direction, Result result) {
			// final ActionFactory.Result result = direction.diagonal ? checkDiagonal(node, direction) : checkStraight(node, direction);
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

			// check to-position
			final BaseBlockPos to = node.getPosCopy().add(direction.dx, -1, direction.dz);
			if (!BlockUtils.isLoaded(to)) {
				return Result.unloaded();
			}
			if (!ActionUtils.canStandAt(to, 3)) {
				return Result.invalid();
			}

			// check from-position
			if (!ActionUtils.canStandAt(node.getPos())) {
				return Result.invalid();
			}

			return Result.valid(NodeCache.get(to), ActionCosts.COST_STEP_DOWN);
		}




		Result checkDiagonal(Node node, Direction direction) {

			// check to-position
			final FastBlockPos to = node.getPosCopy().add(direction.dx, -1, direction.dz);
			if (!BlockUtils.isLoaded(to)) {
				return Result.unloaded();
			}
			if (!ActionUtils.canStandAt(to, 3)) {
				return Result.invalid();
			}

			// check from-position
			if (!ActionUtils.canStandAt(node.getPos())) {
				return Result.invalid();
			}

			// check diagonal
			Direction[] splitDirection = direction.split();
			final FastBlockPos p0 = node.getPosCopy().add(splitDirection[0].dx, 0, splitDirection[0].dz);
			final FastBlockPos p1 = node.getPosCopy().add(splitDirection[1].dx, 0, splitDirection[1].dz);

			boolean traversable0 = ActionUtils.canMoveThrough(p0) && BlockUtils.isLoaded(p0);;
			boolean traversable1 = ActionUtils.canMoveThrough(p0) && BlockUtils.isLoaded(p1);;

			boolean avoid0 = BlockUtils.avoidTouching(p0) || BlockUtils.avoidTouching(p0.add(0, 1, 0));
			boolean avoid1 = BlockUtils.avoidTouching(p1) || BlockUtils.avoidTouching(p1.add(0, 1, 0));

			if (ActionUtils.canStandAt(to) && (traversable0 || traversable1)) {
				if ((traversable0 && avoid1) || (traversable1 && avoid0)) {
					return Result.invalid();
				} else {
					return Result.valid(NodeCache.get(to), ActionCosts.COST_STEP_DOWN * ActionCosts.COST_MULT_DIAGONAL * ((!traversable0 || !traversable1) ? ActionCosts.COST_MULT_TOUCHING : 1));
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
		public Action createAction(Node node, Result result) {
			return create(node, Direction.NORTH, result);
		}

	}






	public static class StepDownFactoryNorthEast extends ActionStepDown.StepDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH_EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.NORTH_EAST, result);
		}

	}






	public static class StepDownFactoryEast extends ActionStepDown.StepDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.EAST, result);
		}

	}






	public static class StepDownFactorySouthEast extends ActionStepDown.StepDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH_EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH_EAST, result);
		}

	}






	public static class StepDownFactorySouth extends ActionStepDown.StepDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH, result);
		}

	}






	public static class StepDownFactorySouthWest extends ActionStepDown.StepDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH_WEST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH_WEST, result);
		}

	}






	public static class StepDownFactoryWest extends ActionStepDown.StepDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.WEST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.WEST, result);
		}

	}






	public static class StepDownFactoryNorthWest extends ActionStepDown.StepDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH_WEST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.NORTH_WEST, result);
		}

	}

}
