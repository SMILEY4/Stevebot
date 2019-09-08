package stevebot.player;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import stevebot.pathfinding.BlockUtils;

public class PlayerUtils {


	private final PlayerController controller;
	private final double AT_LOC_DIST_ERROR = 0.05;




	PlayerUtils(PlayerController controller) {
		this.controller = controller;
	}




	public void sendMessage(String msg) {
		if (controller.getPlayer() != null) {
			controller.getPlayer().sendMessage(new TextComponentString(msg));
		}
	}




	public BlockPos getPlayerBlockPos() {
		EntityPlayerSP player = controller.getPlayer();
		if (player != null) {
			return BlockUtils.toBlockPos(player.posX, player.posY, player.posZ);
		} else {
			return null;
		}
	}




	public Vector3d getPlayerPosition() {
		EntityPlayerSP player = controller.getPlayer();
		if (player != null) {
			return new Vector3d(player.posX, player.posY, player.posZ);
		} else {
			return null;
		}
	}




	public Vector3d getMotionVector() {
		EntityPlayerSP player = controller.getPlayer();
		if (player != null) {
			return new Vector3d(player.motionX, player.motionY, player.motionZ);
		} else {
			return null;
		}
	}




	public boolean isPlayerMoving() {
		return isPlayerMoving(false);
	}




	public boolean isPlayerMoving(boolean includeY) {
		return isPlayerMoving(0.0001, includeY);
	}




	public boolean isPlayerMoving(double threshold) {
		return isPlayerMoving(threshold, false);
	}




	public boolean isPlayerMoving(double threshold, boolean includeY) {
		EntityPlayerSP player = controller.getPlayer();
		if (player != null) {
			if (includeY) {
				return player.motionX > threshold || player.motionY > threshold || player.motionZ > threshold;
			} else {
				return player.motionX > threshold || player.motionZ > threshold;
			}
		} else {
			return false;
		}
	}




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
		final Vector3d current = getPlayerPosition();
		if (current.dist2(x, current.y, z) > AT_LOC_DIST_ERROR) {
			return false;
		} else {
			return true;
		}
	}




	public boolean isAtLocation(double x, double y, double z) {
		final Vector3d current = getPlayerPosition();
		if (current.dist2(x, y, z) > AT_LOC_DIST_ERROR) {
			return false;
		} else {
			return true;
		}
	}


}
