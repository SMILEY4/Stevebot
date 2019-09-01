package stevebot;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import modtools.commands.CommandArgument;
import modtools.commands.CommandBuilder;
import modtools.commands.tokens.ValueToken;
import modtools.player.Camera;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.math.BlockPos;
import stevebot.pathfinding.goal.ExactGoal;
import stevebot.pathfinding.goal.Goal;
import stevebot.pathfinding.goal.XZGoal;

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

		// /pathdir <distance> [<timeout>]
		Stevebot.get().getCommandHandler().registerCommand(
				new CommandBuilder("pathdir")
						.addToken(new ValueToken.IntegerToken("distance"))
						.addOptional(new ValueToken.IntegerToken("timeout"))
						.setListener(Commands::onPathDir)
						.build()
		);

		// /follow
		Stevebot.get().getCommandHandler().registerCommand(
				new CommandBuilder("follow")
						.addToken(new ValueToken.TextToken("state"))
						.setListener(Commands::onFollow)
						.build()
		);


	}




	private static void onPathTo(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
		final BlockPos from = (BlockPos) args.get("from").getValue();
		final BlockPos to = (BlockPos) args.get("to").getValue();
		final int timeoutSec = args.containsKey("timeout") ? (Integer) args.get("timeout").getValue() : 30;
		if (Stevebot.get().getPlayerController().getPlayer() != null) {
			Stevebot.PATH_HANDLER.calculatePath(from, new ExactGoal(to), timeoutSec * 1000);
		}
	}




	private static void onPathDir(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
		final int distance = (Integer) args.get("distance").getValue();
		final int timeoutSec = args.containsKey("timeout") ? (Integer) args.get("timeout").getValue() : 30;
		if (Stevebot.get().getPlayerController().getPlayer() != null) {
			final BlockPos from = Stevebot.get().getPlayerController().getPlayerBlockPos();
			final Vector3d dir = Stevebot.get().getPlayerController().getCamera().getLookDir().setLength(distance);
			Goal goal = new XZGoal(from.getX() + dir.getIntX(), from.getZ() + dir.getIntZ());
			Stevebot.PATH_HANDLER.calculatePath(from, goal, timeoutSec * 1000);
		}
	}




	private static void onFollow(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
		final String state = (String) args.get("state").getValue();
		if (Stevebot.get().getPlayerController().getPlayer() != null) {
			if ("start".equalsIgnoreCase(state)) {
				Stevebot.PATH_HANDLER.startFollowLastPath();
			} else if ("stop".equalsIgnoreCase(state)) {
				Stevebot.PATH_HANDLER.stopFollowing();
			} else {
				Stevebot.get().getPlayerController().sendMessage("Unknown state: " + state + ". Must be 'start' or 'stop'.");
			}
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
