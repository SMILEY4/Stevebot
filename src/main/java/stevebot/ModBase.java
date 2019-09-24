package stevebot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stevebot.commands.CustomCommandHandler;
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




	private Logger logger = LogManager.getLogger(Config.MODID);
	private ModEventHandler eventHandler;
	private PlayerController playerController;
	private CustomCommandHandler commandHandler;
	private RendererImpl renderer;
	private PathHandler pathHandler;
	private BlockLibrary blockLibrary;
	private BlockProvider blockProvider;




	void onPreInit() {
		ModBase.instance = this;
		eventHandler = new ModEventHandler();
		commandHandler = new CustomCommandHandler(eventHandler);
		playerController = new PlayerController(eventHandler);
		pathHandler = new PathHandler();
		blockLibrary = new BlockLibraryImpl();
		blockProvider = new BlockProviderImpl(blockLibrary);
		renderer = new RendererImpl(eventHandler);
		eventHandler.onPreInit();
	}




	void onInit() {
		eventHandler.onInit();
	}




	void onPostInit() {
		eventHandler.onPostInit();
		blockLibrary.initialize();
	}




	public void log(String message) {
		log(true, message);
	}




	public void logNonCritical(String message) {
		log(false, message);
	}




	public void log(boolean critical, String message) {
		if (!Config.isVerboseMode() && !critical) {
			return;
		}
		if (getPlayerController().getPlayer() == null) {
			getLogger().info(message);
		} else {
			getPlayerController().utils().sendMessage(message);
		}
	}




	public ModEventHandler getEventHandler() {
		return eventHandler;
	}




	public PlayerController getPlayerController() {
		return playerController;
	}




	public CustomCommandHandler getCommandHandler() {
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




	public Logger getLogger() {
		return logger;
	}

}
