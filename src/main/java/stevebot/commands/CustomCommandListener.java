package stevebot.commands;

import net.minecraft.command.ICommandSender;

import java.util.Map;

public interface CustomCommandListener {


	/**
	 * Called when the command gets executed.
	 *
	 * @param sender the sender of the command
	 * @param name   the name of the command.
	 * @param args   the arguments of the command.
	 */
	void onCommand(ICommandSender sender, String name, Map<String, CommandArgument<?>> args);

}
