package stevebot.data.blocks;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blockpos.FastBlockPos;
import stevebot.data.modification.BlockBreakModification;
import stevebot.data.modification.BlockPlaceModification;
import stevebot.minecraft.MinecraftAdapter;
import stevebot.data.modification.Modification;

import java.util.HashMap;
import java.util.Map;

public class BlockProviderImpl implements BlockProvider {


	private final BlockCache cache;
	private final BlockLibrary library;

	private final Map<BaseBlockPos, Modification> blockModifications = new HashMap<>();




	/**
	 * @param library the {@link BlockLibrary} to be used by this provider.
	 */
	public BlockProviderImpl(BlockLibrary library) {
		this.library = library;
		this.cache = new BlockCache(library, this);
	}




	@Override
	public boolean isLoaded(BaseBlockPos pos) {
		return MinecraftAdapter.get().isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4);
	}




	@Override
	public BlockWrapper getBlockAt(BaseBlockPos pos) {
		return getBlockAt(pos.getX(), pos.getY(), pos.getZ());
	}




	@Override
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




	@Override
	public int getBlockIdAt(BaseBlockPos pos) {
		return getBlockIdAt(pos.getX(), pos.getY(), pos.getZ());
	}




	@Override
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




	@Override
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




	@Override
	public void clearBlockChanges() {
		blockModifications.clear();
	}




	@Override
	public BlockCache getBlockCache() {
		return this.cache;
	}

}
