package stevebot.pathfinding.actions.playeractions;

import stevebot.data.blockpos.FastBlockPos;
import stevebot.data.blocks.BlockUtils;
import stevebot.misc.Direction;
import stevebot.misc.ProcState;
import stevebot.misc.StateMachine;
import stevebot.pathfinding.BlockChange;
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
	private final BlockChange[] blockChanges;




	private ActionPillarUp(Node from, Node to, double cost, BlockChange[] blockChanges) {
		super(from, to, cost);
		this.blockChanges = blockChanges;
		stateMachine.defineTransition(State.SLOWING_DOWN, Transition.SLOW_ENOUGH, State.JUMPING);
		stateMachine.defineTransition(State.JUMPING, Transition.PLACED_BLOCK, State.LANDING);
	}




	@Override
	public void resetAction() {
		stateMachine.setState(State.SLOWING_DOWN);
	}




	@Override
	public ProcState tick(boolean fistTick) {

		switch (stateMachine.getState()) {

			case SLOWING_DOWN: {
//				if (PlayerUtils.getMovement().moveTowardsSpeed(getTo().getPos().getX(), getTo().getPos().getZ(), 2)) {
//					stateMachine.fireTransition(Transition.SLOW_ENOUGH);
//				}
//				return ProcState.EXECUTING;
				boolean slowEnough = PlayerUtils.getMovement().slowDown(0.075);
				if (slowEnough) {
					stateMachine.fireTransition(Transition.SLOW_ENOUGH);
				} else {
					PlayerUtils.getCamera().setLookAt(getTo().getPos().getX(), getTo().getPos().getY(), getTo().getPos().getZ(), true);
				}
				return ProcState.EXECUTING;
			}

			case JUMPING: {
				PlayerUtils.getMovement().moveTowards(getTo().getPos(), true);
				if (PlayerUtils.getPlayerBlockPos().equals(getFrom().getPos())) {
					PlayerUtils.getInput().setJump(false);
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


			case LANDING: {
				if (PlayerUtils.getPlayer().onGround && PlayerUtils.getPlayerBlockPos().equals(getTo().getPos())) {
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
	public boolean changedBlocks() {
		return true;
	}




	@Override
	public BlockChange[] getBlockChanges() {
		return this.blockChanges;
	}




	public static class PillarUpFactory implements ActionFactory {


		@Override
		public Action createAction(Node node, Result result) {
			return new ActionPillarUp(node, result.to, result.estimatedCost, result.blockChanges);
		}




		@Override
		public Result check(Node node) {

			// check inventory
			if (!PlayerUtils.getInventory().hasThrowawayBlockInHotbar()) {
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
			final BlockChange[] blockChanges = new BlockChange[]{
					new BlockChange(node.getPos(), BlockUtils.getBlockLibrary().getBlockByName("minecraft:stone"))
			};
			return Result.valid(Direction.UP, NodeCache.get(to), ActionCosts.COST_PILLAR_UP, blockChanges);
		}


	}


}
