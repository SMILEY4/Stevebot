package stevebot.player;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import stevebot.data.items.InventorySnapshot;

public class PlayerInventoryImpl implements PlayerInventory {


	private InventorySnapshot currentSnapshot = null;



	@Override
	public InventorySnapshot createSnapshotFromPlayerEntity() {
		final InventoryPlayer inventory = PlayerUtils.getPlayer().inventory;
		final InventorySnapshot snapshot = new InventorySnapshot();
		for (int i = 0; i < 9; i++) {
			final ItemStack item = inventory.getStackInSlot(i);
			if (item != ItemStack.EMPTY) {
				snapshot.setHotbarItemStack(i, item);
			}
		}
		return snapshot;
	}




	@Override
	public InventorySnapshot getCurrentSnapshot() {
		return currentSnapshot;
	}




	@Override
	public void setCurrentSnapshot(InventorySnapshot snapshot) {
		this.currentSnapshot = snapshot;
	}




	//
//	@Override
//	public boolean hasThrowawayBlockInHotbar() {
//		final InventoryPlayer inventory = PlayerUtils.getPlayer().inventory;
//		for (int i = 0; i < 9; i++) {
//			final ItemStack stack = inventory.getStackInSlot(i);
//			if (!stack.isEmpty() && stack.getItem() instanceof ItemBlock) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//
//
//
	@Override
	public boolean selectThrowawayBlock() {
		final InventoryPlayer inventory = PlayerUtils.getPlayer().inventory;
		for (int i = 0; i < 9; i++) {
			final ItemStack stack = inventory.getStackInSlot(i);
			if (!stack.isEmpty() && stack.getItem() instanceof ItemBlock) {
				inventory.currentItem = i;
				return true;
			}
		}
		return false;
	}


}
