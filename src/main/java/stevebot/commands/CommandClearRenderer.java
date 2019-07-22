package stevebot.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import stevebot.Stevebot;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandClearRenderer extends CommandBase {


	@Override
	public String getName() {
		return "clearrenderer";
	}




	@Override
	public String getUsage(ICommandSender sender) {
		return "/clearrenderer";
	}




	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		Stevebot.RENDERER.removeAll();
	}




	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
		return Collections.emptyList();
	}




}
