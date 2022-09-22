package stevebot.core.pathfinding;

import stevebot.core.data.blockpos.BaseBlockPos;
import stevebot.core.minecraft.MinecraftAdapter;
import stevebot.core.misc.StevebotLog;
import stevebot.core.pathfinding.execution.PathExecutor;
import stevebot.core.pathfinding.goal.Goal;
import stevebot.core.rendering.Renderer;

public class PathHandler {


    private PathExecutor excecutor = null;

    private final MinecraftAdapter minecraftAdapter;
    private final Renderer renderer;


    public PathHandler(MinecraftAdapter minecraftAdapter, Renderer renderer) {
        this.minecraftAdapter = minecraftAdapter;
        this.renderer = renderer;
    }


    public void onEventClientTick() {
        if (excecutor != null) {
            excecutor.onClientTick();
        }
    }

    /**
     * Creates a new path and executor from the given start position to the given goal.
     *
     * @param from           the starting position
     * @param goal           the goal of the resulting path
     * @param startFollowing true, to immediately start following the path
     * @param enableFreelook true, to enable freelook when following the path
     */
    public void createPath(BaseBlockPos from, Goal goal, boolean startFollowing, boolean enableFreelook) {
        if (excecutor == null) {
            excecutor = new PathExecutor(minecraftAdapter, from, goal, renderer);
            excecutor.setPathListener(() -> excecutor = null);
            excecutor.start();
            if (startFollowing) {
                excecutor.startFollowing(enableFreelook);
            }
        } else {
            StevebotLog.log("Can not start new path. Another path is already in progress.");
        }
    }


    /**
     * Start following the created path.
     */
    public void startFollowing() {
        if (excecutor != null) {
            excecutor.startFollowing(false);
        }
    }


    /**
     * Stop the current path
     */
    public void cancelPath() {
        if (excecutor != null) {
            excecutor.stop();
        }
    }

}
