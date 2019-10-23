package stevebot.pathfinding.actions.playeractions;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blockpos.FastBlockPos;
import stevebot.data.blocks.BlockUtils;
import stevebot.misc.Direction;
import stevebot.misc.ProcState;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.nodes.NodeCache;
import stevebot.player.PlayerUtils;

public class ActionFall extends Action {


	private ActionFall(Node from, Node to, double cost) {
		super(from, to, cost);

	}




	@Override
	public String getActionName() {
		return "fall";
	}




	@Override
	public ProcState tick(boolean fistTick) {
		if (PlayerUtils.getPlayer().onGround) {
			if (PlayerUtils.getMovement().moveTowards(getTo().getPos(), false)) {
				return ProcState.DONE;
			} else {
				return ProcState.EXECUTING;
			}
		} else {
			PlayerUtils.getMovement().moveTowards(getTo().getPos(), false);
			return ProcState.EXECUTING;
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

			return Result.valid(Direction.NONE, NodeCache.get(fallTo), ActionCosts.COST_FALL_N(from.getY() - fallTo.getY()));
		}




		@Override
		public Action createAction(Node node, Result result) {
			return new ActionFall(node, result.to, result.estimatedCost);
		}


	}

}
