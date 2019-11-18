package stevebot.pathfinding.actions.playeractions;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blockpos.FastBlockPos;
import stevebot.data.blocks.BlockUtils;
import stevebot.misc.Direction;
import stevebot.misc.ProcState;
import stevebot.misc.StateMachine;
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

public class ActionStepUp extends Action {


	private enum State {
		SLOWING_DOWN, JUMPING;
	}






	private enum Transition {
		SLOW_ENOUGH;
	}






	private StateMachine<State, Transition> stateMachine = new StateMachine<>();




	private ActionStepUp(Node from, Node to, double cost) {
		super(from, to, cost);
		stateMachine.defineTransition(State.SLOWING_DOWN, Transition.SLOW_ENOUGH, State.JUMPING);
	}




	@Override
	public void resetAction() {
		stateMachine.setState(State.SLOWING_DOWN);
	}




	@Override
	public String getActionName() {
		return "step-up";
	}




	@Override
	public ProcState tick(boolean firstTick) {
		switch (stateMachine.getState()) {
			case SLOWING_DOWN: {
				return tickSlowDown();
			}
			case JUMPING: {
				return tickJump();
			}
			default: {
				return ProcState.FAILED;
			}
		}
	}




	/**
	 * Prepare for the jump by slowing down.
	 */
	private ProcState tickSlowDown() {
		boolean slowEnough = PlayerUtils.getMovement().slowDown(0.075);
		if (slowEnough) {
			stateMachine.fireTransition(Transition.SLOW_ENOUGH);
		} else {
			PlayerUtils.getCamera().setLookAt(getTo().getPos().getX(), getTo().getPos().getY(), getTo().getPos().getZ(), true);
		}
		return ProcState.EXECUTING;
	}




	/**
	 * Jump up to the target block.
	 */
	private ProcState tickJump() {
		if (PlayerUtils.getPlayerBlockPos().equals(getFrom().getPos())) {
			PlayerUtils.getInput().setJump();
		}
		if (PlayerUtils.getMovement().moveTowards(getTo().getPos(), true)) {
			return ProcState.DONE;
		} else {
			return ProcState.EXECUTING;
		}
	}




	@Override
	public boolean isOnPath(BaseBlockPos position) {
		if (position.equals(getFrom().getPos()) || position.equals(getTo().getPos())) {
			return true;
		} else {
			return position.equals(getFrom().getPosCopy().add(Direction.UP));
		}
	}




	private static abstract class StepUpActionFactory implements ActionFactory {


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

			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.NORTH, new ArrayList<>()).add(ActionWalk.WalkFactoryNorth.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.NORTH_EAST, new ArrayList<>()).add(ActionWalk.WalkFactoryNorthEast.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.EAST, new ArrayList<>()).add(ActionWalk.WalkFactoryEast.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.SOUTH_EAST, new ArrayList<>()).add(ActionWalk.WalkFactorySouthEast.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.SOUTH, new ArrayList<>()).add(ActionWalk.WalkFactorySouth.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.SOUTH_WEST, new ArrayList<>()).add(ActionWalk.WalkFactorySouthWest.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.WEST, new ArrayList<>()).add(ActionWalk.WalkFactoryWest.class);
			IMPOSSIBLE_ACTIONS.getOrDefault(Direction.NORTH_WEST, new ArrayList<>()).add(ActionWalk.WalkFactoryNorthWest.class);
		}




		@Override
		public List<Class<? extends ActionFactory>> makesImpossible(Direction direction) {
			return IMPOSSIBLE_ACTIONS.get(direction);
		}




		ActionStepUp create(Node node, Direction direction, Result result) {
			// final Result result = direction.diagonal ? checkDiagonal(node, direction) : checkStraight(node, direction);
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
			final FastBlockPos to = node.getPosCopy().add(direction.dx, 1, direction.dz);
			if (!BlockUtils.isLoaded(to)) {
				return Result.unloaded();
			}
			if (!ActionUtils.canStandAt(to)) {
				return Result.invalid();
			}

			// check from-position
			if (!ActionUtils.canStandAt(node.getPos(), 3)) {
				return Result.invalid();
			}

			return Result.valid(direction, NodeCache.get(to), ActionCosts.COST_STEP_UP);
		}




		Result checkDiagonal(Node node, Direction direction) {

			// check to-position
			final FastBlockPos to = node.getPosCopy().add(direction.dx, 1, direction.dz);
			if (!BlockUtils.isLoaded(to)) {
				return Result.unloaded();
			}
			if (!ActionUtils.canStandAt(to)) {
				return Result.invalid();
			}

			// check from-position
			if (!ActionUtils.canStandAt(node.getPos(), 3)) {
				return Result.invalid();
			}

			// check diagonal
			Direction[] splitDirection = direction.split();
			final FastBlockPos p0 = node.getPosCopy().add(splitDirection[0].dx, 1, splitDirection[0].dz);
			final FastBlockPos p1 = node.getPosCopy().add(splitDirection[1].dx, 1, splitDirection[1].dz);

			boolean traversable0 = ActionUtils.canMoveThrough(p0, 3) && BlockUtils.isLoaded(p0);
			boolean traversable1 = ActionUtils.canMoveThrough(p1, 3) && BlockUtils.isLoaded(p1);

			if (ActionUtils.canStandAt(to) && traversable0 && traversable1) {
				return Result.valid(direction, NodeCache.get(to), ActionCosts.COST_STEP_UP * ActionCosts.COST_MULT_DIAGONAL);
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
		public Action createAction(Node node, Result result) {
			return create(node, Direction.NORTH, result);
		}

	}






	public static class StepUpFactoryNorthEast extends ActionStepUp.StepUpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH_EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.NORTH_EAST, result);
		}

	}






	public static class StepUpFactoryEast extends ActionStepUp.StepUpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.EAST, result);
		}

	}






	public static class StepUpFactorySouthEast extends ActionStepUp.StepUpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH_EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH_EAST, result);
		}

	}






	public static class StepUpFactorySouth extends ActionStepUp.StepUpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH, result);
		}

	}






	public static class StepUpFactorySouthWest extends ActionStepUp.StepUpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH_WEST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH_WEST, result);
		}

	}






	public static class StepUpFactoryWest extends ActionStepUp.StepUpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.WEST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.WEST, result);
		}

	}






	public static class StepUpFactoryNorthWest extends ActionStepUp.StepUpActionFactory {


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
