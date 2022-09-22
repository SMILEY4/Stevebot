package stevebot.data.blocks;

import stevebot.data.items.ItemLibrary;
import stevebot.data.items.wrapper.ItemWrapper;

public class BlockWrapper {


    private final int id;
    private final String name;
    private ItemWrapper item;


    /**
     * @param id   the id of the block
     * @param name the name of the block
     */
    public BlockWrapper(int id, String name) {
        this.id = id;
        this.name = name;
    }


    /**
     * @return the id of the block
     */
    public int getId() {
        return id;
    }


    /**
     * @return the name of the block ("minecraft:block_name")
     */
    public String getName() {
        return name;
    }

    public boolean isPassable() {
        return false; // TODO  getBlock().isPassable(minecraftAdapter.getWorld(), pos);
    }


    public boolean isNormalCube() {
        return false; // TODO getBlock().getDefaultState().isNormalCube()
    }

    /**
     * @param item sets the item corresponding to this block
     */
    public void setItem(ItemWrapper item) {
        this.item = item;
    }


    /**
     * @return whether this block has an item associated with it
     */
    public boolean hasItem() {
        return this.item.getId() != ItemLibrary.ID_INVALID_ITEM;
    }


    /**
     * @return the item associated with this block
     */
    public ItemWrapper getItem() {
        return item;
    }


}
