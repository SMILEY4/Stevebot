package modtools.player;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class PlayerUtils {


	private final MTPlayerController controller;




	PlayerUtils(MTPlayerController controller) {
		this.controller = controller;
	}








	private final double AT_LOC_DIST_ERROR = 0.05;




	public boolean isAtLocation(BlockPos pos, boolean ignoreY) {
		final double x = pos.getX() + 0.5;
		final double y = pos.getY();
		final double z = pos.getZ() + 0.5;
		if (ignoreY) {
			return isAtLocation(x, z);
		} else {
			return isAtLocation(x, y, z);
		}
	}




	public boolean isAtLocation(double x, double z) {
		final Vec3d current = controller.getPlayerPosition();
		final double dist = (current.x - x) * (current.x - x) + (current.z - z) * (current.z - z);
		if (dist > AT_LOC_DIST_ERROR) {
			return false;
		} else {
			return true;
		}
	}




	public boolean isAtLocation(double x, double y, double z) {
		final Vec3d current = controller.getPlayerPosition();
		final double dist = (current.x - x) * (current.x - x) + (current.y - y) * (current.y - y) + (current.z - z) * (current.z - z);
		if (dist > AT_LOC_DIST_ERROR) {
			return false;
		} else {
			return true;
		}
	}


}
