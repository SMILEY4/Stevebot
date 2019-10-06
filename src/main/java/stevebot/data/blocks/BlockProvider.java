package stevebot.data.blocks;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.pathfinding.actions.playeractions.BlockChange;

public interface BlockProvider {


	/**
	 * @return true, if the given position is in a loaded chunk.
	 */
	boolean isLoaded(BaseBlockPos pos);


	/**
	 * @return the {@link BlockWrapper} at the given position.
	 */
	BlockWrapper getBlockAt(BaseBlockPos pos);

	/**
	 * @return the {@link BlockWrapper} at the given position.
	 */
	BlockWrapper getBlockAt(int x, int y, int z);


	/**
	 * @return the id of the block at the given position.
	 */
	int getBlockIdAt(BaseBlockPos pos);

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
