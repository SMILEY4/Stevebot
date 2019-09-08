package stevebot.data.blocks;

import com.ruegnerlukas.simplemath.MathUtils;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockLibraryImpl implements BlockLibrary {


	private static final BlockEntry NULL_ENTRY = new BlockEntry(BlockLibrary.ID_INVALID_BLOCK, "null", null);
	private BlockEntry[] entries;




	public void initialize() {

		List<BlockEntry> entryList = new ArrayList<>();
		int maxID = 0;
		for (Block block : Block.REGISTRY) {
			final int id = getIdOfBlock(block);
			final String name = getNameOfBlock(block);
			entryList.add(new BlockEntry(id, name, block));
			maxID = Math.max(maxID, id);
		}

		entries = new BlockEntry[maxID + 1];
		Arrays.fill(entries, NULL_ENTRY);
		for (BlockEntry entry : entryList) {
			entries[entry.id] = entry;
		}

	}




	@Override
	public Block getBlockById(int id) {
		return getEntryByIndex(id).block;
	}




	@Override
	public Block getBlockByName(String name) {
		return getEntryByIndex(getIdFromName(name)).block;
	}




	@Override
	public int getIdOfBlock(Block block) {
		return Block.REGISTRY.getIDForObject(block);
	}




	@Override
	public int getIdFromName(String name) {
		for (int i = 0, n = entries.length; i < n; i++) {
			BlockEntry entry = entries[i];
			if (entry.name.equalsIgnoreCase(name)) {
				return entry.id;
			}
		}
		return -1;
	}




	@Override
	public String getNameOfBlock(Block block) {
		return Block.REGISTRY.getNameForObject(block).toString();
	}




	@Override
	public String getNameFromId(int id) {
		return getEntryByIndex(id).name;
	}




	private BlockEntry getEntryByIndex(int index) {
		if (MathUtils.inRange(index, 0, entries.length)) {
			return entries[index];
		} else {
			return NULL_ENTRY;
		}
	}




	static class BlockEntry {


		final int id;
		final String name;
		final Block block;




		BlockEntry(int id, String name, Block block) {
			this.id = id;
			this.name = name;
			this.block = block;
		}

	}


}
