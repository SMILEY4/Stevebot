package stevebot.data.items;

import net.minecraft.item.Item;
import stevebot.events.EventListener;
import stevebot.events.PostInitEvent;

public interface ItemLibrary {


	ItemWrapper INVALID_ITEM = new ItemWrapper(ItemLibrary.ID_INVALID_ITEM, "null", null);

	int ID_INVALID_ITEM = -2;


	/**
	 * The given listener listens for a {@link PostInitEvent} and initializes the library on this event.
	 *
	 * @return the {@link EventListener} of this {@link ItemLibrary}.
	 */
	EventListener getListener();

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
