package stevebot.events;

public interface GameInitListener extends EventListener {


	/**
	 * Called in the pre-init-stage
	 */
	default void onPreInit() {
	}

	/**
	 * Called in the init-stage
	 */
	default void onInit() {
	}

	/**
	 * Called in the post-init-stage
	 */
	default void onPostInit() {
	}

}
