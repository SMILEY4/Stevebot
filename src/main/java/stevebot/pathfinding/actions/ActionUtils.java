package stevebot.pathfinding.actions;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blockpos.FastBlockPos;
import stevebot.data.blocks.BlockUtils;
import stevebot.data.items.ItemLibrary;
import stevebot.data.items.ItemUtils;
import stevebot.data.items.wrapper.ItemWrapper;
import stevebot.minecraft.MinecraftAdapter;
import stevebot.misc.Direction;
import stevebot.player.PlayerUtils;

public class ActionUtils {


	private static final FastBlockPos fastPos1 = new FastBlockPos();
	private static final FastBlockPos fastPos2 = new FastBlockPos();




	/**
	 * There must be a 3-block high space. The block below does not matter.
	 *
	 * @param pos the {@link BaseBlockPos}
	 * @return whether the player can jump at the given position
	 */
	public static boolean canJump(BaseBlockPos pos) {
		fastPos1.set(pos).add(0, 1, 0);
		fastPos2.set(pos).add(0, 2, 0);
		return BlockUtils.canWalkThrough(pos)
				&& BlockUtils.canWalkThrough(fastPos1)
				&& BlockUtils.canWalkThrough(fastPos2)
				&& !BlockUtils.affectsJump(pos)
				&& !BlockUtils.affectsJump(fastPos1)
				&& !BlockUtils.affectsJump(fastPos1);
	}




	/**
	 * There must be a 3-block high space. The block below does not matter. This must be true for all given positions
	 *
	 * @param positions the {@link BaseBlockPos}s
	 * @return whether the player can jump at the given positions.
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
	 *
	 * @param pos the {@link BaseBlockPos}
	 * @return whether the player can jump through the given position.
	 */
	public static boolean canJumpThrough(BaseBlockPos pos) {
		if (BlockUtils.canWalkOn(fastPos1.set(pos).add(Direction.DOWN))) {
			return false;
		}
		return canJump(pos);
	}




	/**
	 * Block below must be walkable and there must be a 3-block high space.
	 *
	 * @param pos the {@link BaseBlockPos}
	 * @return whether the player can jump at the given position
	 */
	public static boolean canJumpAt(BaseBlockPos pos) {
		if (!BlockUtils.canWalkOn(fastPos1.set(pos).add(Direction.DOWN))) {
			return false;
		}
		return canJump(pos);
	}




	/**
	 * There must be space of the given height. The block below does not matter.
	 *
	 * @param pos    the {@link BaseBlockPos}
	 * @param height the number of blocks to check above
	 * @return whether the player can move through the given position
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
	 *
	 * @param pos the {@link BaseBlockPos}
	 * @return whether the player can move through the given position
	 */
	public static boolean canMoveThrough(BaseBlockPos pos) {
		return BlockUtils.canWalkThrough(pos) && BlockUtils.canWalkThrough(fastPos1.set(pos).add(Direction.UP));
	}




	/**
	 * There must be a 2-block high space. The block below does not matter. This must be valid for all given positions.
	 *
	 * @param positions the {@link BaseBlockPos}
	 * @return whether the player can move through the given positions
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
	 *
	 * @param pos the {@link BaseBlockPos}
	 * @return whether the player can stand at the given position.
	 */
	public static boolean canStandAt(BaseBlockPos pos) {
		return canStandAt(pos, 2);
	}




	/**
	 * Block below must be walkable and there must be a space with the given height.
	 *
	 * @param pos    the {@link BaseBlockPos}
	 * @param height the number of blocks to check above
	 * @return whether the player can stand at the given position
	 */
	public static boolean canStandAt(BaseBlockPos pos, int height) {
		if (!BlockUtils.canWalkOn(fastPos1.set(pos).add(Direction.DOWN))) {
			return false;
		}
		return canMoveThrough(pos, height);
	}




	/**
	 * Block below must be non-flowing water and there must be a 2-block high space.
	 *
	 * @param pos the {@link BaseBlockPos}
	 * @return whether the player can swim at the given position
	 */
	public static boolean canSwimAt(BaseBlockPos pos) {
		fastPos1.set(pos).add(Direction.DOWN);
		if (!BlockUtils.isWater(fastPos1) || BlockUtils.isFlowingLiquid(fastPos1)) {
			return false;
		}
		return canMoveThrough(pos);
	}




	/**
	 * Check if there is a door at the given position and if the player can pass through the door ignoring the direction of the door
	 *
	 * @param position the position
	 * @return whether the player can stand at the given position, occupied by a door
	 */
	public static boolean isDoorPassable(BaseBlockPos position) {

		final BaseBlockPos positionTop = position.copyAsFastBlockPos().add(Direction.UP);

		final boolean isDoorBottom = BlockUtils.isDoorLike(position);
		final boolean isDoorTop = BlockUtils.isDoorLike(positionTop);

		// there is no door
		if (!isDoorBottom && !isDoorTop) {
			return false;
		}

		// can not walk through / stand at position
		if (!isDoorBottom && !BlockUtils.canWalkThrough(position)) {
			return false;
		}
		if (!isDoorTop && !BlockUtils.canWalkThrough(positionTop)) {
			return false;
		}

		return true;
	}




	/**
	 * Places a block against a block at the given position
	 *
	 * @param pos       the position of a block to place the new block on
	 * @param direction the side to place the block on
	 * @return true, if the block was placed
	 */
	public static boolean placeBlockAgainst(BaseBlockPos pos, Direction direction) {
		if (BlockUtils.canWalkOn(pos) && facesPlayer(pos, direction)) {
			PlayerUtils.getCamera().setLookAtBlockSide(pos, direction);
			PlayerUtils.getInput().setPlaceBlock();
			return true;
		} else {
			return false;
		}
	}




	/**
	 * @param pos       the position of a block
	 * @param direction the side
	 * @return whether the given side of the block faces the player
	 */
	private static boolean facesPlayer(BaseBlockPos pos, Direction direction) {
		final EntityPlayerSP player = PlayerUtils.getPlayer();
		final Vector3d nFace = new Vector3d(direction.dx, direction.dy, direction.dz);
		final Vector3d posLookAt = new Vector3d(pos.getCenterX(), pos.getCenterY(), pos.getCenterZ())
				.add(direction.dx * 0.5, direction.dy * 0.5, direction.dz * 0.5);
		final Vector3d posHead = new Vector3d(player.getPositionEyes(1.0F).x, player.getPositionEyes(1.0F).y, player.getPositionEyes(1.0F).z);
		final Vector3d dirPlayer = posLookAt.sub(posHead).normalize();
		return dirPlayer.dot(nFace) <= 0;
	}




	/**
	 * Checks whether the block at the given position is breakable, by which item and how long it would take.
	 *
	 * @param blockPos the position of the block to break
	 * @return {@link BreakBlockCheckResult}
	 */
	public static BreakBlockCheckResult checkBlockToBreak(BaseBlockPos blockPos) {
		final ItemWrapper bestTool = PlayerUtils.getActiveSnapshot().findBestToolForBlock(BlockUtils.getBlockProvider().getBlockAt(blockPos));
		if (bestTool == ItemLibrary.INVALID_ITEM) {
			return BreakBlockCheckResult.invalid(blockPos);
		} else {
			final float ticksToBreak = ItemUtils.getBreakDuration(bestTool.getStack(1), blockPos);
			return BreakBlockCheckResult.valid(blockPos, bestTool, ticksToBreak);
		}
	}




	public static boolean breakBlock(BaseBlockPos pos) {
		final Block mcBlock = MinecraftAdapter.get().getBlock(pos.copyAsMCBlockPos());
		if (BlockUtils.isAir(BlockUtils.getBlockLibrary().getBlockByMCBlock(mcBlock))) {
			return true;
		} else {
			PlayerUtils.getCamera().setLookAt(pos);
			PlayerUtils.getInput().setBreakBlock();
			return false;
		}
	}




	/**
	 * @param pos the position of the block to break
	 * @return whether the block can be safely broken, that means, when there is no liquid or falling block above and no liquid next to the block
	 */
	public static boolean canSafelyBreak(BaseBlockPos pos) {

		// check above
		fastPos1.set(pos).add(Direction.UP);
		if (BlockUtils.isLiquid(fastPos1) || BlockUtils.hasGravity(fastPos1)) {
			return false;
		}

		// check north
		fastPos1.set(pos).add(Direction.NORTH);
		if (BlockUtils.isLiquid(fastPos1)) {
			return false;
		}

		// check east
		fastPos1.set(pos).add(Direction.EAST);
		if (BlockUtils.isLiquid(fastPos1)) {
			return false;
		}

		// check south
		fastPos1.set(pos).add(Direction.SOUTH);
		if (BlockUtils.isLiquid(fastPos1)) {
			return false;
		}

		// check west
		fastPos1.set(pos).add(Direction.WEST);
		if (BlockUtils.isLiquid(fastPos1)) {
			return false;
		}

		return true;
	}




	/**
	 * @param heightInBlocks the height of the fall in blocks
	 * @return the amount of damage the player would receive from falling the given height
	 */
	public static int calculateFallDamage(int heightInBlocks) {
		final int fatalHeight = heightInBlocks - 3;
		return Math.max(fatalHeight, 0);
	}


}
