package stevebot.data.blocks;


import com.ruegnerlukas.simplemath.MathUtils;

import java.util.HashMap;
import java.util.Map;

public class ChunkCache {


	private Map<ChunkPos, CachedChunk> chunks = new HashMap<>();




	public ChunkPos getCachedChunkPos(int blockX, int blockY, int blockZ) {
		return new ChunkPos(blockX >> 4, blockY >> 4, blockZ >> 4);
	}




	public CachedChunk getCachedChunk(int blockX, int blockY, int blockZ) {
		final ChunkPos chunkPos = getCachedChunkPos(blockX, blockY, blockZ);
		return getCachedChunk(chunkPos);
	}




	public CachedChunk getCachedChunk(ChunkPos chunkPos) {
		CachedChunk chunk = chunks.get(chunkPos);
		if (chunk == null) {
			chunk = new CachedChunk(chunkPos);
			chunks.put(chunkPos, chunk);
		}
		return chunk;
	}




	public CachedChunk deleteCachedChunk(int blockX, int blockY, int blockZ) {
		final ChunkPos chunkPos = getCachedChunkPos(blockX, blockY, blockZ);
		return deleteCachedChunk(chunkPos);
	}




	public CachedChunk deleteCachedChunk(ChunkPos chunkPos) {
		return chunks.remove(chunkPos);
	}




	static class ChunkPos {


		final int x;
		final int y;
		final int z;




		ChunkPos(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}




		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			ChunkPos chunkPos = (ChunkPos) o;

			if (x != chunkPos.x) return false;
			if (y != chunkPos.y) return false;
			return z == chunkPos.z;

		}




		@Override
		public int hashCode() {
			int result = x;
			result = 31 * result + y;
			result = 31 * result + z;
			return result;
		}

	}






	static class CachedChunk {


		final ChunkPos pos;
		final int[][][] blockIds;




		CachedChunk(ChunkPos pos) {
			this.pos = pos;
			this.blockIds = new int[16][16][16];
			for (int x = 0; x < blockIds.length; x++) {
				int[][] blocksIdsYZ = blockIds[x];
				for (int y = 0; y < blockIds[x].length; y++) {
					int[] blocksIdsZ = blocksIdsYZ[y];
					for (int z = 0; z < blockIds[x][y].length; z++) {
						blocksIdsZ[z] = BlockLibrary.ID_INVALID_BLOCK;
					}
				}
			}
		}




		public int getId(int i, int j, int k) {
			if (MathUtils.inRange(i, 0, 16) && MathUtils.inRange(j, 0, 16) && MathUtils.inRange(k, 0, 16)) {
				return getIdUnsafe(i, j, k);
			} else {
				return BlockLibrary.ID_INVALID_BLOCK;
			}
		}




		private int getIdUnsafe(int i, int j, int k) {
			return blockIds[i][j][k];
		}




		public void setId(int i, int j, int k, int id) {
			if (MathUtils.inRange(i, 0, 16) && MathUtils.inRange(j, 0, 16) && MathUtils.inRange(k, 0, 16)) {
				setIdUnsafe(i, j, k, id);
			}
		}




		private void setIdUnsafe(int i, int j, int k, int id) {
			blockIds[i][j][k] = id;
		}




		public int toLocalX(int globalBlockX) {
			return globalBlockX & 15;
		}




		public int toLocalY(int globalBlockY) {
			return globalBlockY & 15;
		}




		public int toLocalZ(int globalBlockZ) {
			return globalBlockZ & 15;
		}

	}


}



