package stevebot.pathfinding;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class BlockUtils {


	public static Block getBlock(BlockPos pos) {
		return Minecraft.getMinecraft().world.getBlockState(pos).getBlock();
	}




	static final Block WATER_FLOWING = Block.getBlockById(8);
	static final Block WATER_STILL = Block.getBlockById(9);
	static final Block LAVAL_FLOWING = Block.getBlockById(10);
	static final Block LAVA_STILL = Block.getBlockById(11);




	public static boolean isWater(Block block) {
		return WATER_FLOWING.equals(block) || WATER_STILL.equals(block);
	}




	public static boolean isLava(Block block) {
		return LAVAL_FLOWING.equals(block) || LAVA_STILL.equals(block);
	}




	public static boolean isFlowingLiquid(Block block) {
		return WATER_FLOWING.equals(block) || LAVAL_FLOWING.equals(block);
	}




	public static boolean isLiquid(Block block) {
		return isLava(block) || isWater(block);
	}



	public static boolean isDangerous(BlockPos pos) {
		return isDangerous(getBlock(pos));
	}


	public static boolean isDangerous(Block block) {
		return isLava(block) || block == Blocks.FIRE || block == Blocks.CACTUS || block == Blocks.WEB;
	}




	public static boolean canWalkThrough(BlockPos pos) {
		final Block block = getBlock(pos);
		if (isLiquid(block) || Blocks.WATERLILY.equals(block) || isDangerous(block)
				|| Blocks.ICE.equals(block) || Blocks.FROSTED_ICE.equals(block) || Blocks.PACKED_ICE.equals(block)) {
			return false;
		} else {
			return block.isPassable(Minecraft.getMinecraft().world, pos);
		}
	}




	public static boolean canWalkOn(BlockPos pos) {
		final Block block = getBlock(pos);
		if (isLiquid(block) || isDangerous(block)) {
			return false;
		} else {
			return block.isNormalCube(Minecraft.getMinecraft().world.getBlockState(pos), Minecraft.getMinecraft().world, pos);
		}

	}


}
