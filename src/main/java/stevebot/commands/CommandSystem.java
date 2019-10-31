package stevebot.commands;

import net.minecraftforge.client.ClientCommandHandler;

import java.util.HashMap;
import java.util.Map;

public class CommandSystem {


	private static Map<String, CustomCommand> commands = new HashMap<>();




	/**
	 * @param templateId  an unique name of the given command (template)
	 * @param cmdTemplate a list of tokens seperated by a whitespace. A token can either be a word or a variable.
	 *                    A variable is always in the form "<name:type>"
	 */
	public static void addCommand(String templateId, String cmdTemplate, CommandListener listener) {

		final String[] tokens = cmdTemplate.split(" ");
		if (tokens.length == 0) {
			throw new IllegalArgumentException("Command template can not be null.");
		}

		final String cmdName = tokens[0];

		CustomCommand command = commands.get(cmdName);
		if (command == null) {
			command = new CustomCommand(cmdName);
			commands.put(cmdName, command);
		}

		command.registerTemplate(new CommandTemplate(templateId, tokens, listener));
	}




	/**
	 * Registers all added commands at the {@link ClientCommandHandler}
	 */
	public static void registerCommands() {
		for (CustomCommand command : commands.values()) {
			ClientCommandHandler.instance.registerCommand(command);
		}
	}


}
