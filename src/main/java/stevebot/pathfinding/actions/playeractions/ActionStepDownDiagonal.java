package stevebot.pathfinding.actions.playeractions;

import net.minecraft.util.math.BlockPos;
import stevebot.Direction;
import stevebot.Stevebot;
import stevebot.pathfinding.BlockUtils;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;
import stevebot.pathfinding.actions.Action;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionUtils;

public class ActionStepDownDiagonal extends Action {


	public static ActionStepDownDiagonal createValid(Node node, Direction direction) {

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

		// check diagonal
		Direction[] splitDirection = direction.split();
		final BlockPos p0 = node.pos.add(splitDirection[0].dx, 0, splitDirection[0].dz);
		final BlockPos p1 = node.pos.add(splitDirection[1].dx, 0, splitDirection[1].dz);

		boolean traversable0 = ActionUtils.canMoveThrough(p0);
		boolean traversable1 = ActionUtils.canMoveThrough(p0);

		boolean avoid0 = BlockUtils.avoidTouching(p0) || BlockUtils.avoidTouching(p0.add(0, 1, 0));
		boolean avoid1 = BlockUtils.avoidTouching(p1) || BlockUtils.avoidTouching(p1.add(0, 1, 0));

		if (ActionUtils.canStandAt(to) && (traversable0 || traversable1)) {
			if ((traversable0 && avoid1) || (traversable1 && avoid0)) {
				return null;
			}
			return new ActionStepDownDiagonal(node, to, !traversable0 || !traversable1);
		} else {
			return null;
		}

	}




	private ActionStepDownDiagonal(Node from, BlockPos to, boolean touchesBlocks) {
		super(from, Node.get(to), ActionCosts.COST_STEP_DOWN * ActionCosts.COST_MULT_DIAGONAL * (touchesBlocks ? ActionCosts.COST_MULT_TOUCHING : 1));
	}




	@Override
	public PathExecutor.State tick(boolean firstTick) {
		if (Stevebot.get().getPlayerController().movement().moveTowards(getTo().pos, true)) {
			return PathExecutor.State.DONE;
		} else {
			return PathExecutor.State.EXEC;
		}
	}


}
