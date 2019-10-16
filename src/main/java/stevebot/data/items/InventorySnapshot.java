package stevebot.data.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import stevebot.data.blocks.BlockLibrary;
import stevebot.data.blocks.BlockWrapper;
import stevebot.data.modification.BlockBreakModification;
import stevebot.data.modification.BlockPlaceModification;
import stevebot.data.modification.Modification;

public class InventorySnapshot {


	private final ItemStack[] itemsHotbar;
	private final int[] idsHotbar;




	public InventorySnapshot() {
		itemsHotbar = new ItemStack[9];
		idsHotbar = new int[9];
	}




	public InventorySnapshot(InventorySnapshot snapshot) {
		itemsHotbar = new ItemStack[9];
		idsHotbar = new int[9];
		for (int i = 0; i < 9; i++) {
			setHotbarItemStack(i, new ItemStack(snapshot.itemsHotbar[i].getItem(), snapshot.itemsHotbar[i].getCount(), snapshot.itemsHotbar[i].getMetadata()));
		}
	}




	public void setHotbarItemStack(int slot, ItemStack itemStack) {
		itemsHotbar[slot] = itemStack;
		idsHotbar[slot] = ItemUtils.getItemLibrary().getItemByMCItem(itemStack.getItem()).id;
	}




	public void applyModification(Modification modification) {
		if (modification instanceof BlockPlaceModification) {
			final BlockPlaceModification placeModification = (BlockPlaceModification) modification;

			if (placeModification.usedThrowaway()) {
				final int slot = findThrowawayBlock();
				if (slot != -1) {
					if (itemsHotbar[slot].getCount() == 1) {
						itemsHotbar[slot] = null;
					} else {
						itemsHotbar[slot] = new ItemStack(itemsHotbar[slot].getItem(), itemsHotbar[slot].getCount() - 1);
					}
				}

			} else {
				final int slot = findItem(placeModification.getBlock().getItem().item);
				if (slot != -1) {
					if (itemsHotbar[slot].getCount() == 1) {
						itemsHotbar[slot] = null;
					} else {
						itemsHotbar[slot] = new ItemStack(itemsHotbar[slot].getItem(), itemsHotbar[slot].getCount() - 1);
					}
				}
			}
		}

		if (modification instanceof BlockBreakModification) {
			final BlockBreakModification breakModification = (BlockBreakModification) modification;
			// todo
		}
	}




	public int findThrowawayBlock() {
		for (int i = 0; i < 9; i++) {
			final ItemStack stack = itemsHotbar[i];
			if (stack != null && !stack.isEmpty() && stack.getItem() instanceof ItemBlock) {
				return i;
			}
		}
		return -1;
	}




	public int findItem(Item item) {
		final int searchId = ItemUtils.getItemLibrary().getItemByMCItem(item).id;
		for (int i = 0; i < 9; i++) {
			final ItemStack stack = itemsHotbar[i];
			if (stack != null && !stack.isEmpty() && searchId == idsHotbar[i]) {
				return i;
			}
		}
		return -1;
	}




	public boolean hasThrowawayBlockInHotbar() {
		return findThrowawayBlock() != -1;
	}




	public BlockWrapper getAsBlock(int slot) {
		final ItemStack item = itemsHotbar[slot];
		if (item != null) {
			final int id = idsHotbar[slot];
			return ItemUtils.getItemLibrary().getItemById(id).getBlock();
		} else {
			return BlockLibrary.INVALID_BLOCK;
		}
	}

}
