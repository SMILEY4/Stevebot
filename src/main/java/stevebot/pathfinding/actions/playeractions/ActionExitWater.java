package stevebot.pathfinding.actions.playeractions;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blocks.BlockUtils;
import stevebot.misc.Direction;
import stevebot.misc.ProcState;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionUtils;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.nodes.NodeCache;
import stevebot.player.PlayerUtils;

public class ActionExitWater extends Action {


	private ActionExitWater(Node from, Node to, double cost) {
		super(from, to, cost);
	}




	@Override
	public String getActionName() {
		return "exit-water";
	}




	@Override
	public ProcState tick(boolean firstTick) {
		if (PlayerUtils.getMovement().moveTowards(getTo().getPos(), true)) {
			PlayerUtils.getInput().releaseJump();
			return ProcState.DONE;
		} else {
			final boolean isInWater = BlockUtils.isWater(PlayerUtils.getPlayerBlockPos());
			if (isInWater) {
				PlayerUtils.getInput().holdJump();
			} else {
				PlayerUtils.getInput().releaseJump();
			}
			return ProcState.EXECUTING;
		}
	}



	@Override
	public boolean isOnPath(BaseBlockPos position) {
		if (position.equals(getFrom().getPos()) || position.equals(getTo().getPos())) {
			return true;
		} else {
			return position.equals(getFrom().getPosCopy().add(Direction.DOWN)) || position.equals(getTo().getPosCopy().add(Direction.DOWN));
		}
	}





	private static abstract class ExitWaterActionFactory implements ActionFactory {


		ActionExitWater create(Node node, Direction direction, Result result) {
			return new ActionExitWater(node, result.to, result.estimatedCost);
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
			if (ActionUtils.canSwimAt(to)) {
				return Result.invalid();
			}

			// check from-position
			if (!ActionUtils.canSwimAt(node.getPos())) {
				return Result.invalid();
			}

			return Result.valid(direction, NodeCache.get(to), ActionCosts.COST_EXIST_WATER);
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
			if (!ActionUtils.canStandAt(node.getPos())) {
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
					return Result.valid(direction, NodeCache.get(to), ActionCosts.COST_EXIST_WATER * ActionCosts.COST_MULT_DIAGONAL * ((!traversable0 || !traversable1) ? ActionCosts.COST_MULT_TOUCHING : 1));
				}
			} else {
				return Result.invalid();
			}

		}


	}






	public static class ExitWaterFactoryNorth extends ExitWaterActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.NORTH, result);
		}

	}






	public static class ExitWaterFactoryNorthEast extends ExitWaterActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH_EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.NORTH_EAST, result);
		}

	}






	public static class ExitWaterFactoryEast extends ExitWaterActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.EAST, result);
		}

	}






	public static class ExitWaterFactorySouthEast extends ExitWaterActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH_EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH_EAST, result);
		}

	}






	public static class ExitWaterFactorySouth extends ExitWaterActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH, result);
		}

	}






	public static class ExitWaterFactorySouthWest extends ExitWaterActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH_WEST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH_WEST, result);
		}

	}






	public static class ExitWaterFactoryWest extends ExitWaterActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.WEST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.WEST, result);
		}

	}






	public static class ExitWaterFactoryNorthWest extends ExitWaterActionFactory {


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


