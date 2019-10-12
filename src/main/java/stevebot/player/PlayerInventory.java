package stevebot.player;

import net.minecraft.item.ItemStack;
import stevebot.player.inventory.InventoryChange;
import stevebot.player.inventory.InventorySlot;

import java.util.List;

public interface PlayerInventory {


	/**
	 * @return a list of all {@link InventorySlot}s in the hotbar containing items
	 */
	List<InventorySlot> getHotbarItems();

	/**
	 * @param index the index
	 * @return the {@link ItemStack} at the given index or {@link ItemStack#EMPTY}.
	 */
	ItemStack getItem(int index);

	/**
	 * selects the slot with the given index
	 *
	 * @param index the index
	 */
	void selectSlot(int index);

	/**
	 * Removes/Resets all temporary inventory changes.
	 */
	void clearInventoryChanges();

	/**
	 * Temporarily changes an slot in the inventory without changing it in the real world.
	 *
	 * @param change the change to add.
	 */
	void addInventoryChange(InventoryChange change);

}
