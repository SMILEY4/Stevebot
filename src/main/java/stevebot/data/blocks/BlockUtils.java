package stevebot.data.blocks;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blockpos.FastBlockPos;
import stevebot.minecraft.MinecraftAdapter;
import stevebot.misc.Direction;

public class BlockUtils {


	static final int WATER_FLOWING = 8;
	static final int WATER_STILL = 9;
	static final int LAVAL_FLOWING = 10;
	static final int LAVA_STILL = 11;
	static final int FIRE = 51;
	static final int CACTUS = 81;
	static final int WEB = 30;
	static final int WATERLILY = 111;
	static final int ICE = 79;
	static final int FROSTED_ICE = 212;
	static final int PACKED_ICE = 174;
	static final int AIR = 0;
	static final int VINE = 106;
	static final int LADDER = 65;

	static final int WOODEN_DOOR = 64;
	static final int SPRUCE_DOOR = 193;
	static final int BIRCH_DOOR = 194;
	static final int JUNGLE_DOOR = 195;
	static final int ACACIA_DOOR = 196;
	static final int DARK_OAK_DOOR = 197;
	static final int FENCE_GATE = 107;
	static final int SPRUCE_FENCE_GATE = 183;
	static final int BIRCH_FENCE_GATE = 184;
	static final int JUNGLE_FENCE_GATE = 185;
	static final int DARK_OAK_FENCE_GATE = 186;
	static final int ACACIA_FENCE_GATE = 187;

	static final int SAND = 12;
	static final int GRAVEL = 13;
	static final int ANVIL = 145;
	static final int CONCRETE_POWDER = 252;


	private static BlockProvider blockProvider;
	private static BlockLibrary blockLibrary;




	public static void initialize(BlockProvider blockProvider, BlockLibrary blockLibrary) {
		BlockUtils.blockProvider = blockProvider;
		BlockUtils.blockLibrary = blockLibrary;
	}




	public static BlockProvider getBlockProvider() {
		return blockProvider;
	}




	public static BlockLibrary getBlockLibrary() {
		return blockLibrary;
	}




	/**
	 * @param pos the position of the block
	 * @return whether the position is currently in a loaded chunk.
	 */
	public static boolean isLoaded(BaseBlockPos pos) {
		return blockProvider.isLoaded(pos);
	}




	/**
	 * @param block the block
	 * @return whether the given block is air
	 */
	public static boolean isAir(BlockWrapper block) {
		return AIR == block.getId();
	}




	/**
	 * @param pos the position
	 * @return whether the block at the given position is air
	 */
	public static boolean isAir(BaseBlockPos pos) {
		return isAir(blockProvider.getBlockAt(pos));
	}




	/**
	 * @param block the block
	 * @return whether the given block is water (flowing or still)
	 */
	public static boolean isWater(BlockWrapper block) {
		return WATER_FLOWING == block.getId() || WATER_STILL == block.getId();
	}




	/**
	 * @param pos the position
	 * @return whether the block at the given position is water (flowing or still)
	 */
	public static boolean isWater(BaseBlockPos pos) {
		return isWater(blockProvider.getBlockAt(pos));
	}




	/**
	 * @param block the block
	 * @return whether the given block is lava (flowing or still)
	 */
	public static boolean isLava(BlockWrapper block) {
		return LAVAL_FLOWING == block.getId() || LAVA_STILL == block.getId();
	}




	/**
	 * @param pos the position
	 * @return whether the block at the given position is lava (flowing or still)
	 */
	public static boolean isLava(BaseBlockPos pos) {
		return isLava(blockProvider.getBlockAt(pos));
	}




	/**
	 * @param block the block
	 * @return whether the given block is flowing water or lava.
	 */
	public static boolean isFlowingLiquid(BlockWrapper block) {
		return WATER_FLOWING == block.getId() || LAVAL_FLOWING == block.getId();
	}




	/**
	 * @param pos the position
	 * @return whether the block at the given position is flowing water or lava.
	 */
	public static boolean isFlowingLiquid(BaseBlockPos pos) {
		return isFlowingLiquid(blockProvider.getBlockAt(pos));
	}




	/**
	 * @param block the block
	 * @return whether the given block is water or lava (flowing or still)
	 */
	public static boolean isLiquid(BlockWrapper block) {
		return isLava(block) || isWater(block);
	}




	/**
	 * @param pos the position
	 * @return whether the block at the given position is water or lava (flowing or still)
	 */
	public static boolean isLiquid(BaseBlockPos pos) {
		return isLiquid(blockProvider.getBlockAt(pos));
	}




	/**
	 * @param block the block
	 * @return whether the given block can be dangerous to the player and should be avoided.
	 */
	public static boolean isDangerous(BlockWrapper block) {
		return isLava(block) || block.getId() == FIRE || block.getId() == CACTUS || block.getId() == WEB;
	}




	/**
	 * @param pos the position
	 * @return whether the block given position can be dangerous to the player and should be avoided.
	 */
	public static boolean isDangerous(BaseBlockPos pos) {
		return isDangerous(blockProvider.getBlockAt(pos));
	}




	/**
	 * @param pos the position
	 * @return whether the player can walk through the block at the given position. This does not check the surrounding blocks.
	 */
	public static boolean canWalkThrough(BaseBlockPos pos) {
		final BlockWrapper block = blockProvider.getBlockAt(pos);
		return canWalkThrough(block, pos.copyAsMCBlockPos());
	}




	/**
	 * @param block the block
	 * @param pos   the position
	 * @return whether the player can walk through the given block. This does not check the surrounding blocks.
	 */
	public static boolean canWalkThrough(BlockWrapper block, BlockPos pos) {
		if (isLiquid(block) || WATERLILY == block.getId() || isDangerous(block)
				|| ICE == block.getId() || FROSTED_ICE == block.getId() || PACKED_ICE == block.getId()
				|| isDoorLike(block)) {
			return false;
		} else {
			return block.getBlock().isPassable(MinecraftAdapter.get().getWorld(), pos);
		}
	}




	/**
	 * @param pos the position
	 * @return whether the player can walk on the block at the given position. This does not check the surrounding blocks.
	 */
	public static boolean canWalkOn(BaseBlockPos pos) {
		final BlockWrapper block = blockProvider.getBlockAt(pos);
		return canWalkOn(block);
	}




	/**
	 * @param block the block
	 * @return whether the player can walk on the given block. This does not check the surrounding blocks.
	 */
	public static boolean canWalkOn(BlockWrapper block) {
		if (isLiquid(block) || isDangerous(block)) {
			return false;
		} else {
			return block.getBlock().getDefaultState().isNormalCube() || ICE == block.getId() || FROSTED_ICE == block.getId() || PACKED_ICE == block.getId();
		}
	}




	/**
	 * @return whether the block can be replaced by placing a block at its position.
	 */
	public static boolean canBeReplaced(BlockWrapper block) {
		return block.getId() == AIR;
	}




	/**
	 * @return whether the player can place a block at the given position (independent of player position)
	 */
	public static boolean canPlaceBlockAt(BaseBlockPos pos) {
		final BlockWrapper block = blockProvider.getBlockAt(pos);
		return canBeReplaced(block);
	}




	/**
	 * @param block the block
	 * @return whether the player should avoid touching the given block
	 */
	public static boolean avoidTouching(BlockWrapper block) {
		return isDangerous(block) || isFlowingLiquid(block);
	}




	/**
	 * @param pos the position
	 * @return whether the player should avoid touching the block at the given position
	 */
	public static boolean avoidTouching(BaseBlockPos pos) {
		return avoidTouching(blockProvider.getBlockAt(pos));
	}




	/**
	 * @param block the block
	 * @return whether the given block can affect a jump
	 */
	public static boolean affectsJump(BlockWrapper block) {
		return block.getId() == VINE || block.getId() == LADDER;
	}




	/**
	 * @param position the position of the block
	 * @return whether the block at the given position can affect a jump
	 */
	public static boolean affectsJump(BaseBlockPos position) {
		return affectsJump(blockProvider.getBlockAt(position));
	}




	/**
	 * @param block the block to check
	 * @return whether the given block is (part of) a door
	 */
	public static boolean isDoor(BlockWrapper block) {
		final int id = block.getId();
		if (id == WOODEN_DOOR) return true;
		if (id == SPRUCE_DOOR) return true;
		if (id == BIRCH_DOOR) return true;
		if (id == JUNGLE_DOOR) return true;
		if (id == ACACIA_DOOR) return true;
		if (id == DARK_OAK_DOOR) return true;
		return false;
	}




	/**
	 * @param position the position to check
	 * @return whether the given position is (part of) a door
	 */
	public static boolean isDoor(BaseBlockPos position) {
		return isDoor(blockProvider.getBlockAt(position));
	}




	/**
	 * @param block the block to check
	 * @return whether the given block is a fence gate
	 */
	public static boolean isFenceGate(BlockWrapper block) {
		final int id = block.getId();
		if (id == FENCE_GATE) return true;
		if (id == SPRUCE_FENCE_GATE) return true;
		if (id == BIRCH_FENCE_GATE) return true;
		if (id == JUNGLE_FENCE_GATE) return true;
		if (id == DARK_OAK_FENCE_GATE) return true;
		if (id == ACACIA_FENCE_GATE) return true;
		return false;
	}




	/**
	 * @param position the position to check
	 * @return whether the given position is a fence gate
	 */
	public static boolean isFenceGate(BaseBlockPos position) {
		return isFenceGate(blockProvider.getBlockAt(position));
	}




	/**
	 * @param block the block to check
	 * @return whether the given block is (part of) a door or a fence gate
	 */
	public static boolean isDoorLike(BlockWrapper block) {
		return isDoor(block) || isFenceGate(block);
	}




	/**
	 * @param position the position to check
	 * @return whether the given position is (part of) a door or a fence gate
	 */
	public static boolean isDoorLike(BaseBlockPos position) {
		return isDoor(position) || isFenceGate(position);
	}




	/**
	 * @param position  the position of the door or gate to check
	 * @param direction the direction
	 * @return whether the door or fence gate can be passed by the player in the given direction
	 */
	public static boolean canPassDoor(BaseBlockPos position, Direction direction) {

		if (!BlockUtils.isDoorLike(position)) {
			return false;
		}

		final IBlockState blockState = MinecraftAdapter.get().getWorld().getBlockState(position.copyAsMCBlockPos());

		final EnumFacing.Axis facing = blockState.getValue(BlockHorizontal.FACING).getAxis();

		boolean facingDoor = false;
		if (facing == EnumFacing.Axis.X) {
			if (direction == Direction.EAST || direction == Direction.WEST) {
				facingDoor = true;
			}
		} else if (facing == EnumFacing.Axis.Z) {
			if (direction == Direction.NORTH || direction == Direction.SOUTH) {
				facingDoor = true;
			}
		}

		if (isFenceGate(position)) {
			return facingDoor && blockState.getValue(BlockDoor.OPEN);
		} else {
			return !facingDoor;
		}

	}




	/**
	 * @param position the position of the block
	 * @return whether the block at the given position is affected by gravity.
	 */
	public static boolean hasGravity(BaseBlockPos position) {
		return hasGravity(blockProvider.getBlockAt(position));
	}




	/**
	 * @param block the block to check
	 * @return whether the given block is affected by gravity.
	 */
	public static boolean hasGravity(BlockWrapper block) {
		final int id = block.getId();
		return id == SAND || id == GRAVEL || id == ANVIL || id == CONCRETE_POWDER;
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
	 * @param pos      the position
	 * @param blockPos the position of the block
	 * @return the distance of the center of the given block to the given position on one axis
	 */
	public static double distToCenter(BaseBlockPos blockPos, Vector3d pos) {
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
		return new FastBlockPos(toBlockPos(x), toBlockPos(y), toBlockPos(z));
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
		return new BaseBlockPos(toBlockPos(x), toBlockPos(y), toBlockPos(z));
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
		return new BlockPos(toBlockPos(x), toBlockPos(y), toBlockPos(z));
	}




	/**
	 * Converts the given value to a BlockPos-value.
	 *
	 * @param value the given value
	 * @return the value rounded to the block
	 */
	public static int toBlockPos(double value) {
		final boolean isNegative = value < 0;
		return ((int) value) - (isNegative ? 1 : 0);
	}




	/**
	 * Searches the area of the given size for a block of the given type.
	 *
	 * @param blockType the type of the block to search for
	 * @param position  the position
	 * @param sizeX     the size of the area in x direction
	 * @param sizeZ     the size of the area in z direction
	 * @return the position of the block of the given type closest to the given position (or null if none found).
	 */
	public static BaseBlockPos findNearest(BlockWrapper blockType, BaseBlockPos position, int sizeX, int sizeZ) {

		int x = 0;
		int z = 0;
		int dx = 0;
		int dy = -1;

		final int halfSizeX = sizeX / 2;
		final int halfSizeZ = sizeZ / 2;

		// spiral outwards
		for (int i = 0; i < Math.pow(Math.max(halfSizeX, halfSizeZ), 2); i++) {
			if ((-halfSizeX / 2 < x && x <= halfSizeX / 2) && (-halfSizeZ / 2 < z && z <= halfSizeZ / 2)) {
				final BaseBlockPos result = searchColumn(x + position.getX(), z + position.getZ(), blockType, position);
				if (result != null) {
					return result;
				}
			}
			if ((x == z) || (x < 0 && x == -z) || (x > 0 && x == 1 - z)) {
				int tmp = dx;
				dx = -dy;
				dy = tmp;
			}
			x += dx;
			z += dy;
		}

		return null;
	}




	/**
	 * Searches the column of blocks at the given coordinates for the given block.
	 *
	 * @param x         the x position of the column
	 * @param z         the z position of the column
	 * @param blockType the type of the block to search for
	 * @param position  the position
	 * @return the position of the block of the given type closest to the given position (or null if none found).
	 */
	private static BaseBlockPos searchColumn(int x, int z, BlockWrapper blockType, BaseBlockPos position) {

		final int INFINITE = 99999;

		final FastBlockPos closestBlock = new FastBlockPos();
		int minDistY = INFINITE;

		final FastBlockPos blockPos = new FastBlockPos();
		for (int y = 0; y < 256; y++) {
			final BlockWrapper block = getBlockProvider().getBlockAt(blockPos.set(x, y, z));
			if (block.getId() == blockType.getId()) {
				final int dy = Math.abs(position.getY() - y);
				if (dy < minDistY) {
					minDistY = dy;
					closestBlock.set(x, y, z);
				}
			}
		}

		return minDistY == INFINITE ? null : closestBlock;
	}


}
