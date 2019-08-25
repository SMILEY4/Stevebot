//package stevebotOLD;
//
//
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.fml.common.event.FMLInitializationEvent;
//import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
//import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import tools.events.GameEventHandler;
//
//@Mod (
//		modid = Stevebot.MODID,
//		name = Stevebot.NAME,
//		version = Stevebot.VERSION,
//		acceptedMinecraftVersions = Stevebot.MC_VERSION
//)
//public class Stevebot {
//
//
//	public static final String MODID = "stevebotOLD";
//	public static final String NAME = "SteveBot";
//	public static final String VERSION = "0.1-dev";
//	public static final String MC_VERSION = "[1.12.2]";
//
//	public static final Logger LOGGER = LogManager.getLogger(Stevebot.MODID);
//
//	public static final GameEventHandler EVENT_HANDLER = new GameEventHandler();
//
//
//
//
//	@Mod.EventHandler
//	public void preInit(FMLPreInitializationEvent event) {
//		EVENT_HANDLER.onPreInit(event);
//	}
//
//
//
//
//	@Mod.EventHandler
//	public void init(FMLInitializationEvent event) {
//		EVENT_HANDLER.onInit(event);
//	}
//
//
//
//
//	@Mod.EventHandler
//	public void postInit(FMLPostInitializationEvent event) {
//		EVENT_HANDLER.onPostInit(event);
//	}
//
//
//}
