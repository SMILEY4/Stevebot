package stevebot.commands;

import net.minecraftforge.client.ClientCommandHandler;
import stevebot.eventsOLD.GameInitListener;
import stevebot.eventsOLD.ModEventHandler;

import java.util.ArrayList;
import java.util.List;

public class CustomCommandHandler implements GameInitListener {


	private List<CustomCommand> commandList = new ArrayList<>();




	/**
	 * @param eventHandler the {@link ModEventHandler}
	 */
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




	/**
	 * Register the given command. Commands have to be registered before the pre-init phase.
	 */
	void registerCommand(CustomCommand command) {
		commandList.add(command);
	}


}
