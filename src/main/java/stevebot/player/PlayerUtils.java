package stevebot.player;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.util.math.BlockPos;

public class PlayerUtils {


	private final MTPlayerController PLAYER_CONTROLLER;




	PlayerUtils(MTPlayerController controller) {
		this.PLAYER_CONTROLLER = controller;
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
		final Vector3d current = PLAYER_CONTROLLER.getPlayerPosition();
		if (current.dist2(x, current.y, z) > AT_LOC_DIST_ERROR) {
			return false;
		} else {
			return true;
		}
	}




	public boolean isAtLocation(double x, double y, double z) {
		final Vector3d current = PLAYER_CONTROLLER.getPlayerPosition();
		if (current.dist2(x, y, z) > AT_LOC_DIST_ERROR) {
			return false;
		} else {
			return true;
		}
	}


}
