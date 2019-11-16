package stevebot.player;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import stevebot.data.blocks.BlockUtils;
import stevebot.data.blocks.BlockWrapper;
import stevebot.data.items.ItemUtils;
import stevebot.data.items.wrapper.ItemBlockWrapper;
import stevebot.data.items.wrapper.ItemWrapper;

public class PlayerInventoryImpl implements PlayerInventory {


	@Override
	public boolean selectThrowawayBlock(boolean allowGravityBlock) {
		final InventoryPlayer inventory = PlayerUtils.getPlayer().inventory;
		for (int i = 0; i < 9; i++) {
			final ItemStack stack = inventory.getStackInSlot(i);
			if (!stack.isEmpty() && stack.getItem() instanceof ItemBlock) {
				if (allowGravityBlock) {
					inventory.currentItem = i;
					return true;
				} else {
					final ItemWrapper itemWrapper = ItemUtils.getItemLibrary().getItemByMCItem(stack.getItem());
					if (itemWrapper instanceof ItemBlockWrapper) {
						final BlockWrapper block = ((ItemBlockWrapper) itemWrapper).getBlockWrapper();
						if (block != null && !BlockUtils.hasGravity(block)) {
							inventory.currentItem = i;
							return true;
						}
					}
				}
			}
		}
		return false;
	}




	@Override
	public int findItem(ItemWrapper item) {
		final InventoryPlayer inventory = PlayerUtils.getPlayer().inventory;
		for (int i = 0; i < 9; i++) {
			final ItemStack stack = inventory.getStackInSlot(i);
			if (!stack.isEmpty()) {
				if (ItemUtils.getItemLibrary().getItemByMCItem(stack.getItem()).getId() == item.getId()) {
					return i;
				}
			}
		}
		return -1;
	}




	@Override
	public boolean selectItem(ItemWrapper item) {
		final InventoryPlayer inventory = PlayerUtils.getPlayer().inventory;
		final int slot = findItem(item);
		if (slot != -1) {
			inventory.currentItem = slot;
			return true;
		} else {
			return false;
		}
	}




	@Override
	public boolean hasItem(ItemWrapper item) {
		return findItem(item) != -1;
	}


}
