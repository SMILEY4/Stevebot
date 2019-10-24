package stevebot.pathfinding.actions.playeractions;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blocks.BlockUtils;
import stevebot.data.items.ItemLibrary;
import stevebot.data.items.ItemUtils;
import stevebot.data.items.wrapper.ItemToolWrapper;
import stevebot.data.items.wrapper.ItemWrapper;
import stevebot.data.modification.BlockBreakModification;
import stevebot.data.modification.Modification;
import stevebot.misc.Direction;
import stevebot.misc.ProcState;
import stevebot.misc.StateMachine;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionUtils;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.nodes.NodeCache;
import stevebot.player.PlayerUtils;

public class ActionDigDown extends Action {


	private enum State {
		PREPARING, BREAKING_BLOCK, FALLING, FINISHING
	}






	private enum Transition {
		PREPARED, BLOCK_BROKEN, TOUCHED_GROUND
	}






	private StateMachine<State, Transition> stateMachine = new StateMachine<>();

	private final Modification[] modifications;




	private ActionDigDown(Node from, Node to, double cost, Modification[] modifications) {
		super(from, to, cost);
		this.modifications = modifications;
		stateMachine.defineTransition(State.PREPARING, Transition.PREPARED, State.BREAKING_BLOCK);
		stateMachine.defineTransition(State.BREAKING_BLOCK, Transition.BLOCK_BROKEN, State.FALLING);
		stateMachine.defineTransition(State.FALLING, Transition.TOUCHED_GROUND, State.FINISHING);
	}




	@Override
	public void resetAction() {
		stateMachine.setState(State.PREPARING);

	}




	@Override
	public ProcState tick(boolean fistTick) {

		switch (stateMachine.getState()) {
			case PREPARING: {
				PlayerUtils.getInventory().selectItem(((BlockBreakModification) getModifications()[0]).getTool());
				stateMachine.fireTransition(Transition.PREPARED);
				PlayerUtils.getCamera().enableForceCamera();
				return ProcState.EXECUTING;
			}
			case BREAKING_BLOCK: {
				if (ActionUtils.breakBlock(getFrom().getPosCopy().add(0, -1, 0))) {
					stateMachine.fireTransition(Transition.BLOCK_BROKEN);
					PlayerUtils.getCamera().disableForceCamera(true);
				}
				return ProcState.EXECUTING;
			}
			case FALLING: {
				if (PlayerUtils.getPlayer().onGround) {
					stateMachine.fireTransition(Transition.TOUCHED_GROUND);
				}
				return ProcState.EXECUTING;
			}
			case FINISHING: {
				if (PlayerUtils.getMovement().moveTowards(getTo().getPos(), true)) {
					return ProcState.DONE;
				} else {
					return ProcState.EXECUTING;
				}
			}
			default: {
				return ProcState.FAILED;
			}
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
		return "dig-down";
	}




	public static class DigDownFactory implements ActionFactory {


		private final ActionFall.FallActionFactory fallActionFactory = new ActionFall.FallActionFactory();




		@Override
		public Action createAction(Node node, Result result) {
			return new ActionDigDown(node, result.to, result.estimatedCost, result.modifications);
		}




		@Override
		public Result check(Node node) {

			// check from-position
			if (!ActionUtils.canStandAt(node.getPos())) {
				return Result.invalid();
			}

			// check if block breakable
			final BaseBlockPos posBreakBlock = node.getPosCopy().add(0, -1, 0);
			final ItemWrapper bestTool = PlayerUtils.getActiveSnapshot().findBestToolForBlock(BlockUtils.getBlockProvider().getBlockAt(posBreakBlock));
			if (bestTool == ItemLibrary.INVALID_ITEM) {
				return Result.invalid();
			}

			// check fall
			final Node nodeFall = NodeCache.get(posBreakBlock);
			final Result resultFall = fallActionFactory.check(nodeFall);
			if (ResultType.VALID != resultFall.type) {
				return Result.invalid();
			}

			// build valid result
			final Modification[] modifications = new Modification[]{
					Modification.breakBlock(posBreakBlock, (ItemToolWrapper) bestTool)
			};
			float ticksToBreak = ItemUtils.getBreakDuration(bestTool.getStack(1), posBreakBlock);
			return Result.valid(Direction.DOWN, resultFall.to, ticksToBreak + resultFall.estimatedCost, modifications);
		}


	}


}
