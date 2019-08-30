package stevebot.pathfinding.actions;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import stevebot.Stevebot;
import stevebot.pathfinding.BlockUtils;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;

public class ActionWalk extends Action {


	private final Node from;
	private final Node to;
	private final double cost;




	public ActionWalk(Node from, int dx, int dz) {
		this(from, from.pos.add(dx, 0, dz));
	}




	public ActionWalk(Node from, BlockPos to) {
		this(from, to, false);
	}




	public ActionWalk(Node from, BlockPos to, boolean touchesBlocks) {
		this.from = from;
		this.to = Node.get(to);
		this.cost = ActionCosts.COST_WALKING
				* (Action.isDiagonal(from.pos, to) ? ActionCosts.COST_MULT_DIAGONAL : 1)
				* (touchesBlocks ? ActionCosts.COST_MULT_TOUCHING : 1);
	}




	@Override
	public double getCost() {
		return this.cost;
	}




	@Override
	public Node getFrom() {
		return this.from;
	}




	@Override
	public Node getTo() {
		return this.to;
	}




	@Override
	public PathExecutor.State tick(boolean fistTick) {
		if (Stevebot.get().getPlayerController().getMovement().moveTowards(to.pos, true)) {
			return PathExecutor.State.DONE;
		} else {
			return PathExecutor.State.EXEC;
		}
	}




	public static ActionWalk createValid(Node node, int x, int z) {
		final BlockPos from = node.pos;
		final BlockPos to = node.pos.add(x, 0, z);

		// check if standing on a block
		final BlockPos s0 = from.add(0, -1, 0);
		if (!BlockUtils.canWalkOn(s0)) {
			return null;
		}

		// check destination pos
		if (!ActionUtils.canStandAt(to)) {
			return null;
		}

		// additional checks if diagonal movement
		boolean touchesBlocks = false;
		if (ActionWalk.isDiagonal(from, to)) {

			final BlockPos a1 = from.add(to.getX() - from.getX(), 0, 0);
			final BlockPos a2 = from.add(to.getX() - from.getX(), 1, 0);

			final BlockPos b1 = from.add(0, 0, to.getZ() - from.getZ());
			final BlockPos b2 = from.add(0, 1, to.getZ() - from.getZ());

			final boolean aBlocked = !(BlockUtils.canWalkThrough(a1) && BlockUtils.canWalkThrough(a2));
			final boolean bBlocked = !(BlockUtils.canWalkThrough(b1) && BlockUtils.canWalkThrough(b2));

			if (aBlocked && bBlocked) { // can not reach destination block
				return null;
			}

			if (aBlocked) { // check a for additional dangers / blocks to avoid when touching these blocks
				Block a1Block = BlockUtils.getBlock(a1);
				Block a2Block = BlockUtils.getBlock(a2);
				if (!BlockUtils.canWalkThrough(a1) || !BlockUtils.canWalkThrough(a2)) {
					touchesBlocks = true;
					if (BlockUtils.avoidTouching(a1Block) || BlockUtils.avoidTouching(a2Block)) {
						return null;
					}
				}
			}

			if (bBlocked) { // check b for additional dangers / blocks to avoid when touching these blocks
				Block b1Block = BlockUtils.getBlock(b1);
				Block b2Block = BlockUtils.getBlock(b2);
				if (!BlockUtils.canWalkThrough(b1) || !BlockUtils.canWalkThrough(b2)) {
					touchesBlocks = true;
					if (BlockUtils.avoidTouching(b1Block) || BlockUtils.avoidTouching(b2Block)) {
						return null;
					}
				}
			}

		}

		// valid movement -> create action
		return new ActionWalk(node, to, touchesBlocks);
	}


}


