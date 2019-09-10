package stevebot.pathfinding.actions.playeractions;

import net.minecraft.util.math.BlockPos;
import stevebot.Direction;
import stevebot.Stevebot;
import stevebot.pathfinding.BlockUtils;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionUtils;
import stevebot.player.PlayerController;

public class ActionSprintJump extends StatefulAction {


	private static final String STATE_PREPARE = "PREPARE";
	private static final String STATE_JUMP = "JUMP";
	private static final String STATE_LAND = "LAND";




	private ActionSprintJump(Node from, Node to, double cost) {
		super(from, to, cost, STATE_PREPARE, STATE_JUMP, STATE_LAND);
	}




	@Override
	public PathExecutor.State tick(boolean firstTick) {

		PlayerController controller = Stevebot.get().getPlayerController();

		switch (getCurrentState()) {
			case STATE_PREPARE: {
				controller.camera().setLookAt(getTo().pos, true);
				boolean slowEnough = controller.movement().slowDown(0.055);
				if (slowEnough) {
					nextState();
				}
				return PathExecutor.State.EXEC;
			}

			case STATE_JUMP: {
				controller.movement().moveTowards(getTo().pos, true);
				controller.input().setSprint();
				final double distToEdge = BlockUtils.distToCenter(controller.utils().getPlayerPosition());
				if (distToEdge > 0.4) {
					controller.input().setJump(false);
				}
				if (controller.getPlayer().onGround && controller.utils().getPlayerBlockPos().equals(getTo().pos)) {
					nextState();
				}
				return PathExecutor.State.EXEC;
			}


			case STATE_LAND: {
				if (controller.movement().moveTowards(getTo().pos, true)) {
					return PathExecutor.State.DONE;
				} else {
					return PathExecutor.State.EXEC;
				}
			}

			default: {
				return PathExecutor.State.FAILED;
			}

		}

	}




	private static abstract class SprintJumpActionFactory implements ActionFactory {


		ActionSprintJump create(Node node, Direction direction) {
			final Result result = check(node, direction);
			return new ActionSprintJump(node, result.to, result.estimatedCost);
		}




		Result check(Node node, Direction direction) {

			if (direction.diagonal) {
				return Result.invalid();
			}

			// check from-position
			final BlockPos from = node.pos;
			if (!ActionUtils.canJumpAt(from)) {
				return Result.invalid();
			}

			// check to-position
			final BlockPos to = from.add(direction.dx * 4, 0, direction.dz * 4);
			if (!ActionUtils.canJumpAt(to)) {
				return Result.invalid();
			}

			// check gap
			for (int i = 0; i < 3; i++) {
				final BlockPos gap = from.add(direction.dx * (i + 1), 0, direction.dz * (i + 1));
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

			return Result.valid(Node.get(to), ActionCosts.COST_SPRINT_JUMP);
		}


	}






	public static class SprintJumpFactoryNorth extends ActionSprintJump.SprintJumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.NORTH);
		}

	}






	public static class SprintJumpFactoryEast extends ActionSprintJump.SprintJumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.EAST);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.EAST);
		}

	}






	public static class SprintJumpFactorySouth extends ActionSprintJump.SprintJumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.SOUTH);
		}

	}






	public static class SprintJumpFactoryWest extends ActionSprintJump.SprintJumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.WEST);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.WEST);
		}

	}


}
