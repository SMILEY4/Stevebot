package stevebot.pathfinding.actions.playeractions;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blocks.BlockUtils;
import stevebot.data.items.wrapper.ItemToolWrapper;
import stevebot.data.modification.BlockBreakModification;
import stevebot.data.modification.Modification;
import stevebot.misc.Direction;
import stevebot.misc.ProcState;
import stevebot.misc.StateMachine;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionUtils;
import stevebot.pathfinding.actions.BreakBlockCheckResult;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.nodes.NodeCache;
import stevebot.player.PlayerUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActionMineDown extends Action {


	private enum State {
		PREPARE_FIRST, BREAKING_FIRST, BREAKING_SECOND, BREAKING_THIRD, MOVING
	}






	private enum Transition {
		PREPARED_FIRST, BROKE_FIRST, BROKE_SECOND, BROKE_THIRD
	}






	private StateMachine<State, Transition> stateMachine = new StateMachine<>();

	private final Modification[] modifications;




	private ActionMineDown(Node from, Node to, double cost, Modification[] modifications) {
		super(from, to, cost);
		this.modifications = modifications;
		stateMachine.defineTransition(State.PREPARE_FIRST, Transition.PREPARED_FIRST, State.BREAKING_FIRST);
		stateMachine.defineTransition(State.BREAKING_FIRST, Transition.BROKE_FIRST, State.BREAKING_SECOND);
		stateMachine.defineTransition(State.BREAKING_SECOND, Transition.BROKE_SECOND, State.BREAKING_THIRD);
		stateMachine.defineTransition(State.BREAKING_THIRD, Transition.BROKE_THIRD, State.MOVING);
		stateMachine.addListener(Transition.BROKE_FIRST, (previous, next, transition) -> onBrokeFirst());
		stateMachine.addListener(Transition.BROKE_SECOND, (previous, next, transition) -> onBrokeSecond());
		stateMachine.addListener(Transition.BROKE_THIRD, (previous, next, transition) -> onBrokeThird());
	}




	@Override
	public void resetAction() {
		stateMachine.setState(State.PREPARE_FIRST);
	}




	@Override
	public ProcState tick(boolean firstTick) {
		switch (stateMachine.getState()) {
			case PREPARE_FIRST:
				return tickPrepareFirst();
			case BREAKING_FIRST:
				return tickBreakFirst();
			case BREAKING_SECOND:
				return tickBreakSecond();
			case BREAKING_THIRD:
				return tickBreakThird();
			case MOVING:
				return tickMove();
			default: {
				return ProcState.FAILED;
			}
		}
	}




	/**
	 * Prepare to break the first block.
	 */
	private ProcState tickPrepareFirst() {
		PlayerUtils.getInventory().selectItem(((BlockBreakModification) getModifications()[0]).getTool());
		PlayerUtils.getCamera().enableForceCamera();
		stateMachine.fireTransition(Transition.PREPARED_FIRST);
		return ProcState.EXECUTING;
	}




	/**
	 * Break the first block.
	 */
	private ProcState tickBreakFirst() {
		final BlockBreakModification modification = (BlockBreakModification) getModifications()[0];
		if (ActionUtils.breakBlock(modification.getPosition())) {
			stateMachine.fireTransition(Transition.BROKE_FIRST);
		}
		return ProcState.EXECUTING;
	}




	/**
	 * The first block was broken
	 */
	private void onBrokeFirst() {
		if (getModifications().length >= 2) {
			PlayerUtils.getInventory().selectItem(((BlockBreakModification) getModifications()[1]).getTool());
		}
	}




	/**
	 * Break the second block.
	 */
	private ProcState tickBreakSecond() {
		if (getModifications().length >= 2) {
			final BlockBreakModification modification = (BlockBreakModification) getModifications()[1];
			if (ActionUtils.breakBlock(modification.getPosition())) {
				stateMachine.fireTransition(Transition.BROKE_SECOND);
			}
		} else {
			stateMachine.fireTransition(Transition.BROKE_SECOND);
		}
		return ProcState.EXECUTING;
	}




	/**
	 * The third block was broken
	 */
	private void onBrokeSecond() {
		if (getModifications().length == 3) {
			PlayerUtils.getInventory().selectItem(((BlockBreakModification) getModifications()[2]).getTool());
		}
	}




	/**
	 * Break the third block.
	 */
	private ProcState tickBreakThird() {
		if (getModifications().length == 3) {
			final BlockBreakModification modification = (BlockBreakModification) getModifications()[2];
			if (ActionUtils.breakBlock(modification.getPosition())) {
				stateMachine.fireTransition(Transition.BROKE_THIRD);
			}
		} else {
			stateMachine.fireTransition(Transition.BROKE_THIRD);
		}
		return ProcState.EXECUTING;
	}




	/**
	 * The third block was broken
	 */
	private void onBrokeThird() {
		PlayerUtils.getCamera().disableForceCamera(true);
	}




	/**
	 * Move to the target position.
	 */
	private ProcState tickMove() {
		if (PlayerUtils.getMovement().moveTowards(getTo().getPos(), true)) {
			return ProcState.DONE;
		} else {
			return ProcState.EXECUTING;
		}
	}




	@Override
	public boolean hasModifications() {
		return true;
	}




	@Override
	public Modification[] getModifications() {
		return this.modifications;
	}




	@Override
	public String getActionName() {
		return "mine-down";
	}




	private static abstract class MineDownActionFactory implements ActionFactory {


		@Override
		public List<Class<? extends ActionFactory>> makesImpossible(Direction direction) {
			return Collections.emptyList();
		}




		ActionMineDown create(Node node, Direction direction, Result result) {
			return new ActionMineDown(node, result.to, result.estimatedCost, result.modifications);
		}




		Result check(Node node, Direction direction) {

			// check to position
			final BaseBlockPos to = node.getPosCopy().add(direction.dx, -1, direction.dz);
			if (!BlockUtils.isLoaded(to)) {
				return Result.unloaded();
			}
			if (ActionUtils.canStandAt(to, 3)) {
				return Result.invalid();
			}

			// check from-position
			if (!ActionUtils.canStandAt(node.getPos())) {
				return Result.invalid();
			}

			List<Modification> modificationList = new ArrayList<>();
			float totalTicksTopBreak = 0;

			// check top block to break
			final BaseBlockPos posTop = to.copyAsFastBlockPos().add(0, 2, 0);
			if(!ActionUtils.canSafelyBreak(posTop)) {
				return Result.invalid();
			}
			if (!BlockUtils.canWalkThrough(posTop)) {
				final BreakBlockCheckResult resultTop = ActionUtils.checkBlockToBreak(posTop);
				if (!resultTop.breakable) {
					return Result.invalid();
				} else {
					totalTicksTopBreak += resultTop.ticksToBreak;
					modificationList.add(Modification.breakBlock(posTop, (ItemToolWrapper) resultTop.bestTool));
				}
			}

			// check middle block to break
			final BaseBlockPos posMiddle = to.copyAsFastBlockPos().add(0, 1, 0);
			if(!ActionUtils.canSafelyBreak(posMiddle)) {
				return Result.invalid();
			}
			if (!BlockUtils.canWalkThrough(posMiddle)) {
				final BreakBlockCheckResult resultBottom = ActionUtils.checkBlockToBreak(posMiddle);
				if (!resultBottom.breakable) {
					return Result.invalid();
				} else {
					totalTicksTopBreak += resultBottom.ticksToBreak;
					modificationList.add(Modification.breakBlock(posMiddle, (ItemToolWrapper) resultBottom.bestTool));
				}
			}

			// check bottom block to break
			final BaseBlockPos posBottom = to;
			if(!ActionUtils.canSafelyBreak(posBottom)) {
				return Result.invalid();
			}
			if (!BlockUtils.canWalkThrough(posBottom)) {
				final BreakBlockCheckResult resultBottom = ActionUtils.checkBlockToBreak(posBottom);
				if (!resultBottom.breakable) {
					return Result.invalid();
				} else {
					totalTicksTopBreak += resultBottom.ticksToBreak;
					modificationList.add(Modification.breakBlock(posBottom, (ItemToolWrapper) resultBottom.bestTool));
				}
			}

			if (modificationList.isEmpty()) {
				return Result.invalid();
			}

			final Modification[] modifications = modificationList.toArray(Modification.EMPTY);
			return Result.valid(direction, NodeCache.get(to), totalTicksTopBreak + ActionCosts.COST_STEP_DOWN, modifications);
		}

	}






	public static class MineDownFactoryNorth extends MineDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.NORTH, result);
		}

	}






	public static class MineDownFactoryEast extends MineDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.EAST, result);
		}

	}






	public static class MineDownFactorySouth extends MineDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH, result);
		}

	}






	public static class MineDownFactoryWest extends MineDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.WEST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.WEST, result);
		}

	}


}


