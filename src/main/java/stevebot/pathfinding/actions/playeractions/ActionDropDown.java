package stevebot.pathfinding.actions.playeractions;

import stevebot.BlockUtils;
import stevebot.Direction;
import stevebot.Stevebot;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionUtils;
import stevebot.pathfinding.execution.PathExecutor;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.nodes.NodeCache;
import stevebot.player.PlayerController;

public class ActionDropDown extends StatefulAction {


	private static final String STATE_PREPARE_1 = "PREPARE 1";
	private static final String STATE_PREPARE_2 = "PREPARE 2";
	private static final String STATE_FALL = "FALL";
	private static final String STATE_FINISH = "FINISH";

	private final ActionFall fall;
	private final Direction direction;




	private ActionDropDown(Node from, Node to, double cost, ActionFall fall, Direction direction) {
		super(from, to, cost, STATE_PREPARE_1, STATE_PREPARE_2, STATE_FALL, STATE_FINISH);
		this.fall = fall;
		this.direction = direction;
	}




	@Override
	public PathExecutor.StateFollow tick(boolean firstTick) {
		final PlayerController controller = Stevebot.get().getPlayerController();


		switch (getCurrentState()) {

			case STATE_PREPARE_1: {
				final double distToEdge = BlockUtils.distToEdge(controller.utils().getPlayerPosition(), direction);
				if (distToEdge <= 0.4) {
					nextState();
				} else {
					controller.movement().moveTowards(getTo().getPos(), true);
				}
				return PathExecutor.StateFollow.EXEC;
			}

			case STATE_PREPARE_2: {
				if (controller.getPlayer().onGround && !controller.utils().isPlayerMoving(0.0001, false)) {
					controller.movement().moveTowards(getTo().getPos(), true);
				}
				if (!controller.getPlayer().onGround) {
					nextState();
				}
				return PathExecutor.StateFollow.EXEC;
			}

			case STATE_FALL: {
				if (controller.getPlayer().onGround) {
					nextState();
				}
				return PathExecutor.StateFollow.EXEC;
			}

			case STATE_FINISH: {
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




	private static abstract class DropDownActionFactory implements ActionFactory {


		private final ActionFall.FallActionFactory fallActionFactory = new ActionFall.FallActionFactory();




		ActionDropDown create(Node node, Direction direction, Result result) {
			final Node nodeFall = NodeCache.get(node.getPosCopy().add(direction.dx, 0, direction.dz));
			final ActionFall actionFall = (ActionFall) fallActionFactory.createAction(nodeFall, fallActionFactory.check(nodeFall));
			return new ActionDropDown(node, result.to, result.estimatedCost, actionFall, direction);

		}




		Result check(Node node, Direction direction) {
			if (direction.diagonal) {
				return checkDiagonal(node, direction);
			} else {
				return checkStraight(node, direction);
			}
		}




		Result checkStraight(Node node, Direction direction) {

			// check to-position horizontal
			final BaseBlockPos to = node.getPosCopy().add(direction.dx, 0, direction.dz);
			if (!BlockUtils.isLoaded(to)) {
				return Result.unloaded();
			}
			if (!ActionUtils.canMoveThrough(to)) {
				return Result.invalid();
			}

			// check from-position
			if (!ActionUtils.canStandAt(node.getPos())) {
				return Result.invalid();
			}

			// check fall
			final Node nodeFall = NodeCache.get(node.getPosCopy().add(direction.dx, 0, direction.dz));
			final ActionFall actionFall = (ActionFall) fallActionFactory.createAction(nodeFall, fallActionFactory.check(nodeFall));
			if (actionFall == null) {
				return Result.invalid();
			}

			return Result.valid(NodeCache.get(to), ActionCosts.COST_WALKING + actionFall.getCost());
		}




		Result checkDiagonal(Node node, Direction direction) {

			// check to-position horizontal
			final BaseBlockPos to = node.getPosCopy().add(direction.dx, -1, direction.dz);
			if (!BlockUtils.isLoaded(to)) {
				return Result.unloaded();
			}
			if (!ActionUtils.canMoveThrough(to)) {
				return Result.invalid();
			}

			// check from-position
			if (!ActionUtils.canStandAt(node.getPos())) {
				return Result.invalid();
			}

			// check diagonal blocks
			Direction[] splitDirection = direction.split();
			final BaseBlockPos p0 = node.getPosCopy().add(splitDirection[0].dx, 0, splitDirection[0].dz);
			final BaseBlockPos p1 = node.getPosCopy().add(splitDirection[1].dx, 0, splitDirection[1].dz);
			if (!ActionUtils.canMoveThroughAll(p0, p1)) {
				return Result.invalid();
			}

			// check+create fall
			final Node nodeFall = NodeCache.get(node.getPosCopy().add(direction.dx, 0, direction.dz));
			final ActionFall actionFall = (ActionFall) fallActionFactory.createAction(nodeFall, fallActionFactory.check(nodeFall));
			if (actionFall == null) {
				return Result.invalid();
			}

			return Result.valid(NodeCache.get(to), ActionCosts.COST_WALKING * ActionCosts.COST_MULT_DIAGONAL + actionFall.getCost());
		}


	}






	public static class DropDownFactoryNorth extends ActionDropDown.DropDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.NORTH, result);
		}

	}






	public static class DropDownFactoryNorthEast extends ActionDropDown.DropDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH_EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.NORTH_EAST, result);
		}

	}






	public static class DropDownFactoryEast extends ActionDropDown.DropDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.EAST, result);
		}

	}






	public static class DropDownFactorySouthEast extends ActionDropDown.DropDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH_EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH_EAST, result);
		}

	}






	public static class DropDownFactorySouth extends ActionDropDown.DropDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH, result);
		}

	}






	public static class DropDownFactorySouthWest extends ActionDropDown.DropDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH_WEST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH_WEST, result);
		}

	}






	public static class DropDownFactoryWest extends ActionDropDown.DropDownActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.WEST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.WEST, result);
		}

	}






	public static class DropDownFactoryNorthWest extends ActionDropDown.DropDownActionFactory {


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
