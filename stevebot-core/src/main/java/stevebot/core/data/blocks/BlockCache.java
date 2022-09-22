package stevebot.core.data.blocks;

import stevebot.core.data.blockpos.BaseBlockPos;
import stevebot.core.minecraft.MinecraftAdapter;


public class BlockCache {

    private final MinecraftAdapter minecraftAdapter;
    private final ChunkCache chunkCache = new ChunkCache();
    private final BlockProvider blockProvider;


    /**
     * @param minecraftAdapter the adapter for accessing minecraft
     * @param blockProvider    the {@link BlockProvider} used by this cache
     */
    public BlockCache(MinecraftAdapter minecraftAdapter, BlockProvider blockProvider) {
        this.minecraftAdapter = minecraftAdapter;
        this.blockProvider = blockProvider;
    }


    /**
     * Handles a broken block
     *
     * @param posX the x-position of the broken block
     * @param posY the y-position of the broken block
     * @param posZ the z-position of the broken block
     */
    public void onEventBlockBreak(int posX, int posY, int posZ) {
        invalidateBlock(posX, posY, posZ);
    }


    /**
     * Handles a placed block
     *
     * @param posX the x-position of the broken block
     * @param posY the y-position of the broken block
     * @param posZ the z-position of the broken block
     */
    public void onEventBlockPlace(int posX, int posY, int posZ) {
        invalidateBlock(posX, posY, posZ);
    }


    /**
     * @param pos the {@link BaseBlockPos} of the block
     * @return the id of the block at the given position
     */
    public int getBlockIdAt(BaseBlockPos pos) {
        return getBlockIdAt(pos.getX(), pos.getY(), pos.getZ());
    }


    /**
     * @param blockX the x-position of the block
     * @param blockY the y-position of the block
     * @param blockZ the z-position of the block
     * @return the id of the block at the given coordinate
     */
    public int getBlockIdAt(int blockX, int blockY, int blockZ) {
        final ChunkCache.CachedChunk chunk = chunkCache.getCachedChunk(blockX, blockY, blockZ);
        final int chunkX = chunk.toLocalX(blockX);
        final int chunkY = chunk.toLocalY(blockY);
        final int chunkZ = chunk.toLocalZ(blockZ);
        final int cachedId = chunk.getId(chunkX, chunkY, chunkZ);
        if (cachedId == BlockLibrary.ID_INVALID_BLOCK || cachedId == BlockLibrary.ID_UNLOADED_BOCK) {
            return cacheBlockId(blockX, blockY, blockZ, chunk, chunkX, chunkY, chunkZ);
        } else {
            return cachedId;
        }
    }


    /**
     * Saves the block at the given position in the given chunk.
     *
     * @param blockX the x position of the block
     * @param blockY the y position of the block
     * @param blockZ the z position of the block
     * @param chunk  the chunk
     * @param chunkX the x position of the block inside the chunk
     * @param chunkY the y position of the block inside the chunk
     * @param chunkZ the z position of the block inside the chunk
     * @return the id of the block
     */
    private int cacheBlockId(int blockX, int blockY, int blockZ, ChunkCache.CachedChunk chunk, int chunkX, int chunkY, int chunkZ) {

        BaseBlockPos blockPos = new BaseBlockPos(blockX, blockY, blockZ);
        int blockId;

        if (blockProvider.isLoaded(blockPos)) {
            blockId = minecraftAdapter.getBlockId(blockPos);
        } else {
            blockId = BlockLibrary.ID_UNLOADED_BOCK;
        }

        chunk.setId(chunkX, chunkY, chunkZ, blockId);
        return blockId;
    }


    /**
     * Invalidates the cached block at the given position
     *
     * @param pos the position of the block
     */
    public void invalidateBlock(BaseBlockPos pos) {
        invalidateBlock(pos.getX(), pos.getY(), pos.getZ());
    }


    /**
     * Invalidates the cached block at the given position
     *
     * @param blockX the x position of the block
     * @param blockY the y position of the block
     * @param blockZ the z position of the block
     */
    public void invalidateBlock(int blockX, int blockY, int blockZ) {
        final ChunkCache.CachedChunk chunk = chunkCache.getCachedChunk(blockX, blockY, blockZ);
        final int chunkX = chunk.toLocalX(blockX);
        final int chunkY = chunk.toLocalY(blockY);
        final int chunkZ = chunk.toLocalZ(blockZ);
        chunk.setId(chunkX, chunkY, chunkZ, BlockLibrary.ID_INVALID_BLOCK);
    }


    /**
     * Clears this block cache.
     */
    public void clear() {
        getChunkCache().clear();
    }


    /**
     * @return the chunk-cache used by this block-cache
     */
    public ChunkCache getChunkCache() {
        return this.chunkCache;
    }

}
