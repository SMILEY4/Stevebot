package stevebot.data.blocks;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

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
	 * @return the underlying {@link BlockCache}.
	 */
	BlockCache getBlockCache();

}
