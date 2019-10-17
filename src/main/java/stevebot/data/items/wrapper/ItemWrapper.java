package stevebot.data.items.wrapper;

import net.minecraft.item.Item;

public class ItemWrapper {


	private final int id;
	private final String name;
	private final Item item;




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




	public int getId() {
		return id;
	}




	public String getName() {
		return name;
	}




	public Item getItem() {
		return item;
	}


}
