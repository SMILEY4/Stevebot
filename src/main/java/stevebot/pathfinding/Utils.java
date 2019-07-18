package stevebot.pathfinding;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class Utils {


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




	public static boolean isLiquid(Block block) {
		return isLava(block) || isWater(block);
	}




	public static boolean canWalkThrough(BlockPos pos) {
		final Block block = getBlock(pos);
		if(isLiquid(block) || Blocks.WATERLILY.equals(block) || Blocks.FIRE.equals(block)) {
			return false;
		} else {
			return block.isPassable(Minecraft.getMinecraft().world, pos);
		}
	}




	public static boolean canWalkOn(BlockPos pos) {
		final Block block = getBlock(pos);
		if(isLiquid(block)) {
			return false;
		} else {
			return block.isNormalCube(Minecraft.getMinecraft().world.getBlockState(pos), Minecraft.getMinecraft().world, pos);
		}

	}


}
