package stevebot.data.blocks;

import net.minecraft.block.Block;
import stevebot.data.items.wrapper.ItemWrapper;
import stevebot.events.EventListener;
import stevebot.events.PostInitEvent;

import java.util.List;

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
	 * Adds the given {@link ItemWrapper}s to the corresponding blocks
	 *
	 * @param blocks the items to add
	 */
	void insertItems(List<ItemWrapper> blocks);

	/**
	 * @return a list of all blocks
	 */
	List<BlockWrapper> getAllBlocks();

	/**
	 * @param block the minecraft-{@link Block}
	 * @return the {@link BlockWrapper} representing the given {@link Block}
	 */
	BlockWrapper getBlockByMCBlock(Block block);

	/**
	 * @param id the id of the block
	 * @return the block with the given id or null.
	 */
	BlockWrapper getBlockById(int id);

	/**
	 * @param name the name of the block in the format "minecraft:name-of-the-block"
	 * @return the block with the given name or null.
	 */
	BlockWrapper getBlockByName(String name);


}
