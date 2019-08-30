package stevebot;


import modtools.ModBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stevebot.pathfinding.PathHandler;

@Mod (
		modid = Settings.MODID,
		name = Settings.NAME,
		version = Settings.VERSION,
		acceptedMinecraftVersions = Settings.MC_VERSION
)
public class Stevebot extends ModBase {


	public static final Logger LOGGER = LogManager.getLogger(Settings.MODID);

	public static final PathHandler PATH_HANDLER = new PathHandler();




	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.onPreInit();
	}




	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		super.onInit();
	}




	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		super.onPostInit();
	}




	@Override
	protected void createMod() {
		Commands.create();
	}

}
