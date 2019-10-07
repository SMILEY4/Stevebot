package stevebot.pathfinding.actions.playeractions;

import stevebot.data.blocks.BlockUtils;
import stevebot.misc.Direction;
import stevebot.misc.StateMachine;
import stevebot.Stevebot;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionUtils;
import stevebot.pathfinding.execution.PathExecutor;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.nodes.NodeCache;
import stevebot.player.PlayerController;

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
				controller.input().setSprint();
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




	private static abstract class SprintJumpActionFactory implements ActionFactory {


		ActionSprintJump create(Node node, Direction direction, Result result) {
			// final Result result = check(node, direction);
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
