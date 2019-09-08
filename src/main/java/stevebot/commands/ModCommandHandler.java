package stevebot.commands;

import net.minecraftforge.client.ClientCommandHandler;
import stevebot.events.GameInitListener;
import stevebot.events.ModEventHandler;

import java.util.ArrayList;
import java.util.List;

public class ModCommandHandler implements GameInitListener {


	private List<MTCommand> commandList = new ArrayList<>();




	public ModCommandHandler(ModEventHandler eventHandler) {
		eventHandler.addListener(this);
		Commands.create(this);
	}




	@Override
	public void onPreInit() {
		for (MTCommand command : commandList) {
			ClientCommandHandler.instance.registerCommand(command.getCommandBase());
		}
	}




	void registerCommand(MTCommand command) {
		commandList.add(command);
	}


}
