package stevebot.data.items.wrapper;

import net.minecraft.item.ItemStack;
import stevebot.data.items.ItemLibrary;

public class ItemHandWrapper extends ItemToolWrapper {


	public ItemHandWrapper() {
		super(ItemLibrary.ID_INVALID_ITEM, "player_hand", null);
	}




	@Override
	public ItemStack getStack(int stackSize) {
		return ItemStack.EMPTY;
	}

}
