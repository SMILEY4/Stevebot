package stevebot;

import stevebot.commands.ModCommandHandler;
import stevebot.data.blocks.BlockLibrary;
import stevebot.data.blocks.BlockLibraryImpl;
import stevebot.data.blocks.BlockProvider;
import stevebot.data.blocks.BlockProviderImpl;
import stevebot.events.ModEventHandler;
import stevebot.pathfinding.PathHandler;
import stevebot.player.PlayerController;
import stevebot.rendering.RendererImpl;

public class ModBase {


	private static ModBase instance;




	public static ModBase get() {
		return instance;
	}




	private ModEventHandler eventHandler;
	private PlayerController playerController;
	private ModCommandHandler commandHandler;
	private RendererImpl renderer;
	private PathHandler pathHandler;
	private BlockLibrary blockLibrary;
	private BlockProvider blockProvider;




	void onPreInit() {
		ModBase.instance = this;
		eventHandler = new ModEventHandler();
		commandHandler = new ModCommandHandler(eventHandler);
		renderer = new RendererImpl(eventHandler);
		playerController = new PlayerController(eventHandler);
		pathHandler = new PathHandler();
		blockLibrary = new BlockLibraryImpl();
		blockProvider = new BlockProviderImpl(blockLibrary);
		eventHandler.onPreInit();
	}




	void onInit() {
		eventHandler.onInit();
	}




	void onPostInit() {
		eventHandler.onPostInit();
		blockLibrary.initialize();
	}




	public ModEventHandler getEventHandler() {
		return eventHandler;
	}




	public PlayerController getPlayerController() {
		return playerController;
	}




	public ModCommandHandler getCommandHandler() {
		return commandHandler;
	}




	public RendererImpl getRenderer() {
		return renderer;
	}




	public PathHandler getPathHandler() {
		return pathHandler;
	}




	public BlockLibrary getBlockLibrary() {
		return blockLibrary;
	}




	public BlockProvider getBlockProvider() {
		return blockProvider;
	}


}
