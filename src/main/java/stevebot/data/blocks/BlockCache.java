package stevebot.data.blocks;

import net.minecraft.block.Block;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.minecraft.MinecraftAdapter;

public class BlockCache {


	private final ChunkCache chunkCache = new ChunkCache();
	private final BlockLibrary library;
	private final BlockProvider blockProvider;




	/**
	 * @param library       the {@link BlockLibrary} used by this cache
	 * @param blockProvider the {@link BlockProvider} used by this cache
	 */
	public BlockCache(BlockLibrary library, BlockProvider blockProvider) {
		this.library = library;
		this.blockProvider = blockProvider;
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
			blockId = getBlockFromMinecraft(blockPos).id;
		} else {
			blockId = BlockLibrary.ID_UNLOADED_BOCK;
		}

		chunk.setId(chunkX, chunkY, chunkZ, blockId);
		return blockId;
	}




	/**
	 * @param pos the position of the block
	 * @return the block at the given position from the minecraft world (never from the cache).
	 */
	private BlockWrapper getBlockFromMinecraft(BaseBlockPos pos) {
		final Block block = MinecraftAdapter.get().getBlock(pos.copyAsMCBlockPos());
		if (block == null) {
			return BlockLibrary.INVALID_BLOCK;
		} else {
			return library.getBlockByMCBlock(block);
		}
	}




	/**
	 * @return the chunk-cache used by this block-cache
	 */
	public ChunkCache getChunkCache() {
		return this.chunkCache;
	}


}
