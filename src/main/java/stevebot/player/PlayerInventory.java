package stevebot.player;

import net.minecraft.item.ItemStack;

public interface PlayerInventory {


	/**
	 * @return the current {@link ItemStack} or {@link ItemStack#EMPTY}.
	 */
	ItemStack getCurrentItem();

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


}
