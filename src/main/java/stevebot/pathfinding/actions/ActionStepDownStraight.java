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




	private ActionStepDownStraight(Node from, BlockPos to) {
		super(from, Node.get(to), ActionCosts.COST_STEP_DOWN);
	}




	@Override
	public PathExecutor.State tick(boolean firstTick) {
		if (Stevebot.get().getPlayerController().getMovement().moveTowards(getTo().pos, true)) {
			return PathExecutor.State.DONE;
		} else {
			return PathExecutor.State.EXEC;
		}
	}


}
