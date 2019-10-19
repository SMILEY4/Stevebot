package stevebot.data.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;
import stevebot.data.blocks.BlockLibrary;
import stevebot.data.blocks.BlockWrapper;
import stevebot.data.items.wrapper.ItemBlockWrapper;
import stevebot.data.items.wrapper.ItemWrapper;
import stevebot.data.modification.BlockBreakModification;
import stevebot.data.modification.BlockPlaceModification;
import stevebot.data.modification.Modification;

public class InventorySnapshot {


	private final ItemWrapper[] itemsHotbar = new ItemWrapper[9];
	private final int[] stackSizes = new int[9];




	public InventorySnapshot() {
	}




	public InventorySnapshot(InventorySnapshot snapshot) {
		for (int i = 0; i < 9; i++) {
			setHotbarItemStack(i, snapshot.itemsHotbar[i], snapshot.stackSizes[i]);
		}
	}




	public void setHotbarItemStack(int slot, ItemWrapper item, int stackSize) {
		itemsHotbar[slot] = item;
		stackSizes[slot] = stackSize;
	}




	public ItemWrapper getItem(int slot) {
		return itemsHotbar[slot];
	}




	public int getStackSize(int slot) {
		return stackSizes[slot];
	}




	public void applyModification(Modification modification) {

		if (modification instanceof BlockPlaceModification) {
			final BlockPlaceModification placeModification = (BlockPlaceModification) modification;

			final int slot = findSlotById(placeModification.getBlock().getItem().getId());
			if (slot != -1) {
				if (stackSizes[slot] == 1) {
					itemsHotbar[slot] = null;
					stackSizes[slot] = 0;
				} else {
					stackSizes[slot] -= 1;
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
			final ItemWrapper stack = itemsHotbar[i];
			if (stack != null && stackSizes[i] != 0 && stack instanceof ItemBlockWrapper) {
				return i;
			}
		}
		return -1;
	}




	public int findSlotByItem(ItemWrapper item) {
		return findSlotById(item.getId());
	}




	public int findSlotByMCItem(Item item) {
		final int searchId = ItemUtils.getItemLibrary().getItemByMCItem(item).getId();
		return findSlotById(searchId);
	}




	public int findSlotById(int id) {
		for (int i = 0; i < 9; i++) {
			final ItemWrapper stack = itemsHotbar[i];
			if (stack != null && stackSizes[i] != 0 && id == stack.getId()) {
				return i;
			}
		}
		return -1;
	}




	public boolean hasThrowawayBlockInHotbar() {
		return findThrowawayBlock() != -1;
	}




	public BlockWrapper getAsBlock(int slot) {
		final ItemWrapper item = itemsHotbar[slot];
		if (item != null) {
			if (item instanceof ItemBlockWrapper) {
				return ((ItemBlockWrapper) item).getBlockWrapper();
			} else {
				return BlockLibrary.INVALID_BLOCK;
			}
		} else {
			return BlockLibrary.INVALID_BLOCK;
		}
	}




	public ItemWrapper findBestToolForBlock(BlockWrapper block) {
		final int slot = findBestToolSlot(block);
		if (slot == -1) {
			return ItemLibrary.INVALID_ITEM;
		} else {
			return itemsHotbar[slot];
		}
	}




	public int findBestToolSlot(BlockWrapper block) {

		int slotBest = -1;
		float bestSpeed = 999999;

		for (int i = 0; i < 9; i++) {
			final ItemWrapper stack = itemsHotbar[i];
			if (stack != null && stackSizes[i] != 0 && stack.getItem() instanceof ItemTool) {
				final float breakTime = ItemUtils.getBreakDuration(stack.getStack(1), block.block.getDefaultState());
				if (bestSpeed > breakTime) {
					bestSpeed = breakTime;
					slotBest = i;
				}
			}
		}
		return slotBest;
	}

}
