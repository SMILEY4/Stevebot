package stevebot.data.items;

import net.minecraft.item.Item;

public class ItemWrapperTool extends ItemWrapper {


	private int maxDurability;
	private int durability;




	/**
	 * @param id            the id of the item
	 * @param name          the name of the item
	 * @param item          the minecraft item
	 * @param maxDurability the max durability of the wrapped tool
	 */
	public ItemWrapperTool(int id, String name, Item item, int maxDurability) {
		super(id, name, item);
		this.maxDurability = maxDurability;
		this.durability = maxDurability;
	}




	public int getMaxDurability() {
		return maxDurability;
	}




	public int getDurability() {
		return durability;
	}

}
