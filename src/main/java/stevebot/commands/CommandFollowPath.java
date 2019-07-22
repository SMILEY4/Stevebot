package stevebot.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import stevebot.MovementTest;
import stevebot.Stevebot;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandFollowPath extends CommandBase {


	@Override
	public String getName() {
		return "follow";
	}




	@Override
	public String getUsage(ICommandSender sender) {
		return "/follow";
	}




	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		process();
	}




	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
		return Collections.emptyList();
	}




	private void process() {
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Following last path."));
		if (Stevebot.PATH_HANDLER.isFollowingPath()) {
			Stevebot.PATH_HANDLER.stopFollowingLastPath();
		} else {
			Stevebot.PATH_HANDLER.startFollowingLastPath();
		}
		MovementTest.running = !MovementTest.running;
	}

}
