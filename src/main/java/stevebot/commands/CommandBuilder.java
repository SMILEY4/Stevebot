package stevebot.commands;

import stevebot.commands.tokens.CommandToken;
import stevebot.commands.tokens.OptionalToken;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;

public class CommandBuilder {


	private final String commandName;
	private final List<CommandToken> tokens = new ArrayList<>();
	private CustomCommandListener listener;




	public CommandBuilder(String commandName) {
		this.commandName = commandName;
	}




	public CommandBuilder addToken(CommandToken token) {
		tokens.add(token);
		return this;
	}




	public CommandBuilder addOptional(CommandToken token) {
		tokens.add(new OptionalToken(token));
		return this;
	}




	public CommandBuilder setListener(CustomCommandListener listener) {
		this.listener = listener;
		return this;
	}




	public CustomCommand build() {
		CustomCommand command = new CustomCommand(commandName, buildUsageString(), tokens, listener);
		command.setCommandBase(buildCommandBase(command.name, command.usage, command));
		return command;
	}




	private String buildUsageString() {
		StringBuilder builder = new StringBuilder();
		builder.append("/").append(commandName).append(" ");
		for (int i = 0; i < tokens.size(); i++) {
			builder.append(tokens.get(i).getUsage());
			if (i < tokens.size() - 1) {
				builder.append(" ");
			}
		}
		return builder.toString();
	}




	private CommandBase buildCommandBase(String name, String usage, CustomCommand command) {

		return new CommandBase() {
			@Override
			public String getName() {
				return name;
			}




			@Override
			public String getUsage(ICommandSender sender) {
				return usage;
			}




			@Override
			public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
				command.execute(sender, args);
			}
		};

	}

}
