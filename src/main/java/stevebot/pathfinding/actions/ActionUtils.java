package stevebot.pathfinding.actions;

import net.minecraft.util.math.BlockPos;
import stevebot.pathfinding.BlockUtils;

public class ActionUtils {


	/**
	 * There must be a 3-block high space. The block below does not matter.
	 */
	public static boolean canJump(BlockPos pos) {
		final BlockPos d2 = pos.add(0, 1, 0);
		final BlockPos d3 = pos.add(0, 2, 0);
		return BlockUtils.canWalkThrough(pos) && BlockUtils.canWalkThrough(d2) && BlockUtils.canWalkThrough(d3);
	}




	/**
	 * There must be a 3-block high space. The block below does not matter. This must be true for all given positions
	 */
	public static boolean canJump(BlockPos... positions) {
		for (int i = 0; i < positions.length; i++) {
			if (!canJump(positions[i])) {
				return false;
			}
		}
		return true;
	}




	/**
	 * Block below can not be walkable and there must be a 3-block high space.
	 */
	public static boolean canJumpThrough(BlockPos pos) {
		final BlockPos d0 = pos.add(0, -1, 0);
		if (BlockUtils.canWalkOn(d0)) {
			return false;
		}
		return canJump(pos);
	}




	/**
	 * Block below must be walkable and there must be a 3-block high space.
	 */
	public static boolean canJumpAt(BlockPos pos) {
		final BlockPos d0 = pos.add(0, -1, 0);
		if (!BlockUtils.canWalkOn(d0)) {
			return false;
		}
		return canJump(pos);
	}




	/**
	 * There must be space of the given height. The block below does not matter.
	 */
	public static boolean canMoveThrough(BlockPos pos, int height) {
		for (int i = 0; i < height; i++) {
			if (!BlockUtils.canWalkThrough(pos.add(0, i, 0))) {
				return false;
			}
		}
		return true;
	}




	/**
	 * There must be a 2-block high space. The block below does not matter.
	 */
	public static boolean canMoveThrough(BlockPos pos) {
		BlockPos d1 = pos;
		BlockPos d2 = pos.add(0, 1, 0);
		return BlockUtils.canWalkThrough(d1) && BlockUtils.canWalkThrough(d2);
	}




	/**
	 * There must be a 2-block high space. The block below does not matter. This must be valid for all given positions.
	 */
	public static boolean canMoveThroughAll(BlockPos... positions) {
		for (int i = 0; i < positions.length; i++) {
			if (!canMoveThrough(positions[i])) {
				return false;
			}
		}
		return true;
	}




	/**
	 * Block below must be walkable and there must be a 2-block high space.
	 */
	public static boolean canStandAt(BlockPos pos) {
		final BlockPos d0 = pos.add(0, -1, 0);
		if (!BlockUtils.canWalkOn(d0)) {
			return false;
		}
		return canMoveThrough(pos);
	}




	/**
	 * Block below must be walkable and there must be a space with the given height.
	 */
	public static boolean canStandAt(BlockPos pos, int height) {
		final BlockPos d0 = pos.add(0, -1, 0);
		if (!BlockUtils.canWalkOn(d0)) {
			return false;
		}
		return canMoveThrough(pos, height);
	}


}
