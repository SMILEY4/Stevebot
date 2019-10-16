package stevebot.data.items;

import net.minecraft.item.Item;

public class ItemWrapperStack extends ItemWrapper {


	private int stackSize;




	/**
	 * @param itemWrapper the item
	 * @param stackSize   the number of items in this stack
	 */
	public ItemWrapperStack(ItemWrapper itemWrapper, int stackSize) {
		this(itemWrapper.id, itemWrapper.name, itemWrapper.item, stackSize);
	}




	/**
	 * @param id        the id of the item
	 * @param name      the name of the item
	 * @param item      the minecraft item
	 * @param stackSize the number of items in this stack
	 */
	public ItemWrapperStack(int id, String name, Item item, int stackSize) {
		super(id, name, item);
		this.stackSize = stackSize;
	}




	public int getStackSize() {
		return stackSize;
	}

}
