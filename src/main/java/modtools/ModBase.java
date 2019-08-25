package modtools;

import modtools.commands.MTCommandHandler;
import modtools.events.MTEventHandler;
import modtools.player.MTPlayerController;

public abstract class ModBase {


	private static ModBase instance;




	public static ModBase get() {
		return instance;
	}




	private MTEventHandler eventHandler;
	private MTPlayerController playerController;
	private MTCommandHandler commandHandler;




	public void onPreInit() {
		ModBase.instance = this;
		eventHandler = new MTEventHandler(this);
		playerController = new MTPlayerController(this);
		commandHandler = new MTCommandHandler(this);
		createMod();
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




	protected abstract void createMod();

}
