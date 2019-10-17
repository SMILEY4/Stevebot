package stevebot.player;

import stevebot.data.items.InventorySnapshot;

public interface PlayerInventory {


	InventorySnapshot createSnapshotFromPlayerEntity();

	InventorySnapshot getCurrentSnapshot();

	void setCurrentSnapshot(InventorySnapshot snapshot);

	/**
	 * Selects a throwaway-block in the hotbar.
	 *
	 * @return whether a throwaway-block was selected
	 */
	boolean selectThrowawayBlock();



}
