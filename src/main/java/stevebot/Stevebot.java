package stevebot;


import modtools.ModBase;
import modtools.events.GameTickListener;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

@Mod (
		modid = ModInfo.MODID,
		name = ModInfo.NAME,
		version = ModInfo.VERSION,
		acceptedMinecraftVersions = ModInfo.MC_VERSION
)
public class Stevebot extends ModBase {


	public static final Logger LOGGER = LogManager.getLogger(ModInfo.MODID);




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




	long time = 0;
	BlockPos pos = null;




	@Override
	protected void createMod() {
		Commands.create();

		// setLookAt + freelook test
		Stevebot.get().getEventHandler().addListener(new GameTickListener() {
			@Override
			public void onClientTick(TickEvent.ClientTickEvent event) {
				if (Stevebot.get().getPlayerController().getPlayer() != null) {
					if (System.currentTimeMillis() - time > 4000) {
						Random random = new Random();
						pos = Stevebot.get().getPlayerController().getPlayerBlockPos().add(random.nextInt(20) - 10, 0, random.nextInt(20) - 10);
						time = System.currentTimeMillis();
					}
				}

				if (pos != null && event.phase == TickEvent.Phase.START) {
					Stevebot.get().getPlayerController().getCamera().setLookAt(pos);
				}

			}
		});
	}

}
