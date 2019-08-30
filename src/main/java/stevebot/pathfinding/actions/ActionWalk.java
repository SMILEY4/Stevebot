package stevebot.pathfinding.actions;

import net.minecraft.util.math.BlockPos;
import stevebot.Direction;
import stevebot.Stevebot;
import stevebot.pathfinding.BlockUtils;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;

public class ActionWalk extends Action {


	public static ActionWalk createValid(Node node, Direction direction) {
		final BlockPos from = node.pos;

		final BlockPos s0 = from.add(0, -1, 0);
		if (!BlockUtils.canWalkOn(s0)) {
			return null;
		}

		if (direction.diagonal) {
			return createValidDiagonal(node, direction);
		} else {
			return createValidStraight(node, direction);
		}

	}




	private static ActionWalk createValidStraight(Node node, Direction direction) {
		final BlockPos to = node.pos.add(direction.dx, 0, direction.dz);

		if (ActionUtils.canStandAt(to)) {
			return new ActionWalk(node, to);

		} else {
			return null;
		}

	}




	private static ActionWalk createValidDiagonal(Node node, Direction direction) {
		final BlockPos to = node.pos.add(direction.dx, 0, direction.dz);

		Direction[] splitDirection = direction.split();
		final BlockPos p0 = node.pos.add(splitDirection[0].dx, 0, splitDirection[0].dz);
		final BlockPos p1 = node.pos.add(splitDirection[1].dx, 0, splitDirection[1].dz);

		boolean traversable0 = ActionUtils.canStandAt(p0);
		boolean traversable1 = ActionUtils.canStandAt(p1);

		boolean avoid0 = BlockUtils.avoidTouching(p0) || BlockUtils.avoidTouching(p0.add(0, 1, 0));
		boolean avoid1 = BlockUtils.avoidTouching(p1) || BlockUtils.avoidTouching(p1.add(0, 1, 0));

		if (ActionUtils.canStandAt(to) && (traversable0 || traversable1)) {
			if ((traversable0 && avoid1) || (traversable1 && avoid0)) {
				return null;
			}
			return new ActionWalk(node, to, !traversable0 || !traversable1);
		} else {
			return null;
		}
	}




	private final Node from;
	private final Node to;
	private final double cost;




	public ActionWalk(Node from, int dx, int dz) {
		this(from, from.pos.add(dx, 0, dz));
	}




	public ActionWalk(Node from, BlockPos to) {
		this(from, to, false);
	}




	public ActionWalk(Node from, BlockPos to, boolean touchesBlocks) {
		this.from = from;
		this.to = Node.get(to);
		this.cost = ActionCosts.COST_WALKING
				* (Action.isDiagonal(from.pos, to) ? ActionCosts.COST_MULT_DIAGONAL : 1)
				* (touchesBlocks ? ActionCosts.COST_MULT_TOUCHING : 1);
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
	public PathExecutor.State tick(boolean fistTick) {
		if (Stevebot.get().getPlayerController().getMovement().moveTowards(to.pos, true)) {
			return PathExecutor.State.DONE;
		} else {
			return PathExecutor.State.EXEC;
		}
	}


}


