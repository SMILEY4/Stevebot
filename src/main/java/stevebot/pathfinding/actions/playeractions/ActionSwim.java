package stevebot.pathfinding.actions.playeractions;

import stevebot.data.blocks.BlockUtils;
import stevebot.Direction;
import stevebot.Stevebot;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionUtils;
import stevebot.pathfinding.execution.PathExecutor;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.nodes.NodeCache;

public class ActionSwim extends Action {


	private final boolean sprint;




	private ActionSwim(Node from, Node to, double cost) {
		super(from, to, cost);
		this.sprint = true;
	}




	@Override
	public PathExecutor.StateFollow tick(boolean fistTick) {
		if (Stevebot.get().getPlayerController().movement().moveTowards(getTo().getPos(), true)) {
			Stevebot.get().getPlayerController().input().releaseJump();
			return PathExecutor.StateFollow.DONE;
		} else {
			Stevebot.get().getPlayerController().input().holdJump();
			return PathExecutor.StateFollow.EXEC;
		}
	}




	private static abstract class SwimActionFactory implements ActionFactory {


		ActionSwim create(Node node, Direction direction, Result result) {
			return new ActionSwim(node, result.to, result.estimatedCost);
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
			final BaseBlockPos to = node.getPosCopy().add(direction.dx, 0, direction.dz);
			if (!BlockUtils.isLoaded(to)) {
				return Result.unloaded();
			}
			if (!ActionUtils.canSwimAt(to)) {
				return Result.invalid();
			}

			// check from-position
			if (!ActionUtils.canSwimAt(node.getPos())) {
				return Result.invalid();
			}

			return Result.valid(direction, NodeCache.get(to), ActionCosts.COST_SWIM);
		}




		Result checkDiagonal(Node node, Direction direction) {

			// check to-position
			final BaseBlockPos to = node.getPosCopy().add(direction.dx, 0, direction.dz);
			if (!BlockUtils.isLoaded(to)) {
				return Result.unloaded();
			}
			if (!ActionUtils.canSwimAt(to)) {
				return Result.invalid();
			}

			// check from-position
			if (!ActionUtils.canSwimAt(node.getPos())) {
				return Result.invalid();
			}

			// check diagonal
			Direction[] splitDirection = direction.split();
			final BaseBlockPos p0 = node.getPosCopy().add(splitDirection[0].dx, 0, splitDirection[0].dz);
			final BaseBlockPos p1 = node.getPosCopy().add(splitDirection[1].dx, 0, splitDirection[1].dz);

			boolean traversable0 = ActionUtils.canMoveThrough(p0) && BlockUtils.isLoaded(p0);
			boolean traversable1 = ActionUtils.canMoveThrough(p1) && BlockUtils.isLoaded(p1);

			boolean avoid0 = BlockUtils.avoidTouching(p0) || BlockUtils.avoidTouching(p0.copyAsFastBlockPos().add(0, 1, 0));
			boolean avoid1 = BlockUtils.avoidTouching(p1) || BlockUtils.avoidTouching(p1.copyAsFastBlockPos().add(0, 1, 0));

			if (traversable0 || traversable1) {
				if ((traversable0 && avoid1) || (traversable1 && avoid0)) {
					return Result.invalid();
				} else {
					return Result.valid(direction, NodeCache.get(to), ActionCosts.COST_SWIM * ActionCosts.COST_MULT_DIAGONAL * ((!traversable0 || !traversable1) ? ActionCosts.COST_MULT_TOUCHING : 1));
				}
			} else {
				return Result.invalid();
			}

		}


	}






	public static class SwimFactoryNorth extends SwimActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.NORTH, result);
		}

	}






	public static class SwimFactoryNorthEast extends SwimActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH_EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.NORTH_EAST, result);
		}

	}






	public static class SwimFactoryEast extends SwimActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.EAST, result);
		}

	}






	public static class SwimFactorySouthEast extends SwimActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH_EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH_EAST, result);
		}

	}






	public static class SwimFactorySouth extends SwimActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH, result);
		}

	}






	public static class SwimFactorySouthWest extends SwimActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH_WEST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH_WEST, result);
		}

	}






	public static class SwimFactoryWest extends SwimActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.WEST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.WEST, result);
		}

	}






	public static class SwimFactoryNorthWest extends SwimActionFactory {


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


