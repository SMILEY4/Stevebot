package stevebot;

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




	public static int getPathfindingTimeout() {
		return pathfindingTimeoutSeconds;
	}




	public static void setPathfindingTimeout(int timeoutSeconds) {
		Config.pathfindingTimeoutSeconds = timeoutSeconds;
		Stevebot.get().log("Set pathfindingTimeoutSeconds: " + getPathfindingTimeout());
	}




	public static PathRenderable.PathStyle getPathStyle() {
		return pathStyle;
	}




	public static void setPathStyle(PathRenderable.PathStyle pathStyle) {
		Config.pathStyle = pathStyle;
		Stevebot.get().log("Set pathStyle: " + getPathStyle());
	}




	public static boolean isVerboseMode() {
		return verboseMode;
	}




	public static void setVerboseMode(boolean verboseMode) {
		Config.verboseMode = verboseMode;
		Stevebot.get().log("Enable verboseMode: " + isVerboseMode());
	}




	public static boolean isShowChunkCache() {
		return showChunkCache;
	}




	public static void setShowChunkCache(boolean showChunkCache) {
		Config.showChunkCache = showChunkCache;
		Stevebot.get().log("Show chunkCache: " + isShowChunkCache());
	}




	public static void setShowNodeCache(boolean showNodeCache) {
		Config.showNodeCache = showNodeCache;
		Stevebot.get().log("Show nodeCache: " + isShowNodeCache());
	}




	public static boolean isShowNodeCache() {
		return showNodeCache;
	}




	public static void setPathfindingSlowdown(int pathfindingSlowdown) {
		Config.pathfindingSlowdown = pathfindingSlowdown;
		Stevebot.get().log("Set pathfinding-slodown: " + getPathfindingSlowdown() + "ms");
	}




	public static int getPathfindingSlowdown() {
		return pathfindingSlowdown;
	}


}
