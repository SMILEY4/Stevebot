package stevebot.data.items;

import net.minecraft.item.Item;

public class ItemWrapper {


	public final int id;
	public final String name;
	public final Item item;




	public ItemWrapper(int id, String name, Item item) {
		this.id = id;
		this.name = name;
		this.item = item;
	}


}
