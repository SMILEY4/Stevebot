package stevebot.commands;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.misc.Config;
import stevebot.pathfinding.PathHandler;
import stevebot.pathfinding.goal.ExactGoal;
import stevebot.pathfinding.goal.Goal;
import stevebot.pathfinding.goal.XZGoal;
import stevebot.pathfinding.path.PathRenderable;
import stevebot.player.PlayerCameraImpl;
import stevebot.player.PlayerUtils;

public class StevebotCommands {


	public static void initialize(PathHandler pathHandler) {

		// path <from> <to>
		CommandSystem.addCommand("pathFromTo", "path <xs:COORDINATE> <ys:COORDINATE> <zs:COORDINATE> <xd:COORDINATE> <yd:COORDINATE> <zd:COORDINATE>", (templateId, parameters) -> {
			if (PlayerUtils.getPlayer() != null) {
				final BaseBlockPos from = CommandListener.getAsBaseBlockPos("xs", "ys", "zs", parameters);
				final BaseBlockPos to = CommandListener.getAsBaseBlockPos("xs", "ys", "zs", parameters);
				pathHandler.createPath(new BaseBlockPos(from), new ExactGoal(new BaseBlockPos(to)), false, false);
			}
		});

		// path <to>
		CommandSystem.addCommand("pathTo", "path <xs:COORDINATE> <ys:COORDINATE> <zs:COORDINATE>", (templateId, parameters) -> {
			if (PlayerUtils.getPlayer() != null) {
				final BaseBlockPos from = PlayerUtils.getPlayerBlockPos();
				final BaseBlockPos to = CommandListener.getAsBaseBlockPos("xs", "ys", "zs", parameters);
				pathHandler.createPath(new BaseBlockPos(from), new ExactGoal(new BaseBlockPos(to)), true, false);
			}
		});

		// path <to> freelook
		CommandSystem.addCommand("pathToFreelook", "path <xs:COORDINATE> <ys:COORDINATE> <zs:COORDINATE> freelook", (templateId, parameters) -> {
			if (PlayerUtils.getPlayer() != null) {
				final BaseBlockPos from = PlayerUtils.getPlayerBlockPos();
				final BaseBlockPos to = CommandListener.getAsBaseBlockPos("xs", "ys", "zs", parameters);
				pathHandler.createPath(new BaseBlockPos(from), new ExactGoal(new BaseBlockPos(to)), true, true);
			}
		});

		// path <dist>
		CommandSystem.addCommand("pathDir", "path <dist:INTEGER>", (templateId, parameters) -> {
			if (PlayerUtils.getPlayer() != null) {
				final BaseBlockPos from = PlayerUtils.getPlayerBlockPos();
				final Vector3d dir = PlayerUtils.getCamera().getLookDir().setLength(CommandListener.getAsInt("dist", parameters));
				Goal goal = new XZGoal(from.getX() + dir.getIntX(), from.getZ() + dir.getIntZ());
				pathHandler.createPath(new BaseBlockPos(from), goal, true, false);
			}
		});

		// path <dist> freelook
		CommandSystem.addCommand("pathDirFreelook", "path <dist:INTEGER> freelook", (templateId, parameters) -> {
			if (PlayerUtils.getPlayer() != null) {
				final BaseBlockPos from = PlayerUtils.getPlayerBlockPos();
				final Vector3d dir = PlayerUtils.getCamera().getLookDir().setLength(CommandListener.getAsInt("dist", parameters));
				Goal goal = new XZGoal(from.getX() + dir.getIntX(), from.getZ() + dir.getIntZ());
				pathHandler.createPath(new BaseBlockPos(from), goal, true, true);
			}
		});

		// freelook
		CommandSystem.addCommand("freelook", "freelook", (templateId, parameters) -> {
			if (PlayerUtils.getCamera().getState() == PlayerCameraImpl.CameraState.FREELOOK) {
				PlayerUtils.getCamera().setState(PlayerCameraImpl.CameraState.DEFAULT);
				PlayerUtils.sendMessage("Disable Freelook.");
			} else {
				PlayerUtils.getCamera().setState(PlayerCameraImpl.CameraState.FREELOOK);
				PlayerUtils.sendMessage("Enable Freelook.");
			}
		});

		// follow stop
		CommandSystem.addCommand("followStop", "follow stop", (templateId, parameters) -> {
			if (PlayerUtils.getPlayer() != null) {
				pathHandler.cancelPath();
			}
		});

		// set timeout <seconds>
		CommandSystem.addCommand("setTimout", "set timeout <seconds:FLOAT>", (templateId, parameters) -> {
			Config.setPathfindingTimeout(CommandListener.getAsFloat("seconds", parameters));
		});

		// set verbose <enable>
		CommandSystem.addCommand("setverbose", "set verbose <enable:BOOLEAN>", (templateId, parameters) -> {
			Config.setVerboseMode(CommandListener.getAsBoolean("enable", parameters));
		});

		// set keepPathRenderable <keep>
		CommandSystem.addCommand("setKeepPathRenderable", "set keepPathRenderable <keep:BOOLEAN>", (templateId, parameters) -> {
			Config.setKeepPathRenderable(CommandListener.getAsBoolean("keep", parameters));
		});

		// set slowdown
		CommandSystem.addCommand("setSlowdown", "set slowdown <milliseconds:INTEGER>", (templateId, parameters) -> {
			Config.setPathfindingSlowdown(CommandListener.getAsInt("milliseconds", parameters));
		});

		// show chunkcache
		CommandSystem.addCommand("showChunkCache", "show chunkcache <show:BOOLEAN>", (templateId, parameters) -> {
			Config.setShowChunkCache(CommandListener.getAsBoolean("show", parameters));
		});

		// show nodecache
		CommandSystem.addCommand("showNodeCache", "show nodecache <show:BOOLEAN>", (templateId, parameters) -> {
			Config.setShowNodeCache(CommandListener.getAsBoolean("show", parameters));
		});

		// pathstyle <style>
		CommandSystem.addCommand("pathstyle", "pathstyle <style:{solid,pathid,actionid,actioncost,actiontype}>", (templateId, parameters) -> {
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


	}

}
