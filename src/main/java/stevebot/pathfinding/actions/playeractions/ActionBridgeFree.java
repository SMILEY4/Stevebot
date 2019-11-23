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

public class ActionBridgeFree extends Action {


	private enum State {
		PREPARE, PLACE_BRIDGE, FINISHING
	}






	private enum Transition {
		PREPARED, PLACED_BLOCK
	}






	private StateMachine<State, Transition> stateMachine = new StateMachine<>();
	private final Direction direction;
	private final Modification[] modifications;




	private ActionBridgeFree(Node from, Node to, double cost, Direction direction, Modification[] modifications) {
		super(from, to, cost);
		this.direction = direction;
		this.modifications = modifications;
		stateMachine.defineTransition(State.PREPARE, Transition.PREPARED, State.PLACE_BRIDGE);
		stateMachine.defineTransition(State.PLACE_BRIDGE, Transition.PLACED_BLOCK, State.FINISHING);
	}




	@Override
	public void resetAction() {
		stateMachine.setState(State.PREPARE);
	}




	@Override
	public ProcState tick(boolean firstTick) {
		ActionObserver.tickAction(this.getActionName());
		switch (stateMachine.getState()) {
			case PREPARE:
				return tickPrepare();
			case PLACE_BRIDGE:
				return tickPlaceBridge();
			case FINISHING:
				return tickFinishing();
			default: {
				return ProcState.FAILED;
			}
		}
	}




	/**
	 * Move into position required to place the block.
	 */
	private ProcState tickPrepare() {
		PlayerUtils.getInput().holdSneak();
		PlayerUtils.getMovement().moveTowards(getTo().getPos(), true);
		if (getTo().getPos().equals(PlayerUtils.getPlayerBlockPos()) && BlockUtils.distToEdge(PlayerUtils.getPlayerPosition(), direction.opposite()) > 0.2) {
			stateMachine.fireTransition(Transition.PREPARED);
		}
		return ProcState.EXECUTING;
	}




	/**
	 * Place the block after the required position was reached.
	 */
	private ProcState tickPlaceBridge() {
		if (!PlayerUtils.getInventory().selectThrowawayBlock(false)) {
			return ProcState.FAILED;
		}
		PlayerUtils.getCamera().enableForceCamera();
		ActionUtils.placeBlockAgainst(getFrom().getPosCopy().add(Direction.DOWN), direction);
		stateMachine.fireTransition(Transition.PLACED_BLOCK);
		return ProcState.EXECUTING;
	}




	/**
	 * Finish this action after the block was placed.
	 */
	private ProcState tickFinishing() {
		PlayerUtils.getCamera().disableForceCamera(true);
		PlayerUtils.getInput().releaseSneak();
		if (PlayerUtils.getMovement().moveTowards(getTo().getPos(), true)) {
			return ProcState.DONE;
		} else {
			return ProcState.EXECUTING;
		}
	}




	@Override
	public boolean isOnPath(BaseBlockPos position) {
		return position.equals(getFrom().getPos()) || position.equals(getTo().getPos());
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
		return "bridge-free";
	}




	private static abstract class BrideFreeActionFactory implements ActionFactory {


		ActionBridgeFree create(Node node, Direction direction, Result result) {
			return new ActionBridgeFree(node, result.to, result.estimatedCost, direction, result.modifications);
		}




		Result check(Node node, Direction direction) {

			// check to position
			final BaseBlockPos to = node.getPosCopy().add(direction.dx, 0, direction.dz);
			if (!BlockUtils.isLoaded(to)) {
				return Result.unloaded();
			}
			if (!ActionUtils.canMoveThrough(to)) {
				return Result.invalid();
			}
			final BaseBlockPos posBridgeBlock = to.copyAsFastBlockPos().add(Direction.DOWN);
			if (!BlockUtils.canPlaceBlockAt(posBridgeBlock)) {
				return Result.invalid();
			}

			// check from-position
			if (!ActionUtils.canStandAt(node.getPos())) {
				return Result.invalid();
			}

			// check inventory
			if (!PlayerUtils.getActiveSnapshot().hasThrowawayBlockInHotbar(false)) {
				return Result.invalid();
			}

			// build valid result
			final int indexThrowaway = PlayerUtils.getActiveSnapshot().findThrowawayBlock(false);
			final Modification[] modifications = new Modification[]{
					Modification.placeBlock(posBridgeBlock, PlayerUtils.getActiveSnapshot().getAsBlock(indexThrowaway))
			};
			return Result.valid(direction, NodeCache.get(to), ActionCosts.get().BRIDGE_FREE, modifications);
		}

	}






	private static abstract class AbstractBrideFreeActionFactory extends BrideFreeActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, getDirection());
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, getDirection(), result);
		}




		@Override
		public Class<ActionBridgeFree> producesAction() {
			return ActionBridgeFree.class;
		}

	}






	public static class BrideFreeFactoryNorth extends AbstractBrideFreeActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.NORTH;
		}

	}






	public static class BrideFreeFactoryEast extends AbstractBrideFreeActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.EAST;
		}

	}






	public static class BrideFreeFactorySouth extends AbstractBrideFreeActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.SOUTH;
		}

	}






	public static class BrideFreeFactoryWest extends AbstractBrideFreeActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.WEST;
		}

	}


}


