package stevebot.pathfinding.actions.playeractions;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blocks.BlockUtils;
import stevebot.misc.Direction;
import stevebot.misc.ProcState;
import stevebot.misc.StateMachine;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionUtils;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.nodes.NodeCache;
import stevebot.player.PlayerUtils;

public class ActionSprintJump extends Action {


	private enum State {
		PREPARING, JUMPING, LANDING;
	}






	private enum Transition {
		PREPARATION_DONE, TOUCHED_GROUND
	}






	private StateMachine<State, Transition> stateMachine = new StateMachine<>();




	private ActionSprintJump(Node from, Node to, double cost) {
		super(from, to, cost);
		stateMachine.defineTransition(State.PREPARING, Transition.PREPARATION_DONE, State.JUMPING);
		stateMachine.defineTransition(State.JUMPING, Transition.TOUCHED_GROUND, State.LANDING);
	}




	@Override
	public void resetAction() {
		stateMachine.setState(State.PREPARING);
	}




	@Override
	public String getActionName() {
		return "sprint-jump";
	}




	@Override
	public ProcState tick(boolean firstTick) {
		switch (stateMachine.getState()) {
			case PREPARING: {
				return tickPreparation();
			}
			case JUMPING: {
				return tickJump();
			}
			case LANDING: {
				return tickLanding();
			}
			default: {
				return ProcState.FAILED;
			}
		}
	}




	/**
	 * Prepare everything before jumping
	 */
	private ProcState tickPreparation() {
		if (PlayerUtils.getMovement().moveTowardsSpeed(getFrom().getPos().getCenterX(), getFrom().getPos().getCenterZ(), 0.055)) {
			stateMachine.fireTransition(Transition.PREPARATION_DONE);
		}
		return ProcState.EXECUTING;
	}




	/**
	 * Move into position and jump.
	 */
	private ProcState tickJump() {
		PlayerUtils.getMovement().moveTowards(getTo().getPos(), true);
		PlayerUtils.getInput().setSprint();
		final double distToCenter = BlockUtils.distToCenter(getFrom().getPos(), PlayerUtils.getPlayerPosition());
		if (distToCenter > 0.4) {
			PlayerUtils.getInput().setJump();
		}
		if (PlayerUtils.getPlayer().onGround && PlayerUtils.getPlayerBlockPos().equals(getTo().getPos())) {
			stateMachine.fireTransition(Transition.TOUCHED_GROUND);
		}
		return ProcState.EXECUTING;
	}




	/**
	 * After landing on the target block.
	 */
	private ProcState tickLanding() {
		if (PlayerUtils.getMovement().moveTowards(getTo().getPos(), true)) {
			return ProcState.DONE;
		} else {
			return ProcState.EXECUTING;
		}
	}




	private static abstract class SprintJumpActionFactory implements ActionFactory {


		ActionSprintJump create(Node node, Direction direction, Result result) {
			return new ActionSprintJump(node, result.to, result.estimatedCost);
		}




		Result check(Node node, Direction direction) {

			if (direction.diagonal) {
				return Result.invalid();
			}

			// check to-position
			final BaseBlockPos to = node.getPosCopy().add(direction.dx * 4, 0, direction.dz * 4);
			if (!BlockUtils.isLoaded(to)) {
				return Result.unloaded();
			}
			if (!ActionUtils.canJumpAt(to)) {
				return Result.invalid();
			}

			// check from-position
			if (!ActionUtils.canJumpAt(node.getPos())) {
				return Result.invalid();
			}

			// check gap
			for (int i = 0; i < 3; i++) {
				final BaseBlockPos gap = node.getPosCopy().add(direction.dx * (i + 1), 0, direction.dz * (i + 1));
				if (i == 2) {
					if (!ActionUtils.canJump(gap)) {
						return Result.invalid();
					}
				} else {
					if (!ActionUtils.canJumpThrough(gap)) {
						return Result.invalid();
					}
				}
			}

			return Result.valid(Direction.NONE, NodeCache.get(to), ActionCosts.COST_SPRINT_JUMP);
		}


	}






	public static class SprintJumpFactoryNorth extends ActionSprintJump.SprintJumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.NORTH, result);
		}

	}






	public static class SprintJumpFactoryEast extends ActionSprintJump.SprintJumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.EAST, result);
		}

	}






	public static class SprintJumpFactorySouth extends ActionSprintJump.SprintJumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH, result);
		}

	}






	public static class SprintJumpFactoryWest extends ActionSprintJump.SprintJumpActionFactory {


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
