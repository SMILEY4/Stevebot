package stevebot.pathfinding;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;

public class World {


	public static Block getBlock(BlockPos pos) {
		if (isBlockLoaded(pos)) {
			return Minecraft.getMinecraft().world.getBlockState(pos).getBlock();
		} else {
			return null;
		}
	}




	public static boolean isBlockLoaded(BlockPos pos) {
		return Minecraft.getMinecraft().world.isBlockLoaded(pos);
	}

}
