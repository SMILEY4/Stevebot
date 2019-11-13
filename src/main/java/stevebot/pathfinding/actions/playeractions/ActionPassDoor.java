package stevebot.pathfinding.actions.playeractions;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blocks.BlockUtils;
import stevebot.misc.Direction;
import stevebot.misc.ProcState;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionUtils;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.nodes.NodeCache;
import stevebot.player.PlayerUtils;

public class ActionPassDoor extends Action {


	private final BaseBlockPos positionDoorBottom;
	private final BaseBlockPos positionDoorTop;
	private final Direction direction;

	private boolean hasToOpenDoorTop = false;
	private boolean hasToOpenDoorBottom = false;




	private ActionPassDoor(Node from, Node to, BaseBlockPos positionDoor, Direction direction, double cost) {
		super(from, to, cost);
		this.positionDoorBottom = positionDoor;
		this.positionDoorTop = positionDoor.copyAsFastBlockPos().add(0, 1, 0);
		this.direction = direction;
	}




	@Override
	public String getActionName() {
		return "pass-door";
	}




	@Override
	public void resetAction() {
		hasToOpenDoorTop = false;
		hasToOpenDoorBottom = false;
	}




	@Override
	public ProcState tick(boolean fistTick) {

		if (fistTick) {
			// check, which door-blocks the player has to open
			hasToOpenDoorBottom = !BlockUtils.canPassDoor(positionDoorBottom, direction);
			if (BlockUtils.isDoor(positionDoorBottom)) {
				hasToOpenDoorTop = true;
			} else {
				hasToOpenDoorTop = !BlockUtils.canPassDoor(positionDoorTop, direction);
			}
		}

		// enable/disable force camera
		if ((hasToOpenDoorTop || hasToOpenDoorBottom) && !PlayerUtils.getCamera().isForceEnabled()) {
			PlayerUtils.getCamera().enableForceCamera();
		}
		if ((!hasToOpenDoorTop && !hasToOpenDoorBottom) && PlayerUtils.getCamera().isForceEnabled()) {
			PlayerUtils.getCamera().disableForceCamera(true);
		}

		if (PlayerUtils.getMovement().moveTowards(getTo().getPos(), true)) {
			return ProcState.DONE;
		} else {

			if (hasToOpenDoorBottom) {
				PlayerUtils.getCamera().setLookAtBlockSide(positionDoorBottom, direction);
				PlayerUtils.getInput().setPlaceBlock();
				hasToOpenDoorBottom = false;

			} else if (hasToOpenDoorTop) {
				PlayerUtils.getCamera().setLookAtBlockSide(positionDoorTop, direction);
				PlayerUtils.getInput().setPlaceBlock();
				hasToOpenDoorTop = false;
			}
			return ProcState.EXECUTING;
		}
	}




	private static abstract class WalkActionFactory implements ActionFactory {


		ActionPassDoor create(Node node, Direction direction, Result result) {
			return new ActionPassDoor(node, result.to,
					node.getPosCopy().add(direction.dx, 0, direction.dz), direction, result.estimatedCost);
		}




		Result check(Node node, Direction direction) {
			return checkStraight(node, direction);
		}




		Result checkStraight(Node node, Direction direction) {

			// check to-position
			final BaseBlockPos to = node.getPosCopy().add(direction.dx * 2, 0, direction.dz * 2);
			if (!BlockUtils.isLoaded(to)) {
				return Result.unloaded();
			}
			if (!ActionUtils.canStandAt(to)) {
				return Result.invalid();
			}

			// check door position
			if (!ActionUtils.isDoorPassable(node.getPosCopy().add(direction.dx, 0, direction.dz))) {
				return Result.invalid();
			}

			// check from-position
			if (!ActionUtils.canStandAt(node.getPos())) {
				return Result.invalid();
			}

			return Result.valid(direction, NodeCache.get(to), ActionCosts.COST_WALKING * 2);
		}


	}






	public static class PassDoorFactoryNorth extends WalkActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.NORTH);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.NORTH, result);
		}

	}






	public static class PassDoorFactoryEast extends WalkActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.EAST);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.EAST, result);
		}

	}






	public static class PassDoorFactorySouth extends WalkActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, Direction.SOUTH);
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, Direction.SOUTH, result);
		}

	}






	public static class PassDoorFactoryWest extends WalkActionFactory {


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


