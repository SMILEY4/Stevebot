package stevebot.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import stevebot.commands.tokens.CommandToken;
import stevebot.commands.tokens.OptionalToken;

import java.util.ArrayList;
import java.util.List;

public class CommandBuilder {


	private final String commandName;
	private final List<CommandToken> tokens = new ArrayList<>();
	private CustomCommandListener listener;




	/**
	 * @param commandName the unique name of the minecraft command. The final command will start with "/commandName"
	 */
	public CommandBuilder(String commandName) {
		this.commandName = commandName;
	}




	/**
	 * Add the given token to this command.
	 *
	 * @param token the token to add
	 * @return this builder for chaining
	 */
	public CommandBuilder addToken(CommandToken token) {
		tokens.add(token);
		return this;
	}




	/**
	 * Add the given token as an option token to this command.
	 *
	 * @param token the optional token to add
	 * @return this builder for chaining
	 */
	public CommandBuilder addOptional(CommandToken token) {
		tokens.add(new OptionalToken(token));
		return this;
	}




	/**
	 * Set the listener of the command to the given listener.
	 *
	 * @param listener the command listener
	 * @return this builder for chaining
	 */
	public CommandBuilder setListener(CustomCommandListener listener) {
		this.listener = listener;
		return this;
	}




	/**
	 * Builds the final command.
	 *
	 * @return the finished command.
	 */
	public CustomCommand build() {
		CustomCommand command = new CustomCommand(commandName, buildUsageString(), tokens, listener);
		command.setCommandBase(buildCommandBase(command.name, command.usage, command));
		return command;
	}




	/**
	 * @return the usage string
	 */
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




	/**
	 * @param name    the name of the command
	 * @param usage   the usage string of the command
	 * @param command the custom command
	 * @return the {@link CommandBase}
	 */
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
