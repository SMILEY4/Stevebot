package stevebot.data.items.wrapper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemWrapper {


	private final int id;
	private final String name;
	private final Item item;
	private final ItemStack stack;




	/**
	 * @param id   the id of the item
	 * @param name the name of the item
	 * @param item the minecraft item
	 */
	public ItemWrapper(int id, String name, Item item) {
		this.id = id;
		this.name = name;
		this.item = item;
		this.stack = new ItemStack(item, 1);
	}




	public int getId() {
		return id;
	}




	public String getName() {
		return name;
	}




	public Item getItem() {
		return item;
	}




	public ItemStack getStack(int stackSize) {
		stack.setCount(stackSize);
		return stack;
	}


}
