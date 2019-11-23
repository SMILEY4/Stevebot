package stevebot.pathfinding.actions.playeractions;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blocks.BlockUtils;
import stevebot.misc.Direction;
import stevebot.misc.ProcState;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionObserver;
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
		this.positionDoorTop = positionDoor.copyAsFastBlockPos().add(Direction.UP);
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
	public ProcState tick(boolean firstTick) {
		ActionObserver.tickAction(this.getActionName());

		if (firstTick) {
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




	@Override
	public boolean isOnPath(BaseBlockPos position) {
		if (position.equals(getFrom().getPos()) || position.equals(getTo().getPos())) {
			return true;
		} else {
			final BaseBlockPos posDoor = getFrom().getPosCopy().add(Direction.get(getFrom().getPos(), getTo().getPos()));
			return position.equals(posDoor);
		}
	}




	private static abstract class PassDoorActionFactory implements ActionFactory {


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

			return Result.valid(direction, NodeCache.get(to), ActionCosts.get().PASS_DOOR);
		}


	}






	private static abstract class AbstractPassDoorActionFactory extends PassDoorActionFactory {


		@Override
		public Result check(Node node) {
			return check(node, getDirection());
		}




		@Override
		public Action createAction(Node node, Result result) {
			return create(node, getDirection(), result);
		}




		@Override
		public Class<ActionPassDoor> producesAction() {
			return ActionPassDoor.class;
		}

	}






	public static class PassDoorFactoryNorth extends AbstractPassDoorActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.NORTH;
		}

	}






	public static class PassDoorFactoryEast extends AbstractPassDoorActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.EAST;
		}

	}






	public static class PassDoorFactorySouth extends AbstractPassDoorActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.SOUTH;
		}

	}






	public static class PassDoorFactoryWest extends AbstractPassDoorActionFactory {


		@Override
		public Direction getDirection() {
			return Direction.WEST;
		}

	}


}


