package stevebot;


import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stevebot.data.blocks.BlockLibrary;
import stevebot.data.blocks.BlockLibraryImpl;
import stevebot.data.blocks.BlockProvider;
import stevebot.data.blocks.BlockProviderImpl;
import stevebot.events.EventManager;
import stevebot.events.EventManagerImpl;
import stevebot.events.ModEventProvider;
import stevebot.pathfinding.PathHandler;
import stevebot.player.PlayerController;
import stevebot.rendering.Renderer;
import stevebot.rendering.RendererImpl;

@Mod (
		modid = Config.MODID,
		name = Config.NAME,
		version = Config.VERSION,
		acceptedMinecraftVersions = Config.MC_VERSION
)
public class Stevebot {


	private static Logger logger = LogManager.getLogger(Config.MODID);

	private static EventManager eventManager;
	private static ModEventProvider eventProvider;
	private static BlockLibrary blockLibrary;
	private static BlockProvider blockProvider;
	private static Renderer renderer;
	private static PlayerController playerController;
	private static PathHandler pathHandler;




	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Stevebot.eventManager = new EventManagerImpl();
		Stevebot.eventProvider = new ModEventProvider(Stevebot.eventManager);
		Stevebot.blockLibrary = new BlockLibraryImpl(Stevebot.eventManager);
		Stevebot.blockProvider = new BlockProviderImpl(Stevebot.blockLibrary);
		Stevebot.renderer = new RendererImpl(Stevebot.eventManager, Stevebot.blockProvider);
		Stevebot.playerController = new PlayerController(Stevebot.eventManager);
		Stevebot.pathHandler = new PathHandler(Stevebot.eventManager, Stevebot.renderer, Stevebot.playerController);
		Stevebot.eventProvider.onPreInit();
	}




	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		Stevebot.eventProvider.onInit();
	}




	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		Stevebot.eventProvider.onPostInit();
	}




	/**
	 * Sends the message to the players chat (if possible) and to the logger of this mod.
	 *
	 * @param message the message to log
	 */
	public static void log(String message) {
		log(true, message);
	}




	/**
	 * Sends the message to the players chat (if possible) and to the logger of this mod only if {@code Config.isVerboseMode()} is true.
	 *
	 * @param message the message to log
	 */
	public static void logNonCritical(String message) {
		log(false, message);
	}




	/**
	 * Sends the message to the players chat (if possible) and to the logger of this mod.
	 *
	 * @param message  the message to log
	 * @param critical set to false to not send the message if {@code Config.isVerboseMode()} is false
	 */
	public static void log(boolean critical, String message) {
		if (!Config.isVerboseMode() && !critical) {
			return;
		}
		if (playerController.getPlayer() == null) {
			getLogger().info(message);
		} else {
			playerController.utils().sendMessage(message);
		}
	}




	/**
	 * @return the {@link Logger}
	 */
	public static Logger getLogger() {
		return logger;
	}

}

