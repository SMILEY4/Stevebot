package stevebot.pathfinding.actions.playeractions;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blockpos.FastBlockPos;
import stevebot.data.blocks.BlockUtils;
import stevebot.data.modification.Modification;
import stevebot.misc.Direction;
import stevebot.misc.ProcState;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionUtils;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.nodes.NodeCache;
import stevebot.player.PlayerUtils;

public class ActionFall extends Action {


	private final Modification[] modifications = new Modification[1];




	private ActionFall(Node from, Node to, double cost) {
		super(from, to, cost);
		final int fallDamage = ActionUtils.calculateFallDamage(from.getPos().getY() - to.getPos().getY());
		if (fallDamage > 0) {
			modifications[0] = Modification.healthChange(-fallDamage);
		}
	}




	@Override
	public String getActionName() {
		return "fall";
	}




	@Override
	public boolean hasModifications() {
		return modifications[0] != null;
	}




	@Override
	public Modification[] getModifications() {
		return modifications;
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

			// find destination position and fall-height
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

			// check if there is a fall
			if (height <= 0) {
				return Result.invalid();
			}

			// check if player can walk on destination position / destination is not dangerous
			final FastBlockPos d0 = fallTo.copy().add(0, -1, 0);
			if (!BlockUtils.canWalkOn(d0) || BlockUtils.isDangerous(d0)) {
				return Result.invalid();
			}

			// check if player can survive fall
			final int currentHealth = PlayerUtils.getActiveSnapshot().getPlayerHealth();
			final int damage = ActionUtils.calculateFallDamage(height);
			if (currentHealth - damage <= 1) {
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
