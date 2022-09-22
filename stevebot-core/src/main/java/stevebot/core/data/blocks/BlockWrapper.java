package stevebot.core.data.blocks;

import stevebot.core.data.items.ItemLibrary;
import stevebot.core.data.items.wrapper.ItemWrapper;

public class BlockWrapper {


    private final int id;
    private final String name;
    private final boolean isNormalCube;
    private ItemWrapper item;

    /**
     * @param id   the id of the block
     * @param name the name of the block
     */
    public BlockWrapper(int id, String name, boolean isNormalCube) {
        this.id = id;
        this.name = name;
        this.isNormalCube = isNormalCube;
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


    public boolean isNormalCube() {
        return isNormalCube;
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
