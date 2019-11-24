package stevebot.pathfinding.actions.playeractions;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blocks.BlockUtils;
import stevebot.misc.Direction;
import stevebot.misc.ProcState;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionObserver;
import stevebot.pathfinding.actions.ActionUtils;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.nodes.NodeCache;
import stevebot.player.PlayerUtils;

public class ActionSwim extends Action {


	private final boolean sprint;




	private ActionSwim(Node from, Node to, double cost) {
		super(from, to, cost);
		this.sprint = true;
	}




	@Override
	public String getActionName() {
		return "swim";
	}




	@Override
	public String getActionNameExp() {
		return this.getActionName() + (Direction.get(getFrom().getPos(), getTo().getPos()).diagonal ? "-diagonal" : "-straight");
	}




	@Override
	public ProcState tick(boolean firstTick) {
		ActionObserver.tickAction(this.getActionNameExp());
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
			return position.getY() <= getFrom().getPosCopy().getY() || position.getY() <= getTo().getPosCopy().getY();
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

			return Result.valid(direction, NodeCache.get(to), ActionCosts.get().SWIM_STRAIGHT);
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

			boolean avoid0 = BlockUtils.avoidTouching(p0) || BlockUtils.avoidTouching(p0.copyAsFastBlockPos().add(Direction.UP));
			boolean avoid1 = BlockUtils.avoidTouching(p1) || BlockUtils.avoidTouching(p1.copyAsFastBlockPos().add(Direction.UP));

			if (traversable0 || traversable1) {
				if ((traversable0 && avoid1) || (traversable1 && avoid0)) {
					return Result.invalid();
				} else {
					return Result.valid(direction, NodeCache.get(to),
							direction.diagonal ? ActionCosts.get().SWIM_DIAGONAL : ActionCosts.get().SWIM_STRAIGHT);
				}
			} else {
				return Result.invalid();
			}

		}


	}






	private static abstract class AbstractSwimActionFactory extends SwimActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, getDirection());
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, getDirection(), result);
		}




		@Override
		public Class<ActionSwim> producesAction() {
			return ActionSwim.class;
		}

	}






	public static class SwimFactoryNorth extends AbstractSwimActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.NORTH;
		}

	}






	public static class SwimFactoryNorthEast extends AbstractSwimActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.NORTH_EAST;
		}

	}






	public static class SwimFactoryEast extends AbstractSwimActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.EAST;
		}

	}






	public static class SwimFactorySouthEast extends AbstractSwimActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.SOUTH_EAST;
		}

	}






	public static class SwimFactorySouth extends AbstractSwimActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.SOUTH;
		}

	}






	public static class SwimFactorySouthWest extends AbstractSwimActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.SOUTH_WEST;
		}

	}






	public static class SwimFactoryWest extends AbstractSwimActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.WEST;
		}

	}






	public static class SwimFactoryNorthWest extends AbstractSwimActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.NORTH_WEST;
		}

	}

}


