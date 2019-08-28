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
	private final BlockPos[] touching;




	public ActionWalk(Node from, int dx, int dz) {
		this(from, from.pos.add(dx, 0, dz));
	}




	public ActionWalk(Node from, int dx, int dz, BlockPos[] touching) {
		this(from, from.pos.add(dx, 0, dz), touching);
	}




	public ActionWalk(Node from, BlockPos to) {
		this(from, to, null);
	}




	public ActionWalk(Node from, BlockPos to, BlockPos[] touching) {
		this.from = from;
		this.to = Node.get(to);
		this.cost = ActionCosts.COST_WALKING
				* (Action.isDiagonal(from.pos, to) ? ActionCosts.COST_MULT_DIAGONAL : 1)
				* (touching == null ? 1 : ActionCosts.COST_MULT_TOUCHING);
		this.touching = touching;
	}




	public BlockPos[] getBlocksTouching() {
		return touching;
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
		final BlockPos d0 = to.add(0, -1, 0);
		final BlockPos d1 = to;
		final BlockPos d2 = to.add(0, 1, 0);
		if (!BlockUtils.canWalkOn(d0)) { // can not stand on dest block
			return null;
		}
		if (!BlockUtils.canWalkThrough(d1) || !BlockUtils.canWalkThrough(d2)) { // can not move into dest blocks
			return null;
		}

		// additional checks if diagonal movement
		BlockPos[] blocksTouching = null;
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
				if (BlockUtils.isDangerous(a1Block) || BlockUtils.isFlowingLiquid(a1Block) || BlockUtils.isDangerous(a2Block) || BlockUtils.isFlowingLiquid(a2Block)) {
					return null;
				}
				if (!BlockUtils.canWalkThrough(a1) && !BlockUtils.canWalkThrough(a2)) { // touches both blocks
					blocksTouching = new BlockPos[]{a1, a2};
				} else if (BlockUtils.canWalkThrough(a1) && !BlockUtils.canWalkThrough(a2)) { // touches top block
					blocksTouching = new BlockPos[]{a2};
				} else if (!BlockUtils.canWalkThrough(a1) && BlockUtils.canWalkThrough(a2)) { // touches bottom block
					blocksTouching = new BlockPos[]{a1};
				}
			}

			if (bBlocked) { // check b for additional dangers / blocks to avoid when touching these blocks
				Block b1Block = BlockUtils.getBlock(b1);
				Block b2Block = BlockUtils.getBlock(b2);
				if (BlockUtils.isDangerous(b1Block) || BlockUtils.isFlowingLiquid(b1Block) || BlockUtils.isDangerous(b2Block) || BlockUtils.isFlowingLiquid(b2Block)) {
					return null;
				}
				if (!BlockUtils.canWalkThrough(b1) && !BlockUtils.canWalkThrough(b2)) { // touches both blocks
					blocksTouching = new BlockPos[]{b1, b2};
				} else if (BlockUtils.canWalkThrough(b1) && !BlockUtils.canWalkThrough(b2)) { // touches top block
					blocksTouching = new BlockPos[]{b2};
				} else if (!BlockUtils.canWalkThrough(b1) && BlockUtils.canWalkThrough(b2)) { // touches bottom block
					blocksTouching = new BlockPos[]{b1};
				}
			}

		}

		// valid movement -> create action
		return new ActionWalk(node, to, blocksTouching);
	}


}