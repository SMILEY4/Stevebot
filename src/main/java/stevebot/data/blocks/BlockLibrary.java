package stevebot.data.blocks;

import net.minecraft.block.Block;
import stevebot.events.EventListener;
import stevebot.events.PostInitEvent;

public interface BlockLibrary {


	BlockWrapper INVALID_BLOCK = new BlockWrapper(BlockLibrary.ID_INVALID_BLOCK, "null", null);

	int ID_UNLOADED_BOCK = -1;
	int ID_INVALID_BLOCK = -2;

	/**
	 * The given listener listens for a {@link PostInitEvent} and initializes the library on this event.
	 *
	 * @return the {@link EventListener} of this {@link BlockLibrary}.
	 */
	EventListener getListener();

	/**
	 * @return the {@link BlockWrapper} representing the given {@link Block}
	 */
	BlockWrapper getBlockByMCBlock(Block block);

	/**
	 * @return the block with the given id or null.
	 */
	BlockWrapper getBlockById(int id);

	/**
	 * @return the block with the given name or null.
	 */
	BlockWrapper getBlockByName(String name);

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
