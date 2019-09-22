package stevebot.commands;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.math.BlockPos;
import stevebot.Config;
import stevebot.Stevebot;
import stevebot.commands.tokens.MultiCommandToken;
import stevebot.commands.tokens.ValueToken;
import stevebot.pathfinding.goal.ExactGoal;
import stevebot.pathfinding.goal.Goal;
import stevebot.pathfinding.goal.XZGoal;
import stevebot.pathfinding.path.PathRenderable;
import stevebot.player.Camera;

import java.util.Map;

public class Commands {


	public static void create(CustomCommandHandler commandHandler) {


		// /path ...
		commandHandler.registerCommand(new CommandBuilder("path")
				.addToken(new MultiCommandToken("pathMulti")

						// /path <from> <to>
						.addCommand(new CommandBuilder("path")
								.addToken(new ValueToken.BlockPosToken("from", true))
								.addToken(new ValueToken.BlockPosToken("to", true))
								.setListener(Commands::onPathFromTo)
								.build())

						// /path <to> [follow] [freelook]
						.addCommand(new CommandBuilder("path")
								.addToken(new ValueToken.BlockPosToken("to", true))
								.addOptional(new ValueToken.BooleanToken("follow"))
								.addOptional(new ValueToken.BooleanToken("freelook"))
								.setListener(Commands::onPathTo)
								.build())

						// /path <dist> [follow] [freelook]
						.addCommand(new CommandBuilder("path")
								.addToken(new ValueToken.IntegerToken("distance"))
								.addOptional(new ValueToken.BooleanToken("follow"))
								.addOptional(new ValueToken.BooleanToken("freelook"))
								.setListener(Commands::onPathDir)
								.build())

				)
				.build());

		// /freelook
		commandHandler.registerCommand(
				new CommandBuilder("freelook")
						.setListener(Commands::onFreelook)
						.build()
		);

		// /follow <start|stop>
		commandHandler.registerCommand(
				new CommandBuilder("follow")
						.addToken(new ValueToken.EnumToken("state", "start", "stop"))
						.setListener(Commands::onFollow)
						.build()
		);


		// /setvar...
		commandHandler.registerCommand(new CommandBuilder("setvar")
				.addToken(new MultiCommandToken("setvarMulti")

						// /setvar <variablename> <integer>
						.addCommand(new CommandBuilder("setvar")
								.addToken(new ValueToken.EnumToken("varname", "timeout", "pathfindingslowdown"))
								.addToken(new ValueToken.IntegerToken("value"))
								.setListener(Commands::onSetVarInteger)
								.build())

						// /setvar <variablename> <boolean>
						.addCommand(new CommandBuilder("setvar")
								.addToken(new ValueToken.EnumToken("varname", "showchunkcache", "shownodecache", "verbose"))
								.addToken(new ValueToken.BooleanToken("value"))
								.setListener(Commands::onSetVarBoolean)
								.build())

						// /setvar <variablename> <enum>
						.addCommand(new CommandBuilder("setvar")
								.addToken(new ValueToken.EnumToken("varname", "pathstyle"))
								.addToken(new ValueToken.EnumToken("value", "solid", "pathid", "actionid", "actioncost", "actiontype"))
								.setListener(Commands::onSetVarEnum)
								.build())

				)
				.build());


	}




	private static void onPathFromTo(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
		final BlockPos from = (BlockPos) args.get("from").getValue();
		final BlockPos to = (BlockPos) args.get("to").getValue();

		if (Stevebot.get().getPlayerController().getPlayer() != null) {
			Stevebot.get().getPathHandler().createPath(from, new ExactGoal(to), false, false);
		}
	}




	private static void onPathTo(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
		final BlockPos from = Stevebot.get().getPlayerController().utils().getPlayerBlockPos();
		final BlockPos to = (BlockPos) args.get("to").getValue();
		final boolean follow = (Boolean) args.getOrDefault("follow", new CommandArgument<>(false)).getValue();
		final boolean freelook = (Boolean) args.getOrDefault("freelook", new CommandArgument<>(false)).getValue();

		if (Stevebot.get().getPlayerController().getPlayer() != null) {
			Stevebot.get().getPathHandler().createPath(from, new ExactGoal(to), follow, freelook);
		}
	}




	private static void onPathDir(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
		final int distance = (Integer) args.get("distance").getValue();
		final boolean follow = (Boolean) args.getOrDefault("follow", new CommandArgument<>(false)).getValue();
		final boolean freelook = (Boolean) args.getOrDefault("freelook", new CommandArgument<>(false)).getValue();

		if (Stevebot.get().getPlayerController().getPlayer() != null) {
			final BlockPos from = Stevebot.get().getPlayerController().utils().getPlayerBlockPos();
			final Vector3d dir = Stevebot.get().getPlayerController().camera().getLookDir().setLength(distance);
			Goal goal = new XZGoal(from.getX() + dir.getIntX(), from.getZ() + dir.getIntZ());
			Stevebot.get().getPathHandler().createPath(from, goal, follow, freelook);
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




	private static void onSetVarInteger(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
		final String varname = (String) args.get("varname").getValue();
		final int value = (Integer) args.get("value").getValue();

		switch (varname.toLowerCase()) {
			case "timeout": {
				Config.setPathfindingTimeout(value);
				break;
			}
			case "pathfindingslowdown": {
				Config.setPathfindingSlowdown(value);
				break;
			}
			default: {
				Stevebot.get().log("Invalid name '" + varname + "'.");
				break;
			}
		}
	}




	private static void onSetVarBoolean(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
		final String varname = (String) args.get("varname").getValue();
		final boolean value = (Boolean) args.get("value").getValue();

		switch (varname.toLowerCase()) {
			case "verbose": {
				Config.setVerboseMode(value);
				break;
			}
			case "showchunkcache": {
				Config.setShowChunkCache(value);
				break;
			}
			case "shownodecache": {
				Config.setShowNodeCache(value);
				break;
			}
			default: {
				Stevebot.get().log("Invalid name '" + varname + "'.");
				break;
			}
		}
	}




	private static void onSetVarEnum(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
		final String varname = (String) args.get("varname").getValue();
		final String value = (String) args.get("value").getValue();

		switch (varname.toLowerCase()) {
			case "pathstyle": {
				PathRenderable.PathStyle style = PathRenderable.PathStyle.fromString(value);
				if (style != null) {
					Config.setPathStyle(style);
				} else {
					Stevebot.get().log("Invalid pathstyle: '" + value + "'.");
				}
				break;
			}
			default: {
				Stevebot.get().log("Invalid name '" + varname + "'.");
				break;
			}
		}
	}

}
