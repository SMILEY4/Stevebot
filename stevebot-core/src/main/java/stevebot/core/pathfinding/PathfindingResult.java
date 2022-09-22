package stevebot.core.pathfinding;

import java.util.ArrayList;
import java.util.List;
import stevebot.core.misc.StevebotLog;
import stevebot.core.data.blockpos.BaseBlockPos;
import stevebot.core.pathfinding.goal.Goal;
import stevebot.core.pathfinding.path.EmptyPath;
import stevebot.core.pathfinding.path.Path;

public class PathfindingResult {


    public Path finalPath = new EmptyPath();

    public BaseBlockPos start;
    public Goal goal;

    public boolean pathFound = false;
    public long timeStart = -1;
    public long timeEnd = -1;

    public double pathCost = -1;
    public int pathLength = -1;

    public boolean hitTimeout = false;
    public int pathsFoundTotal = 0;
    public int betterPathsFound = 0;

    public List<Path> paths = new ArrayList<>();

    public int nodesConsidered = 0;
    public int nodesProcessed = 0;

    public int nodesWorseThanPath = 0;
    public int nodesWorseThanBestRow = 0;
    public int nodesWorseThanBestTotal = 0;

    public int actionsConsidered = 0;
    public int actionsImpossible = 0;
    public int actionsInvalid = 0;
    public int actionsUnloaded = 0;
    public int actionsValid = 0;
    public int actionsCreated = 0;


    /**
     * Log the statistics formatted for ingame-output.
     */
    public void log() {
        StevebotLog.log("=== STATISTICS ===");
        StevebotLog.log("start: " + start.toString());
        StevebotLog.log("goal: " + goal.goalString());
        StevebotLog.log("found path: " + pathFound + ", path=" + (finalPath == null ? "null" : finalPath.getClass().getSimpleName()));
        StevebotLog.log("time: " + (timeEnd - timeStart) + "ms");
        if (pathFound) {
            StevebotLog.log("path cost: " + pathCost);
            StevebotLog.log("path length: " + pathLength);
            StevebotLog.log("");
            StevebotLog.log("hit timeout: " + hitTimeout);
            StevebotLog.log("paths found total: " + pathsFoundTotal);
            StevebotLog.log("better paths found: " + betterPathsFound);
            StevebotLog.log("");
            StevebotLog.log("paths:");
            for (Path path : paths) {
                StevebotLog.log("    cost: " + path.getCost());
                StevebotLog.log("    length: " + path.getNodes().size());
                StevebotLog.log("------------");
            }
            StevebotLog.log("");
            StevebotLog.log("nodes considered: " + nodesConsidered);
            StevebotLog.log("nodes processed: " + nodesProcessed + "  (" + (((float) nodesConsidered / (float) nodesProcessed) * 100f) + "%)" + " -> discarded " + (nodesConsidered - nodesProcessed));
            StevebotLog.log("");
            StevebotLog.log("nodes worse than path: " + nodesWorseThanPath);
            StevebotLog.log("nodes worse than best (row): " + nodesWorseThanBestRow);
            StevebotLog.log("nodes worse than best (total): " + nodesWorseThanBestTotal);
            StevebotLog.log("");
            StevebotLog.log("actions (impossible): " + actionsImpossible + "(" + (((float) actionsConsidered / (float) actionsImpossible) * 100f) + "%)");
            StevebotLog.log("actions (invalid): " + actionsInvalid + "(" + (((float) actionsConsidered / (float) actionsInvalid) * 100f) + "%)");
            StevebotLog.log("actions (unloaded): " + actionsUnloaded + "(" + (((float) actionsConsidered / (float) actionsUnloaded) * 100f) + "%)");
            StevebotLog.log("actions (valid): " + actionsValid + "(" + (((float) actionsConsidered / (float) actionsValid) * 100f) + "%)");
            StevebotLog.log("actions (created): " + actionsCreated + "(" + (((float) actionsConsidered / (float) actionsCreated) * 100f) + "%)");

        }
        StevebotLog.log("==================");
    }


    /**
     * Log the statistics formatted for console-output as non-critical.
     */
    public void logConsole() {
        StevebotLog.logNonCritical("=== STATISTICS ===");
        StevebotLog.logNonCritical("      start: " + start.toString());
        StevebotLog.logNonCritical("       goal: " + goal.goalString());
        StevebotLog.logNonCritical(" found path: " + pathFound + ", path=" + (finalPath == null ? "null" : finalPath.getClass().getSimpleName()));
        StevebotLog.logNonCritical("       time: " + (timeEnd - timeStart) + "ms");
        if (pathFound) {
            StevebotLog.logNonCritical("  path cost: " + pathCost);
            StevebotLog.logNonCritical("path length: " + pathLength);
            StevebotLog.logNonCritical("");
            StevebotLog.logNonCritical("       hit timeout: " + hitTimeout);
            StevebotLog.logNonCritical(" paths found total: " + pathsFoundTotal);
            StevebotLog.logNonCritical("better paths found: " + betterPathsFound);
            StevebotLog.logNonCritical("");
            StevebotLog.logNonCritical("paths:");
            for (Path path : paths) {
                StevebotLog.logNonCritical("        cost: " + path.getCost());
                StevebotLog.logNonCritical("      length: " + path.getNodes().size());
                StevebotLog.logNonCritical("      ------");
            }
            StevebotLog.logNonCritical("");
            StevebotLog.logNonCritical("nodes considered: " + nodesConsidered);
            StevebotLog.logNonCritical(" nodes processed: " + nodesProcessed + "  (" + (((float) nodesProcessed / (float) nodesConsidered) * 100f) + "%)" + " -> discarded " + (nodesConsidered - nodesProcessed));
            StevebotLog.logNonCritical("");
            StevebotLog.logNonCritical("        nodes worse than path: " + nodesWorseThanPath);
            StevebotLog.logNonCritical("  nodes worse than best (row): " + nodesWorseThanBestRow);
            StevebotLog.logNonCritical("nodes worse than best (total): " + nodesWorseThanBestTotal);
            StevebotLog.logNonCritical("");
            StevebotLog.logNonCritical("  actions considered: " + actionsConsidered);
            StevebotLog.logNonCritical("actions (impossible): " + actionsImpossible + "(" + (((float) actionsImpossible / (float) actionsConsidered) * 100f) + "%)");
            StevebotLog.logNonCritical("   actions (invalid): " + actionsInvalid + "(" + (((float) actionsInvalid / (float) actionsConsidered) * 100f) + "%)");
            StevebotLog.logNonCritical("  actions (unloaded): " + actionsUnloaded + "(" + (((float) actionsUnloaded / (float) actionsConsidered) * 100f) + "%)");
            StevebotLog.logNonCritical("     actions (valid): " + actionsValid + "(" + (((float) actionsValid / (float) actionsConsidered) * 100f) + "%)");
            StevebotLog.logNonCritical("   actions (created): " + actionsCreated + "(" + (((float) actionsCreated / (float) actionsConsidered) * 100f) + "%)");
        }
        StevebotLog.logNonCritical("==================");
    }


}
