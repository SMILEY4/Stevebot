package stevebot.pathfinding.actions.playeractions;

import stevebot.BlockUtils;
import stevebot.Stevebot;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blockpos.FastBlockPos;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.execution.PathExecutor;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.nodes.NodeCache;
import stevebot.player.PlayerController;

public class ActionFall extends Action {


	private ActionFall(Node from, Node to, double cost) {
		super(from, to, cost);
	}




	@Override
	public PathExecutor.StateFollow tick(boolean fistTick) {
		final PlayerController controller = Stevebot.get().getPlayerController();
		if (controller.getPlayer().onGround) {
			if (controller.movement().moveTowards(getTo().getPos(), false)) {
				return PathExecutor.StateFollow.DONE;
			} else {
				return PathExecutor.StateFollow.EXEC;
			}
		} else {
			return PathExecutor.StateFollow.EXEC;
		}
	}




	public static class FallActionFactory implements ActionFactory {


		@Override
		public Result check(Node node) {

			final BaseBlockPos from = node.getPos();

			int height = 0;
			FastBlockPos fallTo = from.copyAsFastBlockPos().add(0, -1, 0);
			while (BlockUtils.canWalkThrough(fallTo)) {
				fallTo = fallTo.add(0, -1, 0);
				height++;
				if (height > 300) {
					return Result.invalid();
				}
			}
			fallTo = fallTo.add(0, 1, 0);

			final FastBlockPos d0 = fallTo.copy().add(0, -1, 0);
			if (height <= 0 || !BlockUtils.canWalkOn(d0) || BlockUtils.isDangerous(d0)) {
				return Result.invalid();
			}

			return Result.valid(NodeCache.get(fallTo), ActionCosts.COST_FALL_N(from.getY() - fallTo.getY()));
		}




		@Override
		public Action createAction(Node node, Result result) {
			// final Result result = check(node);
			return new ActionFall(node, result.to, result.estimatedCost);
		}

	}

}
