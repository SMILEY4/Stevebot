package stevebot.data.blocks;

import com.ruegnerlukas.simplemath.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import stevebot.data.items.ItemLibrary;
import stevebot.data.items.wrapper.ItemWrapper;
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
	 * Initializes this library. Collects all blocks and stores them.
	 */
	private void initialize() {

		List<BlockWrapper> blockList = new ArrayList<>();
		int maxID = 0;
		for (Block block : MinecraftAdapter.get().getRegisteredBlocks()) {
			final int id = getId(block);
			final String name = getName(block);
			blockList.add(new BlockWrapper(id, name, block));
			maxID = Math.max(maxID, id);
		}

		blocks = new BlockWrapper[maxID + 1];
		Arrays.fill(blocks, INVALID_BLOCK);
		for (BlockWrapper block : blockList) {
			blocks[block.getId()] = block;
		}

	}




	@Override
	public void insertItems(List<ItemWrapper> items) {
		for (BlockWrapper block : blocks) {
			block.setItem(ItemLibrary.INVALID_ITEM);
			if (block.getId() != BlockLibrary.ID_INVALID_BLOCK) {
				final Item itemFromBlock = Item.getItemFromBlock(block.getBlock());
				final int itemIdFromBlock = MinecraftAdapter.get().getItemId(itemFromBlock);
				for (ItemWrapper item : items) {
					if (item.getId() == itemIdFromBlock) {
						block.setItem(item);
						break;
					}
				}
			}
		}
	}




	@Override
	public List<BlockWrapper> getAllBlocks() {
		return new ArrayList<>(Arrays.asList(blocks));
	}




	@Override
	public EventListener getListener() {
		return listener;
	}




	@Override
	public BlockWrapper getBlockByMCBlock(Block block) {
		return getBlockByIndex(getId(block));
	}




	@Override
	public BlockWrapper getBlockById(int id) {
		return getBlockByIndex(id);
	}




	@Override
	public BlockWrapper getBlockByName(String name) {
		return getBlockByIndex(getIdFromName(name));
	}




	/**
	 * @param name the name of the block in the format "minecraft:name-of-the-block"
	 * @return the id of the block with the given name or {@link BlockLibrary#ID_INVALID_BLOCK}.
	 */
	private int getIdFromName(String name) {
		for (int i = 0, n = blocks.length; i < n; i++) {
			final BlockWrapper entry = blocks[i];
			if (entry.getName().equalsIgnoreCase(name)) {
				return entry.getId();
			}
		}
		return BlockLibrary.ID_INVALID_BLOCK;
	}




	/**
	 * @param block the {@link Block}
	 * @return the name of the given block.
	 */
	private String getName(Block block) {
		return MinecraftAdapter.get().getBlockName(block);
	}




	/**
	 * @param block the {@link Block}
	 * @return the id of the given block
	 */
	private int getId(Block block) {
		return MinecraftAdapter.get().getBlockId(block);
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
