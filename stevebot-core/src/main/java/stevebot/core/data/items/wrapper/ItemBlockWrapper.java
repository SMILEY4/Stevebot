package stevebot.core.data.items.wrapper;

import stevebot.core.data.blocks.BlockLibrary;
import stevebot.core.data.blocks.BlockWrapper;

public class ItemBlockWrapper extends ItemWrapper {


    private BlockWrapper block = BlockLibrary.INVALID_BLOCK;


    /**
     * @param id   the id of the item
     * @param name the name of the item
     */
    public ItemBlockWrapper(int id, String name) {
        super(id, name);
    }


    /**
     * @param block sets the block corresponding to this item
     */
    public void setBlockWrapper(BlockWrapper block) {
        this.block = block;
    }


    /**
     * @return the block associated with this item
     */
    public BlockWrapper getBlockWrapper() {
        return block;
    }

}
