package stevebot.player;

import net.minecraft.item.ItemStack;
import stevebot.minecraft.MinecraftAdapter;
import stevebot.player.inventory.InventoryChange;
import stevebot.player.inventory.InventorySlot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PlayerInventoryImpl implements PlayerInventory {


	private Map<Integer, InventoryChange> inventoryChanges = new HashMap<>();




	@Override
	public List<InventorySlot> getHotbarItems() {
		final List<InventorySlot> items = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			final InventoryChange change = inventoryChanges.get(i);
			if (change == null) {
				items.add(change.newItem);
			} else {
				items.add(getItem(i));
			}
		}
		return items;
	}




	@Override
	public ItemStack getItem(int index) {
		return MinecraftAdapter.get().getPlayerInventory().getStackInSlot(index);
	}




	@Override
	public void selectSlot(int index) {
		MinecraftAdapter.get().getPlayerInventory().currentItem = index;
	}




	@Override
	public void clearInventoryChanges() {
		inventoryChanges.clear();
	}




	@Override
	public void addInventoryChange(InventoryChange change) {
		inventoryChanges.add(change);

	}


}
