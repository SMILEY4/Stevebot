package stevebot.data.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;

public class BlockProviderImpl implements BlockProvider {


	private final BlockCache cache;
	private final BlockLibrary library;




	/**
	 * @param library the {@link BlockLibrary} to be used by this provider.
	 */
	public BlockProviderImpl(BlockLibrary library) {
		this.library = library;
		this.cache = new BlockCache(library);
	}




	@Override
	public boolean isLoaded(BlockPos pos) {
		return Minecraft.getMinecraft().world.getChunkFromBlockCoords(pos).isLoaded();
	}




	@Override
	public Block getBlockAt(BlockPos pos) {
		return getBlockAt(pos.getX(), pos.getY(), pos.getZ());
	}




	@Override
	public Block getBlockAt(int x, int y, int z) {
		final int id = cache.getBlockIdAt(x, y, z);
		return library.getBlockById(id);
	}




	@Override
	public int getBlockIdAt(BlockPos pos) {
		return getBlockIdAt(pos.getX(), pos.getY(), pos.getZ());
	}




	@Override
	public int getBlockIdAt(int x, int y, int z) {
		return cache.getBlockIdAt(x, y, z);
	}




	@Override
	public BlockCache getBlockCache() {
		return this.cache;
	}

}
