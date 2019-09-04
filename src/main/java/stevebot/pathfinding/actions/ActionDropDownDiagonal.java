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
			return new ActionDropDownDiagonal(node, fall);
		}
	}




	private final Node from;
	private final Node to;
	private final double cost;
	private final ActionFall fall;

	private int state = 0;




	public ActionDropDownDiagonal(Node from, ActionFall fall) {
		this.from = from;
		this.fall = fall;
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




	@Override
	public void resetAction() {
		fall.resetAction();
		state = 0;
	}




	@Override
	public PathExecutor.State tick(boolean firstTick) {
		final MTPlayerController controller = Stevebot.get().getPlayerController();


		if (state == 0) { // 0 = prepare fall
			final double distToDrop = controller.getPlayerPosition().dist(getTo().pos.getX() + 0.5, controller.getPlayerPosition().y, getTo().pos.getZ() + 0.5);
			final double threshold = isDiagonal(from.pos, to.pos) ? Math.sqrt(0.38) : (0.38);
			if (distToDrop <= threshold) {
				state = 1;
			} else {
				controller.getMovement().moveTowards(to.pos, false);
			}
			return PathExecutor.State.EXEC;


		} else if (state == 1) { // 1 = slide/decelerate towards fall
			if (controller.getPlayer().onGround && controller.isPlayerMoving()) {
				controller.getMovement().moveTowards(to.pos, true);
			}
			if (!controller.getPlayer().onGround) {
				state = 2;
			}
			return PathExecutor.State.EXEC;


		} else if (state == 2) { // 2 = falling
			return fall.tick(false);
		}

		return PathExecutor.State.EXEC; // should never reach this
	}


}
