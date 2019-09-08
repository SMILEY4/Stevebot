package stevebot.pathfinding.actions.playeractions;

import modtools.player.MTPlayerController;
import net.minecraft.util.math.BlockPos;
import stevebot.Stevebot;
import stevebot.pathfinding.BlockUtils;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;
import stevebot.pathfinding.actions.Action;
import stevebot.pathfinding.actions.ActionCosts;

public class ActionFall extends Action {


	public static ActionFall createValid(Node node) {

		final BlockPos from = node.pos;

		int height = 0;
		BlockPos fallTo = from.add(0, -1, 0);
		while (BlockUtils.canWalkThrough(fallTo)) {
			fallTo = fallTo.add(0, -1, 0);
			height++;
			if(height > 300) {
				return null;
			}
		}
		fallTo = fallTo.add(0, 1, 0);

		final BlockPos d0 = fallTo.add(0, -1, 0);
		if (height <= 0 || !BlockUtils.canWalkOn(d0) || BlockUtils.isDangerous(d0)) {
			return null;
		}

		return new ActionFall(node, fallTo);
	}




	private ActionFall(Node from, BlockPos to) {
		super(from, Node.get(to), ActionCosts.COST_FALL_N(from.pos.getY() - to.getY()));
	}




	@Override
	public PathExecutor.State tick(boolean fistTick) {
		final MTPlayerController controller = Stevebot.get().getPlayerController();
		if (controller.getPlayer().onGround) {
			if (controller.getMovement().moveTowards(getTo().pos, false)) {
				return PathExecutor.State.DONE;
			} else {
				return PathExecutor.State.EXEC;
			}
		} else {
			return PathExecutor.State.EXEC;
		}
	}

}
