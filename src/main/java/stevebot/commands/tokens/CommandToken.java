package stevebot.commands.tokens;

import net.minecraft.command.ICommandSender;

public interface CommandToken {


	/**
	 * @return the name or identifier of this token.
	 */
	String getID();

	/**
	 * @return the usage string of this token
	 */
	String getUsage();

	/**
	 * @param nRemaining how many total args remain while parsing.
	 * @return the number of required args this token needs (depending on the number of remaining args).
	 */
	int requiredArguments(int nRemaining);

	/**
	 * @param sender the {@link ICommandSender} of the command
	 * @param args   the list of arguments
	 * @return the result of parsing the given args.
	 */
	TokenParseResult parse(ICommandSender sender, String[] args);

}
