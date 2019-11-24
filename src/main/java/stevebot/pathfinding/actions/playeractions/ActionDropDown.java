package stevebot.pathfinding.actions.playeractions;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blocks.BlockUtils;
import stevebot.data.modification.Modification;
import stevebot.misc.Direction;
import stevebot.misc.ProcState;
import stevebot.misc.StateMachine;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionObserver;
import stevebot.pathfinding.actions.ActionUtils;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.nodes.NodeCache;
import stevebot.player.PlayerUtils;

public class ActionDropDown extends Action {


	private enum State {
		WALK_TOWARDS_EDGE, SLIDE_OFF_EDGE, FALLING;
	}






	private enum Transition {
		IS_AT_POSITION, DROPPED_OFF_EDGE
	}






	private StateMachine<State, Transition> stateMachine = new StateMachine<>();


	private final ActionFall fall;
	private boolean firstTickFall = true;
	private final Direction direction;




	private ActionDropDown(Node from, Node to, double cost, ActionFall fall, Direction direction) {
		super(from, to, cost);
		this.fall = fall;
		this.direction = direction;
		stateMachine.defineTransition(State.WALK_TOWARDS_EDGE, Transition.IS_AT_POSITION, State.SLIDE_OFF_EDGE);
		stateMachine.defineTransition(State.SLIDE_OFF_EDGE, Transition.DROPPED_OFF_EDGE, State.FALLING);
	}




	@Override
	public boolean hasModifications() {
		return fall.hasModifications();
	}




	@Override
	public Modification[] getModifications() {
		return fall.getModifications();
	}




	@Override
	public void resetAction() {
		stateMachine.setState(State.WALK_TOWARDS_EDGE);
		firstTickFall = false;
		fall.resetAction();
	}




	@Override
	public String getActionName() {
		return "drop-down";
	}




	@Override
	public String getActionNameExp() {
		return this.getActionName() + (Direction.get(getFrom().getPos(), getTo().getPos(), true).diagonal ? "-diagonal" : "-straight");
	}




	@Override
	public ProcState tick(boolean firstTick) {
		switch (stateMachine.getState()) {
			case WALK_TOWARDS_EDGE: {
				ActionObserver.tickAction(getActionNameExp());
				return tickWalkTowardsEdge();
			}
			case SLIDE_OFF_EDGE: {
				ActionObserver.tickAction(getActionNameExp());
				return tickSlideOffEdge();
			}
			case FALLING: {
				return tickFall();
			}
			default: {
				return ProcState.FAILED;
			}
		}
	}




	/**
	 * Walk towards the edge but do not fall off.
	 */
	private ProcState tickWalkTowardsEdge() {
		final double distToEdge = BlockUtils.distToEdge(PlayerUtils.getPlayerPosition(), direction);
		if (distToEdge <= 0.4) {
			stateMachine.fireTransition(Transition.IS_AT_POSITION);
		} else {
			PlayerUtils.getMovement().moveTowards(getTo().getPos(), true);
		}
		return ProcState.EXECUTING;
	}




	/**
	 * Walk the last few units and fall off the edge of the block.
	 */
	private ProcState tickSlideOffEdge() {
		if (PlayerUtils.getPlayer().onGround && !PlayerUtils.isPlayerMoving(0.0001, false)) {
			PlayerUtils.getMovement().moveTowards(getTo().getPos(), true);
		}
		if (!PlayerUtils.getPlayer().onGround) {
			stateMachine.fireTransition(Transition.DROPPED_OFF_EDGE);
		}
		return ProcState.EXECUTING;
	}




	/**
	 * The fall.
	 */
	private ProcState tickFall() {
		final ProcState stateFall = fall.tick(firstTickFall);
		firstTickFall = false;
		return stateFall;
	}




	@Override
	public boolean isOnPath(BaseBlockPos position) {
		return position.equals(getFrom().getPos()) || fall.isOnPath(position);
	}




	private static abstract class DropDownActionFactory implements ActionFactory {


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
			final Result resultFall = fallActionFactory.check(nodeFall);
			if (ResultType.VALID != resultFall.type) {
				return Result.invalid();
			}

			final ActionFall actionFall = (ActionFall) fallActionFactory.createAction(nodeFall, resultFall);

			return Result.valid(direction, actionFall.getTo(), ActionCosts.get().DROP_DOWN_STRAIGHT + actionFall.getCost());
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
			final Result resultFall = fallActionFactory.check(nodeFall);
			if (ResultType.INVALID == resultFall.type) {
				return Result.invalid();
			}
			if (ResultType.UNLOADED == resultFall.type) {
				return Result.unloaded();
			}

			return Result.valid(direction, resultFall.to, ActionCosts.get().DROP_DOWN_DIAGONAL + resultFall.estimatedCost, resultFall.modifications);
		}


	}






	private static abstract class AbstractDropDownActionFactory extends DropDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, getDirection());
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, getDirection(), result);
		}




		@Override
		public Class<ActionDropDown> producesAction() {
			return ActionDropDown.class;
		}

	}






	public static class DropDownFactoryNorth extends AbstractDropDownActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.NORTH;
		}

	}






	public static class DropDownFactoryNorthEast extends AbstractDropDownActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.NORTH_EAST;
		}

	}






	public static class DropDownFactoryEast extends AbstractDropDownActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.EAST;
		}

	}






	public static class DropDownFactorySouthEast extends AbstractDropDownActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.SOUTH_EAST;
		}

	}






	public static class DropDownFactorySouth extends AbstractDropDownActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.SOUTH;
		}

	}






	public static class DropDownFactorySouthWest extends AbstractDropDownActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.SOUTH_WEST;
		}

	}






	public static class DropDownFactoryWest extends AbstractDropDownActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.WEST;
		}

	}






	public static class DropDownFactoryNorthWest extends AbstractDropDownActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.NORTH_WEST;
		}

	}

}
