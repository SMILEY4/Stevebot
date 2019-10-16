package stevebot.data.items;

import net.minecraft.item.Item;
import stevebot.data.blocks.BlockLibrary;
import stevebot.data.blocks.BlockWrapper;

public class ItemWrapper {


	public final int id;
	public final String name;
	public final Item item;

	private BlockWrapper block = BlockLibrary.INVALID_BLOCK;




	/**
	 * @param id   the id of the item
	 * @param name the name of the item
	 * @param item the minecraft item
	 */
	public ItemWrapper(int id, String name, Item item) {
		this.id = id;
		this.name = name;
		this.item = item;
	}




	/**
	 * @param block sets the block corresponding to this item
	 */
	void setBlock(BlockWrapper block) {
		this.block = block;
	}




	/**
	 * @return whether this item is a block
	 */
	public boolean isBlock() {
		return block.id != BlockLibrary.ID_INVALID_BLOCK;
	}




	/**
	 * @return the block associated with this item
	 */
	public BlockWrapper getBlock() {
		return block;
	}


}
