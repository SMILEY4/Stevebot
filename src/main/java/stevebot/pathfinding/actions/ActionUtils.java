package stevebot.pathfinding.actions;

import stevebot.data.blocks.BlockUtils;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blockpos.FastBlockPos;

public class ActionUtils {


	private static final FastBlockPos fastPos1 = new FastBlockPos();
	private static final FastBlockPos fastPos2 = new FastBlockPos();




	/**
	 * There must be a 3-block high space. The block below does not matter.
	 */
	public static boolean canJump(BaseBlockPos pos) {
		fastPos1.set(pos).add(0, 1, 0);
		fastPos2.set(pos).add(0, 2, 0);
		return BlockUtils.canWalkThrough(pos) && BlockUtils.canWalkThrough(fastPos1) && BlockUtils.canWalkThrough(fastPos2);
	}




	/**
	 * There must be a 3-block high space. The block below does not matter. This must be true for all given positions
	 */
	public static boolean canJump(BaseBlockPos... positions) {
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
	public static boolean canJumpThrough(BaseBlockPos pos) {
		if (BlockUtils.canWalkOn(fastPos1.set(pos).add(0, -1, 0))) {
			return false;
		}
		return canJump(pos);
	}




	/**
	 * Block below must be walkable and there must be a 3-block high space.
	 */
	public static boolean canJumpAt(BaseBlockPos pos) {
		if (!BlockUtils.canWalkOn(fastPos1.set(pos).add(0, -1, 0))) {
			return false;
		}
		return canJump(pos);
	}




	/**
	 * There must be space of the given height. The block below does not matter.
	 */
	public static boolean canMoveThrough(BaseBlockPos pos, int height) {
		for (int i = 0; i < height; i++) {
			if (!BlockUtils.canWalkThrough(fastPos1.set(pos).add(0, i, 0))) {
				return false;
			}
		}
		return true;
	}




	/**
	 * There must be a 2-block high space. The block below does not matter.
	 */
	public static boolean canMoveThrough(BaseBlockPos pos) {
		return BlockUtils.canWalkThrough(pos) && BlockUtils.canWalkThrough(fastPos1.set(pos).add(0, 1, 0));
	}




	/**
	 * There must be a 2-block high space. The block below does not matter. This must be valid for all given positions.
	 */
	public static boolean canMoveThroughAll(BaseBlockPos... positions) {
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
	public static boolean canStandAt(BaseBlockPos pos) {
		return canStandAt(pos, 2);
	}




	/**
	 * Block below must be walkable and there must be a space with the given height.
	 */
	public static boolean canStandAt(BaseBlockPos pos, int height) {
		if (!BlockUtils.canWalkOn(fastPos1.set(pos).add(0, -1, 0))) {
			return false;
		}
		return canMoveThrough(pos, height);
	}




	/**
	 * Block below must be non-flowing water and there must be a 2-block high space.
	 */
	public static boolean canSwimAt(BaseBlockPos pos) {
		if (!BlockUtils.isWater(fastPos1.set(pos).add(0, -1, 0))) {
			return false;
		}
		return canMoveThrough(pos);
	}


}
