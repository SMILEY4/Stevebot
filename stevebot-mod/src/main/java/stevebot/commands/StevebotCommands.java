package stevebot.commands;

import stevebot.StevebotApi;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.pathfinding.path.PathRenderable;
import stevebot.player.PlayerUtils;

public class StevebotCommands {


    public static void initialize(StevebotApi api) {

        // path <from> <to>
        CommandSystem.addCommand(
                "pathFromTo",
                "path <xs:COORDINATE> <ys:COORDINATE> <zs:COORDINATE> <xd:COORDINATE> <yd:COORDINATE> <zd:COORDINATE>",
                "/path <x> <y> <z> <x> <y> <z>\n    Finds a path from the first position to the second position.",
                (templateId, parameters) -> {
                    if (PlayerUtils.hasPlayer()) {
                        final BaseBlockPos from = CommandListener.getAsBaseBlockPos("xs", "ys", "zs", parameters);
                        final BaseBlockPos to = CommandListener.getAsBaseBlockPos("xs", "ys", "zs", parameters);
                        api.path(new BaseBlockPos(from), new BaseBlockPos(to), false, false);
                    }
                });

        // path <to>
        CommandSystem.addCommand(
                "pathTo",
                "path <xs:COORDINATE> <ys:COORDINATE> <zs:COORDINATE>",
                "/path <x> <y> <z>\n    Finds a path from the current position to the given position.",
                (templateId, parameters) -> {
                    if (PlayerUtils.hasPlayer()) {
                        final BaseBlockPos from = PlayerUtils.getPlayerBlockPos();
                        final BaseBlockPos to = CommandListener.getAsBaseBlockPos("xs", "ys", "zs", parameters);
                        api.path(new BaseBlockPos(from), new BaseBlockPos(to), true, false);
                    }
                });

        // path <to> freelook
        CommandSystem.addCommand(
                "pathToFreelook",
                "path <xs:COORDINATE> <ys:COORDINATE> <zs:COORDINATE> freelook",
                "/path <x> <y> <z>\n    Finds a path from the current position to the given position and enables freelook.",
                (templateId, parameters) -> {
                    if (PlayerUtils.hasPlayer()) {
                        final BaseBlockPos from = PlayerUtils.getPlayerBlockPos();
                        final BaseBlockPos to = CommandListener.getAsBaseBlockPos("xs", "ys", "zs", parameters);
                        api.path(new BaseBlockPos(from), new BaseBlockPos(to), true, true);
                    }
                });

        // path <dist>
        CommandSystem.addCommand(
                "pathDir",
                "path <dist:INTEGER>",
                "/path <dist>\n    Finds a path 'dist' blocks in the direction the player is looking.",
                (templateId, parameters) -> {
                    if (PlayerUtils.hasPlayer()) {
                        api.pathDirection(CommandListener.getAsInt("dist", parameters), true, false);
                    }
                });

        // path <dist> freelook
        CommandSystem.addCommand(
                "pathDirFreelook",
                "path <dist:INTEGER> freelook",
                "/path <dist>\n    Finds a path 'dist' blocks in the direction the player is looking and enables freelook.",
                (templateId, parameters) -> {
                    if (PlayerUtils.hasPlayer()) {
                        api.pathDirection(CommandListener.getAsInt("dist", parameters), true, true);
                    }
                });

        // path level <level>
        CommandSystem.addCommand(
                "pathLevel",
                "path level <level:INTEGER>",
                "/path level <level>\n    Finds a path to the given y-level.",
                (templateId, parameters) -> {
                    if (PlayerUtils.hasPlayer()) {
                        api.pathLevel(CommandListener.getAsInt("level", parameters), true, false);
                    }
                });

        // path level <level> freelook
        CommandSystem.addCommand(
                "pathLevelFreelook",
                "path level <level:INTEGER> freelook",
                "/path level <level>\n    Finds a path to the given y-level and enables freelook.\",.",
                (templateId, parameters) -> {
                    if (PlayerUtils.hasPlayer()) {
                        api.pathLevel(CommandListener.getAsInt("level", parameters), true, true);
                    }
                });

        // path <block>
        CommandSystem.addCommand(
                "pathBlock",
                "path <block:STRING>",
                "/path level <level>\n    Finds a path to the nearest with of the given type.",
                (templateId, parameters) -> {
                    if (PlayerUtils.hasPlayer()) {
                        api.pathBlock(CommandListener.getAsString("block", parameters), true, false);
                    }
                });

        // path <block> freelook
        CommandSystem.addCommand(
                "pathBlockFreelook",
                "path <block:STRING> freelook",
                "/path level <level>\n    Finds a path to the nearest with of the given type and enables freelook.\",.",
                (templateId, parameters) -> {
                    if (PlayerUtils.hasPlayer()) {
                        api.pathBlock(CommandListener.getAsString("block", parameters), true, true);
                    }
                });

        // freelook
        CommandSystem.addCommand(
                "freelook",
                "freelook",
                "/freelook\n    Toggles freelook-mode.",
                (templateId, parameters) -> {
                    api.toggleFreelook();
                });

        // follow stop
        CommandSystem.addCommand(
                "followStop",
                "follow stop",
                "follow stop\n    Stop following the current path.",
                (templateId, parameters) -> {
                    api.stopFollowing();
                });

        // set timeout <seconds>
        CommandSystem.addCommand(
                "setTimout",
                "set timeout <seconds:FLOAT>",
                "/set timeout <seconds>\n    Sets the timeout for pathfinding.",
                (templateId, parameters) -> {
                    api.setTimeout(CommandListener.getAsFloat("seconds", parameters));
                });

        // set verbose <enable>
        CommandSystem.addCommand(
                "setverbose",
                "set verbose <enable:BOOLEAN>",
                "/set verbose <enable>\n    Enable/Disable verbose log-mode.",
                (templateId, parameters) -> {
                    api.setVerbose(CommandListener.getAsBoolean("enable", parameters));
                });

        // set keepPathRenderable <keep>
        CommandSystem.addCommand(
                "setKeepPathRenderable",
                "set keepPathRenderable <keep:BOOLEAN>",
                "/set keepPathRenderable <keep>\n    Keep/Discard the path-overlay after completion.",
                (templateId, parameters) -> {
                    api.setKeepPath(CommandListener.getAsBoolean("keep", parameters));
                });

        // set slowdown
        CommandSystem.addCommand(
                "setSlowdown",
                "set slowdown <milliseconds:INTEGER>",
                "/set slowdown <ms>\n    Slows down the pathfinding algorithm by the given duration each step.",
                (templateId, parameters) -> {
                    api.setPathfindingSlowdown(CommandListener.getAsInt("milliseconds", parameters));
                });

        // show chunkcache
        CommandSystem.addCommand(
                "showChunkCache",
                "show chunkcache <show:BOOLEAN>",
                "/show chunkcache <show>\n    Show/Hide the overlay for the chunk cache.",
                (templateId, parameters) -> {
                    api.setShowChunkCache(CommandListener.getAsBoolean("show", parameters));
                });

        // show nodecache
        CommandSystem.addCommand(
                "showNodeCache",
                "show nodecache <show:BOOLEAN>",
                "/show nodecache <show>\n    Show/Hide the overlay for the node cache.",
                (templateId, parameters) -> {
                    api.setShowNodeCache(CommandListener.getAsBoolean("show", parameters));
                });

        // pathstyle <style>
        CommandSystem.addCommand(
                "pathstyle",
                "pathstyle <style:{solid,pathid,actionid,actioncost,actiontype}>",
                "/pathstyle <style>\n     Set the style of the path-overlay (solid, pathid, actionid, actioncost or actiontype)",
                (templateId, parameters) -> {
                    switch (CommandListener.getAsString("style", parameters)) {
                        case "solid": {
                            api.setPathDisplayStyle(PathRenderable.PathStyle.SOLID);
                            break;
                        }
                        case "pathid": {
                            api.setPathDisplayStyle(PathRenderable.PathStyle.PATH_ID);
                            break;
                        }
                        case "actionid": {
                            api.setPathDisplayStyle(PathRenderable.PathStyle.ACTION_ID);
                            break;
                        }
                        case "actioncost": {
                            api.setPathDisplayStyle(PathRenderable.PathStyle.ACTION_COST);
                            break;
                        }
                        case "actiontype": {
                            api.setPathDisplayStyle(PathRenderable.PathStyle.ACTION_TYPE);
                            break;
                        }
                    }
                });

        CommandSystem.addCommand(
                "statistics",
                "statistics",
                "/statistics\n    Displays statistics about the last pathfinding process.",
                (templateId, parameters) -> {
                    api.printStatistics(false);
                });

        // statistics console
        CommandSystem.addCommand(
                "statisticsConsole",
                "statistics console",
                "/statistics console\n    Displays statistics about the last pathfinding process (formatted for console output).",
                (templateId, parameters) -> {
                    api.printStatistics(true);
                });

        // clear node cache
        CommandSystem.addCommand(
                "clearBlockCache",
                "clear blockcache",
                "/clear blockcache\n    Clears the block-cache.",
                (templateId, parameters) -> {
                    api.clearNodeCache();
                });

        // display action cost recording stats
        CommandSystem.addCommand(
                "actionCostStats",
                "actioncosts",
                "/actioncosts\n    Logs the costs of the recorded actions.",
                (templateId, parameters) -> {
                    api.displayActionCostStats();
                });

        // clear action cost recording stats
        CommandSystem.addCommand(
                "actionCostClear",
                "actioncosts clear",
                "/actioncosts clear\n    Clears costs of the recorded actions.",
                (templateId, parameters) -> {
                    api.clearActionCostStats();
                });

    }

}
