package stevebot.pathfinding.actions.playeractions;

import stevebot.data.blocks.BlockUtils;
import stevebot.misc.Direction;
import stevebot.misc.StateMachine;
import stevebot.Stevebot;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blockpos.FastBlockPos;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionUtils;
import stevebot.pathfinding.execution.PathExecutor;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.nodes.NodeCache;
import stevebot.player.PlayerController;

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
	public PathExecutor.StateFollow tick(boolean firstTick) {

		PlayerController controller = Stevebot.get().getPlayerController();

		switch (stateMachine.getState()) {
			case PREPARING: {
				controller.camera().setLookAt(getTo().getPos().getX(), getTo().getPos().getY(), getTo().getPos().getZ(), true);
				boolean slowEnough = controller.movement().slowDown(0.055);
				if (slowEnough) {
					stateMachine.fireTransition(Transition.PREPARATION_DONE);
				}
				return PathExecutor.StateFollow.EXEC;
			}

			case JUMPING: {
				controller.movement().moveTowards(getTo().getPos(), true);
				final double distToEdge = BlockUtils.distToCenter(controller.utils().getPlayerPosition());
				if (distToEdge > 0.4) {
					controller.input().setJump(false);
				}
				if (controller.getPlayer().onGround && controller.utils().getPlayerBlockPos().equals(getTo().getPos())) {
					stateMachine.fireTransition(Transition.TOUCHED_GROUND);
				}
				return PathExecutor.StateFollow.EXEC;
			}

			case LANDING: {
				if (controller.movement().moveTowards(getTo().getPos(), true)) {
					return PathExecutor.StateFollow.DONE;
				} else {
					return PathExecutor.StateFollow.EXEC;
				}
			}

			default: {
				return PathExecutor.StateFollow.FAILED;
			}

		}

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

			return Result.valid(Direction.NONE, NodeCache.get(to), ActionCosts.COST_WALK_JUMP);
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
			if (!ActionUtils.canJumpThrough(from.add(direction.dx, 0, direction.dz))) {
				return Result.invalid();
			}

			// check diagonal
			Direction[] splitDirection = direction.split();
			final FastBlockPos p0 = node.getPosCopy().add(splitDirection[0].dx, 0, splitDirection[0].dz);
			final FastBlockPos p1 = node.getPosCopy().add(splitDirection[1].dx, 0, splitDirection[1].dz);
			if (!ActionUtils.canJump(p0, p1)) {
				return Result.invalid();
			}

			return Result.valid(Direction.NONE, NodeCache.get(to), ActionCosts.COST_WALK_JUMP * ActionCosts.COST_MULT_DIAGONAL);
		}


	}






	public static class JumpFactoryNorth extends ActionJump.JumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.NORTH, result);
		}

	}






	public static class JumpFactoryNorthEast extends ActionJump.JumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH_EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.NORTH_EAST, result);
		}

	}






	public static class JumpFactoryEast extends ActionJump.JumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.EAST, result);
		}

	}






	public static class JumpFactorySouthEast extends ActionJump.JumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH_EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH_EAST, result);
		}

	}






	public static class JumpFactorySouth extends ActionJump.JumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH, result);
		}

	}






	public static class JumpFactorySouthWest extends ActionJump.JumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH_WEST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH_WEST, result);
		}

	}






	public static class JumpFactoryWest extends ActionJump.JumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.WEST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.WEST, result);
		}

	}






	public static class JumpFactoryNorthWest extends ActionJump.JumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH_WEST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.NORTH_WEST, result);
		}

	}

}
