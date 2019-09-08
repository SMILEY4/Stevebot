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

public class ActionWalkDiagonal extends Action {


	public static ActionWalkDiagonal createValid(Node node, Direction direction) {

		// check from-position
		final BlockPos from = node.pos;
		if (!ActionUtils.canStandAt(from)) {
			return null;
		}

		// check to-position
		final BlockPos to = node.pos.add(direction.dx, 0, direction.dz);
		if (!ActionUtils.canStandAt(to)) {
			return null;
		}

		// check diagonal
		Direction[] splitDirection = direction.split();
		final BlockPos p0 = node.pos.add(splitDirection[0].dx, 0, splitDirection[0].dz);
		final BlockPos p1 = node.pos.add(splitDirection[1].dx, 0, splitDirection[1].dz);

		boolean traversable0 = ActionUtils.canMoveThrough(p0);
		boolean traversable1 = ActionUtils.canMoveThrough(p1);

		boolean avoid0 = BlockUtils.avoidTouching(p0) || BlockUtils.avoidTouching(p0.add(0, 1, 0));
		boolean avoid1 = BlockUtils.avoidTouching(p1) || BlockUtils.avoidTouching(p1.add(0, 1, 0));

		if (traversable0 || traversable1) {
			if ((traversable0 && avoid1) || (traversable1 && avoid0)) {
				return null;
			}
			return new ActionWalkDiagonal(node, to, true, !traversable0 || !traversable1);
		} else {
			return null;
		}

	}




	private final boolean sprint;




	private ActionWalkDiagonal(Node from, BlockPos to, boolean sprint, boolean touchesBlocks) {
		super(from, Node.get(to), (sprint ? ActionCosts.COST_SPRINTING : ActionCosts.COST_WALKING) * ActionCosts.COST_MULT_DIAGONAL * (touchesBlocks ? ActionCosts.COST_MULT_TOUCHING : 1));
		this.sprint = sprint;
	}




	@Override
	public PathExecutor.State tick(boolean fistTick) {
		if (Stevebot.get().getPlayerController().movement().moveTowards(getTo().pos, true)) {
			return PathExecutor.State.DONE;
		} else {
			if (sprint) {
				Stevebot.get().getPlayerController().input().setSprint();
			}
			return PathExecutor.State.EXEC;
		}
	}


}


