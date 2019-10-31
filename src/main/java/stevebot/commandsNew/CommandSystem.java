package stevebot.commandsNew;

import net.minecraft.command.CommandException;

import java.util.HashMap;
import java.util.Map;

public class CommandSystem {


	public static void main(String[] args) {

		CommandListener listener = (templateId, parameter) -> {
			System.out.println(templateId);
			for (Map.Entry<String, Object> entry : parameter.entrySet()) {
				System.out.println("  " + entry.getKey() + " = " + entry.getValue());
			}
		};

		CommandSystem commandSystem = new CommandSystem();

		commandSystem.registerCommand("pathFromTo", "path <x1:COORDINATE> <y1:COORDINATE> <z1:COORDINATE> <x2:COORDINATE> <y2:COORDINATE> <z2:COORDINATE>", listener);

		commandSystem.registerCommand("pathFromToFreelook", "path <x1:COORDINATE> <y1:COORDINATE> <z1:COORDINATE> <x2:COORDINATE> <y2:COORDINATE> <z2:COORDINATE> freelook", listener);

		commandSystem.registerCommand("pathTo", "path <x:COORDINATE> <y:COORDINATE> <z:COORDINATE>", listener);

		commandSystem.registerCommand("pathToFreelook", "path <x:COORDINATE> <y:COORDINATE> <z:COORDINATE> freelook", listener);

		try {
			commandSystem.commands.get("path").execute(null, null, new String[]{"1", "2", "-3", "9", "8", "7"});
		} catch (CommandException e) {
			e.printStackTrace();
		}
	}




	private Map<String, CustomCommand> commands = new HashMap<>();




	/**
	 * @param templateId  an unique name of the given command (template)
	 * @param cmdTemplate a list of tokens seperated by a whitespace. A token can either be a word or a variable.
	 *                    A variable is always in the form "<name:type>"
	 */
	public void registerCommand(String templateId, String cmdTemplate, CommandListener listener) {

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


}
