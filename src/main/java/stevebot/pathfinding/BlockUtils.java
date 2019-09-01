package stevebot.pathfinding;

import com.ruegnerlukas.simplemath.vectors.vec2.Vector2d;
import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import stevebot.Direction;

import java.util.ArrayList;
import java.util.List;

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




	public static boolean isWater(BlockPos pos) {
		return isWater(getBlock(pos));
	}




	public static boolean isLava(Block block) {
		return LAVAL_FLOWING.equals(block) || LAVA_STILL.equals(block);
	}




	public static boolean isLava(BlockPos pos) {
		return isLava(getBlock(pos));
	}




	public static boolean isFlowingLiquid(Block block) {
		return WATER_FLOWING.equals(block) || LAVAL_FLOWING.equals(block);
	}




	public static boolean isFlowingLiquid(BlockPos pos) {
		return isFlowingLiquid(getBlock(pos));
	}




	public static boolean isLiquid(Block block) {
		return isLava(block) || isWater(block);
	}




	public static boolean isLiquid(BlockPos pos) {
		return isLiquid(getBlock(pos));
	}




	public static boolean isDangerous(Block block) {
		return isLava(block) || block == Blocks.FIRE || block == Blocks.CACTUS || block == Blocks.WEB;
	}




	public static boolean isDangerous(BlockPos pos) {
		return isDangerous(getBlock(pos));
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




	public static boolean avoidTouching(Block block) {
		return BlockUtils.isDangerous(block) || BlockUtils.isFlowingLiquid(block);
	}




	public static boolean avoidTouching(BlockPos pos) {
		return avoidTouching(getBlock(pos));
	}




	private static final List<Vector2d> intersections = new ArrayList<>();




	public static double distToEdge(Vector3d pos, Direction direction) {

		final double edgeNorth = Math.floor(pos.z);
		final double edgeSouth = Math.ceil(pos.z);
		final double edgeEast = Math.ceil(pos.x);
		final double edgeWest = Math.floor(pos.x);

		final double distEdgeNorth = Math.abs(pos.z - edgeNorth);
		final double distEdgeEast = Math.abs(pos.x - edgeEast);
		final double distEdgeSouth = Math.abs(pos.z - edgeSouth);
		final double distEdgeWest = Math.abs(pos.x - edgeWest);

		switch (direction) {
			case NORTH:
				return distEdgeNorth;
			case EAST:
				return distEdgeEast;
			case SOUTH:
				return distEdgeSouth;
			case WEST:
				return distEdgeWest;
			case NORTH_EAST:
				return Math.sqrt(distEdgeNorth * distEdgeNorth + distEdgeEast * distEdgeEast);
			case NORTH_WEST:
				return Math.sqrt(distEdgeNorth * distEdgeNorth + distEdgeWest * distEdgeWest);
			case SOUTH_EAST:
				return Math.sqrt(distEdgeSouth * distEdgeSouth + distEdgeEast * distEdgeEast);
			case SOUTH_WEST:
				return Math.sqrt(distEdgeSouth * distEdgeSouth + distEdgeWest * distEdgeWest);
			default: {
				return -1;
			}
		}

	}

}
