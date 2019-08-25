package modtools.commands.tokens;

import net.minecraft.command.ICommandSender;

public interface ICommandToken {


	String getID();

	String getUsage();

	int requiredArguments();

	TokenParseResult parse(ICommandSender sender, String[] args);

}