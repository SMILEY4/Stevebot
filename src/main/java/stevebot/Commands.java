package stevebot;

import modtools.commands.CommandArgument;
import modtools.commands.CommandBuilder;
import modtools.player.Camera;
import net.minecraft.command.ICommandSender;

import java.util.Map;

public class Commands {


	public static void create() {
		Stevebot.get().getCommandHandler().registerCommand(
				new CommandBuilder("freelook")
						.setListener(Commands::onFreelook)
						.build()
		);
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
