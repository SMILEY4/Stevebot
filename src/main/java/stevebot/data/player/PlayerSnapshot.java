package stevebot.data.player;

import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;
import stevebot.data.blocks.BlockLibrary;
import stevebot.data.blocks.BlockUtils;
import stevebot.data.blocks.BlockWrapper;
import stevebot.data.items.ItemLibrary;
import stevebot.data.items.ItemUtils;
import stevebot.data.items.wrapper.ItemBlockWrapper;
import stevebot.data.items.wrapper.ItemHandWrapper;
import stevebot.data.items.wrapper.ItemToolWrapper;
import stevebot.data.items.wrapper.ItemWrapper;
import stevebot.data.modification.BlockBreakModification;
import stevebot.data.modification.BlockPlaceModification;
import stevebot.data.modification.HealthChangeModification;
import stevebot.data.modification.Modification;
import stevebot.minecraft.MinecraftAdapter;
import stevebot.pathfinding.actions.ActionCosts;

public class PlayerSnapshot {


	// health
	private int health;

	// inventory
	private final ItemWrapper[] hotbarItems = new ItemWrapper[9];
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
		this.health = snapshot.health;
		for (int i = 0; i < 9; i++) {
			setHotbarItemStack(i, snapshot.hotbarItems[i], snapshot.hotbarStackSizes[i]);
		}
	}




	/**
	 * Applies the givne {@link Modification} to this snapshot
	 *
	 * @param modification the modification to apply
	 */
	public void applyModification(Modification modification) {

		if (modification instanceof BlockPlaceModification) {
			if (MinecraftAdapter.get().isPlayerCreativeMode()) {
				return;
			}

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

		if (modification instanceof HealthChangeModification) {
			final HealthChangeModification healthChangeModification = (HealthChangeModification) modification;
			this.health += healthChangeModification.getHealthChange();
		}
	}


	//==================//
	//      HEALTH      //
	//==================//




	/**
	 * Sets the health of the player (1hp = half a heart)
	 */
	public void setPlayerHealth(int health) {
		this.health = health;
	}




	/**
	 * @return the health of the player (1hp = half a heart)
	 */
	public int getPlayerHealth() {
		return health;
	}


	//==================//
	//    INVENTORY     //
	//==================//




	/**
	 * Sets the item in the given slot
	 *
	 * @param slot      the slot (0-8)
	 * @param item      the {@link ItemWrapper}
	 * @param stackSize the size of the stack
	 */
	public void setHotbarItemStack(int slot, ItemWrapper item, int stackSize) {
		hotbarItems[slot] = item;
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
		return hotbarItems[slot];
	}




	/**
	 * @param slot the slot (0-8)
	 * @return the size of the stack at the given slot or -1
	 */
	public int getStackSize(int slot) {
		return hotbarStackSizes[slot];
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
			final ItemWrapper stack = hotbarItems[i];
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
		final ItemWrapper item = hotbarItems[slot];
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
	 * @param allowGravityBlock true, to include blocks that have gravity, like sand or gravel
	 * @return the slot containing at least one throwaway block, or -1
	 */
	public int findThrowawayBlock(boolean allowGravityBlock) {
		for (int i = 0; i < 9; i++) {
			final ItemWrapper stack = hotbarItems[i];
			if (stack != null && hotbarStackSizes[i] != 0 && stack instanceof ItemBlockWrapper) {
				if (allowGravityBlock) {
					return i;
				} else {
					final BlockWrapper block = ((ItemBlockWrapper) stack).getBlockWrapper();
					if (block != null && !BlockUtils.hasGravity(block)) {
						return i;
					}
				}
			}
		}
		return -1;
	}




	/**
	 * @param allowGravityBlock true, to include blocks that have gravity, like sand or gravel
	 * @return whether a slot contains a throwaway block
	 */
	public boolean hasThrowawayBlockInHotbar(boolean allowGravityBlock) {
		return findThrowawayBlock(allowGravityBlock) != -1;
	}




	/**
	 * @return the slot containing a bucket with water, or -1
	 */
	public int findWaterBucketInHotbar() {
		final int ID_BUCKET_WATER = 326;
		for (int i = 0; i < 9; i++) {
			final ItemWrapper stack = hotbarItems[i];
			if (stack != null && hotbarStackSizes[i] != 0) {
				if (stack.getId() == ID_BUCKET_WATER) {
					return i;
				}
			}
		}
		return -1;
	}




	/**
	 * @return whether a slot contains a water bucket
	 */
	public boolean hasWaterBucketInHotbar() {
		return findWaterBucketInHotbar() != -1;
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
			return getAsTool(slot);
		}
	}




	/**
	 * @param block the block to break
	 * @return the slot containing the best item to break the given block or -1
	 */
	public int findBestToolSlot(BlockWrapper block) {
		int slotBest = -1;
		float bestSpeed = (float) ActionCosts.get().COST_INFINITE;
		for (int i = 0; i < 9; i++) {
			final ItemToolWrapper tool = getAsTool(i);
			final float breakTime = ItemUtils.getBreakDuration(tool.getStack(1), block.getBlock().getDefaultState());
			if (bestSpeed > breakTime) {
				bestSpeed = breakTime;
				slotBest = i;
			}
		}
		return slotBest;
	}




	/**
	 * @param slot the slot (0-8)
	 * @return the tool at the given slot. If the slot does not contain a tool, {@link ItemHandWrapper} is returned instead.
	 */
	public ItemToolWrapper getAsTool(int slot) {
		final ItemWrapper stack = hotbarItems[slot];
		if (stack != null && hotbarStackSizes[slot] != 0 && stack.getItem() instanceof ItemTool) {
			return (ItemToolWrapper) stack;
		} else {
			return ItemLibrary.ITEM_HAND;
		}
	}

}
