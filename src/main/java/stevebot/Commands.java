package stevebot;

import modtools.commands.CommandArgument;
import modtools.commands.CommandBuilder;
import modtools.commands.tokens.ValueToken;
import modtools.player.Camera;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

public class Commands {


	public static void create() {

		// /freelook
		Stevebot.get().getCommandHandler().registerCommand(
				new CommandBuilder("freelook")
						.setListener(Commands::onFreelook)
						.build()
		);

		// /path <from> <to> [<timeout>]
		Stevebot.get().getCommandHandler().registerCommand(
				new CommandBuilder("path")
						.addToken(new ValueToken.BlockPosToken("from", true))
						.addToken(new ValueToken.BlockPosToken("to", true))
						.addOptional(new ValueToken.IntegerToken("timeout"))
						.setListener(Commands::onPathTo)
						.build()
		);

		// /follor
		Stevebot.get().getCommandHandler().registerCommand(
				new CommandBuilder("follor")
						.setListener(Commands::onPathTo)
						.build()
		);


	}




	private static void onPathTo(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
		final BlockPos from = (BlockPos) args.get("from").getValue();
		final BlockPos to = (BlockPos) args.get("to").getValue();
		final int timeoutSec = args.containsKey("timeout") ? (Integer) args.get("timeout").getValue() : 30;
		if (Stevebot.get().getPlayerController().getPlayer() != null) {
			Stevebot.PATH_HANDLER.calculatePath(from, to, timeoutSec * 1000);
		}
	}




	private static void onFreelook(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
		if (Stevebot.get().getPlayerController().getCamera().getState() == Camera.CameraState.FREELOOK) {
			Stevebot.get().getPlayerController().getCamera().setState(Camera.CameraState.DEFAULT);
			Stevebot.get().getPlayerController().sendMessage("Disable Freelook.");
		} else {
			Stevebot.get().getPlayerController().getCamera().setState(Camera.CameraState.FREELOOK);
			Stevebot.get().getPlayerController().sendMessage("Enable Freelook.");
		}
	}


}
