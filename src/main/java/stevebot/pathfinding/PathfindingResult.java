package stevebot.pathfinding;

import stevebot.Stevebot;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.pathfinding.goal.Goal;
import stevebot.pathfinding.path.EmptyPath;
import stevebot.pathfinding.path.Path;

import java.util.ArrayList;
import java.util.List;

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
		Stevebot.log("=== STATISTICS ===");
		Stevebot.log("start: " + start.toString());
		Stevebot.log("goal: " + goal.goalString());
		Stevebot.log("found path: " + pathFound + ", path=" + (finalPath == null ? "null" : finalPath.getClass().getSimpleName()));
		Stevebot.log("time: " + (timeEnd - timeStart) + "ms");
		if (pathFound) {
			Stevebot.log("path cost: " + pathCost);
			Stevebot.log("path length: " + pathLength);
			Stevebot.log("");
			Stevebot.log("hit timeout: " + hitTimeout);
			Stevebot.log("paths found total: " + pathsFoundTotal);
			Stevebot.log("better paths found: " + betterPathsFound);
			Stevebot.log("");
			Stevebot.log("paths:");
			for (Path path : paths) {
				Stevebot.log("    cost: " + path.getCost());
				Stevebot.log("    length: " + path.getNodes().size());
				Stevebot.log("------------");
			}
			Stevebot.log("");
			Stevebot.log("nodes considered: " + nodesConsidered);
			Stevebot.log("nodes processed: " + nodesProcessed + "  (" + (((float) nodesConsidered / (float) nodesProcessed) * 100f) + "%)" + " -> discarded " + (nodesConsidered - nodesProcessed));
			Stevebot.log("");
			Stevebot.log("nodes worse than path: " + nodesWorseThanPath);
			Stevebot.log("nodes worse than best (row): " + nodesWorseThanBestRow);
			Stevebot.log("nodes worse than best (total): " + nodesWorseThanBestTotal);
			Stevebot.log("");
			Stevebot.log("actions (impossible): " + actionsImpossible + "(" + (((float) actionsConsidered / (float) actionsImpossible) * 100f) + "%)");
			Stevebot.log("actions (invalid): " + actionsInvalid + "(" + (((float) actionsConsidered / (float) actionsInvalid) * 100f) + "%)");
			Stevebot.log("actions (unloaded): " + actionsUnloaded + "(" + (((float) actionsConsidered / (float) actionsUnloaded) * 100f) + "%)");
			Stevebot.log("actions (valid): " + actionsValid + "(" + (((float) actionsConsidered / (float) actionsValid) * 100f) + "%)");
			Stevebot.log("actions (created): " + actionsCreated + "(" + (((float) actionsConsidered / (float) actionsCreated) * 100f) + "%)");

		}
		Stevebot.log("==================");
	}




	/**
	 * Log the statistics formatted for console-output as non-critical.
	 */
	public void logConsole() {
		Stevebot.logNonCritical("=== STATISTICS ===");
		Stevebot.logNonCritical("      start: " + start.toString());
		Stevebot.logNonCritical("       goal: " + goal.goalString());
		Stevebot.logNonCritical(" found path: " + pathFound + ", path=" + (finalPath == null ? "null" : finalPath.getClass().getSimpleName()));
		Stevebot.logNonCritical("       time: " + (timeEnd - timeStart) + "ms");
		if (pathFound) {
			Stevebot.logNonCritical("  path cost: " + pathCost);
			Stevebot.logNonCritical("path length: " + pathLength);
			Stevebot.logNonCritical("");
			Stevebot.logNonCritical("       hit timeout: " + hitTimeout);
			Stevebot.logNonCritical(" paths found total: " + pathsFoundTotal);
			Stevebot.logNonCritical("better paths found: " + betterPathsFound);
			Stevebot.logNonCritical("");
			Stevebot.logNonCritical("paths:");
			for (Path path : paths) {
				Stevebot.logNonCritical("        cost: " + path.getCost());
				Stevebot.logNonCritical("      length: " + path.getNodes().size());
				Stevebot.logNonCritical("      ------");
			}
			Stevebot.logNonCritical("");
			Stevebot.logNonCritical("nodes considered: " + nodesConsidered);
			Stevebot.logNonCritical(" nodes processed: " + nodesProcessed + "  (" + (((float) nodesProcessed / (float) nodesConsidered) * 100f) + "%)" + " -> discarded " + (nodesConsidered - nodesProcessed));
			Stevebot.logNonCritical("");
			Stevebot.logNonCritical("        nodes worse than path: " + nodesWorseThanPath);
			Stevebot.logNonCritical("  nodes worse than best (row): " + nodesWorseThanBestRow);
			Stevebot.logNonCritical("nodes worse than best (total): " + nodesWorseThanBestTotal);
			Stevebot.logNonCritical("");
			Stevebot.logNonCritical("  actions considered: " + actionsConsidered);
			Stevebot.logNonCritical("actions (impossible): " + actionsImpossible + "(" + (((float) actionsImpossible / (float) actionsConsidered) * 100f) + "%)");
			Stevebot.logNonCritical("   actions (invalid): " + actionsInvalid + "(" + (((float) actionsInvalid / (float) actionsConsidered) * 100f) + "%)");
			Stevebot.logNonCritical("  actions (unloaded): " + actionsUnloaded + "(" + (((float) actionsUnloaded / (float) actionsConsidered) * 100f) + "%)");
			Stevebot.logNonCritical("     actions (valid): " + actionsValid + "(" + (((float) actionsValid / (float) actionsConsidered) * 100f) + "%)");
			Stevebot.logNonCritical("   actions (created): " + actionsCreated + "(" + (((float) actionsCreated / (float) actionsConsidered) * 100f) + "%)");
		}
		Stevebot.logNonCritical("==================");
	}


}
