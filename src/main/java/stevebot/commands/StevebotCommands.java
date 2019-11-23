package stevebot.commands;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import stevebot.Stevebot;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blocks.BlockUtils;
import stevebot.misc.Config;
import stevebot.pathfinding.PathHandler;
import stevebot.pathfinding.Pathfinding;
import stevebot.pathfinding.PathfindingResult;
import stevebot.pathfinding.actions.ActionObserver;
import stevebot.pathfinding.goal.ExactGoal;
import stevebot.pathfinding.goal.Goal;
import stevebot.pathfinding.goal.XZGoal;
import stevebot.pathfinding.goal.YGoal;
import stevebot.pathfinding.path.PathRenderable;
import stevebot.player.PlayerCameraImpl;
import stevebot.player.PlayerUtils;

public class StevebotCommands {


	public static void initialize(PathHandler pathHandler) {

		// path <from> <to>
		CommandSystem.addCommand(
				"pathFromTo",
				"path <xs:COORDINATE> <ys:COORDINATE> <zs:COORDINATE> <xd:COORDINATE> <yd:COORDINATE> <zd:COORDINATE>",
				"/path <x> <y> <z> <x> <y> <z>\n    Finds a path from the first position to the second position.",
				(templateId, parameters) -> {
					if (PlayerUtils.getPlayer() != null) {
						final BaseBlockPos from = CommandListener.getAsBaseBlockPos("xs", "ys", "zs", parameters);
						final BaseBlockPos to = CommandListener.getAsBaseBlockPos("xs", "ys", "zs", parameters);
						pathHandler.createPath(new BaseBlockPos(from), new ExactGoal(new BaseBlockPos(to)), false, false);
					}
				});

		// path <to>
		CommandSystem.addCommand(
				"pathTo",
				"path <xs:COORDINATE> <ys:COORDINATE> <zs:COORDINATE>",
				"/path <x> <y> <z>\n    Finds a path from the current position to the given position.",
				(templateId, parameters) -> {
					if (PlayerUtils.getPlayer() != null) {
						final BaseBlockPos from = PlayerUtils.getPlayerBlockPos();
						final BaseBlockPos to = CommandListener.getAsBaseBlockPos("xs", "ys", "zs", parameters);
						pathHandler.createPath(new BaseBlockPos(from), new ExactGoal(new BaseBlockPos(to)), true, false);
					}
				});

		// path <to> freelook
		CommandSystem.addCommand(
				"pathToFreelook",
				"path <xs:COORDINATE> <ys:COORDINATE> <zs:COORDINATE> freelook",
				"/path <x> <y> <z>\n    Finds a path from the current position to the given position and enables freelook.",
				(templateId, parameters) -> {
					if (PlayerUtils.getPlayer() != null) {
						final BaseBlockPos from = PlayerUtils.getPlayerBlockPos();
						final BaseBlockPos to = CommandListener.getAsBaseBlockPos("xs", "ys", "zs", parameters);
						pathHandler.createPath(new BaseBlockPos(from), new ExactGoal(new BaseBlockPos(to)), true, true);
					}
				});

		// path <dist>
		CommandSystem.addCommand(
				"pathDir",
				"path <dist:INTEGER>",
				"/path <dist>\n    Finds a path 'dist' blocks in the direction the player is looking.",
				(templateId, parameters) -> {
					if (PlayerUtils.getPlayer() != null) {
						final BaseBlockPos from = PlayerUtils.getPlayerBlockPos();
						final Vector3d dir = PlayerUtils.getCamera().getLookDir().setLength(CommandListener.getAsInt("dist", parameters));
						Goal goal = new XZGoal(from.getX() + dir.getIntX(), from.getZ() + dir.getIntZ());
						pathHandler.createPath(new BaseBlockPos(from), goal, true, false);
					}
				});

		// path <dist> freelook
		CommandSystem.addCommand(
				"pathDirFreelook",
				"path <dist:INTEGER> freelook",
				"/path <dist>\n    Finds a path 'dist' blocks in the direction the player is looking and enables freelook.",
				(templateId, parameters) -> {
					if (PlayerUtils.getPlayer() != null) {
						final BaseBlockPos from = PlayerUtils.getPlayerBlockPos();
						final Vector3d dir = PlayerUtils.getCamera().getLookDir().setLength(CommandListener.getAsInt("dist", parameters));
						Goal goal = new XZGoal(from.getX() + dir.getIntX(), from.getZ() + dir.getIntZ());
						pathHandler.createPath(new BaseBlockPos(from), goal, true, true);
					}
				});

		// path level <level>
		CommandSystem.addCommand(
				"pathLevel",
				"path level <level:INTEGER>",
				"/path level <level>\n    Finds a path to the given y-level.",
				(templateId, parameters) -> {
					if (PlayerUtils.getPlayer() != null) {
						final BaseBlockPos from = PlayerUtils.getPlayerBlockPos();
						final int level = CommandListener.getAsInt("level", parameters);
						Goal goal = new YGoal(level);
						pathHandler.createPath(new BaseBlockPos(from), goal, true, false);
					}
				});

		// path level <level> freelook
		CommandSystem.addCommand(
				"pathLevelFreelook",
				"path level <level:INTEGER> freelook",
				"/path level <level>\n    Finds a path to the given y-level and enables freelook.\",.",
				(templateId, parameters) -> {
					if (PlayerUtils.getPlayer() != null) {
						final BaseBlockPos from = PlayerUtils.getPlayerBlockPos();
						final int level = CommandListener.getAsInt("level", parameters);
						Goal goal = new YGoal(level);
						pathHandler.createPath(new BaseBlockPos(from), goal, true, true);
					}
				});

		// path <block>
		CommandSystem.addCommand(
				"pathBlock",
				"path <block:STRING>",
				"/path level <level>\n    Finds a path to the nearest with of the given type.",
				(templateId, parameters) -> {
					if (PlayerUtils.getPlayer() != null) {
						final BaseBlockPos from = PlayerUtils.getPlayerBlockPos();
						final String blockName = CommandListener.getAsString("block", parameters);
						final BaseBlockPos posBlock = BlockUtils.findNearest(BlockUtils.getBlockLibrary().getBlockByName(blockName), from, 219, 219);
						if (posBlock != null) {
							Goal goal = new ExactGoal(posBlock);
							pathHandler.createPath(new BaseBlockPos(from), goal, true, false);
						} else {
							Stevebot.log("No block of the given type found.");
						}
					}
				});

		// path <block> freelook
		CommandSystem.addCommand(
				"pathBlockFreelook",
				"path <block:STRING> freelook",
				"/path level <level>\n    Finds a path to the nearest with of the given type and enables freelook.\",.",
				(templateId, parameters) -> {
					if (PlayerUtils.getPlayer() != null) {
						final BaseBlockPos from = PlayerUtils.getPlayerBlockPos();
						final String blockName = CommandListener.getAsString("block", parameters);
						final BaseBlockPos posBlock = BlockUtils.findNearest(BlockUtils.getBlockLibrary().getBlockByName(blockName), from, 219, 219);
						if (posBlock != null) {
							Goal goal = new ExactGoal(posBlock);
							pathHandler.createPath(new BaseBlockPos(from), goal, true, true);
						} else {
							Stevebot.log("No block of the given type found.");
						}
					}
				});

		// freelook
		CommandSystem.addCommand(
				"freelook",
				"freelook",
				"/freelook\n    Toggles freelook-mode.",
				(templateId, parameters) -> {
					if (PlayerUtils.getCamera().getState() == PlayerCameraImpl.CameraState.FREELOOK) {
						PlayerUtils.getCamera().setState(PlayerCameraImpl.CameraState.DEFAULT);
						PlayerUtils.sendMessage("Disable Freelook.");
					} else {
						PlayerUtils.getCamera().setState(PlayerCameraImpl.CameraState.FREELOOK);
						PlayerUtils.sendMessage("Enable Freelook.");
					}
				});

		// follow stop
		CommandSystem.addCommand(
				"followStop",
				"follow stop",
				"follow stop\n    Stop following the current path.",
				(templateId, parameters) -> {
					if (PlayerUtils.getPlayer() != null) {
						pathHandler.cancelPath();
					}
				});

		// set timeout <seconds>
		CommandSystem.addCommand(
				"setTimout",
				"set timeout <seconds:FLOAT>",
				"/set timeout <seconds>\n    Sets the timeout for pathfinding.",
				(templateId, parameters) -> {
					Config.setPathfindingTimeout(CommandListener.getAsFloat("seconds", parameters));
				});

		// set verbose <enable>
		CommandSystem.addCommand(
				"setverbose",
				"set verbose <enable:BOOLEAN>",
				"/set verbose <enable>\n    Enable/Disable verbose log-mode.",
				(templateId, parameters) -> {
					Config.setVerboseMode(CommandListener.getAsBoolean("enable", parameters));
				});

		// set keepPathRenderable <keep>
		CommandSystem.addCommand(
				"setKeepPathRenderable",
				"set keepPathRenderable <keep:BOOLEAN>",
				"/set keepPathRenderable <keep>\n    Keep/Discard the path-overlay after completion.",
				(templateId, parameters) -> {
					Config.setKeepPathRenderable(CommandListener.getAsBoolean("keep", parameters));
				});

		// set slowdown
		CommandSystem.addCommand(
				"setSlowdown",
				"set slowdown <milliseconds:INTEGER>",
				"/set slowdown <ms>\n    Slows down the pathfinding algorithm by the given duration each step.",
				(templateId, parameters) -> {
					Config.setPathfindingSlowdown(CommandListener.getAsInt("milliseconds", parameters));
				});

		// show chunkcache
		CommandSystem.addCommand(
				"showChunkCache",
				"show chunkcache <show:BOOLEAN>",
				"/show chunkcache <show>\n    Show/Hide the overlay for the chunk cache.",
				(templateId, parameters) -> {
					Config.setShowChunkCache(CommandListener.getAsBoolean("show", parameters));
				});

		// show nodecache
		CommandSystem.addCommand(
				"showNodeCache",
				"show nodecache <show:BOOLEAN>",
				"/show nodecache <show>\n    Show/Hide the overlay for the node cache.",
				(templateId, parameters) -> {
					Config.setShowNodeCache(CommandListener.getAsBoolean("show", parameters));
				});

		// pathstyle <style>
		CommandSystem.addCommand(
				"pathstyle",
				"pathstyle <style:{solid,pathid,actionid,actioncost,actiontype}>",
				"/pathstyle <style>\n     Set the style of the path-overlay (solid, pathid, actionid, actioncost or actiontype)",
				(templateId, parameters) -> {
					switch (CommandListener.getAsString("style", parameters)) {
						case "solid": {
							Config.setPathStyle(PathRenderable.PathStyle.SOLID);
							break;
						}
						case "pathid": {
							Config.setPathStyle(PathRenderable.PathStyle.PATH_ID);
							break;
						}
						case "actionid": {
							Config.setPathStyle(PathRenderable.PathStyle.ACTION_ID);
							break;
						}
						case "actioncost": {
							Config.setPathStyle(PathRenderable.PathStyle.ACTION_COST);
							break;
						}
						case "actiontype": {
							Config.setPathStyle(PathRenderable.PathStyle.ACTION_TYPE);
							break;
						}
					}
				});

		CommandSystem.addCommand(
				"statistics",
				"statistics",
				"/statistics\n    Displays statistics about the last pathfinding process.",
				(templateId, parameters) -> {
					final PathfindingResult result = Pathfinding.lastResults;
					if (result == null) {
						Stevebot.log("No statistics available.");
					} else {
						result.log();
					}
				});

		// statistics console
		CommandSystem.addCommand(
				"statisticsConsole",
				"statistics console",
				"/statistics console\n    Displays statistics about the last pathfinding process (formatted for console output).",
				(templateId, parameters) -> {
					final PathfindingResult result = Pathfinding.lastResults;
					if (result == null) {
						Stevebot.log("No statistics available.");
					} else {
						result.logConsole();
					}
				});

		// clear node cache
		CommandSystem.addCommand(
				"clearBlockCache",
				"clear blockcache",
				"/clear blockcache\n    Clears the block-cache.",
				(templateId, parameters) -> {
					BlockUtils.getBlockProvider().getBlockCache().clear();
					Stevebot.log("Cache cleared.");
				});

		// display action cost recording stats
		CommandSystem.addCommand(
				"actionCostStats",
				"actioncosts",
				"/actioncosts\n    Logs the costs of the recorded actions.",
				(templateId, parameters) -> {
					ActionObserver.log();
				});

		// clear action cost recording stats
		CommandSystem.addCommand(
				"actionCostClear",
				"actioncosts clear",
				"/actioncosts clear\n    Clears costs of the recorded actions.",
				(templateId, parameters) -> {
					ActionObserver.clear();
				});

	}

}
