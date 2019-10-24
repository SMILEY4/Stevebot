package stevebot.data.player;

import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;
import stevebot.data.blocks.BlockLibrary;
import stevebot.data.blocks.BlockWrapper;
import stevebot.data.items.ItemLibrary;
import stevebot.data.items.ItemUtils;
import stevebot.data.items.wrapper.ItemBlockWrapper;
import stevebot.data.items.wrapper.ItemWrapper;
import stevebot.data.modification.BlockBreakModification;
import stevebot.data.modification.BlockPlaceModification;
import stevebot.data.modification.Modification;

public class PlayerSnapshot {


	private final ItemWrapper[] itemsHotbar = new ItemWrapper[9];
	private final int[] hotbarStackSizes = new int[9];




	/**
	 * Creates a new empty snapshot
	 */
	public PlayerSnapshot() {
	}




	/**
	 * Creates a new snapshot with the same content as the given snapshot
	 */
	public PlayerSnapshot(PlayerSnapshot snapshot) {
		for (int i = 0; i < 9; i++) {
			setHotbarItemStack(i, snapshot.itemsHotbar[i], snapshot.hotbarStackSizes[i]);
		}
	}




	/**
	 * Sets the item in the given slot
	 *
	 * @param slot      the slot (0-8)
	 * @param item      the {@link ItemWrapper}
	 * @param stackSize the size of the stack
	 */
	public void setHotbarItemStack(int slot, ItemWrapper item, int stackSize) {
		itemsHotbar[slot] = item;
		hotbarStackSizes[slot] = stackSize;
	}




	/**
	 * Removes the item from the given slot
	 *
	 * @param slot the slot (0-8)
	 */
	public void clearSlot(int slot) {
		setHotbarItemStack(slot, null, -1);
	}




	/**
	 * @param slot the slot (0-8)
	 * @return the item at the given slot or null
	 */
	public ItemWrapper getItem(int slot) {
		return itemsHotbar[slot];
	}




	/**
	 * @param slot the slot (0-8)
	 * @return the size of the stack at the given slot or -1
	 */
	public int getStackSize(int slot) {
		return hotbarStackSizes[slot];
	}




	/**
	 * Applies the givne {@link Modification} to this snapshot
	 *
	 * @param modification the modification to apply
	 */
	public void applyModification(Modification modification) {

		if (modification instanceof BlockPlaceModification) {
			final BlockPlaceModification placeModification = (BlockPlaceModification) modification;

			final int slot = findSlotById(placeModification.getBlock().getItem().getId());
			if (slot != -1) {
				if (hotbarStackSizes[slot] == 1) {
					clearSlot(slot);
				} else {
					hotbarStackSizes[slot] -= 1;
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




	/**
	 * @param item the item to search for
	 * @return the slot with a same item as the given item or -1
	 */
	public int findSlotByItem(ItemWrapper item) {
		return findSlotById(item.getId());
	}




	/**
	 * @param item the item to search for
	 * @return the slot with a same item as the given item or -1
	 */
	public int findSlotByMCItem(Item item) {
		final int searchId = ItemUtils.getItemLibrary().getItemByMCItem(item).getId();
		return findSlotById(searchId);
	}




	/**
	 * @param id the item-id to search for
	 * @return the slot with an item with the id as the given id, or -1
	 */
	public int findSlotById(int id) {
		for (int i = 0; i < 9; i++) {
			final ItemWrapper stack = itemsHotbar[i];
			if (stack != null && hotbarStackSizes[i] != 0 && id == stack.getId()) {
				return i;
			}
		}
		return -1;
	}




	/**
	 * @param slot the slot (0-8)
	 * @return the item at the given slot as a {@link BlockWrapper}, or {@link BlockLibrary#INVALID_BLOCK}
	 */
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




	/**
	 * @return the slot containing at least one throwaway block, or -1
	 */
	public int findThrowawayBlock() {
		for (int i = 0; i < 9; i++) {
			final ItemWrapper stack = itemsHotbar[i];
			if (stack != null && hotbarStackSizes[i] != 0 && stack instanceof ItemBlockWrapper) {
				return i;
			}
		}
		return -1;
	}




	/**
	 * @return whether a slot contains a throwaway block
	 */
	public boolean hasThrowawayBlockInHotbar() {
		return findThrowawayBlock() != -1;
	}




	/**
	 * @param block the block to break
	 * @return the best item to break the given block or {@link ItemLibrary#INVALID_ITEM}
	 */
	public ItemWrapper findBestToolForBlock(BlockWrapper block) {
		final int slot = findBestToolSlot(block);
		if (slot == -1) {
			return ItemLibrary.INVALID_ITEM;
		} else {
			return itemsHotbar[slot];
		}
	}




	/**
	 * @param block the block to break
	 * @return the slot containing the best item to break the given block or -1
	 */
	public int findBestToolSlot(BlockWrapper block) {

		int slotBest = -1;
		float bestSpeed = 999999;

		for (int i = 0; i < 9; i++) {
			final ItemWrapper stack = itemsHotbar[i];
			if (stack != null && hotbarStackSizes[i] != 0 && stack.getItem() instanceof ItemTool) {
				final float breakTime = ItemUtils.getBreakDuration(stack.getStack(1), block.getBlock().getDefaultState());
				if (bestSpeed > breakTime) {
					bestSpeed = breakTime;
					slotBest = i;
				}
			}
		}
		return slotBest;
	}

}
