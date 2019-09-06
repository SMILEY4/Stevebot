package stevebot.pathfinding.actions;

import net.minecraft.util.math.BlockPos;
import stevebot.Direction;
import stevebot.Stevebot;
import stevebot.pathfinding.BlockUtils;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;

public class ActionWalkStraight extends Action {


	public static ActionWalkStraight createValid(Node node, Direction direction) {

		final BlockPos from = node.pos;
		if (!BlockUtils.canWalkOn(from.add(0, -1, 0))) {
			return null;
		}

		final BlockPos to = node.pos.add(direction.dx, 0, direction.dz);
		if (ActionUtils.canStandAt(to)) {
			return new ActionWalkStraight(node, to, true);
		} else {
			return null;
		}

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


