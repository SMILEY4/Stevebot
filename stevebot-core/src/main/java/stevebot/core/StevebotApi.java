package stevebot.core;

import stevebot.core.data.blockpos.BaseBlockPos;
import stevebot.core.data.blocks.BlockUtils;
import stevebot.core.math.vectors.vec3.Vector3d;
import stevebot.core.misc.Config;
import stevebot.core.misc.StevebotLog;
import stevebot.core.pathfinding.PathHandler;
import stevebot.core.pathfinding.Pathfinding;
import stevebot.core.pathfinding.PathfindingResult;
import stevebot.core.pathfinding.actions.ActionObserver;
import stevebot.core.pathfinding.goal.ExactGoal;
import stevebot.core.pathfinding.goal.Goal;
import stevebot.core.pathfinding.goal.XZGoal;
import stevebot.core.pathfinding.goal.YGoal;
import stevebot.core.pathfinding.path.PathRenderable;
import stevebot.core.player.PlayerCamera;
import stevebot.core.player.PlayerUtils;

/**
 * Simple API for controlling Stevebot
 */
public class StevebotApi {

    private final PathHandler pathHandler;

    public StevebotApi(final PathHandler pathHandler) {
        this.pathHandler = pathHandler;
    }


    /**
     * Finds a path from the first position to the second position.
     */
    public void path(BaseBlockPos from, BaseBlockPos to, boolean startFollowing, boolean enableFreelook) {
        pathHandler.createPath(from, new ExactGoal(to), startFollowing, enableFreelook);
    }


    /**
     * Finds a path from the current position to the given position
     */
    public void path(BaseBlockPos to, boolean startFollowing, boolean enableFreelook) {
        if (PlayerUtils.hasPlayer()) {
            path(PlayerUtils.getPlayerBlockPos(), to, startFollowing, enableFreelook);
        }
    }


    /**
     * Finds a path 'distance' blocks in the direction the player is looking
     */
    public void pathDirection(double distance, boolean startFollowing, boolean enableFreelook) {
        if (PlayerUtils.hasPlayer()) {
            final BaseBlockPos from = PlayerUtils.getPlayerBlockPos();
            final Vector3d dir = PlayerUtils.getCamera().getLookDir().setLength(distance);
            final Goal goal = new XZGoal(from.getX() + dir.getIntX(), from.getZ() + dir.getIntZ());
            pathHandler.createPath(new BaseBlockPos(from), goal, startFollowing, enableFreelook);
        }
    }


    /**
     * Finds a path to the given y-level
     */
    public void pathLevel(int yLevel, boolean startFollowing, boolean enableFreelook) {
        if (PlayerUtils.hasPlayer()) {
            final BaseBlockPos from = PlayerUtils.getPlayerBlockPos();
            Goal goal = new YGoal(yLevel);
            pathHandler.createPath(new BaseBlockPos(from), goal, startFollowing, enableFreelook);
        }
    }


    /**
     * Finds a path to the nearest block of the given type
     */
    public void pathBlock(String blockName, boolean startFollowing, boolean enableFreelook) {
        if (PlayerUtils.hasPlayer()) {
            final BaseBlockPos from = PlayerUtils.getPlayerBlockPos();
            final BaseBlockPos posBlock = BlockUtils.findNearest(BlockUtils.getBlockLibrary().getBlockByName(blockName), from, 219, 219);
            if (posBlock != null) {
                Goal goal = new ExactGoal(posBlock);
                pathHandler.createPath(new BaseBlockPos(from), goal, true, true);
            } else {
                StevebotLog.log("No block of the given type found.");
            }
        }
    }


    /**
     * Toggles freelook-mode.
     */
    public void toggleFreelook() {
        if (PlayerUtils.hasPlayer()) {
            if (PlayerUtils.getCamera().getState() == PlayerCamera.CameraState.FREELOOK) {
                PlayerUtils.getCamera().setState(PlayerCamera.CameraState.DEFAULT);
                PlayerUtils.sendMessage("Disable Freelook.");
            } else {
                PlayerUtils.getCamera().setState(PlayerCamera.CameraState.FREELOOK);
                PlayerUtils.sendMessage("Enable Freelook.");
            }
        }
    }


    /**
     * Stop following the current path.
     */
    public void stopFollowing() {
        pathHandler.cancelPath();
    }

    /**
     * Sets the timeout for pathfinding.
     */
    public void setTimeout(float timeoutSeconds) {
        Config.setPathfindingTimeout(timeoutSeconds);
    }


    /**
     * Enable/Disable verbose log-mode.
     */
    public void setVerbose(boolean enable) {
        Config.setVerboseMode(enable);
    }


    /**
     * Keep/Discard the path-overlay after completion.
     */
    public void setKeepPath(boolean keep) {
        Config.setKeepPathRenderable(keep);
    }


    /**
     * Slow down the pathfinding-algorithm
     */
    public void setPathfindingSlowdown(int delayMilliseconds) {
        Config.setPathfindingSlowdown(delayMilliseconds);
    }

    /**
     * @param show whether to render the chunk-cache data
     */
    public void setShowChunkCache(boolean show) {
        Config.setShowChunkCache(show);
    }


    /**
     * @param show whether to render the node-cache data
     */
    public void setShowNodeCache(boolean show) {
        Config.setShowNodeCache(show);
    }

    /**
     * Set the display style of the rendered path
     */
    public void setPathDisplayStyle(PathRenderable.PathStyle style) {
        Config.setPathStyle(style);
    }


    /**
     * print statistics about the last path result
     */
    public void printStatistics(boolean toConsole) {
        final PathfindingResult result = Pathfinding.lastResults;
        if (result == null) {
            StevebotLog.log("No statistics available.");
        } else {
            if (toConsole) {
                result.logConsole();
            } else {
                result.log();
            }
        }
    }


    /**
     * clear node cache
     */
    public void clearNodeCache() {
        BlockUtils.getBlockProvider().getBlockCache().clear();
        StevebotLog.log("Cache cleared.");
    }


    /**
     * display action cost recording stats
     */
    public void displayActionCostStats() {
        ActionObserver.log();
    }


    /**
     * clear action cost recording stats
     */
    public void clearActionCostStats() {
        ActionObserver.clear();
    }

}
