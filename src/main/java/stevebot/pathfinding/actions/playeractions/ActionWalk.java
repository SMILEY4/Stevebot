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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


		private static final Map<Direction, List<Class<? extends ActionFactory>>> IMPOSSIBLE_ACTIONS = new HashMap<>();




		static {
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.NORTH, new ArrayList<>()).add(ActionDropDown.DropDownFactoryNorth.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.NORTH_EAST, new ArrayList<>()).add(ActionDropDown.DropDownFactoryNorthEast.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.EAST, new ArrayList<>()).add(ActionDropDown.DropDownFactoryEast.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.SOUTH_EAST, new ArrayList<>()).add(ActionDropDown.DropDownFactorySouthEast.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.SOUTH, new ArrayList<>()).add(ActionDropDown.DropDownFactorySouth.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.SOUTH_WEST, new ArrayList<>()).add(ActionDropDown.DropDownFactorySouthWest.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.WEST, new ArrayList<>()).add(ActionDropDown.DropDownFactoryWest.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.NORTH_WEST, new ArrayList<>()).add(ActionDropDown.DropDownFactoryNorthWest.class);

			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.NORTH, new ArrayList<>()).add(ActionStepDown.StepDownFactoryNorth.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.NORTH_EAST, new ArrayList<>()).add(ActionStepDown.StepDownFactoryNorthEast.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.EAST, new ArrayList<>()).add(ActionStepDown.StepDownFactoryEast.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.SOUTH_EAST, new ArrayList<>()).add(ActionStepDown.StepDownFactorySouthEast.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.SOUTH, new ArrayList<>()).add(ActionStepDown.StepDownFactorySouth.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.SOUTH_WEST, new ArrayList<>()).add(ActionStepDown.StepDownFactorySouthWest.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.WEST, new ArrayList<>()).add(ActionStepDown.StepDownFactoryWest.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.NORTH_WEST, new ArrayList<>()).add(ActionStepDown.StepDownFactoryNorthWest.class);

			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.NORTH, new ArrayList<>()).add(ActionStepUp.StepUpFactoryNorth.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.NORTH_EAST, new ArrayList<>()).add(ActionStepUp.StepUpFactoryNorthEast.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.EAST, new ArrayList<>()).add(ActionStepUp.StepUpFactoryEast.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.SOUTH_EAST, new ArrayList<>()).add(ActionStepUp.StepUpFactorySouthEast.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.SOUTH, new ArrayList<>()).add(ActionStepUp.StepUpFactorySouth.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.SOUTH_WEST, new ArrayList<>()).add(ActionStepUp.StepUpFactorySouthWest.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.WEST, new ArrayList<>()).add(ActionStepUp.StepUpFactoryWest.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.NORTH_WEST, new ArrayList<>()).add(ActionStepUp.StepUpFactoryNorthWest.class);
		}




		@Override
		public List<Class<? extends ActionFactory>> makesImpossible(Direction direction) {
			return IMPOSSIBLE_ACTIONS.get(direction);
		}




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

			boolean avoid0 = BlockUtils.avoidTouching(p0) || BlockUtils.avoidTouching(p0.copyAsFastBlockPos().add(0, 1, 0));
			boolean avoid1 = BlockUtils.avoidTouching(p1) || BlockUtils.avoidTouching(p1.copyAsFastBlockPos().add(0, 1, 0));

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






	public static class WalkFactoryNorth extends WalkActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.NORTH, result);
		}

	}






	public static class WalkFactoryNorthEast extends WalkActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH_EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.NORTH_EAST, result);
		}

	}






	public static class WalkFactoryEast extends WalkActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.EAST, result);
		}

	}






	public static class WalkFactorySouthEast extends WalkActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH_EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH_EAST, result);
		}

	}






	public static class WalkFactorySouth extends WalkActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH, result);
		}

	}






	public static class WalkFactorySouthWest extends WalkActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH_WEST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH_WEST, result);
		}

	}






	public static class WalkFactoryWest extends WalkActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.WEST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.WEST, result);
		}

	}






	public static class WalkFactoryNorthWest extends WalkActionFactory {


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


