package stevebot;


import modtools.ModBase;
import modtools.commands.CommandArgument;
import modtools.commands.CommandBuilder;
import modtools.commands.MTCommand;
import modtools.commands.MTCommandListener;
import modtools.commands.tokens.ValueToken;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

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




	@Override
	protected void createMod() {
		MTCommand cmd = new CommandBuilder("path")
				.addToken(new ValueToken.BlockPosToken("from", true))
				.addOptional(new ValueToken.IntegerToken("time"))
				.setListener(new MTCommandListener() {
					@Override
					public void onCommand(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
						LOGGER.info("ON COMMAND: " + name);
						for (Map.Entry<String, CommandArgument<?>> entry : args.entrySet()) {
							String str = "  " + entry.getKey();
							if (entry.getValue().existsValue()) {
								str += " " + entry.getValue().getValue();
							} else {
								str += " - ";
							}
							LOGGER.info(str);
						}
					}
				})
				.build();
		getCommandHandler().registerCommand(cmd);
	}

}
