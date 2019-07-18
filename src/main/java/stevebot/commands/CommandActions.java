package stevebot.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.actions.ActionGenerator;
import stevebot.pathfinding.actions.IAction;
import stevebot.rendering.Renderer;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandActions extends CommandBase {


	@Override
	public String getName() {
		return "actions";
	}




	@Override
	public String getUsage(ICommandSender sender) {
		return "/actions <x> <y> <z> ";
	}




	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length < 3) {
			throw new WrongUsageException(getUsage(sender), new Object[0]);

		} else {
			BlockPos pos = parseBlockPos(sender, args, 0, false);
			process(pos);
		}
	}




	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
		return Collections.emptyList();
	}




	private void process(BlockPos pos) {
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Display available actions from " + pos));
		List<IAction> actions = ActionGenerator.getActions(Node.get(pos));
		Renderer.add(actions);
	}

}
