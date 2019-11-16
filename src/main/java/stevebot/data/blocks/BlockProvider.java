package stevebot.data.blocks;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.modification.Modification;

public interface BlockProvider {


	/**
	 * @param pos the position of the block
	 * @return true, if the given position is in a loaded chunk.
	 */
	boolean isLoaded(BaseBlockPos pos);


	/**
	 * @param pos the position of the block
	 * @return the {@link BlockWrapper} at the given position.
	 */
	BlockWrapper getBlockAt(BaseBlockPos pos);


	/**
	 * @param x the x-position of the block
	 * @param y the y-position of the block
	 * @param z the z-position of the block
	 * @return the {@link BlockWrapper} at the given position.
	 */
	BlockWrapper getBlockAt(int x, int y, int z);


	/**
	 * @param pos the position of the block
	 * @return the id of the block at the given position.
	 */
	int getBlockIdAt(BaseBlockPos pos);

	/**
	 * @param x the x-position of the block
	 * @param y the y-position of the block
	 * @param z the z-position of the block
	 * @return the id of the block at the given position.
	 */
	int getBlockIdAt(int x, int y, int z);

	/**
	 * Temporarily changes a block at a specified position without placing/breaking it in the real world.
	 *
	 * @param modification     the modification to add.
	 * @param overrideExisting true, to override any existing modification at that same position.
	 */
	void addModification(Modification modification, boolean overrideExisting);

	/**
	 * Removes/Resets all temporary block changes.
	 */
	void clearBlockChanges();

	/**
	 * @return the underlying {@link BlockCache}.
	 */
	BlockCache getBlockCache();


}
