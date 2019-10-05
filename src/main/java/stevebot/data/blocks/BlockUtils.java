package stevebot.data.blocks;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import stevebot.Direction;
import stevebot.Stevebot;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blockpos.FastBlockPos;

public class BlockUtils {


	static final Block WATER_FLOWING = Block.getBlockById(8);
	static final Block WATER_STILL = Block.getBlockById(9);
	static final Block LAVAL_FLOWING = Block.getBlockById(10);
	static final Block LAVA_STILL = Block.getBlockById(11);




	/**
	 * @param pos the position of the block
	 * @return whether the position is currently in a loaded chunk.
	 */
	public static boolean isLoaded(BaseBlockPos pos) {
		return Stevebot.get().getBlockProvider().isLoaded(pos);
	}




	/**
	 * @param block the block
	 * @return whether the given block is water (flowing or still)
	 */
	public static boolean isWater(Block block) {
		return WATER_FLOWING.equals(block) || WATER_STILL.equals(block);
	}




	/**
	 * @param pos the position
	 * @return whether the block at the given position is water (flowing or still)
	 */
	public static boolean isWater(BaseBlockPos pos) {
		return isWater(Stevebot.get().getBlockProvider().getBlockAt(pos));
	}




	/**
	 * @param block the block
	 * @return whether the given block is lava (flowing or still)
	 */
	public static boolean isLava(Block block) {
		return LAVAL_FLOWING.equals(block) || LAVA_STILL.equals(block);
	}




	/**
	 * @param pos the position
	 * @return whether the block at the given position is lava (flowing or still)
	 */
	public static boolean isLava(BaseBlockPos pos) {
		return isLava(Stevebot.get().getBlockProvider().getBlockAt(pos));
	}




	/**
	 * @param block the block
	 * @return whether the given block is flowing water or lava.
	 */
	public static boolean isFlowingLiquid(Block block) {
		return WATER_FLOWING.equals(block) || LAVAL_FLOWING.equals(block);
	}




	/**
	 * @param pos the position
	 * @return whether the block at the given position is flowing water or lava.
	 */
	public static boolean isFlowingLiquid(BaseBlockPos pos) {
		return isFlowingLiquid(Stevebot.get().getBlockProvider().getBlockAt(pos));
	}




	/**
	 * @param block the block
	 * @return whether the given block is water or lava (flowing or still)
	 */
	public static boolean isLiquid(Block block) {
		return isLava(block) || isWater(block);
	}




	/**
	 * @param pos the position
	 * @return whether the block at the given position is water or lava (flowing or still)
	 */
	public static boolean isLiquid(BaseBlockPos pos) {
		return isLiquid(Stevebot.get().getBlockProvider().getBlockAt(pos));
	}




	/**
	 * @param block the block
	 * @return whether the given block can be dangerous to the player and should be avoided.
	 */
	public static boolean isDangerous(Block block) {
		return isLava(block) || block == Blocks.FIRE || block == Blocks.CACTUS || block == Blocks.WEB;
	}




	/**
	 * @param pos the position
	 * @return whether the block given position can be dangerous to the player and should be avoided.
	 */
	public static boolean isDangerous(BaseBlockPos pos) {
		return isDangerous(Stevebot.get().getBlockProvider().getBlockAt(pos));
	}




	/**
	 * @param pos the position
	 * @return whether the player can walk through the block at the given position. This does not check the surrounding blocks.
	 */
	public static boolean canWalkThrough(BaseBlockPos pos) {
		final Block block = Stevebot.get().getBlockProvider().getBlockAt(pos);
		return canWalkThrough(block, pos.copyAsMCBlockPos());
	}




	/**
	 * @param block the block
	 * @return whether the player can walk through the given block. This does not check the surrounding blocks.
	 */
	public static boolean canWalkThrough(Block block, BlockPos pos) {
		if (isLiquid(block) || Blocks.WATERLILY.equals(block) || isDangerous(block)
				|| Blocks.ICE.equals(block) || Blocks.FROSTED_ICE.equals(block) || Blocks.PACKED_ICE.equals(block)) {
			return false;
		} else {
			return block.isPassable(Minecraft.getMinecraft().world, pos);
		}
	}




	/**
	 * @param pos the position
	 * @return whether the player can walk on the block at the given position. This does not check the surrounding blocks.
	 */
	public static boolean canWalkOn(BaseBlockPos pos) {
		final Block block = Stevebot.get().getBlockProvider().getBlockAt(pos);
		return canWalkOn(block);
	}




	/**
	 * @param block the block
	 * @return whether the player can walk on the given block. This does not check the surrounding blocks.
	 */
	public static boolean canWalkOn(Block block) {
		if (isLiquid(block) || isDangerous(block)) {
			return false;
		} else {
			return block.getDefaultState().isNormalCube();
		}
	}




	/**
	 * @param block the block
	 * @return whether the player should avoid touching the given block
	 */
	public static boolean avoidTouching(Block block) {
		return BlockUtils.isDangerous(block) || BlockUtils.isFlowingLiquid(block);
	}




	/**
	 * @param pos the position
	 * @return whether the player should avoid touching the block at the given position
	 */
	public static boolean avoidTouching(BaseBlockPos pos) {
		return avoidTouching(Stevebot.get().getBlockProvider().getBlockAt(pos));
	}




	/**
	 * @param pos the position
	 * @return the distance of the nearest block-center to the given position on one axis
	 */
	public static double distToCenter(Vector3d pos) {
		final BaseBlockPos blockPos = toBaseBlockPos(pos);
		final double dx = Math.abs((blockPos.getX() + 0.5) - pos.x);
		final double dy = Math.abs((blockPos.getZ() + 0.5) - pos.z);
		return Math.max(dx, dy);
	}




	/**
	 * @param pos       the position
	 * @param direction the direction
	 * @return the distance of the nearest block-edge in the given direction to the given position
	 */
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




	/**
	 * @param pos the position as a {@link Vector3d}
	 * @return the position as a {@link FastBlockPos}
	 */
	public static FastBlockPos toFastBlockPos(Vector3d pos) {
		return toFastBlockPos(pos.x, pos.y, pos.z);
	}




	/**
	 * @param x the x-position
	 * @param y the x-position
	 * @param z the x-position
	 * @return the position as a {@link BaseBlockPos}
	 */
	public static FastBlockPos toFastBlockPos(double x, double y, double z) {
		final boolean isNegativeX = x < 0;
		final boolean isNegativeY = y < 0;
		final boolean isNegativeZ = z < 0;
		int bpx = ((int) x) - (isNegativeX ? 1 : 0);
		int bpy = ((int) y) - (isNegativeY ? 1 : 0);
		int bpz = ((int) z) - (isNegativeZ ? 1 : 0);
		return new FastBlockPos(bpx, bpy, bpz);
	}




	/**
	 * @param pos the position as a {@link Vector3d}
	 * @return the position as a {@link FastBlockPos}
	 */
	public static BaseBlockPos toBaseBlockPos(Vector3d pos) {
		return toBaseBlockPos(pos.x, pos.y, pos.z);
	}




	/**
	 * @param x the x-position
	 * @param y the x-position
	 * @param z the x-position
	 * @return the position as a {@link BaseBlockPos}
	 */
	public static BaseBlockPos toBaseBlockPos(double x, double y, double z) {
		final boolean isNegativeX = x < 0;
		final boolean isNegativeY = y < 0;
		final boolean isNegativeZ = z < 0;
		int bpx = ((int) x) - (isNegativeX ? 1 : 0);
		int bpy = ((int) y) - (isNegativeY ? 1 : 0);
		int bpz = ((int) z) - (isNegativeZ ? 1 : 0);
		return new BaseBlockPos(bpx, bpy, bpz);
	}




	/**
	 * @param pos the position as a {@link Vector3d}
	 * @return the position as a {@link BlockPos}
	 */
	public static BlockPos toMCBlockPos(Vector3d pos) {
		return toMCBlockPos(pos.x, pos.y, pos.z);
	}




	/**
	 * @param x the x-position
	 * @param y the x-position
	 * @param z the x-position
	 * @return the position as a {@link BlockPos}
	 */
	public static BlockPos toMCBlockPos(double x, double y, double z) {
		final boolean isNegativeX = x < 0;
		final boolean isNegativeY = y < 0;
		final boolean isNegativeZ = z < 0;
		int bpx = ((int) x) - (isNegativeX ? 1 : 0);
		int bpy = ((int) y) - (isNegativeY ? 1 : 0);
		int bpz = ((int) z) - (isNegativeZ ? 1 : 0);
		return new BlockPos(bpx, bpy, bpz);
	}


}
