package stevebot.core.data.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import stevebot.core.data.blocks.BlockWrapper;
import stevebot.core.data.items.wrapper.ItemBlockWrapper;
import stevebot.core.data.items.wrapper.ItemHandWrapper;
import stevebot.core.data.items.wrapper.ItemWrapper;
import stevebot.core.math.MathUtils;
import stevebot.core.minecraft.MinecraftAdapter;

public class ItemLibrary {

    public static final ItemWrapper INVALID_ITEM = new ItemWrapper(ItemLibrary.ID_INVALID_ITEM, "null");
    public static final ItemHandWrapper ITEM_HAND = new ItemHandWrapper();
    public static final int ID_INVALID_ITEM = -2;

    private final MinecraftAdapter minecraftAdapter;
    private ItemWrapper[] items;

    public ItemLibrary(final MinecraftAdapter minecraftAdapter) {
        this.minecraftAdapter = minecraftAdapter;
    }

    /**
     * Initializes this library.
     */
    public void onEventInitialize() {

        List<ItemWrapper> itemList = minecraftAdapter.getItems();
        int maxId = 0;
        for (ItemWrapper item : itemList) {
            maxId = Math.max(maxId, item.getId());
        }

        items = new ItemWrapper[maxId + 1];
        Arrays.fill(items, INVALID_ITEM);
        for (ItemWrapper item : itemList) {
            items[item.getId()] = item;
        }

    }

    public void insertBlocks(List<BlockWrapper> blocks) {
        for (ItemWrapper itemWrapper : items) {
            if (itemWrapper.getId() != ItemLibrary.ID_INVALID_ITEM && itemWrapper instanceof ItemBlockWrapper) {
                final ItemBlockWrapper item = (ItemBlockWrapper) itemWrapper;
                final int blockIdFromItem = minecraftAdapter.getBlockIdFromItem(item);
                for (BlockWrapper block : blocks) {
                    if (block.getId() == blockIdFromItem) {
                        item.setBlockWrapper(block);
                        break;
                    }
                }
            }
        }
    }


    /**
     * @return a list of all items
     */
    public List<ItemWrapper> getAllItems() {
        return new ArrayList<>(Arrays.asList(items));
    }


    /**
     * @param id the id of the item
     * @return the item with the given id or null.
     */
    public ItemWrapper getItemById(int id) {
        return getItemByIndex(id);
    }


    /**
     * @param name the name of the item in the format "minecraft:name-of-the-item"
     * @return the item with the given name or null.
     */
    public ItemWrapper getItemByName(String name) {
        return getItemByIndex(getIdFromName(name));
    }


    /**
     * @param name the name of the item in the format "minecraft:name-of-the-item"
     * @return the id of the item with the given name or {@link ItemLibrary#ID_INVALID_ITEM}.
     */
    private int getIdFromName(String name) {
        for (int i = 0, n = items.length; i < n; i++) {
            final ItemWrapper entry = items[i];
            if (entry.getName().equalsIgnoreCase(name)) {
                return entry.getId();
            }
        }
        return ItemLibrary.ID_INVALID_ITEM;
    }


    /**
     * @return the ItemWrapper at the given index or the {@code NULL_ENTRY}.
     */
    private ItemWrapper getItemByIndex(int index) {
        if (MathUtils.inRange(index, 0, items.length)) {
            return items[index];
        } else {
            return INVALID_ITEM;
        }
    }


}
