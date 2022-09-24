package stevebot.core.data.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import stevebot.core.data.items.ItemLibrary;
import stevebot.core.data.items.wrapper.ItemWrapper;
import stevebot.core.math.MathUtils;
import stevebot.core.minecraft.MinecraftAdapter;

public class BlockLibrary {

    public static final BlockWrapper INVALID_BLOCK = new BlockWrapper(BlockLibrary.ID_INVALID_BLOCK, "null", false);
    public static final int ID_UNLOADED_BOCK = -1;
    public static final int ID_INVALID_BLOCK = -2;

    private final MinecraftAdapter minecraftAdapter;
    private BlockWrapper[] blocks;


    public BlockLibrary(final MinecraftAdapter minecraftAdapter) {
        this.minecraftAdapter = minecraftAdapter;
    }


    /**
     * Initializes this library. Collects all blocks and stores them.
     */
    public void onEventInitialize() {

        List<BlockWrapper> blockList = minecraftAdapter.getBlocks();
        int maxID = 0;
        for (BlockWrapper block : blockList) {
            maxID = Math.max(maxID, block.getId());
        }

        blocks = new BlockWrapper[maxID + 1];
        Arrays.fill(blocks, INVALID_BLOCK);
        for (BlockWrapper block : blockList) {
            blocks[block.getId()] = block;
        }

    }


    /**
     * Adds the given {@link ItemWrapper}s to the corresponding blocks
     *
     * @param items the items to add
     */
    public void insertItems(List<ItemWrapper> items) {
        for (BlockWrapper block : blocks) {
            block.setItem(ItemLibrary.INVALID_ITEM);
            if (block.getId() != BlockLibrary.ID_INVALID_BLOCK) {
                final int itemIdFromBlock = minecraftAdapter.getItemIdFromBlock(block);
                for (ItemWrapper item : items) {
                    if (item.getId() == itemIdFromBlock) {
                        block.setItem(item);
                        break;
                    }
                }
            }
        }
    }


    /**
     * @return a list of all blocks
     */
    public List<BlockWrapper> getAllBlocks() {
        return new ArrayList<>(Arrays.asList(blocks));
    }


    /**
     * @param id the id of the block
     * @return the block with the given id or null.
     */
    public BlockWrapper getBlockById(int id) {
        return getBlockByIndex(id);
    }


    /**
     * @param name the name of the block in the format "minecraft:name-of-the-block"
     * @return the block with the given name or null.
     */
    public BlockWrapper getBlockByName(String name) {
        return getBlockByIndex(getIdFromName(name));
    }


    /**
     * @param name the name of the block in the format "minecraft:name-of-the-block"
     * @return the id of the block with the given name or {@link BlockLibrary#ID_INVALID_BLOCK}.
     */
    private int getIdFromName(String name) {
        for (int i = 0, n = blocks.length; i < n; i++) {
            final BlockWrapper entry = blocks[i];
            if (entry.getName().equalsIgnoreCase(name)) {
                return entry.getId();
            }
        }
        return BlockLibrary.ID_INVALID_BLOCK;
    }


    /**
     * @return the BlockWrapper at the given index or the {@code NULL_ENTRY}.
     */
    private BlockWrapper getBlockByIndex(int index) {
        if (MathUtils.inRange(index, 0, blocks.length)) {
            return blocks[index];
        } else {
            return INVALID_BLOCK;
        }
    }

}
