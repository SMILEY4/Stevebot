package stevebot.pathfinding.actions.playeractions;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blockpos.FastBlockPos;
import stevebot.data.blocks.BlockUtils;
import stevebot.data.modification.BlockBreakModification;
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

public class ActionFall extends Action {


	private enum State {
		FALLING, FINISHING
	}






	private enum Transition {
		LANDED
	}






	private StateMachine<State, Transition> stateMachine = new StateMachine<>();

	private final Modification[] modifications = new Modification[1];
	private boolean landInWater;




	private ActionFall(Node from, Node to, double cost) {
		super(from, to, cost);
		stateMachine.defineTransition(State.FALLING, Transition.LANDED, State.FINISHING);

		final FastBlockPos fallOn = to.getPosCopy().add(Direction.DOWN);
		this.landInWater = BlockUtils.isWater(fallOn);

		if (!landInWater) {
			final int fallDamage = ActionUtils.calculateFallDamage(from.getPos().getY() - to.getPos().getY());
			if (fallDamage > 0) {
				modifications[0] = Modification.healthChange(-fallDamage);
			}
		}

	}




	@Override
	public String getActionName() {
		return "fall";
	}




	@Override
	public boolean hasModifications() {
		return modifications[0] != null;
	}




	@Override
	public Modification[] getModifications() {
		return modifications;
	}




	@Override
	public void resetAction() {
		stateMachine.setState(State.FALLING);
	}




	@Override
	public ProcState tick(boolean firstTick) {
		ActionObserver.tickAction(this.getActionName());
		switch (stateMachine.getState()) {
			case FALLING:
				return tickFalling();
			case FINISHING:
				return tickFinishing();
			default:
				return ProcState.FAILED;
		}
	}




	/**
	 * The player is falling
	 */
	private ProcState tickFalling() {
		PlayerUtils.getMovement().moveTowards(getTo().getPos(), false);
		if (PlayerUtils.getPlayer().onGround || BlockUtils.isWater(PlayerUtils.getPlayerBlockPos())) {
			stateMachine.fireTransition(Transition.LANDED);
		}
		return ProcState.EXECUTING;
	}




	/**
	 * The player hit the ground or landed in water and is now moving to the final destination.
	 */
	private ProcState tickFinishing() {
		final boolean isInWater = BlockUtils.isWater(PlayerUtils.getPlayerBlockPos());
		if (isInWater) {
			PlayerUtils.getInput().holdJump();
		} else {
			PlayerUtils.getInput().releaseJump();
		}
		if (PlayerUtils.getMovement().moveTowards(getTo().getPos(), false)) {
			return ProcState.DONE;
		} else {
			return ProcState.EXECUTING;
		}
	}




	@Override
	public boolean isOnPath(BaseBlockPos position) {
		return position.getX() == getFrom().getPos().getX()
				&& position.getZ() == getTo().getPos().getZ()
				&& (position.getY() <= getFrom().getPos().getY() && position.getY() >= getTo().getPos().getY());
	}




	public static class FallActionFactory implements ActionFactory {


		@Override
		public Result check(Node node) {
			return checkWithModification(node, null);
		}




		Result checkWithModification(Node node, BlockBreakModification modification) {


			final BaseBlockPos from = node.getPos();

			// find destination position and fall-height
			int height = 0;
			FastBlockPos fallTo = from.copyAsFastBlockPos().add(Direction.DOWN);
			while (canFallThrough(fallTo, modification)) {
				fallTo = fallTo.add(Direction.DOWN);
				height++;
				if (height > 300) {
					return Result.invalid();
				}
			}
			fallTo = fallTo.add(Direction.UP);

			// check if there is a fall
			if (height <= 0) {
				return Result.invalid();
			}

			// check if destination pos is water
			final FastBlockPos d0 = fallTo.copy().add(Direction.DOWN);
			final boolean landInWater = BlockUtils.isWater(d0) && !BlockUtils.isFlowingLiquid(d0);

			// check if player can walk on destination position / destination is not dangerous
			if (!(BlockUtils.canWalkOn(d0) || landInWater) || BlockUtils.isDangerous(d0)) {
				return Result.invalid();
			}

			// check if player can survive fall
			final int damage = ActionUtils.calculateFallDamage(height);
			if (!landInWater) {
				final int currentHealth = PlayerUtils.getActiveSnapshot().getPlayerHealth();
				if (currentHealth - damage <= 1) {
					return Result.invalid();
				}
			}

			Modification modDamage = Modification.healthChange(-damage);

			return Result.valid(Direction.NONE, NodeCache.get(fallTo),
					ActionCosts.get().COST_FALL_N(from.getY() - fallTo.getY()), new Modification[]{modDamage});
		}




		private boolean canFallThrough(BaseBlockPos position, BlockBreakModification modification) {
			if (modification != null && modification.getPosition().equals(position)) {
				return true;
			} else {
				return BlockUtils.canWalkThrough(position);
			}
		}




		@Override
		public Action createAction(Node node, Result result) {
			return new ActionFall(node, result.to, result.estimatedCost);
		}




		@Override
		public Direction getDirection() {
			return Direction.DOWN;
		}




		@Override
		public Class<ActionFall> producesAction() {
			return ActionFall.class;
		}


	}

}
