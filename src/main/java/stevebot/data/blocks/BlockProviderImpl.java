package stevebot.data.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blockpos.FastBlockPos;
import stevebot.pathfinding.actions.playeractions.BlockChange;

import java.util.HashMap;
import java.util.Map;

public class BlockProviderImpl implements BlockProvider {


	private final BlockCache cache;
	private final BlockLibrary library;

	private final Map<BaseBlockPos, BlockChange> blockChanges = new HashMap<>();




	/**
	 * @param library the {@link BlockLibrary} to be used by this provider.
	 */
	public BlockProviderImpl(BlockLibrary library) {
		this.library = library;
		this.cache = new BlockCache(library);
	}




	@Override
	public boolean isLoaded(BaseBlockPos pos) {
		return Minecraft.getMinecraft().world.getChunkFromChunkCoords(pos.getX() >> 4, pos.getZ() >> 4).isLoaded();
	}




	@Override
	public Block getBlockAt(BaseBlockPos pos) {
		return getBlockAt(pos.getX(), pos.getY(), pos.getZ());
	}




	@Override
	public Block getBlockAt(int x, int y, int z) {
		BlockChange blockChange = getBlockChangeAt(x, y, z);
		if (blockChange != null) {
			return blockChange.newBlock;
		}
		return library.getBlockById(cache.getBlockIdAt(x, y, z));
	}




	@Override
	public int getBlockIdAt(BaseBlockPos pos) {
		return getBlockIdAt(pos.getX(), pos.getY(), pos.getZ());
	}




	@Override
	public int getBlockIdAt(int x, int y, int z) {
		BlockChange blockChange = getBlockChangeAt(x, y, z);
		if (blockChange != null) {
			return library.getIdOfBlock(blockChange.newBlock);
		}
		return cache.getBlockIdAt(x, y, z);
	}




	private final FastBlockPos tempKey = new FastBlockPos();




	/**
	 * @param pos the position
	 * @return the temporary {@link BlockChange} at the given position or null.
	 */
	public BlockChange getBlockChangeAt(BaseBlockPos pos) {
		return getBlockChangeAt(pos.getX(), pos.getY(), pos.getZ());
	}




	/**
	 * @param x the x position
	 * @param y the y position
	 * @param z the z position
	 * @return the temporary {@link BlockChange} at the given position or null.
	 */
	public BlockChange getBlockChangeAt(int x, int y, int z) {
		if (blockChanges.isEmpty()) {
			return null;
		} else {
			return blockChanges.get(tempKey.set(x, y, z));
		}
	}




	@Override
	public void addBlockChange(BlockChange change, boolean overrideExisting) {
		if (!blockChanges.containsKey(change.pos) || overrideExisting) {
			blockChanges.put(change.pos, change);
		}
	}




	@Override
	public void clearBlockChanges() {
		blockChanges.clear();
	}




	@Override
	public BlockCache getBlockCache() {
		return this.cache;
	}

}
