package stevebot.data.items;

import com.ruegnerlukas.simplemath.MathUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemTool;
import stevebot.data.blocks.BlockWrapper;
import stevebot.data.items.wrapper.ItemBlockWrapper;
import stevebot.data.items.wrapper.ItemToolWrapper;
import stevebot.data.items.wrapper.ItemWrapper;
import stevebot.events.EventListener;
import stevebot.events.PostInitEvent;
import stevebot.minecraft.MinecraftAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemLibraryImpl implements ItemLibrary {


	private ItemWrapper[] items;

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
	 * Initializes this library. Fetches all items from the {@link Item#REGISTRY} and stores them.
	 */
	private void initialize() {

		List<ItemWrapper> itemList = new ArrayList<>();
		int maxId = 0;
		for (Item item : MinecraftAdapter.get().getRegisteredItems()) {

			final int id = getId(item);
			final String name = getName(item);
			maxId = Math.max(maxId, id);

			if (item instanceof ItemBlock) {
				itemList.add(new ItemBlockWrapper(id, name, (ItemBlock) item));
			} else if (item instanceof ItemTool) {
				itemList.add(new ItemToolWrapper(id, name, (ItemTool) item));
			} else {
				itemList.add(new ItemWrapper(id, name, item));
			}
		}

		items = new ItemWrapper[maxId + 1];
		Arrays.fill(items, INVALID_ITEM);
		for (ItemWrapper item : itemList) {
			items[item.getId()] = item;
		}

	}




	@Override
	public EventListener getListener() {
		return listener;
	}




	@Override
	public void insertBlocks(List<BlockWrapper> blocks) {
		for (ItemWrapper itemWrapper : items) {
			if (itemWrapper.getId() != ItemLibrary.ID_INVALID_ITEM && itemWrapper instanceof ItemBlockWrapper) {
				final ItemBlockWrapper item = (ItemBlockWrapper) itemWrapper;
				final ItemBlock itemBlock = (ItemBlock) item.getItem();
				final int blockIdFromItem = MinecraftAdapter.get().getBlockId(itemBlock.getBlock());
				for (BlockWrapper block : blocks) {
					if (block.getId() == blockIdFromItem) {
						item.setBlockWrapper(block);
						break;
					}
				}
			}

		}
	}




	@Override
	public List<ItemWrapper> getAllItems() {
		return new ArrayList<>(Arrays.asList(items));
	}




	@Override
	public ItemWrapper getItemByMCItem(Item item) {
		return getItemByIndex(getId(item));
	}




	@Override
	public ItemWrapper getItemById(int id) {
		return getItemByIndex(id);
	}




	@Override
	public ItemWrapper getItemByName(String name) {
		return getItemByIndex(getIdFromName(name));
	}




	/**
	 * @param item the {@link Item}
	 * @return the name of the given item.
	 */
	private int getId(Item item) {
		return MinecraftAdapter.get().getItemId(item);
	}




	/**
	 * @param item the {@link Item}
	 * @return the id of the given item
	 */
	private String getName(Item item) {
		return MinecraftAdapter.get().getItemName(item);
	}




	/**
	 * @param name the name of the item in the format "minecraft:name-of-the-item"
	 * @return the id of the item with the given name or {@link ItemLibrary#ID_INVALID_ITEM}.
	 */
	private int getIdFromName(String name) {
		for (int i = 0, n = items.length; i < n; i++) {
			final ItemWrapper entry = items[i];
			if (entry.getName().equalsIgnoreCase(name)) {
				return entry.getId();
			}
		}
		return ItemLibrary.ID_INVALID_ITEM;
	}




	/**
	 * @return the ItemWrapper at the given index or the {@code NULL_ENTRY}.
	 */
	private ItemWrapper getItemByIndex(int index) {
		if (MathUtils.inRange(index, 0, items.length)) {
			return items[index];
		} else {
			return INVALID_ITEM;
		}
	}

}
