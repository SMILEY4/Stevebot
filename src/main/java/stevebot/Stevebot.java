package stevebot;


import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stevebot.commands.CommandHandler;
import stevebot.pathfinding.PathHandler;
import stevebot.player.PlayerController;
import stevebot.rendering.Renderer;
import stevebot.utils.GameEventHandler;

@Mod (
		modid = Stevebot.MODID,
		name = Stevebot.NAME,
		version = Stevebot.VERSION,
		acceptedMinecraftVersions = Stevebot.MC_VERSION
)
public class Stevebot {


	public static final String MODID = "stevebot";
	public static final String NAME = "SteveBot";
	public static final String VERSION = "0.1-dev";
	public static final String MC_VERSION = "[1.12.2]";

	public static final Logger LOGGER = LogManager.getLogger(Stevebot.MODID);

	public static final GameEventHandler EVENT_HANDLER = new GameEventHandler();
	public static final CommandHandler COMMAND_HANDLER = new CommandHandler();
	public static final Renderer RENDERER = new Renderer();
	public static final PlayerController PLAYER_CONTROLLER = new PlayerController();
	public static final PathHandler PATH_HANDLER = new PathHandler();

//	public static final MovementTest test = new MovementTest();



	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		EVENT_HANDLER.onPreInit(event);
	}




	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		EVENT_HANDLER.onInit(event);
	}




	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		EVENT_HANDLER.onPostInit(event);
	}


}
