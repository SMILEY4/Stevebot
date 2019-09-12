package stevebot.commands;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import stevebot.Stevebot;
import stevebot.commands.tokens.ValueToken;
import stevebot.pathfinding.path.PathRenderable;
import stevebot.player.Camera;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.math.BlockPos;
import stevebot.pathfinding.goal.ExactGoal;
import stevebot.pathfinding.goal.Goal;
import stevebot.pathfinding.goal.XZGoal;

import java.util.Map;

public class Commands {


	public static void create(ModCommandHandler commandHandler) {

		// /freelook
		commandHandler.registerCommand(
				new CommandBuilder("freelook")
						.setListener(Commands::onFreelook)
						.build()
		);

		// /path <from> <to> [<timeout>]
		commandHandler.registerCommand(
				new CommandBuilder("path")
						.addToken(new ValueToken.BlockPosToken("from", true))
						.addToken(new ValueToken.BlockPosToken("to", true))
						.addOptional(new ValueToken.IntegerToken("timeout"))
						.setListener(Commands::onPathTo)
						.build()
		);

		// /pathdir <distance> [<timeout>]
		commandHandler.registerCommand(
				new CommandBuilder("pathdir")
						.addToken(new ValueToken.IntegerToken("distance"))
						.addOptional(new ValueToken.IntegerToken("timeout"))
						.setListener(Commands::onPathDir)
						.build()
		);

		// /follow <start|stop>
		commandHandler.registerCommand(
				new CommandBuilder("follow")
						.addToken(new ValueToken.TextToken("state"))
						.setListener(Commands::onFollow)
						.build()
		);

		// /pathstyle <style>
		commandHandler.registerCommand(
				new CommandBuilder("pathstyle")
						.addToken(new ValueToken.TextToken("style"))
						.setListener(Commands::onPathStyle)
						.build()
		);

		// /showCachedChunks <show>
		commandHandler.registerCommand(
				new CommandBuilder("showCachedChunks")
						.addToken(new ValueToken.BooleanToken("show"))
						.setListener(Commands::displayCachedChunks)
						.build()
		);

	}




	private static void onPathTo(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
		final BlockPos from = (BlockPos) args.get("from").getValue();
		final BlockPos to = (BlockPos) args.get("to").getValue();
		final int timeoutSec = args.containsKey("timeout") ? (Integer) args.get("timeout").getValue() : 30;
		if (Stevebot.get().getPlayerController().getPlayer() != null) {
			Stevebot.get().getPathHandler().createPath(from, new ExactGoal(to), timeoutSec * 1000);
		}
	}




	private static void onPathDir(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
		final int distance = (Integer) args.get("distance").getValue();
		final int timeoutSec = args.containsKey("timeout") ? (Integer) args.get("timeout").getValue() : 30;
		if (Stevebot.get().getPlayerController().getPlayer() != null) {
			final BlockPos from = Stevebot.get().getPlayerController().utils().getPlayerBlockPos();
			final Vector3d dir = Stevebot.get().getPlayerController().camera().getLookDir().setLength(distance);
			Goal goal = new XZGoal(from.getX() + dir.getIntX(), from.getZ() + dir.getIntZ());
			Stevebot.get().getPathHandler().createPath(from, goal, timeoutSec * 1000);
		}
	}




	private static void onFollow(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
		final String state = (String) args.get("state").getValue();
		if (Stevebot.get().getPlayerController().getPlayer() != null) {
			if ("start".equalsIgnoreCase(state)) {
				Stevebot.get().getPathHandler().startFollowLastPath();
			} else if ("stop".equalsIgnoreCase(state)) {
				Stevebot.get().getPathHandler().stopFollowing();
			} else {
				Stevebot.get().getPlayerController().utils().sendMessage("Unknown state: " + state + ". Must be 'start' or 'stop'.");
			}
		}
	}




	private static void onFreelook(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
		if (Stevebot.get().getPlayerController().camera().getState() == Camera.CameraState.FREELOOK) {
			Stevebot.get().getPlayerController().camera().setState(Camera.CameraState.DEFAULT);
			Stevebot.get().getPlayerController().utils().sendMessage("Disable Freelook.");
		} else {
			Stevebot.get().getPlayerController().camera().setState(Camera.CameraState.FREELOOK);
			Stevebot.get().getPlayerController().utils().sendMessage("Enable Freelook.");
		}
	}




	private static void onPathStyle(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
		final String strStyle = ((String) args.get("style").getValue()).trim().toUpperCase();
		try {
			final PathRenderable.Style style = PathRenderable.Style.valueOf(strStyle);
//			Stevebot.get().getPathHandler().setPathRenderableStyle(style);
		} catch (IllegalArgumentException e) {
			Stevebot.get().getPlayerController().utils().sendMessage("Unknown style: '" + strStyle + "'");
			for (PathRenderable.Style s : PathRenderable.Style.values()) {
				Stevebot.get().getPlayerController().utils().sendMessage("    - '" + s.toString() + "'");
			}
		}
	}


	private static void displayCachedChunks(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
		final boolean enable = (Boolean)args.get("show").getValue();
//		Stevebot.get().getPathHandler().showCachedChunks(enable);
		Stevebot.get().getPlayerController().utils().sendMessage("Display Cached Chunks: '" + enable + "'");
	}

}
