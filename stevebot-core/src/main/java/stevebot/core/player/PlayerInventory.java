package stevebot.core.player;

import java.util.List;
import stevebot.core.data.blocks.BlockUtils;
import stevebot.core.data.blocks.BlockWrapper;
import stevebot.core.data.items.wrapper.ItemBlockWrapper;
import stevebot.core.data.items.wrapper.ItemStackWrapper;
import stevebot.core.data.items.wrapper.ItemWrapper;
import stevebot.core.minecraft.MinecraftAdapter;

public class PlayerInventory {

    private final MinecraftAdapter minecraftAdapter;

    public PlayerInventory(final MinecraftAdapter minecraftAdapter) {
        this.minecraftAdapter = minecraftAdapter;
    }

    /**
     * Selects a throwaway-block in the hotbar.
     *
     * @param allowGravityBlock true, to include blocks that have gravity, like sand or gravel
     * @return whether a throwaway-block was selected
     */
    public boolean selectThrowawayBlock(boolean allowGravityBlock) {
        for (ItemStackWrapper stack : minecraftAdapter.getHotbarItems()) {
            if (stack.getItem().isBlock()) {
                if (allowGravityBlock) {
                    minecraftAdapter.selectHotbarSlot(stack.getSlot());
                    return true;
                } else {
                    final ItemWrapper itemWrapper = stack.getItem();
                    if (itemWrapper instanceof ItemBlockWrapper) {
                        final BlockWrapper block = ((ItemBlockWrapper) itemWrapper).getBlockWrapper();
                        if (block != null && !BlockUtils.hasGravity(block)) {
                            minecraftAdapter.selectHotbarSlot(stack.getSlot());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    /**
     * Searches for the given item in the players hotbar
     *
     * @param item the item to find
     * @return the slot with the given item or -1
     */
    public int findItem(ItemWrapper item) {
        List<ItemStackWrapper> items = minecraftAdapter.getHotbarItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getItem().getId() == item.getId()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Selects the slot containing the given item
     *
     * @param item the item to select
     * @return true, if the item was selected
     */
    public boolean selectItem(ItemWrapper item) {
        final int slot = findItem(item);
        if (slot != -1) {
            minecraftAdapter.selectHotbarSlot(slot);
            return true;
        } else {
            return false;
        }
    }


    /**
     * Check if the given item is in the players hotbar
     *
     * @param item the item to check
     * @return true, if the item is in the hotbar
     */
    public boolean hasItem(ItemWrapper item) {
        return findItem(item) != -1;
    }

}
