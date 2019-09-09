package stevebot.pathfinding.actions.playeractions;

import net.minecraft.util.math.BlockPos;
import stevebot.Stevebot;
import stevebot.pathfinding.BlockUtils;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.player.PlayerController;

public class ActionFall extends Action {


	private ActionFall(Node from, Node to, double cost) {
		super(from, to, cost);
	}




	@Override
	public PathExecutor.State tick(boolean fistTick) {
		final PlayerController controller = Stevebot.get().getPlayerController();
		if (controller.getPlayer().onGround) {
			if (controller.movement().moveTowards(getTo().pos, false)) {
				return PathExecutor.State.DONE;
			} else {
				return PathExecutor.State.EXEC;
			}
		} else {
			return PathExecutor.State.EXEC;
		}
	}




	public static class FallActionFactory implements ActionFactory {


		@Override
		public Result check(Node node) {

			final BlockPos from = node.pos;

			int height = 0;
			BlockPos fallTo = from.add(0, -1, 0);
			while (BlockUtils.canWalkThrough(fallTo)) {
				fallTo = fallTo.add(0, -1, 0);
				height++;
				if (height > 300) {
					return Result.invalid();
				}
			}
			fallTo = fallTo.add(0, 1, 0);

			final BlockPos d0 = fallTo.add(0, -1, 0);
			if (height <= 0 || !BlockUtils.canWalkOn(d0) || BlockUtils.isDangerous(d0)) {
				return Result.invalid();
			}

			return Result.valid(Node.get(fallTo), ActionCosts.COST_FALL_N(from.getY() - fallTo.getY()));
		}




		@Override
		public Action createAction(Node node) {
			final Result result = check(node);
			return new ActionFall(node, result.to, result.estimatedCost);
		}

	}

}
