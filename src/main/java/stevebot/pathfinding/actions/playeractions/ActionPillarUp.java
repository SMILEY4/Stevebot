package stevebot.pathfinding.actions.playeractions;

import stevebot.data.blockpos.FastBlockPos;
import stevebot.data.modification.Modification;
import stevebot.misc.Direction;
import stevebot.misc.ProcState;
import stevebot.misc.StateMachine;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionUtils;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.nodes.NodeCache;
import stevebot.player.PlayerUtils;

public class ActionPillarUp extends Action {


	private enum State {
		SLOWING_DOWN, JUMPING, LANDING;
	}






	private enum Transition {
		SLOW_ENOUGH, PLACED_BLOCK;
	}






	private StateMachine<State, Transition> stateMachine = new StateMachine<>();
	private final Modification[] modifications;




	private ActionPillarUp(Node from, Node to, double cost, Modification[] modifications) {
		super(from, to, cost);
		this.modifications = modifications;
		stateMachine.defineTransition(State.SLOWING_DOWN, Transition.SLOW_ENOUGH, State.JUMPING);
		stateMachine.defineTransition(State.JUMPING, Transition.PLACED_BLOCK, State.LANDING);
		stateMachine.addListener(Transition.SLOW_ENOUGH, ((previous, next, transition) -> onSlowEnough()));
	}




	@Override
	public void resetAction() {
		stateMachine.setState(State.SLOWING_DOWN);
	}




	@Override
	public ProcState tick(boolean fistTick) {
		switch (stateMachine.getState()) {
			case SLOWING_DOWN: {
				return tickSlowDown();
			}
			case JUMPING: {
				return tickJump();
			}
			case LANDING: {
				return tickLand();
			}
			default: {
				return ProcState.FAILED;
			}
		}
	}




	/**
	 * Prepare by slowing down enough.
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




	private void onSlowEnough() {
		PlayerUtils.getCamera().enableForceCamera();
	}




	/**
	 * Pillar up by jumping and placing a block below.
	 */
	private ProcState tickJump() {
		PlayerUtils.getMovement().moveTowards(getTo().getPos(), true);
		if (PlayerUtils.getPlayerBlockPos().equals(getFrom().getPos())) {
			PlayerUtils.getInput().setJump();
		}
		if (PlayerUtils.getPlayerBlockPos().equals(getTo().getPos())) {
			if (!PlayerUtils.getInventory().selectThrowawayBlock()) {
				return ProcState.FAILED;
			}
			ActionUtils.placeBlockAgainst(getFrom().getPosCopy().add(0, -1, 0), Direction.UP);
			stateMachine.fireTransition(Transition.PLACED_BLOCK);
		}
		return ProcState.EXECUTING;
	}




	/**
	 * Land on the new block.
	 */
	private ProcState tickLand() {
		if (PlayerUtils.getPlayer().onGround && PlayerUtils.getPlayerBlockPos().equals(getTo().getPos())) {
			PlayerUtils.getCamera().disableForceCamera(true);
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
		return "pillar-up";
	}




	public static class PillarUpFactory implements ActionFactory {


		@Override
		public Action createAction(Node node, Result result) {
			return new ActionPillarUp(node, result.to, result.estimatedCost, result.modifications);
		}




		@Override
		public Result check(Node node) {

			// check inventory
			if (!PlayerUtils.getActiveSnapshot().hasThrowawayBlockInHotbar()) {
				return Result.invalid();
			}

			// check to-position
			final FastBlockPos to = node.getPosCopy().add(0, 1, 0);
			if (!ActionUtils.canMoveThrough(to)) {
				return Result.invalid();
			}

			// check from-position
			if (!ActionUtils.canStandAt(node.getPos())) {
				return Result.invalid();
			}

			// build valid result
			int indexThrowaway = PlayerUtils.getActiveSnapshot().findThrowawayBlock();
			final Modification[] modifications = new Modification[]{
					Modification.placeBlock(node.getPos(), PlayerUtils.getActiveSnapshot().getAsBlock(indexThrowaway))
			};
			return Result.valid(Direction.UP, NodeCache.get(to), ActionCosts.COST_PILLAR_UP, modifications);
		}


	}


}
