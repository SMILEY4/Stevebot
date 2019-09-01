package stevebot.pathfinding.actions;

import net.minecraft.util.math.BlockPos;
import stevebot.Direction;
import stevebot.Stevebot;
import stevebot.pathfinding.BlockUtils;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;

public class ActionStepDownStraight extends Action {


	public static ActionStepDownStraight createValid(Node node, Direction direction) {

		final BlockPos from = node.pos;
		if (!BlockUtils.canWalkOn(from.add(0, -1, 0))) {
			return null;
		}

		final BlockPos to = node.pos.add(direction.dx, -1, direction.dz);
		if (ActionUtils.canStandAt(to, 3)) {
			return new ActionStepDownStraight(node, to);
		} else {
			return null;
		}
	}




	private final Node from;
	private final Node to;
	private final double cost;




	public ActionStepDownStraight(Node from, BlockPos to) {
		this.from = from;
		this.to = Node.get(to);
		this.cost = ActionCosts.COST_STEP_DOWN;
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
	public PathExecutor.State tick(boolean firstTick) {
		if (Stevebot.get().getPlayerController().getMovement().moveTowards(to.pos, true)) {
			return PathExecutor.State.DONE;
		} else {
			return PathExecutor.State.EXEC;
		}
	}


}
