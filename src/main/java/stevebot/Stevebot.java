package stevebot;


import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod (
		modid = Settings.MODID,
		name = Settings.NAME,
		version = Settings.VERSION,
		acceptedMinecraftVersions = Settings.MC_VERSION
)
public class Stevebot extends ModBase {


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




}
