package stevebot.data.blocks;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import stevebot.pathfinding.actions.playeractions.BlockChange;

public interface BlockProvider {


	/**
	 * @return true, if the given position is in a loaded chunk.
	 */
	boolean isLoaded(BlockPos pos);

	/**
	 * @return the {@link Block} at the given position.
	 */
	Block getBlockAt(BlockPos pos);

	/**
	 * @return the {@link Block} at the given position.
	 */
	Block getBlockAt(int x, int y, int z);

	/**
	 * @return the id of the block at the given position.
	 */
	int getBlockIdAt(BlockPos pos);

	/**
	 * @return the id of the block at the given position.
	 */
	int getBlockIdAt(int x, int y, int z);

	/**
	 * Temporarily changes a block at a specified position without placing/breaking it in the world.
	 *
	 * @param change           the change to add.
	 * @param overrideExisting true, to override any existing block change at that same position.
	 */
	void addBlockChange(BlockChange change, boolean overrideExisting);

	/**
	 * Removes/Resets all temporary block changes.
	 */
	void clearBlockChanges();

	/**
	 * @return the underlying {@link BlockCache}.
	 */
	BlockCache getBlockCache();


}
