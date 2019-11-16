package stevebot.player;

import stevebot.data.items.wrapper.ItemWrapper;

public interface PlayerInventory {


	/**
	 * Selects a throwaway-block in the hotbar.
	 *
	 * @param allowGravityBlock true, to include blocks that have gravity, like sand or gravel
	 * @return whether a throwaway-block was selected
	 */
	boolean selectThrowawayBlock(boolean allowGravityBlock);


	/**
	 * Searches for the given item in the players hotbar
	 *
	 * @param item the item to find
	 * @return the slot with the given item or -1
	 */
	int findItem(ItemWrapper item);

	/**
	 * Selects the slot containing the given item
	 *
	 * @param item the item to select
	 * @return true, if the item was selected
	 */
	boolean selectItem(ItemWrapper item);


	/**
	 * Check if the given item is in the players hotbar
	 *
	 * @param item the item to check
	 * @return true, if the item is in the hotbar
	 */
	boolean hasItem(ItemWrapper item);

}
