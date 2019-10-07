package stevebot.commands;

import net.minecraftforge.client.ClientCommandHandler;
import stevebot.events.EventListener;
import stevebot.events.EventManager;
import stevebot.events.PreInitEvent;

import java.util.ArrayList;
import java.util.List;

public class CustomCommandHandler {


	private List<CustomCommand> commandList = new ArrayList<>();




	/**
	 * @param eventHandler the {@link EventManager}
	 */
	public CustomCommandHandler(EventManager eventHandler) {
		eventHandler.addListener(new EventListener<PreInitEvent>() {
			@Override
			public Class<PreInitEvent> getEventClass() {
				return PreInitEvent.class;
			}




			@Override
			public void onEvent(PreInitEvent event) {
				onPreInit();
			}
		});
		Commands.create(this);
	}




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
