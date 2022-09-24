package stevebot.core.data.blocks;

import java.util.HashMap;
import java.util.Map;
import stevebot.core.data.blockpos.BaseBlockPos;
import stevebot.core.data.blockpos.FastBlockPos;
import stevebot.core.data.modification.BlockBreakModification;
import stevebot.core.data.modification.BlockPlaceModification;
import stevebot.core.data.modification.Modification;
import stevebot.core.minecraft.MinecraftAdapter;

public class BlockProvider {

    private final MinecraftAdapter minecraftAdapter;
    private final BlockCache cache;
    private final BlockLibrary library;

    private final Map<BaseBlockPos, Modification> blockModifications = new HashMap<>();


    /**
     * @param library the {@link BlockLibrary} to be used by this provider.
     */
    public BlockProvider(MinecraftAdapter minecraftAdapter, BlockLibrary library) {
        this.minecraftAdapter = minecraftAdapter;
        this.library = library;
        this.cache = new BlockCache(minecraftAdapter, this);
    }


    /**
     * @param pos the position of the block
     * @return true, if the given position is in a loaded chunk.
     */
    public boolean isLoaded(BaseBlockPos pos) {
        return minecraftAdapter.isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4);
    }


    /**
     * @param pos the position of the block
     * @return the {@link BlockWrapper} at the given position.
     */
    public BlockWrapper getBlockAt(BaseBlockPos pos) {
        return getBlockAt(pos.getX(), pos.getY(), pos.getZ());
    }


    /**
     * @param x the x-position of the block
     * @param y the y-position of the block
     * @param z the z-position of the block
     * @return the {@link BlockWrapper} at the given position.
     */
    public BlockWrapper getBlockAt(int x, int y, int z) {
        Modification modification = getBlockChangeAt(x, y, z);
        if (modification != null) {
            if (modification instanceof BlockBreakModification) {
                return BlockUtils.getBlockLibrary().getBlockById(0); // air
            }
            if (modification instanceof BlockPlaceModification) {
                final BlockPlaceModification placeModification = (BlockPlaceModification) modification;
                return placeModification.getBlock();
            }
        }
        return library.getBlockById(cache.getBlockIdAt(x, y, z));
    }


    /**
     * @param pos the position of the block
     * @return the id of the block at the given position.
     */
    public int getBlockIdAt(BaseBlockPos pos) {
        return getBlockIdAt(pos.getX(), pos.getY(), pos.getZ());
    }


    /**
     * @param x the x-position of the block
     * @param y the y-position of the block
     * @param z the z-position of the block
     * @return the id of the block at the given position.
     */
    public int getBlockIdAt(int x, int y, int z) {
        Modification modification = getBlockChangeAt(x, y, z);
        if (modification != null) {
            if (modification instanceof BlockBreakModification) {
                return 0; // air
            }
            if (modification instanceof BlockPlaceModification) {
                final BlockPlaceModification placeModification = (BlockPlaceModification) modification;
                return placeModification.getBlock().getId();
            }
        }
        return cache.getBlockIdAt(x, y, z);
    }


    private final FastBlockPos tempKey = new FastBlockPos();


    /**
     * @param pos the position
     * @return the temporary {@link Modification} at the given position or null.
     */
    public Modification getBlockChangeAt(BaseBlockPos pos) {
        return getBlockChangeAt(pos.getX(), pos.getY(), pos.getZ());
    }


    /**
     * @param x the x position
     * @param y the y position
     * @param z the z position
     * @return the temporary {@link Modification} at the given position or null.
     */
    public Modification getBlockChangeAt(int x, int y, int z) {
        if (blockModifications.isEmpty()) {
            return null;
        } else {
            return blockModifications.get(tempKey.set(x, y, z));
        }
    }


    /**
     * Temporarily changes a block at a specified position without placing/breaking it in the real world.
     *
     * @param modification     the modification to add.
     * @param overrideExisting true, to override any existing modification at that same position.
     */
    public void addModification(Modification modification, boolean overrideExisting) {
        if (modification instanceof BlockBreakModification) {
            final BlockBreakModification breakModification = (BlockBreakModification) modification;
            if (!blockModifications.containsKey(breakModification.getPosition()) || overrideExisting) {
                blockModifications.put(breakModification.getPosition(), modification);
            }
        }
        if (modification instanceof BlockPlaceModification) {
            final BlockPlaceModification placeModification = (BlockPlaceModification) modification;
            if (!blockModifications.containsKey(placeModification.getPosition()) || overrideExisting) {
                blockModifications.put(placeModification.getPosition(), modification);
            }
        }
    }


    /**
     * Removes/Resets all temporary block changes.
     */
    public void clearBlockChanges() {
        blockModifications.clear();
    }


    /**
     * @return the underlying {@link BlockCache}.
     */
    public BlockCache getBlockCache() {
        return this.cache;
    }

}
