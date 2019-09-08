package stevebot.events;

public interface GameInitListener extends EventListener {


	default void onPreInit() {
	}

	default void onInit() {
	}

	default void onPostInit() {
	}

}
