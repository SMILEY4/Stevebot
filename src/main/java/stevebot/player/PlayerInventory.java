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
	 * Selects the slot containing the given item
	 *
	 * @param item the item to select
	 * @return true, if the item was selected
	 */
	boolean selectItem(ItemWrapper item);


}
