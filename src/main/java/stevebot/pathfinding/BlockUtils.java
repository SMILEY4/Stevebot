package stevebot.pathfinding;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import stevebot.Direction;
import stevebot.Stevebot;

public class BlockUtils {


	static final Block WATER_FLOWING = Block.getBlockById(8);
	static final Block WATER_STILL = Block.getBlockById(9);
	static final Block LAVAL_FLOWING = Block.getBlockById(10);
	static final Block LAVA_STILL = Block.getBlockById(11);




	public static boolean isLoaded(BlockPos pos) {
		return Stevebot.get().getBlockProvider().isLoaded(pos);
	}




	public static boolean isWater(Block block) {
		return WATER_FLOWING.equals(block) || WATER_STILL.equals(block);
	}




	public static boolean isWater(BlockPos pos) {
		return isWater(Stevebot.get().getBlockProvider().getBlockAt(pos));
	}




	public static boolean isLava(Block block) {
		return LAVAL_FLOWING.equals(block) || LAVA_STILL.equals(block);
	}




	public static boolean isLava(BlockPos pos) {
		return isLava(Stevebot.get().getBlockProvider().getBlockAt(pos));
	}




	public static boolean isFlowingLiquid(Block block) {
		return WATER_FLOWING.equals(block) || LAVAL_FLOWING.equals(block);
	}




	public static boolean isFlowingLiquid(BlockPos pos) {
		return isFlowingLiquid(Stevebot.get().getBlockProvider().getBlockAt(pos));
	}




	public static boolean isLiquid(Block block) {
		return isLava(block) || isWater(block);
	}




	public static boolean isLiquid(BlockPos pos) {
		return isLiquid(Stevebot.get().getBlockProvider().getBlockAt(pos));
	}




	public static boolean isDangerous(Block block) {
		return isLava(block) || block == Blocks.FIRE || block == Blocks.CACTUS || block == Blocks.WEB;
	}




	public static boolean isDangerous(BlockPos pos) {
		return isDangerous(Stevebot.get().getBlockProvider().getBlockAt(pos));
	}




	public static boolean canWalkThrough(BlockPos pos) {
		final Block block = Stevebot.get().getBlockProvider().getBlockAt(pos);
		if (isLiquid(block) || Blocks.WATERLILY.equals(block) || isDangerous(block)
				|| Blocks.ICE.equals(block) || Blocks.FROSTED_ICE.equals(block) || Blocks.PACKED_ICE.equals(block)) {
			return false;
		} else {
			return block.isPassable(Minecraft.getMinecraft().world, pos);
		}
	}




	public static boolean canWalkOn(BlockPos pos) {
		final Block block = Stevebot.get().getBlockProvider().getBlockAt(pos);
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
		return avoidTouching(Stevebot.get().getBlockProvider().getBlockAt(pos));
	}




	public static double distToCenter(Vector3d pos) {
		final BlockPos blockPos = toBlockPos(pos);
		final double dx = Math.abs((blockPos.getX() + 0.5) - pos.x);
		final double dy = Math.abs((blockPos.getZ() + 0.5) - pos.z);
		return Math.max(dx, dy);
	}




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




	public static BlockPos toBlockPos(Vector3d pos) {
		return toBlockPos(pos.x, pos.y, pos.z);
	}




	public static BlockPos toBlockPos(double x, double y, double z) {
		final boolean isNegativeX = x < 0;
		final boolean isNegativeY = y < 0;
		final boolean isNegativeZ = z < 0;
		int bpx = ((int) x) - (isNegativeX ? 1 : 0);
		int bpy = ((int) y) - (isNegativeY ? 1 : 0);
		int bpz = ((int) z) - (isNegativeZ ? 1 : 0);
		return new BlockPos(bpx, bpy, bpz);
	}

}
