package stevebot.data.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;

public class BlockCache {


	private final ChunkCache chunkCache = new ChunkCache();
	private final BlockLibrary library;




	public BlockCache(BlockLibrary library) {
		this.library = library;
	}




	public int getBlockIdAt(BlockPos pos) {
		return getBlockIdAt(pos.getX(), pos.getY(), pos.getZ());
	}




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




	private int cacheBlockId(int blockX, int blockY, int blockZ, ChunkCache.CachedChunk chunk, int chunkX, int chunkY, int chunkZ) {

		BlockPos blockPos = new BlockPos(blockX, blockY, blockZ);
		int blockId;

		if (isBlockLoaded(blockPos)) {
			Block block = getBlockFromMinecraft(blockPos);
			if (block == null) {
				blockId = BlockLibrary.ID_INVALID_BLOCK;
			} else {
				blockId = library.getIdOfBlock(block);
			}
		} else {
			blockId = BlockLibrary.ID_UNLOADED_BOCK;
		}

		chunk.setId(chunkX, chunkY, chunkZ, blockId);
		return blockId;
	}




	private Block getBlockFromMinecraft(BlockPos pos) {
		return Minecraft.getMinecraft().world.getBlockState(pos).getBlock();
	}




	private boolean isBlockLoaded(BlockPos pos) {
		return Minecraft.getMinecraft().world.isBlockLoaded(pos);
	}


}
