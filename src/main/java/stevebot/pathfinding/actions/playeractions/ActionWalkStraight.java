package stevebot.pathfinding.actions.playeractions;

import net.minecraft.util.math.BlockPos;
import stevebot.Direction;
import stevebot.Stevebot;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;
import stevebot.pathfinding.actions.Action;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionUtils;

public class ActionWalkStraight extends Action {


	public static ActionWalkStraight createValid(Node node, Direction direction) {

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
		return new ActionWalkStraight(node, to, true);

	}




	private final boolean sprint;




	private ActionWalkStraight(Node from, BlockPos to, boolean sprint) {
		super(from, Node.get(to), sprint ? ActionCosts.COST_SPRINTING : ActionCosts.COST_WALKING);
		this.sprint = sprint;
	}




	@Override
	public PathExecutor.State tick(boolean fistTick) {
		if (Stevebot.get().getPlayerController().getMovement().moveTowards(getTo().pos, true)) {
			return PathExecutor.State.DONE;
		} else {
			if (sprint) {
				Stevebot.get().getPlayerController().setSprint();
			}
			return PathExecutor.State.EXEC;
		}
	}


}


