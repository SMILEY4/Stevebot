package stevebot.commands;

import net.minecraft.command.ICommandSender;

import java.util.Map;

public interface CustomCommandListener {


	void onCommand(ICommandSender sender, String name, Map<String, CommandArgument<?>> args);

}
