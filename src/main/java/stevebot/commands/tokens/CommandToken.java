package stevebot.commands.tokens;

import net.minecraft.command.ICommandSender;

public interface CommandToken {


	String getID();

	String getUsage();

	int requiredArguments(int nRemaining);

	TokenParseResult parse(ICommandSender sender, String[] args);

}
