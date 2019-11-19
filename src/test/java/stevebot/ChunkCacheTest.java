package stevebot;

import org.junit.jupiter.api.Test;
import stevebot.data.blocks.BlockLibrary;
import stevebot.data.blocks.ChunkCache;

import static org.assertj.core.api.Assertions.assertThat;

public class ChunkCacheTest {


	@Test
	void testCachedChunkPos() {

		// conversion reference:
		// https://dinnerbone.com/minecraft/tools/coordinates/

		final int blockX = 345;
		final int blockY = 1;
		final int blockZ = -49;

		final int chunkX = 21;
		final int chunkY = 0;
		final int chunkZ = -4;

		final ChunkCache cache = new ChunkCache();
		final ChunkCache.ChunkPos actual = cache.getCachedChunkPos(blockX, blockY, blockZ);
		final ChunkCache.ChunkPos expected = new ChunkCache.ChunkPos(chunkX, chunkY, chunkZ);

		assertThat(actual).isNotNull();
		assertThat(actual.x).isEqualTo(expected.x);
		assertThat(actual.y).isEqualTo(expected.y);
		assertThat(actual.z).isEqualTo(expected.z);
		assertThat(actual.equals(expected)).isTrue();
	}




	@Test
	void testGetCachedChunk() {

		final int blockXa = 21;
		final int blockYa = 0;
		final int blockZa = -4;

		final int blockXb = 32;
		final int blockYb = 12;
		final int blockZb = -42;

		final ChunkCache cache = new ChunkCache();
		final ChunkCache.ChunkPos chunkPosA = new ChunkCache.ChunkPos(blockXa, blockYa, blockZa);
		final ChunkCache.ChunkPos chunkPosB = new ChunkCache.ChunkPos(blockXb, blockYb, blockZb);

		final ChunkCache.CachedChunk chunk1 = cache.getCachedChunk(chunkPosA);
		final ChunkCache.CachedChunk chunk2 = cache.getCachedChunk(chunkPosA);
		final ChunkCache.CachedChunk chunk3 = cache.getCachedChunk(chunkPosB);

		assertThat(chunk1).isNotNull();
		assertThat(chunk2).isNotNull();
		assertThat(chunk3).isNotNull();

		assertThat(chunk1).isEqualTo(chunk2);
		assertThat(chunk1).isNotEqualTo(chunk3);

		assertThat(chunk1.pos).isEqualTo(chunkPosA);
		assertThat(chunk2.pos).isEqualTo(chunkPosA);
		assertThat(chunk3.pos).isEqualTo(chunkPosB);
	}




	@Test
	void testDeleteCachedChunk() {

		final int blockXa = 21;
		final int blockYa = 0;
		final int blockZa = -4;

		final int blockXb = 32;
		final int blockYb = 12;
		final int blockZb = -42;

		final ChunkCache cache = new ChunkCache();
		final ChunkCache.CachedChunk chunkA = cache.getCachedChunk(blockXa, blockYa, blockZa);
		final ChunkCache.CachedChunk chunkB = cache.getCachedChunk(blockXb, blockYb, blockZb);

		final ChunkCache.CachedChunk chunkDeleted = cache.deleteCachedChunk(blockXa, blockYa, blockZa);
		assertThat(chunkDeleted).isEqualTo(chunkA);

		final ChunkCache.CachedChunk chunkA2 = cache.getCachedChunk(blockXa, blockYa, blockZa);
		final ChunkCache.CachedChunk chunkB2 = cache.getCachedChunk(blockXb, blockYb, blockZb);

		assertThat(chunkA2).isNotEqualTo(chunkA);
		assertThat(chunkB2).isEqualTo(chunkB);
	}




	@Test
	void testClearCache() {

		final int blockXa = 21;
		final int blockYa = 0;
		final int blockZa = -4;

		final int blockXb = 32;
		final int blockYb = 12;
		final int blockZb = -42;

		final ChunkCache cache = new ChunkCache();
		final ChunkCache.CachedChunk chunkA = cache.getCachedChunk(blockXa, blockYa, blockZa);
		final ChunkCache.CachedChunk chunkB = cache.getCachedChunk(blockXb, blockYb, blockZb);

		cache.clear();

		final ChunkCache.CachedChunk chunkA2 = cache.getCachedChunk(blockXa, blockYa, blockZa);
		final ChunkCache.CachedChunk chunkB2 = cache.getCachedChunk(blockXb, blockYb, blockZb);

		assertThat(chunkA2).isNotEqualTo(chunkA);
		assertThat(chunkB2).isNotEqualTo(chunkB);
	}




	@Test
	void testCachedChunkIds() {

		final int blockX = 21;
		final int blockY = 0;
		final int blockZ = -4;

		final int blockID = 32;

		final int indexX0 = 9;
		final int indexY0 = 14;
		final int indexZ0 = 6;

		final int indexX1 = -9;
		final int indexY1 = 5;
		final int indexZ1 = 6;

		final int indexX2 = 9;
		final int indexY2 = 19;
		final int indexZ2 = 6;


		final ChunkCache cache = new ChunkCache();
		final ChunkCache.ChunkPos chunkPos = new ChunkCache.ChunkPos(blockX, blockY, blockZ);
		final ChunkCache.CachedChunk chunk = cache.getCachedChunk(chunkPos);

		assertThat(chunk.getId(indexX0, indexY0, indexZ0)).isEqualTo(BlockLibrary.ID_INVALID_BLOCK);
		assertThat(chunk.getId(indexX1, indexY1, indexZ1)).isEqualTo(BlockLibrary.ID_INVALID_BLOCK);
		assertThat(chunk.getId(indexX2, indexY2, indexZ2)).isEqualTo(BlockLibrary.ID_INVALID_BLOCK);

		chunk.setId(indexX0, indexY0, indexZ0, blockID);
		assertThat(chunk.getId(indexX0, indexY0, indexZ0)).isEqualTo(blockID);
	}




	@Test
	void testCachedChunkLocalCoords() {

		final int blockX = 21;
		final int blockY = 0;
		final int blockZ = -4;

		final int localX = 5;
		final int localY = 0;
		final int localZ = 12;

		final ChunkCache cache = new ChunkCache();
		final ChunkCache.ChunkPos chunkPos = new ChunkCache.ChunkPos(blockX, blockY, blockZ);
		final ChunkCache.CachedChunk chunk = cache.getCachedChunk(chunkPos);

		assertThat(chunk.toLocalX(blockX)).isEqualTo(localX);
		assertThat(chunk.toLocalY(blockY)).isEqualTo(localY);
		assertThat(chunk.toLocalZ(blockZ)).isEqualTo(localZ);
	}

}
