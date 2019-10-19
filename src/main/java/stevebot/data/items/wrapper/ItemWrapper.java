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




	/**
	 * @return the id of the item
	 */
	public int getId() {
		return id;
	}




	/**
	 * @return the name of the item ("minecraft:item_name")
	 */
	public String getName() {
		return name;
	}




	/**
	 * @return the {@link Item} or null
	 */
	public Item getItem() {
		return item;
	}




	/**
	 * @param stackSize the size of the stack
	 * @return a {@link ItemStack} with the item of this wrapper and the given size
	 */
	public ItemStack getStack(int stackSize) {
		stack.setCount(stackSize);
		return stack;
	}


}
