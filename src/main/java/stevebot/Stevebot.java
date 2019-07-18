package stevebot;


import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stevebot.commands.CommandActions;
import stevebot.commands.CommandGoto;
import stevebot.rendering.Renderer;

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




	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ClientCommandHandler.instance.registerCommand(new CommandGoto());
		ClientCommandHandler.instance.registerCommand(new CommandActions());
	}




	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
	}




	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new Renderer());
	}


}
