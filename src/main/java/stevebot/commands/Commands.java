package stevebot.commands;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.ClientCommandHandler;
import stevebot.Stevebot;
import stevebot.commands.tokens.MultiCommandToken;
import stevebot.commands.tokens.ValueToken;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blockpos.FastBlockPos;
import stevebot.misc.Config;
import stevebot.pathfinding.PathHandler;
import stevebot.pathfinding.goal.ExactGoal;
import stevebot.pathfinding.goal.Goal;
import stevebot.pathfinding.goal.XZGoal;
import stevebot.pathfinding.path.PathRenderable;
import stevebot.player.PlayerCameraImpl;
import stevebot.player.PlayerUtils;

import java.util.Map;

public class Commands {


	private static PathHandler pathHandler;




	public static void initialize(PathHandler pathHandler) {
		Commands.pathHandler = pathHandler;

		// /path ...
		registerCommand(new CommandBuilder("path")
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
		registerCommand(
				new CommandBuilder("freelook")
						.setListener(Commands::onFreelook)
						.build()
		);

		// /follow <start|stop>
		registerCommand(
				new CommandBuilder("follow")
						.addToken(new ValueToken.EnumToken("state", "start", "stop"))
						.setListener(Commands::onFollow)
						.build()
		);


		// /setvar...
		registerCommand(new CommandBuilder("setvar")
				.addToken(new MultiCommandToken("setvarMulti")

						// /setvar <variablename> <number>
						.addCommand(new CommandBuilder("setvar")
								.addToken(new ValueToken.EnumToken("varname", "timeout", "pathfindingslowdown"))
								.addToken(new ValueToken.FloatToken("value"))
								.setListener(Commands::onSetVarNumber)
								.build())

						// /setvar <variablename> <boolean>
						.addCommand(new CommandBuilder("setvar")
								.addToken(new ValueToken.EnumToken("varname", "showchunkcache", "shownodecache", "verbose", "keeppathrenderable"))
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




	private static void registerCommand(CustomCommand command) {
		ClientCommandHandler.instance.registerCommand(command.getCommandBase());
	}




	private static void onPathFromTo(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
		final BlockPos from = (BlockPos) args.get("from").getValue();
		final BlockPos to = (BlockPos) args.get("to").getValue();

		if (PlayerUtils.getPlayer() != null) {
			pathHandler.createPath(new BaseBlockPos(from), new ExactGoal(new BaseBlockPos(to)), false, false);
		}
	}




	private static void onPathTo(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
		final BaseBlockPos from = PlayerUtils.getPlayerBlockPos();
		final BlockPos to = (BlockPos) args.get("to").getValue();
		final boolean follow = (Boolean) args.getOrDefault("follow", new CommandArgument<>(true)).getValue();
		final boolean freelook = (Boolean) args.getOrDefault("freelook", new CommandArgument<>(false)).getValue();

		if (PlayerUtils.getPlayer() != null) {
			pathHandler.createPath(new FastBlockPos(from), new ExactGoal(new BaseBlockPos(to)), follow, freelook);
		}
	}




	private static void onPathDir(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
		final int distance = (Integer) args.get("distance").getValue();
		final boolean follow = (Boolean) args.getOrDefault("follow", new CommandArgument<>(true)).getValue();
		final boolean freelook = (Boolean) args.getOrDefault("freelook", new CommandArgument<>(false)).getValue();

		if (PlayerUtils.getPlayer() != null) {
			final BaseBlockPos from = PlayerUtils.getPlayerBlockPos();
			final Vector3d dir = PlayerUtils.getCamera().getLookDir().setLength(distance);
			Goal goal = new XZGoal(from.getX() + dir.getIntX(), from.getZ() + dir.getIntZ());
			pathHandler.createPath(new BaseBlockPos(from), goal, follow, freelook);
		}
	}




	private static void onFollow(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
		final String state = (String) args.get("state").getValue();
		if (PlayerUtils.getPlayer() != null) {
			if ("start".equalsIgnoreCase(state)) {
				pathHandler.startFollowing();
			} else if ("stop".equalsIgnoreCase(state)) {
				pathHandler.cancelPath();
			} else {
				PlayerUtils.sendMessage("Unknown state: " + state + ". Must be 'start' or 'stop'.");
			}
		}
	}




	private static void onFreelook(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
		if (PlayerUtils.getCamera().getState() == PlayerCameraImpl.CameraState.FREELOOK) {
			PlayerUtils.getCamera().setState(PlayerCameraImpl.CameraState.DEFAULT);
			PlayerUtils.sendMessage("Disable Freelook.");
		} else {
			PlayerUtils.getCamera().setState(PlayerCameraImpl.CameraState.FREELOOK);
			PlayerUtils.sendMessage("Enable Freelook.");
		}
	}




	private static void onSetVarNumber(ICommandSender sender, String name, Map<String, CommandArgument<?>> args) {
		final String varname = (String) args.get("varname").getValue();
		final Number value = (Number) args.get("value").getValue();


		switch (varname.toLowerCase()) {
			case "timeout": {
				Config.setPathfindingTimeout(value.floatValue());
				break;
			}
			case "pathfindingslowdown": {
				Config.setPathfindingSlowdown(value.intValue());
				break;
			}
			default: {
				Stevebot.log("Invalid name '" + varname + "'.");
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
			case "keeppathrenderable": {
				Config.setKeepPathRenderable(value);
				break;
			}
			default: {
				Stevebot.log("Invalid name '" + varname + "'.");
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
					Stevebot.log("Invalid pathstyle: '" + value + "'.");
				}
				break;
			}
			default: {
				Stevebot.log("Invalid name '" + varname + "'.");
				break;
			}
		}
	}

}
