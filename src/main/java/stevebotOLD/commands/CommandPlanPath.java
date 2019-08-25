package stevebotOLD.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandPlanPath extends CommandBase {


	@Override
	public String getName() {
		return "path";
	}




	@Override
	public String getUsage(ICommandSender sender) {
		return "/path <x> <y> <z> <x> <y> <z> ";
	}




	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length < 6) {
			throw new WrongUsageException(getUsage(sender), new Object[0]);

		} else {
			BlockPos posStart = parseBlockPos(sender, args, 0, false);
			BlockPos posDest = parseBlockPos(sender, args, 3, false);
			process(posStart, posDest);
		}
	}




	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
		return Collections.emptyList();
	}




	private void process(BlockPos posStart, BlockPos posDest) {
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Going from " + posStart + " to " + posDest));
//		Stevebot.PATH_HANDLER.onPlanPath(posStart, posDest);
	}

}
