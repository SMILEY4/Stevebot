package stevebot.pathfinding.actions.playeractions;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blockpos.FastBlockPos;
import stevebot.data.blocks.BlockUtils;
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
	public String getActionNameExp() {
		return this.getActionName() + (Direction.get(getFrom().getPos(), getTo().getPos(), true).diagonal ? "-diagonal" : "-straight");
	}




	@Override
	public ProcState tick(boolean firstTick) {
		ActionObserver.tickAction(this.getActionNameExp());
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

			return Result.valid(direction, NodeCache.get(to), ActionCosts.get().STEP_UP_STRAIGHT);
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
				return Result.valid(direction, NodeCache.get(to), ActionCosts.get().STEP_UP_DIAGONAL);
			} else {
				return Result.invalid();
			}

		}


	}






	private static abstract class AbstractStepUpActionFactory extends StepUpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, getDirection());
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, getDirection(), result);
		}




		@Override
		public Class<ActionStepUp> producesAction() {
			return ActionStepUp.class;
		}

	}






	public static class StepUpFactoryNorth extends AbstractStepUpActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.NORTH;
		}

	}






	public static class StepUpFactoryNorthEast extends AbstractStepUpActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.NORTH_EAST;
		}

	}






	public static class StepUpFactoryEast extends AbstractStepUpActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.EAST;
		}

	}






	public static class StepUpFactorySouthEast extends AbstractStepUpActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.SOUTH_EAST;
		}

	}






	public static class StepUpFactorySouth extends AbstractStepUpActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.SOUTH;
		}

	}






	public static class StepUpFactorySouthWest extends AbstractStepUpActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.SOUTH_WEST;
		}

	}






	public static class StepUpFactoryWest extends AbstractStepUpActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.WEST;
		}

	}






	public static class StepUpFactoryNorthWest extends AbstractStepUpActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.NORTH_WEST;
		}

	}

}
