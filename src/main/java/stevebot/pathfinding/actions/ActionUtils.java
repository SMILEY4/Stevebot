package stevebot.pathfinding.actions;

import net.minecraft.util.math.BlockPos;
import stevebot.pathfinding.BlockUtils;

public class ActionUtils {


	public static boolean canStandAt(BlockPos pos) {
		final BlockPos d0 = pos.add(0, -1, 0);
		final BlockPos d1 = pos;
		final BlockPos d2 = pos.add(0, 1, 0);
		if (!BlockUtils.canWalkOn(d0)) {
			return false;
		}
		if (!BlockUtils.canWalkThrough(d1) || !BlockUtils.canWalkThrough(d2)) {
			return false;
		}
		return true;
	}




	public static boolean canStandAt(BlockPos pos, int height) {
		final BlockPos d0 = pos.add(0, -1, 0);
		if (!BlockUtils.canWalkOn(d0)) {
			return false;
		}
		for (int i = 0; i < height; i++) {
			if (!BlockUtils.canWalkThrough(pos.add(0, i, 0))) {
				return false;
			}
		}
		return true;
	}


}
