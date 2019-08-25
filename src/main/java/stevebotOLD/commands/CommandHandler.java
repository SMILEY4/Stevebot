package stevebotOLD.commands;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tools.listener.GameMiscListener;

public class CommandHandler implements GameMiscListener {


	public CommandHandler() {
//		Stevebot.EVENT_HANDLER.addListener(this);
	}




	@Override
	public void onPreInit(FMLPreInitializationEvent event) {
		ClientCommandHandler.instance.registerCommand(new CommandPlanPath());
		ClientCommandHandler.instance.registerCommand(new CommandActions());
		ClientCommandHandler.instance.registerCommand(new CommandFollowPath());
		ClientCommandHandler.instance.registerCommand(new CommandClearRenderer());
	}

}
