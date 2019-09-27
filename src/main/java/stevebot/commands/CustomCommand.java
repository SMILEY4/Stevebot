package stevebot.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import stevebot.commands.tokens.CommandToken;
import stevebot.commands.tokens.OptionalToken;
import stevebot.commands.tokens.TokenParseResult;

import java.util.*;

public class CustomCommand {


	public final String name;
	public final String usage;
	public final List<CommandToken> tokens;
	public final CustomCommandListener listener;
	private CommandBase commandBase;




	/**
	 * @param name     the minecraft name of this command. This command will start with "/name ...".
	 * @param usage    the usage string of this command.
	 * @param tokens   the tokens that make up this command.
	 * @param listener a command listener.
	 */
	public CustomCommand(String name, String usage, List<CommandToken> tokens, CustomCommandListener listener) {
		this.name = name;
		this.usage = usage;
		this.tokens = Collections.unmodifiableList(tokens);
		this.listener = listener;
	}




	/**
	 * Execute this command.
	 *
	 * @param sender the sender of the command.
	 * @param args   the command arguments
	 */
	public void execute(ICommandSender sender, String[] args) throws WrongUsageException {

		Map<String, CommandArgument<?>> argsMap = new HashMap<>();
		String status = "success";

		if (!tokens.isEmpty()) {
			status = parse(sender, args, argsMap);
		}

		if (status.equalsIgnoreCase("success")) {
			if (listener != null) {
				listener.onCommand(sender, name, argsMap);
			}
		} else {
			throw new WrongUsageException(usage);
		}

	}




	/**
	 * Parse the command arguments into the given argsMap
	 *
	 * @return the string "success" or "failed".
	 */
	private String parse(ICommandSender sender, String[] args, Map<String, CommandArgument<?>> argsMap) {

		List<String> argsList = new ArrayList<>(Arrays.asList(args));

		for (int i = 0; i < tokens.size(); i++) {

			// get command
			boolean isOptional = false;
			CommandToken token = tokens.get(i);
			isOptional = token instanceof OptionalToken;

			// get next args
			String[] currentArgs = nextArgs(argsList, token.requiredArguments(argsList.size()));
			if (currentArgs == null && !isOptional) {
				return "failed";
			}

			// parse
			TokenParseResult result = token.parse(sender, currentArgs);
			if (result.success) {
				if (result.argument != null) {
					argsMap.put(token.getID(), result.argument);
				}
			} else {
				if (isOptional) {
					continue;
				} else {
					return "failed";
				}
			}

			// consume tokens
			consume(argsList, token.requiredArguments(argsList.size()));

		}

		if (argsList.isEmpty()) {
			return "success";
		} else {
			return "failed";
		}


	}




	/**
	 * @return the next n args from the given list, or null, if the list has less elements than n.
	 */
	private String[] nextArgs(List<String> argsList, int n) {
		if (argsList.size() < n) {
			return null;
		} else {
			String[] args = new String[n];
			for (int i = 0; i < n; i++) {
				args[i] = argsList.get(i);
			}
			if (args.length == 1 && args[0].isEmpty()) {
				return null;
			}
			return args;
		}
	}




	/**
	 * Remove the first n elements from the given list.
	 */
	private void consume(List<String> argsList, int n) {
		if (argsList.size() >= n) {
			for (int i = 0; i < n; i++) {
				argsList.remove(0);
			}
		}
	}




	/**
	 * @param commandBase the new {@link CommandBase} of this command.
	 */
	void setCommandBase(CommandBase commandBase) {
		this.commandBase = commandBase;
	}




	/**
	 * @return the {@link CommandBase} of this command.
	 */
	public CommandBase getCommandBase() {
		return this.commandBase;
	}

}
