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

public class ActionWalk extends Action {


	private final boolean sprint;




	private ActionWalk(Node from, Node to, double cost) {
		super(from, to, cost);
		this.sprint = true;
	}




	@Override
	public String getActionName() {
		return "walk";
	}




	@Override
	public ProcState tick(boolean firstTick) {
		if (PlayerUtils.getMovement().moveTowards(getTo().getPos(), true)) {
			PlayerUtils.getInput().setSneak();
			return ProcState.DONE;
		} else {
			if (sprint) {
				PlayerUtils.getInput().setSprint();
			}
			return ProcState.EXECUTING;
		}
	}




	@Override
	public boolean isOnPath(BaseBlockPos position) {
		Direction direction = Direction.get(getFrom().getPos(), getTo().getPos());
		if (direction.diagonal) {
			if (position.equals(getFrom().getPos()) || position.equals(getTo().getPos())) {
				return true;
			}
			final BaseBlockPos posGap0 = getFrom().getPosCopy().add(direction.dx, 0, 0);
			final BaseBlockPos posGap1 = getFrom().getPosCopy().add(0, 0, direction.dz);
			return position.equals(posGap0) || position.equals(posGap1);
		} else {
			return position.equals(getFrom().getPos()) || position.equals(getTo().getPos());
		}
	}




	private static abstract class WalkActionFactory implements ActionFactory {


		ActionWalk create(Node node, Direction direction, Result result) {
			return new ActionWalk(node, result.to, result.estimatedCost);
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
			if (!ActionUtils.canStandAt(to)) {
				return Result.invalid();
			}

			// check from-position
			if (!ActionUtils.canStandAt(node.getPos())) {
				return Result.invalid();
			}

			return Result.valid(direction, NodeCache.get(to), ActionCosts.COST_SPRINTING);
		}




		Result checkDiagonal(Node node, Direction direction) {

			// check to-position
			final BaseBlockPos to = node.getPosCopy().add(direction.dx, 0, direction.dz);
			if (!BlockUtils.isLoaded(to)) {
				return Result.unloaded();
			}
			if (!ActionUtils.canStandAt(to)) {
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

			boolean avoid0 = BlockUtils.avoidTouching(p0) || BlockUtils.avoidTouching(p0.copyAsFastBlockPos().add(Direction.UP));
			boolean avoid1 = BlockUtils.avoidTouching(p1) || BlockUtils.avoidTouching(p1.copyAsFastBlockPos().add(Direction.UP));

			if (traversable0 || traversable1) {
				if ((traversable0 && avoid1) || (traversable1 && avoid0)) {
					return Result.invalid();
				} else {
					return Result.valid(direction, NodeCache.get(to), ActionCosts.COST_SPRINTING * ActionCosts.COST_MULT_DIAGONAL * ((!traversable0 || !traversable1) ? ActionCosts.COST_MULT_TOUCHING : 1));
				}
			} else {
				return Result.invalid();
			}

		}


	}






	private static abstract class AbstractWalkActionFactory extends WalkActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, getDirection());
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, getDirection(), result);
		}




		@Override
		public Class<ActionWalk> producesAction() {
			return ActionWalk.class;
		}

	}






	public static class WalkFactoryNorth extends AbstractWalkActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.NORTH;
		}

	}






	public static class WalkFactoryNorthEast extends AbstractWalkActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.NORTH_EAST;
		}

	}






	public static class WalkFactoryEast extends AbstractWalkActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.EAST;
		}

	}






	public static class WalkFactorySouthEast extends AbstractWalkActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.SOUTH_EAST;
		}

	}






	public static class WalkFactorySouth extends AbstractWalkActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.SOUTH;
		}

	}






	public static class WalkFactorySouthWest extends AbstractWalkActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.SOUTH_WEST;
		}

	}






	public static class WalkFactoryWest extends AbstractWalkActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.WEST;
		}

	}






	public static class WalkFactoryNorthWest extends AbstractWalkActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.NORTH_WEST;
		}

	}

}


