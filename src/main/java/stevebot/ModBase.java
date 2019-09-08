package stevebot;

import stevebot.commands.MTCommandHandler;
import stevebot.events.MTEventHandler;
import stevebot.player.MTPlayerController;
import stevebot.rendering.MTRenderer;

public class ModBase {


	private static ModBase instance;




	public static ModBase get() {
		return instance;
	}




	private MTEventHandler eventHandler;
	private MTPlayerController playerController;
	private MTCommandHandler commandHandler;
	private MTRenderer renderer;




	public void onPreInit() {
		ModBase.instance = this;

		eventHandler = new MTEventHandler(this);
		playerController = new MTPlayerController(this);
		commandHandler = new MTCommandHandler(this);
		renderer = new MTRenderer(this);

		Commands.create();

		eventHandler.onPreInit();
	}




	public void onInit() {
		eventHandler.onInit();
	}




	public void onPostInit() {
		eventHandler.onPostInit();
	}




	public MTEventHandler getEventHandler() {
		return eventHandler;
	}




	public MTPlayerController getPlayerController() {
		return playerController;
	}




	public MTCommandHandler getCommandHandler() {
		return commandHandler;
	}




	public MTRenderer getRenderer() {
		return renderer;
	}



}
