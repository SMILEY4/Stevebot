package stevebot.data.blocks;

import com.ruegnerlukas.simplemath.MathUtils;
import net.minecraft.block.Block;
import stevebot.events.EventListener;
import stevebot.events.PostInitEvent;
import stevebot.minecraft.MinecraftAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockLibraryImpl implements BlockLibrary {


	private BlockWrapper[] blocks;

	private final EventListener listener = new EventListener<PostInitEvent>() {

		@Override
		public Class<PostInitEvent> getEventClass() {
			return PostInitEvent.class;
		}




		@Override
		public void onEvent(PostInitEvent event) {
			initialize();
		}

	};




	/**
	 * Initializes this library. Fetches all blocks from the {@link Block#REGISTRY} and stores them.
	 */
	private void initialize() {

		List<BlockWrapper> blockList = new ArrayList<>();
		int maxID = 0;
		for (Block block : MinecraftAdapter.get().getRegisteredBlocks()) {
			final int id = getIdOfBlock(block);
			final String name = getNameOfBlock(block);
			blockList.add(new BlockWrapper(id, name, block));
			maxID = Math.max(maxID, id);
		}

		blocks = new BlockWrapper[maxID + 1];
		Arrays.fill(blocks, INVALID_BLOCK);
		for (BlockWrapper block : blockList) {
			blocks[block.id] = block;
		}

	}




	@Override
	public EventListener getListener() {
		return listener;
	}




	@Override
	public BlockWrapper getBlockByMCBlock(Block block) {
		return getBlockByIndex(getIdOfBlock(block));
	}




	@Override
	public BlockWrapper getBlockById(int id) {
		return getBlockByIndex(id);
	}




	@Override
	public BlockWrapper getBlockByName(String name) {
		return getBlockByIndex(getIdFromName(name));
	}




	@Override
	public int getIdOfBlock(Block block) {
		return Block.REGISTRY.getIDForObject(block);
	}




	@Override
	public int getIdFromName(String name) {
		for (int i = 0, n = blocks.length; i < n; i++) {
			BlockWrapper entry = blocks[i];
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
		return getBlockByIndex(id).name;
	}




	/**
	 * @return the BlockWrapper at the given index or the {@code NULL_ENTRY}.
	 */
	private BlockWrapper getBlockByIndex(int index) {
		if (MathUtils.inRange(index, 0, blocks.length)) {
			return blocks[index];
		} else {
			return INVALID_BLOCK;
		}
	}


}
