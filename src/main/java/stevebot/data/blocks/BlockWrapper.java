package stevebot.data.blocks;

import net.minecraft.block.Block;
import stevebot.data.items.ItemLibrary;
import stevebot.data.items.ItemWrapper;

public class BlockWrapper {


	public final int id;
	public final String name;
	public final Block block;
	private ItemWrapper item;




	/**
	 * @param id    the id of the block
	 * @param name  the name of the block
	 * @param block the minecraft block
	 */
	public BlockWrapper(int id, String name, Block block) {
		this.id = id;
		this.name = name;
		this.block = block;
	}




	/**
	 * @param item sets the item corresponding to this block
	 */
	void setItem(ItemWrapper item) {
		this.item = item;
	}




	/**
	 * @return whether this block has an item associated with it
	 */
	public boolean hasItem() {
		return this.item.id != ItemLibrary.ID_INVALID_ITEM;
	}




	/**
	 * @return the item associated with this block
	 */
	public ItemWrapper getItem() {
		return item;
	}


}
