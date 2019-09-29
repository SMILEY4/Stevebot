package stevebot.pathfinding.actions.playeractions;

import stevebot.BlockUtils;
import stevebot.Direction;
import stevebot.StateMachine;
import stevebot.Stevebot;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionUtils;
import stevebot.pathfinding.execution.PathExecutor;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.nodes.NodeCache;
import stevebot.player.PlayerController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionDropDown extends Action {


	private enum State {
		PREPARING_1, PREPARING_2, FALLING, FINISHING;
	}






	private enum Transition {
		PREPARATION_1_DONE, PREPARATION_2_DONE, TOUCHED_GROUND
	}






	private StateMachine<State, Transition> stateMachine = new StateMachine<>();


	private final ActionFall fall;
	private final Direction direction;




	private ActionDropDown(Node from, Node to, double cost, ActionFall fall, Direction direction) {
		super(from, to, cost);
		this.fall = fall;
		this.direction = direction;
		stateMachine.defineTransition(State.PREPARING_1, Transition.PREPARATION_1_DONE, State.PREPARING_2);
		stateMachine.defineTransition(State.PREPARING_2, Transition.PREPARATION_2_DONE, State.FALLING);
		stateMachine.defineTransition(State.FALLING, Transition.TOUCHED_GROUND, State.FINISHING);
	}




	@Override
	public void resetAction() {
		stateMachine.setState(State.PREPARING_1);
	}




	@Override
	public PathExecutor.StateFollow tick(boolean firstTick) {
		final PlayerController controller = Stevebot.get().getPlayerController();


		switch (stateMachine.getState()) {

			case PREPARING_1: {
				final double distToEdge = BlockUtils.distToEdge(controller.utils().getPlayerPosition(), direction);
				if (distToEdge <= 0.4) {
					stateMachine.fireTransition(Transition.PREPARATION_1_DONE);
				} else {
					controller.movement().moveTowards(getTo().getPos(), true);
				}
				return PathExecutor.StateFollow.EXEC;
			}

			case PREPARING_2: {
				if (controller.getPlayer().onGround && !controller.utils().isPlayerMoving(0.0001, false)) {
					controller.movement().moveTowards(getTo().getPos(), true);
				}
				if (!controller.getPlayer().onGround) {
					stateMachine.fireTransition(Transition.PREPARATION_2_DONE);
				}
				return PathExecutor.StateFollow.EXEC;
			}

			case FALLING: {
				if (controller.getPlayer().onGround) {
					stateMachine.fireTransition(Transition.TOUCHED_GROUND);
				}
				return PathExecutor.StateFollow.EXEC;
			}

			case FINISHING: {
				if (controller.movement().moveTowards(getTo().getPos(), true)) {
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




	private static abstract class DropDownActionFactory implements ActionFactory {


		private static final Map<Direction, List<Class<? extends ActionFactory>>> IMPOSSIBLE_ACTIONS = new HashMap<>();




		static {
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




		private final ActionFall.FallActionFactory fallActionFactory = new ActionFall.FallActionFactory();




		ActionDropDown create(Node node, Direction direction, Result result) {
			final Node nodeFall = NodeCache.get(node.getPosCopy().add(direction.dx, 0, direction.dz));
			final ActionFall actionFall = (ActionFall) fallActionFactory.createAction(nodeFall, fallActionFactory.check(nodeFall));
			return new ActionDropDown(node, result.to, result.estimatedCost, actionFall, direction);

		}




		Result check(Node node, Direction direction) {
			if (direction.diagonal) {
				return checkDiagonal(node, direction);
			} else {
				return checkStraight(node, direction);
			}
		}




		Result checkStraight(Node node, Direction direction) {

			// check to-position horizontal
			final BaseBlockPos to = node.getPosCopy().add(direction.dx, 0, direction.dz);
			if (!BlockUtils.isLoaded(to)) {
				return Result.unloaded();
			}
			if (!ActionUtils.canMoveThrough(to)) {
				return Result.invalid();
			}

			// check from-position
			if (!ActionUtils.canStandAt(node.getPos())) {
				return Result.invalid();
			}

			// check fall
			final Node nodeFall = NodeCache.get(node.getPosCopy().add(direction.dx, 0, direction.dz));
			final ActionFall actionFall = (ActionFall) fallActionFactory.createAction(nodeFall, fallActionFactory.check(nodeFall));
			if (actionFall == null) {
				return Result.invalid();
			}

			return Result.valid(direction, NodeCache.get(to), ActionCosts.COST_WALKING + actionFall.getCost());
		}




		Result checkDiagonal(Node node, Direction direction) {

			// check to-position horizontal
			final BaseBlockPos to = node.getPosCopy().add(direction.dx, -1, direction.dz);
			if (!BlockUtils.isLoaded(to)) {
				return Result.unloaded();
			}
			if (!ActionUtils.canMoveThrough(to)) {
				return Result.invalid();
			}

			// check from-position
			if (!ActionUtils.canStandAt(node.getPos())) {
				return Result.invalid();
			}

			// check diagonal blocks
			Direction[] splitDirection = direction.split();
			final BaseBlockPos p0 = node.getPosCopy().add(splitDirection[0].dx, 0, splitDirection[0].dz);
			final BaseBlockPos p1 = node.getPosCopy().add(splitDirection[1].dx, 0, splitDirection[1].dz);
			if (!ActionUtils.canMoveThroughAll(p0, p1)) {
				return Result.invalid();
			}

			// check+create fall
			final Node nodeFall = NodeCache.get(node.getPosCopy().add(direction.dx, 0, direction.dz));
			final ActionFall actionFall = (ActionFall) fallActionFactory.createAction(nodeFall, fallActionFactory.check(nodeFall));
			if (actionFall == null) {
				return Result.invalid();
			}

			return Result.valid(direction, NodeCache.get(to), ActionCosts.COST_WALKING * ActionCosts.COST_MULT_DIAGONAL + actionFall.getCost());
		}


	}






	public static class DropDownFactoryNorth extends ActionDropDown.DropDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.NORTH, result);
		}

	}






	public static class DropDownFactoryNorthEast extends ActionDropDown.DropDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH_EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.NORTH_EAST, result);
		}

	}






	public static class DropDownFactoryEast extends ActionDropDown.DropDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.EAST, result);
		}

	}






	public static class DropDownFactorySouthEast extends ActionDropDown.DropDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH_EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH_EAST, result);
		}

	}






	public static class DropDownFactorySouth extends ActionDropDown.DropDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH, result);
		}

	}






	public static class DropDownFactorySouthWest extends ActionDropDown.DropDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH_WEST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH_WEST, result);
		}

	}






	public static class DropDownFactoryWest extends ActionDropDown.DropDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.WEST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.WEST, result);
		}

	}






	public static class DropDownFactoryNorthWest extends ActionDropDown.DropDownActionFactory {


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
