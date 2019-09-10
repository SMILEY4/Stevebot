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

public class ActionJump extends StatefulAction {


	private static final String STATE_PREPARE = "PREPARE";
	private static final String STATE_JUMP = "JUMP";
	private static final String STATE_LAND = "LAND";




	private ActionJump(Node from, Node to, double cost) {
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




	private static abstract class JumpActionFactory implements ActionFactory {


		ActionJump create(Node node, Direction direction) {
			final Result result = direction.diagonal ? checkDiagonal(node, direction) : checkStraight(node, direction);
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

			// check from-position
			final BlockPos from = node.pos;
			if (!ActionUtils.canJumpAt(from)) {
				return Result.invalid();
			}

			// check to-position
			final BlockPos to = node.pos.add(direction.dx * 2, 0, direction.dz * 2);
			if (!ActionUtils.canJumpAt(to)) {
				return Result.invalid();
			}

			// check gap
			if (!ActionUtils.canJumpThrough(from.add(direction.dx, 0, direction.dy))) {
				return Result.invalid();
			}

			return Result.valid(Node.get(to), ActionCosts.COST_WALK_JUMP);
		}




		Result checkDiagonal(Node node, Direction direction) {


			// check from-position
			final BlockPos from = node.pos;
			if (!ActionUtils.canJumpAt(from)) {
				return Result.invalid();
			}

			// check to-position
			final BlockPos to = node.pos.add(direction.dx * 2, 0, direction.dz * 2);
			if (!ActionUtils.canJumpAt(to)) {
				return Result.invalid();
			}

			// check gap
			if (!ActionUtils.canJumpThrough(from.add(direction.dx, 0, direction.dz))) {
				return Result.invalid();
			}

			// check diagonal
			Direction[] splitDirection = direction.split();
			final BlockPos p0 = node.pos.add(splitDirection[0].dx, 1, splitDirection[0].dz);
			final BlockPos p1 = node.pos.add(splitDirection[1].dx, 1, splitDirection[1].dz);
			if (!ActionUtils.canJump(p0, p1)) {
				return Result.invalid();
			}

			return Result.valid(Node.get(to), ActionCosts.COST_WALK_JUMP * ActionCosts.COST_MULT_DIAGONAL);
		}


	}






	public static class JumpFactoryNorth extends ActionJump.JumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.NORTH);
		}

	}






	public static class JumpFactoryNorthEast extends ActionJump.JumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH_EAST);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.NORTH_EAST);
		}

	}






	public static class JumpFactoryEast extends ActionJump.JumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.EAST);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.EAST);
		}

	}






	public static class JumpFactorySouthEast extends ActionJump.JumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH_EAST);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.SOUTH_EAST);
		}

	}






	public static class JumpFactorySouth extends ActionJump.JumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.SOUTH);
		}

	}






	public static class JumpFactorySouthWest extends ActionJump.JumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH_WEST);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.SOUTH_WEST);
		}

	}






	public static class JumpFactoryWest extends ActionJump.JumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.WEST);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.WEST);
		}

	}






	public static class JumpFactoryNorthWest extends ActionJump.JumpActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH_WEST);
		}




		@Override
		public Action createAction(Node node) {
			return create(node, Direction.NORTH_WEST);
		}

	}

}
