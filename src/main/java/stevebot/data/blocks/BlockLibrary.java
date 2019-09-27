package stevebot.data.blocks;

import net.minecraft.block.Block;

public interface BlockLibrary {


	int ID_UNLOADED_BOCK = -1;
	int ID_INVALID_BLOCK = -2;

	/**
	 * Initialize this library.
	 */
	void initialize();

	/**
	 * @return the block with the given id or null.
	 */
	Block getBlockById(int id);

	/**
	 * @return the block with the given name or null.
	 */
	Block getBlockByName(String name);

	/**
	 * @return the id of the given block
	 */
	int getIdOfBlock(Block block);

	/**
	 * @return the id of the block with the given name or -1.
	 */
	int getIdFromName(String name);

	/**
	 * @return the name of the given block.
	 */
	String getNameOfBlock(Block block);

	/**
	 * @return the name of the block with the given id or "null".
	 */
	String getNameFromId(int id);

}
