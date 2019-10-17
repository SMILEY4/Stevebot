package stevebot.data.items.wrapper;

import net.minecraft.item.ItemBlock;
import stevebot.data.blocks.BlockLibrary;
import stevebot.data.blocks.BlockWrapper;

public class ItemBlockWrapper extends ItemWrapper {


	private BlockWrapper block = BlockLibrary.INVALID_BLOCK;




	/**
	 * @param id   the id of the item
	 * @param name the name of the item
	 * @param item the minecraft item
	 */
	public ItemBlockWrapper(int id, String name, ItemBlock item) {
		super(id, name, item);
	}




	/**
	 * @param block sets the block corresponding to this item
	 */
	public void setBlockWrapper(BlockWrapper block) {
		this.block = block;
	}




	/**
	 * @return the block associated with this item
	 */
	public BlockWrapper getBlockWrapper() {
		return block;
	}

}
