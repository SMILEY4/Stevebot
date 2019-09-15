package stevebot.commands;

import net.minecraftforge.client.ClientCommandHandler;
import stevebot.events.GameInitListener;
import stevebot.events.ModEventHandler;

import java.util.ArrayList;
import java.util.List;

public class CustomCommandHandler implements GameInitListener {


	private List<CustomCommand> commandList = new ArrayList<>();




	public CustomCommandHandler(ModEventHandler eventHandler) {
		eventHandler.addListener(this);
		Commands.create(this);
	}




	@Override
	public void onPreInit() {
		for (CustomCommand command : commandList) {
			ClientCommandHandler.instance.registerCommand(command.getCommandBase());
		}
	}




	void registerCommand(CustomCommand command) {
		commandList.add(command);
	}


}
