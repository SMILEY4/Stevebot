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

public class ActionJump extends Action {


	private enum State {
		PREPARING, JUMPING, LANDING;
	}






	private enum Transition {
		PREPARATION_DONE, TOUCHED_GROUND
	}






	private StateMachine<State, Transition> stateMachine = new StateMachine<>();




	private ActionJump(Node from, Node to, double cost) {
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
		return "jump";
	}




	@Override
	public String getActionNameExp() {
		return this.getActionName() + (Direction.get(getFrom().getPos(), getTo().getPos(), true).diagonal ? "-diagonal" : "-straight");
	}




	@Override
	public ProcState tick(boolean firstTick) {
		ActionObserver.tickAction(this.getActionNameExp());
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




	@Override
	public boolean isOnPath(BaseBlockPos position) {
		return position.getY() >= getFrom().getPos().getY();
	}




	private static abstract class JumpActionFactory implements ActionFactory {


		ActionJump create(Node node, Direction direction, Result result) {
			//final Result result = direction.diagonal ? checkDiagonal(node, direction) : checkStraight(node, direction);
			return new ActionJump(node, result.to, result.estimatedCost);
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
			final BaseBlockPos to = node.getPosCopy().add(direction.dx * 2, 0, direction.dz * 2);
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
			if (!ActionUtils.canJumpThrough(node.getPosCopy().add(direction.dx, 0, direction.dy))) {
				return Result.invalid();
			}

			return Result.valid(Direction.NONE, NodeCache.get(to), ActionCosts.get().JUMP_STRAIGHT);
		}




		Result checkDiagonal(Node node, Direction direction) {

			// check to-position
			final BaseBlockPos to = node.getPosCopy().add(direction.dx * 2, 0, direction.dz * 2);
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
			final FastBlockPos from = node.getPosCopy();
			if (!ActionUtils.canJumpThrough(from.add(direction))) {
				return Result.invalid();
			}

			// check diagonal 1
			Direction[] splitDirection1 = direction.split();
			final FastBlockPos p0 = node.getPosCopy().add(splitDirection1[0]);
			final FastBlockPos p1 = node.getPosCopy().add(splitDirection1[1]);
			if (!ActionUtils.canJump(p0, p1)) {
				return Result.invalid();
			}

			// check diagonal 2
			Direction[] splitDirection2 = direction.split();
			final FastBlockPos p2 = node.getPosCopy().add(direction).add(splitDirection2[0]);
			final FastBlockPos p3 = node.getPosCopy().add(direction).add(splitDirection2[1]);
			if (!ActionUtils.canJump(p2, p3)) {
				return Result.invalid();
			}

			return Result.valid(Direction.NONE, NodeCache.get(to), ActionCosts.get().JUMP_DIAGONAL);
		}


	}






	private static abstract class AbstractJumpActionFactory extends JumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, getDirection());
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, getDirection(), result);
		}




		@Override
		public Class<ActionJump> producesAction() {
			return ActionJump.class;
		}


	}






	public static class JumpFactoryNorth extends AbstractJumpActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.NORTH;
		}

	}






	public static class JumpFactoryNorthEast extends AbstractJumpActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.NORTH_EAST;
		}

	}






	public static class JumpFactoryEast extends AbstractJumpActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.EAST;
		}

	}






	public static class JumpFactorySouthEast extends AbstractJumpActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.SOUTH_EAST;
		}

	}






	public static class JumpFactorySouth extends AbstractJumpActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.SOUTH;
		}

	}






	public static class JumpFactorySouthWest extends AbstractJumpActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.SOUTH_WEST;
		}

	}






	public static class JumpFactoryWest extends AbstractJumpActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.WEST;
		}

	}






	public static class JumpFactoryNorthWest extends AbstractJumpActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.WEST;
		}

	}

}
