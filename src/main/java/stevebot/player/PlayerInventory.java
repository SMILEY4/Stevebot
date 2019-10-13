package stevebot.player;

public interface PlayerInventory {


	/**
	 * @return true, if the player has at least one throwaway-block in the hotbar.
	 */
	boolean hasThrowawayBlockInHotbar();

	/**
	 * Selects a throwaway-block in the hotbar.
	 *
	 * @return whether a throwaway-block was selected
	 */
	boolean selectThrowawayBlock();

}
