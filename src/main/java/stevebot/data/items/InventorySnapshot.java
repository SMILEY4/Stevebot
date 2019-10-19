package stevebot.data.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import stevebot.data.blocks.BlockLibrary;
import stevebot.data.blocks.BlockWrapper;
import stevebot.data.items.wrapper.ItemBlockWrapper;
import stevebot.data.items.wrapper.ItemWrapper;
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
		idsHotbar[slot] = ItemUtils.getItemLibrary().getItemByMCItem(itemStack.getItem()).getId();
	}




	public void applyModification(Modification modification) {
		if (modification instanceof BlockPlaceModification) {
			final BlockPlaceModification placeModification = (BlockPlaceModification) modification;

			final int slot = findItem(placeModification.getBlock().getItem().getItem());
			if (slot != -1) {
				if (itemsHotbar[slot].getCount() == 1) {
					itemsHotbar[slot] = null;
				} else {
					itemsHotbar[slot] = new ItemStack(itemsHotbar[slot].getItem(), itemsHotbar[slot].getCount() - 1);
				}
			}
		}

		if (modification instanceof BlockBreakModification) {
			final BlockBreakModification breakModification = (BlockBreakModification) modification;
			if (breakModification.usedTool()) {
				// todo
			} else {
				// todo
			}
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
		final int searchId = ItemUtils.getItemLibrary().getItemByMCItem(item).getId();
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
			final ItemWrapper itemWrapper = ItemUtils.getItemLibrary().getItemById(id);
			if (itemWrapper instanceof ItemBlockWrapper) {
				return ((ItemBlockWrapper) itemWrapper).getBlockWrapper();
			} else {
				return BlockLibrary.INVALID_BLOCK;
			}
		} else {
			return BlockLibrary.INVALID_BLOCK;
		}
	}




	public ItemWrapper findBestTool(BlockWrapper block) {
		final int slot = findBestToolSlot(block);
		if (slot == -1) {
			return ItemLibrary.INVALID_ITEM;
		} else {
			return ItemUtils.getItemLibrary().getItemById(idsHotbar[slot]);
		}
	}




	public int findBestToolSlot(BlockWrapper block) {

		int slotBest = -1;
		float bestSpeed = 999999;

		for (int i = 0; i < 9; i++) {
			final ItemStack stack = itemsHotbar[i];
			if (stack != null && !stack.isEmpty() && stack.getItem() instanceof ItemTool) {
				final float breakTime = ItemUtils.getBreakDuration(stack, block.block.getDefaultState());
				if (bestSpeed > breakTime) {
					bestSpeed = breakTime;
					slotBest = i;
				}
			}
		}
		return slotBest;
	}

}
