package stevebot.player;

import stevebot.data.items.InventorySnapshot;
import stevebot.data.items.wrapper.ItemWrapper;

public interface PlayerInventory {


	/**
	 * @return a new {@link InventorySnapshot} with the current content of the player
	 */
	InventorySnapshot createSnapshotFromPlayerEntity();

	/**
	 * @return the {@link InventorySnapshot} set with {@link PlayerInventory#setCurrentSnapshot(InventorySnapshot)}
	 */
	InventorySnapshot getCurrentSnapshot();

	/**
	 * @param snapshot sets the snapshot. This snapshot can be retrieved with {@link PlayerInventory#getCurrentSnapshot()}
	 */
	void setCurrentSnapshot(InventorySnapshot snapshot);

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
