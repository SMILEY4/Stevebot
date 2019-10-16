package stevebot.data.items;

public class ItemUtils {


	private static ItemLibrary itemLibrary;




	public static void initialize(ItemLibrary itemLibrary) {
		ItemUtils.itemLibrary = itemLibrary;
	}




	public static ItemLibrary getItemLibrary() {
		return ItemUtils.itemLibrary;
	}




}
