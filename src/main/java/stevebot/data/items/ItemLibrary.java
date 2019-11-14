package stevebot.data.items;

import net.minecraft.item.Item;
import stevebot.data.blocks.BlockWrapper;
import stevebot.data.items.wrapper.ItemHandWrapper;
import stevebot.data.items.wrapper.ItemWrapper;
import stevebot.events.EventListener;
import stevebot.events.PostInitEvent;

import java.util.List;

public interface ItemLibrary {


	ItemWrapper INVALID_ITEM = new ItemWrapper(ItemLibrary.ID_INVALID_ITEM, "null", null);
	ItemHandWrapper ITEM_HAND = new ItemHandWrapper();


	int ID_INVALID_ITEM = -2;


	/**
	 * The given listener listens for a {@link PostInitEvent} and initializes the library on this event.
	 *
	 * @return the {@link EventListener} of this {@link ItemLibrary}.
	 */
	EventListener getListener();

	/**
	 * Adds the given {@link BlockWrapper}s to the corresponding items
	 *
	 * @param blocks the blocks to add
	 */
	void insertBlocks(List<BlockWrapper> blocks);

	/**
	 * @return a list of all items
	 */
	List<ItemWrapper> getAllItems();

	/**
	 * @param item the minecraft-{@link Item}
	 * @return the {@link ItemWrapper} representing the given {@link Item}
	 */
	ItemWrapper getItemByMCItem(Item item);

	/**
	 * @param id the id of the item
	 * @return the item with the given id or null.
	 */
	ItemWrapper getItemById(int id);

	/**
	 * @param name the name of the item in the format "minecraft:name-of-the-item"
	 * @return the item with the given name or null.
	 */
	ItemWrapper getItemByName(String name);

}
