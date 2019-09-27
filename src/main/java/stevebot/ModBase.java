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
import stevebot.rendering.Renderer;
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




	/**
	 * Called in the pre-init-stage
	 */
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




	/**
	 * Called in the init-stage
	 */
	void onInit() {
		eventHandler.onInit();
	}




	/**
	 * Called in the post-init-stage
	 */
	void onPostInit() {
		eventHandler.onPostInit();
		blockLibrary.initialize();
	}




	/**
	 * Sends the message to the players chat (if possible) and to the logger of this mod.
	 *
	 * @param message the message to log
	 */
	public void log(String message) {
		log(true, message);
	}




	/**
	 * Sends the message to the players chat (if possible) and to the logger of this mod only if {@code Config.isVerboseMode()} is true.
	 *
	 * @param message the message to log
	 */
	public void logNonCritical(String message) {
		log(false, message);
	}




	/**
	 * Sends the message to the players chat (if possible) and to the logger of this mod.
	 *
	 * @param message  the message to log
	 * @param critical set to false to not send the message if {@code Config.isVerboseMode()} is false
	 */
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




	/**
	 * @return the {@link ModEventHandler}
	 */
	public ModEventHandler getEventHandler() {
		return eventHandler;
	}




	/**
	 * @return the {@link PlayerController}
	 */
	public PlayerController getPlayerController() {
		return playerController;
	}




	/**
	 * @return the {@link CustomCommandHandler}
	 */
	public CustomCommandHandler getCommandHandler() {
		return commandHandler;
	}




	/**
	 * @return the {@link Renderer}
	 */
	public Renderer getRenderer() {
		return renderer;
	}




	/**
	 * @return the {@link PathHandler}
	 */
	public PathHandler getPathHandler() {
		return pathHandler;
	}




	/**
	 * @return the {@link BlockLibrary}
	 */
	public BlockLibrary getBlockLibrary() {
		return blockLibrary;
	}




	/**
	 * @return the {@link BlockProvider}
	 */
	public BlockProvider getBlockProvider() {
		return blockProvider;
	}




	/**
	 * @return the {@link Logger}
	 */
	public Logger getLogger() {
		return logger;
	}

}
