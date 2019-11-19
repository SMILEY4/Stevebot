package stevebot.data.blocks;


import com.ruegnerlukas.simplemath.MathUtils;
import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import com.ruegnerlukas.simplemath.vectors.vec3.Vector3i;
import stevebot.rendering.Color;
import stevebot.rendering.Renderable;
import stevebot.rendering.Renderer;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;

public class ChunkCache {


	/**
	 * The maximum number of chunks cached
	 */
	private final int MAX_CAPACITY = 9999;

	private Map<ChunkPos, CachedChunk> chunks = new HashMap<>();




	/**
	 * @param blockX the x position of the block
	 * @param blockY the y position of the block
	 * @param blockZ the z position of the block
	 * @return the position of the block inside / relative to the chunk,
	 */
	public ChunkPos getCachedChunkPos(int blockX, int blockY, int blockZ) {
		return new ChunkPos(blockX >> 4, blockY >> 4, blockZ >> 4);
	}




	/**
	 * @param blockX the x position of the block
	 * @param blockY the y position of the block
	 * @param blockZ the z position of the block
	 * @return the {@link CachedChunk} containing this position. If the chunk is not yet cached, it will be put into the cache.
	 */
	public CachedChunk getCachedChunk(int blockX, int blockY, int blockZ) {
		final ChunkPos chunkPos = getCachedChunkPos(blockX, blockY, blockZ);
		return getCachedChunk(chunkPos);
	}




	/**
	 * @param chunkPos the position of the chunk.
	 * @return the chunk at the given position. If the chunk is not yet cached, it will be put into the cache.
	 */
	public CachedChunk getCachedChunk(ChunkPos chunkPos) {
		CachedChunk chunk = chunks.get(chunkPos);
		if (chunk == null) {
			chunk = addChunk(chunkPos);
		}
		return chunk;
	}




	/**
	 * Adds the chunk at the given position to the cache.
	 *
	 * @param chunkPos the position of the chunk
	 * @return the {@link CachedChunk} that was saved
	 */
	private CachedChunk addChunk(ChunkPos chunkPos) {
		if (chunks.size() >= MAX_CAPACITY) {
			removeFurthestChunk(chunkPos);
		}
		CachedChunk chunk = new CachedChunk(chunkPos);
		chunks.put(chunkPos, chunk);
		return chunk;
	}




	private final Vector3i vecHelper = new Vector3i();




	/**
	 * Removes the cached chunk that is the furthest away from the given position
	 *
	 * @param refPos the position
	 */
	private void removeFurthestChunk(ChunkPos refPos) {
		ChunkPos furthestChunkPos = null;
		int maxDist = 0;

		for (ChunkPos pos : chunks.keySet()) {
			int dist2 = vecHelper.set(refPos.x, refPos.y, refPos.z).dist2(pos.x, pos.y, pos.z);
			if (maxDist < dist2) {
				maxDist = dist2;
				furthestChunkPos = pos;
			}
		}

		if (furthestChunkPos != null) {
			chunks.remove(furthestChunkPos);
		}

	}




	/**
	 * Removes the chunk containing the given block-position from the cache
	 *
	 * @param blockX the x position of the block
	 * @param blockY the y position of the block
	 * @param blockZ the z position of the block
	 * @return the removed {@link CachedChunk}
	 */
	public CachedChunk deleteCachedChunk(int blockX, int blockY, int blockZ) {
		final ChunkPos chunkPos = getCachedChunkPos(blockX, blockY, blockZ);
		return deleteCachedChunk(chunkPos);
	}




	/**
	 * Removes the chunk at the given position from the cache
	 *
	 * @param chunkPos the position of the chunk
	 * @return the removed {@link CachedChunk}
	 */

	public CachedChunk deleteCachedChunk(ChunkPos chunkPos) {
		return chunks.remove(chunkPos);
	}




	/**
	 * Clears this chunk-cache
	 */
	public void clear() {
		chunks.clear();
	}




	/**
	 * @return a {@link Renderable} representing this {@link ChunkCache}.
	 */
	public Renderable getChunkCacheRenderable() {
		return new ChunkCacheRenderable(chunks);
	}




	public static class ChunkPos {


		public final int x;
		public final int y;
		public final int z;




		public ChunkPos(int x, int y, int z) {
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






	/**
	 * Represents a 16x16x16 part of the world in the cache.
	 */
	public static class CachedChunk {


		public final ChunkPos pos;
		final int[][][] blockIds;




		/**
		 * @param pos the chunk-position of this {@link CachedChunk}
		 */
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




		/**
		 * @param i the x position of the block inside / relative to this chunk
		 * @param j the y position of the block inside / relative to this chunk
		 * @param k the z position of the block inside / relative to this chunk
		 * @return the cached id of the block or {@code BlockLibrary.ID_INVALID_BLOCK}.
		 */
		public int getId(int i, int j, int k) {
			if (MathUtils.inRange(i, 0, 16) && MathUtils.inRange(j, 0, 16) && MathUtils.inRange(k, 0, 16)) {
				return getIdUnsafe(i, j, k);
			} else {
				return BlockLibrary.ID_INVALID_BLOCK;
			}
		}




		/**
		 * @param i the x position of the block inside / relative to this chunk
		 * @param j the y position of the block inside / relative to this chunk
		 * @param k the z position of the block inside / relative to this chunk
		 * @return the cached id of the block or {@code BlockLibrary.ID_INVALID_BLOCK} without checking i, j and k.
		 */
		private int getIdUnsafe(int i, int j, int k) {
			return blockIds[i][j][k];
		}




		/**
		 * Sets the id of a block in this {@link CachedChunk}.
		 *
		 * @param i  the x position of the block inside / relative to this chunk
		 * @param j  the y position of the block inside / relative to this chunk
		 * @param k  the z position of the block inside / relative to this chunk
		 * @param id the new id of the block
		 */
		public void setId(int i, int j, int k, int id) {
			if (MathUtils.inRange(i, 0, 16) && MathUtils.inRange(j, 0, 16) && MathUtils.inRange(k, 0, 16)) {
				setIdUnsafe(i, j, k, id);
			}
		}




		/**
		 * Sets the id of a block in this {@link CachedChunk} without checking i, j, and k.
		 *
		 * @param i  the x position of the block inside / relative to this chunk
		 * @param j  the y position of the block inside / relative to this chunk
		 * @param k  the z position of the block inside / relative to this chunk
		 * @param id the new id of the block
		 */
		private void setIdUnsafe(int i, int j, int k, int id) {
			blockIds[i][j][k] = id;
		}




		/**
		 * @param globalBlockX the x position of the block in the world.
		 * @return the x position of the block inside / relative to this chunk
		 */
		public int toLocalX(int globalBlockX) {
			return globalBlockX & 15;
		}




		/**
		 * @param globalBlockY the y position of the block in the world.
		 * @return the y position of the block inside / relative to this chunk
		 */
		public int toLocalY(int globalBlockY) {
			return globalBlockY & 15;
		}




		/**
		 * @param globalBlockZ the z position of the block in the world.
		 * @return the z position of the block inside / relative to this chunk
		 */
		public int toLocalZ(int globalBlockZ) {
			return globalBlockZ & 15;
		}

	}






	public static class ChunkCacheRenderable implements Renderable {


		private Map<ChunkPos, CachedChunk> chunks;




		public ChunkCacheRenderable(Map<ChunkPos, CachedChunk> chunks) {
			this.chunks = chunks;
		}




		@Override
		public void render(Renderer renderer) {
			renderer.beginBoxes(3);
			final Vector3d posMin = new Vector3d();
			final Vector3d posMax = new Vector3d();
			try {
				for (CachedChunk chunk : chunks.values()) {
					ChunkPos chunkPos = chunk.pos;
					posMin.set(chunkPos.x * 16, chunkPos.y * 16, chunkPos.z * 16);
					posMax.set(chunkPos.x * 16 + 16, chunkPos.y * 16 + 16, chunkPos.z * 16 + 16);
					renderer.drawBoxOpen(posMin, posMax, Color.BLUE);
				}
			} catch (ConcurrentModificationException e) {
				// ignore
			}
			renderer.end();
		}

	}

}




