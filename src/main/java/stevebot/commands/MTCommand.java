package stevebot.commands;

import stevebot.commands.tokens.ICommandToken;
import stevebot.commands.tokens.OptionalToken;
import stevebot.commands.tokens.TokenParseResult;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

import java.util.*;

public class MTCommand {


	public final String name;
	public final String usage;
	public final List<ICommandToken> tokens;
	public final MTCommandListener listener;
	private CommandBase commandBase;




	public MTCommand(String name, String usage, List<ICommandToken> tokens, MTCommandListener listener) {
		this.name = name;
		this.usage = usage;
		this.tokens = Collections.unmodifiableList(tokens);
		this.listener = listener;
	}




	void execute(MinecraftServer server, ICommandSender sender, String[] args) throws WrongUsageException {
		if (listener == null) {
			return;
		}

		Map<String, CommandArgument<?>> argsMap = new HashMap<>();
		String status = "success";

		if (!tokens.isEmpty()) {
			status = parse(sender, args, argsMap);
		}

		if (status.equalsIgnoreCase("success")) {
			listener.onCommand(sender, name, argsMap);
		} else {
			throw new WrongUsageException(usage);
		}

	}




	private String parse(ICommandSender sender, String[] args, Map<String, CommandArgument<?>> argsMap) {

		List<String> argsList = new ArrayList<>(Arrays.asList(args));

		for (int i = 0; i < tokens.size(); i++) {

			// get command
			boolean isOptional = false;
			ICommandToken token = tokens.get(i);
			isOptional = token instanceof OptionalToken;

			// get next args
			String[] currentArgs = nextArgs(argsList, token.requiredArguments(argsList.size()));
			if (currentArgs == null && !isOptional) {
				return "failed";
			}

			// parse
			TokenParseResult result = token.parse(sender, currentArgs);
			if (result.success ) {
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




	private String[] nextArgs(List<String> argsList, int n) {
		if (argsList.size() < n) {
			return null;
		} else {
			String[] args = new String[n];
			for (int i = 0; i < n; i++) {
				args[i] = argsList.get(i);
			}
			return args;
		}
	}




	private void consume(List<String> argsList, int n) {
		if (argsList.size() >= n) {
			for (int i = 0; i < n; i++) {
				argsList.remove(0);
			}
		}
	}




	void setCommandBase(CommandBase commandBase) {
		this.commandBase = commandBase;
	}




	public CommandBase getCommandBase() {
		return this.commandBase;
	}

}
