package stevebot.player;

import stevebot.data.items.wrapper.ItemWrapper;

public interface PlayerInventory {


	/**
	 * Selects a throwaway-block in the hotbar.
	 *
	 * @return whether a throwaway-block was selected
	 */
	boolean selectThrowawayBlock();

	/**
	 * Selects the slot containing the given item
	 *
	 * @param item the item to select
	 * @return true, if the item was selected
	 */
	boolean selectItem(ItemWrapper item);


}
