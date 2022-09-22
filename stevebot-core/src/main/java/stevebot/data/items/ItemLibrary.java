package stevebot.data.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemTool;
import stevebot.data.blocks.BlockWrapper;
import stevebot.data.items.wrapper.ItemBlockWrapper;
import stevebot.data.items.wrapper.ItemHandWrapper;
import stevebot.data.items.wrapper.ItemToolWrapper;
import stevebot.data.items.wrapper.ItemWrapper;
import stevebot.math.MathUtils;
import stevebot.minecraft.MinecraftAdapter;

public class ItemLibrary {


    public static final ItemWrapper INVALID_ITEM = new ItemWrapper(ItemLibrary.ID_INVALID_ITEM, "null", null);
    public static final ItemHandWrapper ITEM_HAND = new ItemHandWrapper();
    public static final int ID_INVALID_ITEM = -2;

    private final MinecraftAdapter minecraftAdapter;
    private ItemWrapper[] items;

    public ItemLibrary(final MinecraftAdapter minecraftAdapter) {
        this.minecraftAdapter = minecraftAdapter;
    }

    /**
     * Initializes this library. Fetches all items from the {@link Item#REGISTRY} and stores them.
     */
    public void onEventInitialize() {

        List<ItemWrapper> itemList = new ArrayList<>();
        int maxId = 0;
        for (Item item : minecraftAdapter.getRegisteredItems()) {

            final int id = getId(item);
            final String name = getName(item);
            maxId = Math.max(maxId, id);

            if (item instanceof ItemBlock) {
                itemList.add(new ItemBlockWrapper(id, name, (ItemBlock) item));
            } else if (item instanceof ItemTool) {
                itemList.add(new ItemToolWrapper(id, name, (ItemTool) item));
            } else {
                itemList.add(new ItemWrapper(id, name, item));
            }
        }

        items = new ItemWrapper[maxId + 1];
        Arrays.fill(items, INVALID_ITEM);
        for (ItemWrapper item : itemList) {
            items[item.getId()] = item;
        }

    }


    /**
     * Adds the given {@link BlockWrapper}s to the corresponding items
     *
     * @param blocks the blocks to add
     */
    public void insertBlocks(List<BlockWrapper> blocks) {
        for (ItemWrapper itemWrapper : items) {
            if (itemWrapper.getId() != ItemLibrary.ID_INVALID_ITEM && itemWrapper instanceof ItemBlockWrapper) {
                final ItemBlockWrapper item = (ItemBlockWrapper) itemWrapper;
                final ItemBlock itemBlock = (ItemBlock) item.getItem();
                final int blockIdFromItem = minecraftAdapter.getBlockId(itemBlock.getBlock());
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
     * @param item the minecraft-{@link Item}
     * @return the {@link ItemWrapper} representing the given {@link Item}
     */
    public ItemWrapper getItemByMCItem(Item item) {
        return getItemByIndex(getId(item));
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
     * @param item the {@link Item}
     * @return the name of the given item.
     */
    private int getId(Item item) {
        return minecraftAdapter.getItemId(item);
    }


    /**
     * @param item the {@link Item}
     * @return the id of the given item
     */
    private String getName(Item item) {
        return minecraftAdapter.getItemName(item);
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
