package stevebot.player;

import net.minecraft.item.ItemStack;
import stevebot.minecraft.MinecraftAdapter;

public class PlayerInventoryImpl implements PlayerInventory {


	@Override
	public ItemStack getCurrentItem() {
		return MinecraftAdapter.get().getPlayerInventory().getCurrentItem();
	}




	@Override
	public ItemStack getItem(int index) {
		return MinecraftAdapter.get().getPlayerInventory().getStackInSlot(index);
	}




	@Override
	public void selectSlot(int index) {
		MinecraftAdapter.get().getPlayerInventory().currentItem = index;
	}


}
