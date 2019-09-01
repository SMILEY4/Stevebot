package stevebot.pathfinding.actions;

import net.minecraft.util.math.BlockPos;
import stevebot.Direction;
import stevebot.Stevebot;
import stevebot.pathfinding.BlockUtils;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;

public class ActionWalkDiagonal extends Action {


	public static ActionWalkDiagonal createValid(Node node, Direction direction) {

		final BlockPos from = node.pos;
		if (!BlockUtils.canWalkOn(from.add(0, -1, 0))) {
			return null;
		}

		final BlockPos to = node.pos.add(direction.dx, 0, direction.dz);

		Direction[] splitDirection = direction.split();
		final BlockPos p0 = node.pos.add(splitDirection[0].dx, 0, splitDirection[0].dz);
		final BlockPos p1 = node.pos.add(splitDirection[1].dx, 0, splitDirection[1].dz);

		boolean traversable0 = BlockUtils.canWalkThrough(p0) && BlockUtils.canWalkThrough(p0.add(0, 1, 0));
		boolean traversable1 = BlockUtils.canWalkThrough(p1) && BlockUtils.canWalkThrough(p1.add(0, 1, 0));

		boolean avoid0 = BlockUtils.avoidTouching(p0) || BlockUtils.avoidTouching(p0.add(0, 1, 0));
		boolean avoid1 = BlockUtils.avoidTouching(p1) || BlockUtils.avoidTouching(p1.add(0, 1, 0));

		if (ActionUtils.canStandAt(to) && (traversable0 || traversable1)) {
			if ((traversable0 && avoid1) || (traversable1 && avoid0)) {
				return null;
			}
			return new ActionWalkDiagonal(node, to, !traversable0 || !traversable1);
		} else {
			return null;
		}

	}




	private final Node from;
	private final Node to;
	private final double cost;




	public ActionWalkDiagonal(Node from, BlockPos to, boolean touchesBlocks) {
		this.from = from;
		this.to = Node.get(to);
		this.cost = ActionCosts.COST_SPRINTING * ActionCosts.COST_MULT_DIAGONAL * (touchesBlocks ? ActionCosts.COST_MULT_TOUCHING : 1);
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
			Stevebot.get().getPlayerController().setSprint();
			return PathExecutor.State.EXEC;
		}
	}


}


