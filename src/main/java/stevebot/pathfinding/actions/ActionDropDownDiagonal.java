package stevebot.pathfinding.actions;

import modtools.player.MTPlayerController;
import net.minecraft.util.math.BlockPos;
import stevebot.Direction;
import stevebot.Stevebot;
import stevebot.pathfinding.BlockUtils;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;

public class ActionDropDownDiagonal extends Action {


	@SuppressWarnings ("Duplicates")
	public static ActionDropDownDiagonal createValid(Node node, Direction direction) {

		final BlockPos from = node.pos;
		if (!BlockUtils.canWalkOn(from.add(0, -1, 0))) {
			return null;
		}

		final BlockPos to = node.pos.add(direction.dx, -1, direction.dz);
		final BlockPos toUp = to.add(0, 1, 0);
		if (!BlockUtils.canWalkThrough(to) || !BlockUtils.canWalkThrough(toUp)) { // can not move into dest blocks
			return null;
		}

		Direction[] splitDirection = direction.split();
		final BlockPos p0 = node.pos.add(splitDirection[0].dx, 0, splitDirection[0].dz);
		final BlockPos p1 = node.pos.add(splitDirection[1].dx, 0, splitDirection[1].dz);

		boolean traversable0 = BlockUtils.canWalkThrough(p0) && BlockUtils.canWalkThrough(p0.add(0, 1, 0));
		boolean traversable1 = BlockUtils.canWalkThrough(p1) && BlockUtils.canWalkThrough(p1.add(0, 1, 0));

		if (!traversable0 || !traversable1) {
			return null;
		}

		final ActionFall fall = ActionFall.createValid(Node.get(to));
		if (fall == null) {
			return null;
		} else {
			return new ActionDropDownDiagonal(node, fall, direction);
		}
	}




	private final Node from;
	private final Node to;
	private final double cost;
	private final ActionFall fall;
	private final Direction direction;




	public ActionDropDownDiagonal(Node from, ActionFall fall, Direction direction) {
		this.from = from;
		this.fall = fall;
		this.direction = direction;
		this.to = fall.getTo();
		this.cost = ActionCosts.COST_WALKING * ActionCosts.COST_MULT_DIAGONAL + fall.getCost();
	}




	@Override
	public double getCost() {
		return this.cost;
	}




	@Override
	public Node getFrom() {
		return this.from;
	}




	@Override
	public Node getTo() {
		return this.to;
	}




	private int currentState = 0;
	private final int PREPARE_1 = 0;
	private final int PREPARE_2 = 1;
	private final int FALL = 2;
	private final int FINISH = 3;




	@Override
	public void resetAction() {
		fall.resetAction();
		currentState = PREPARE_1;
	}




	@Override
	public PathExecutor.State tick(boolean firstTick) {
		final MTPlayerController controller = Stevebot.get().getPlayerController();


		if (currentState == PREPARE_1) { // prepare fall
			final double distToEdge = BlockUtils.distToEdge(controller.getPlayerPosition(), direction);
			if (distToEdge <= 0.4) {
				currentState = PREPARE_2;
			} else {
				controller.getMovement().moveTowards(to.pos, true);
			}
			return PathExecutor.State.EXEC;


		} else if (currentState == PREPARE_2) { // slide/decelerate towards fall
			if (controller.getPlayer().onGround && !controller.isPlayerMoving(0.0001, false)) {
				controller.getMovement().moveTowards(to.pos, true);
			}
			if (!controller.getPlayer().onGround) {
				currentState = FALL;
			}
			return PathExecutor.State.EXEC;


		} else if (currentState == FALL) { // falling
			if (controller.getPlayer().onGround) {
				currentState = FINISH;
			}
			return PathExecutor.State.EXEC;


		} else if (currentState == FINISH) { // landed, walk to center
			if (controller.getMovement().moveTowards(to.pos, true)) {
				return PathExecutor.State.DONE;
			} else {
				return PathExecutor.State.EXEC;
			}


		} else {
			return PathExecutor.State.EXEC;
		}

	}

}
