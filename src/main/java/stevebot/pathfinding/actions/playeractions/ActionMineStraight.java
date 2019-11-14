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

public class ActionMineStraight extends Action {


	private enum State {
		PREPARE_FIRST, BREAKING_FIRST, BREAKING_SECOND, MOVING
	}






	private enum Transition {
		PREPARED, BROKE_FIRST, BROKE_SECOND
	}






	private StateMachine<State, Transition> stateMachine = new StateMachine<>();

	private final Modification[] modifications;




	private ActionMineStraight(Node from, Node to, double cost, Modification[] modifications) {
		super(from, to, cost);
		this.modifications = modifications;
		stateMachine.defineTransition(State.PREPARE_FIRST, Transition.PREPARED, State.BREAKING_FIRST);
		stateMachine.defineTransition(State.BREAKING_FIRST, Transition.BROKE_FIRST, State.BREAKING_SECOND);
		stateMachine.defineTransition(State.BREAKING_SECOND, Transition.BROKE_SECOND, State.MOVING);
		stateMachine.addListener(Transition.BROKE_FIRST, (previous, next, transition) -> onBrokeFirst());
		stateMachine.addListener(Transition.BROKE_SECOND, (previous, next, transition) -> onBrokeSecond());
	}




	@Override
	public void resetAction() {
		stateMachine.setState(State.PREPARE_FIRST);
	}




	@Override
	public ProcState tick(boolean firstTick) {
		switch (stateMachine.getState()) {
			case PREPARE_FIRST:
				return tickPrepare();
			case BREAKING_FIRST:
				return tickBreakFirst();
			case BREAKING_SECOND:
				return tickBreakSecond();
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
	private ProcState tickPrepare() {
		PlayerUtils.getInventory().selectItem(((BlockBreakModification) getModifications()[0]).getTool());
		PlayerUtils.getCamera().enableForceCamera();
		stateMachine.fireTransition(Transition.PREPARED);
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
		if (getModifications().length == 2) {
			PlayerUtils.getInventory().selectItem(((BlockBreakModification) getModifications()[1]).getTool());
		}
	}




	/**
	 * Break the seconds block
	 */
	private ProcState tickBreakSecond() {
		if (getModifications().length == 2) {
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
	 * The second block was broken
	 */
	private void onBrokeSecond() {
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
		return "mine-straight";
	}




	private static abstract class MineStraightActionFactory implements ActionFactory {


		@Override
		public List<Class<? extends ActionFactory>> makesImpossible(Direction direction) {
			return Collections.emptyList();
		}




		ActionMineStraight create(Node node, Direction direction, Result result) {
			return new ActionMineStraight(node, result.to, result.estimatedCost, result.modifications);
		}




		Result check(Node node, Direction direction) {

			// check to position
			final BaseBlockPos to = node.getPosCopy().add(direction.dx, 0, direction.dz);
			if (!BlockUtils.isLoaded(to)) {
				return Result.unloaded();
			}
			if (ActionUtils.canStandAt(to)) {
				return Result.invalid();
			}

			// check from-position
			if (!ActionUtils.canStandAt(node.getPos())) {
				return Result.invalid();
			}

			List<Modification> modificationList = new ArrayList<>();
			float totalTicksTopBreak = 0;

			// check top block to break
			final BaseBlockPos posTop = to.copyAsFastBlockPos().add(0, 1, 0);
			if (!BlockUtils.canWalkThrough(posTop)) {
				final BreakBlockCheckResult resultTop = ActionUtils.checkBlockToBreak(posTop);
				if (!resultTop.breakable) {
					return Result.invalid();
				} else {
					totalTicksTopBreak += resultTop.ticksToBreak;
					modificationList.add(Modification.breakBlock(posTop, (ItemToolWrapper) resultTop.bestTool));
				}
			}

			// check bottom block to break
			final BaseBlockPos posBottom = to;
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
			return Result.valid(direction, NodeCache.get(to), totalTicksTopBreak + ActionCosts.COST_WALKING, modifications);
		}

	}






	public static class MineStraightFactoryNorth extends MineStraightActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.NORTH, result);
		}

	}






	public static class MineStraightFactoryEast extends MineStraightActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.EAST, result);
		}

	}






	public static class MineStraightFactorySouth extends MineStraightActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH, result);
		}

	}






	public static class MineStraightFactoryWest extends MineStraightActionFactory {


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


