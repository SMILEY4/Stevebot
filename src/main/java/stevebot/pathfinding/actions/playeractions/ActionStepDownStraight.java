package stevebot.pathfinding.actions.playeractions;

import net.minecraft.util.math.BlockPos;
import stevebot.Direction;
import stevebot.Stevebot;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;
import stevebot.pathfinding.actions.Action;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionUtils;

public class ActionStepDownStraight extends Action {


	public static ActionStepDownStraight createValid(Node node, Direction direction) {

		// check from-position
		final BlockPos from = node.pos;
		if (!ActionUtils.canStandAt(from)) {
			return null;
		}

		// check to-position
		final BlockPos to = node.pos.add(direction.dx, -1, direction.dz);
		if (!ActionUtils.canStandAt(to, 3)) {
			return null;
		}

		return new ActionStepDownStraight(node, to);
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
