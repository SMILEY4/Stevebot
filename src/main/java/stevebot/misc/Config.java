package stevebot.misc;

import stevebot.Stevebot;
import stevebot.pathfinding.path.PathRenderable;

public class Config {


	public static final String MODID = "stevebot";
	public static final String NAME = "SteveBot";
	public static final String VERSION = "0.1-dev";
	public static final String MC_VERSION = "[1.12.2]";

	private static int pathfindingTimeoutSeconds = 10;
	private static PathRenderable.PathStyle pathStyle = PathRenderable.PathStyle.SOLID;
	private static boolean verboseMode = true;
	private static boolean showChunkCache = false;
	private static boolean showNodeCache = false;
	private static int pathfindingSlowdown = -1;




	/**
	 * @return the timeout for the pathfinding in seconds
	 */
	public static int getPathfindingTimeout() {
		return pathfindingTimeoutSeconds;
	}




	/**
	 * Sets the timeout for the pathfinding in seconds
	 *
	 * @param timeoutSeconds the new timeout in seconds
	 */
	public static void setPathfindingTimeout(int timeoutSeconds) {
		Config.pathfindingTimeoutSeconds = timeoutSeconds;
		Stevebot.log("Set pathfindingTimeoutSeconds: " + getPathfindingTimeout());
	}




	/**
	 * @return the style of the paths
	 */
	public static PathRenderable.PathStyle getPathStyle() {
		return pathStyle;
	}




	/**
	 * Sets the style of the paths.
	 *
	 * @param pathStyle the new style
	 */
	public static void setPathStyle(PathRenderable.PathStyle pathStyle) {
		Config.pathStyle = pathStyle;
		Stevebot.log("Set pathStyle: " + getPathStyle());
	}




	/**
	 * @return whether verbose-mode is enabled. Verbose mode will display more status/debug messages
	 */
	public static boolean isVerboseMode() {
		return verboseMode;
	}




	/**
	 * @param verboseMode true, to enable verbose mode.
	 */
	public static void setVerboseMode(boolean verboseMode) {
		Config.verboseMode = verboseMode;
		Stevebot.log("Enable verboseMode: " + isVerboseMode());
	}




	/**
	 * @return whether the outlines of all currently cached chunks are shown
	 */
	public static boolean isShowChunkCache() {
		return showChunkCache;
	}




	/**
	 * @param showChunkCache true, to show the outlines of all currently cached chunks
	 */
	public static void setShowChunkCache(boolean showChunkCache) {
		Config.showChunkCache = showChunkCache;
		Stevebot.log("Show chunkCache: " + isShowChunkCache());
	}




	/**
	 * @return whether the positions of all currently cached nodes are shown
	 */
	public static boolean isShowNodeCache() {
		return showNodeCache;
	}




	/**
	 * @param showNodeCache true, to show the positions of all currently cached nodes
	 */
	public static void setShowNodeCache(boolean showNodeCache) {
		Config.showNodeCache = showNodeCache;
		Stevebot.log("Show nodeCache: " + isShowNodeCache());
	}




	/**
	 * @return the delay in each step of the pathfinder in milliseconds
	 */
	public static int getPathfindingSlowdown() {
		return pathfindingSlowdown;
	}




	/**
	 * @param pathfindingSlowdown the delay of each step of the pathfinder in milliseconds
	 */
	public static void setPathfindingSlowdown(int pathfindingSlowdown) {
		Config.pathfindingSlowdown = pathfindingSlowdown;
		Stevebot.log("Set pathfinding-slodown: " + getPathfindingSlowdown() + "ms");
	}


}
