package stevebot.player;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextComponentString;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.BlockUtils;

public class PlayerUtils {


	public final double AT_LOC_DIST_ERROR = 0.05;
	private final PlayerController controller;




	PlayerUtils(PlayerController controller) {
		this.controller = controller;
	}




	/**
	 * Sends the player the given message. The message will be printed in the chat.
	 *
	 * @param msg the message.
	 */
	public void sendMessage(String msg) {
		if (controller.getPlayer() != null) {
			controller.getPlayer().sendMessage(new TextComponentString(msg));
		}
	}




	/**
	 * @return the current position of the player as a {@link BaseBlockPos}
	 */
	public BaseBlockPos getPlayerBlockPos() {
		EntityPlayerSP player = controller.getPlayer();
		if (player != null) {
			return BlockUtils.toBaseBlockPos(player.posX, player.posY, player.posZ);
		} else {
			return null;
		}
	}




	/**
	 * @return the exact current position of the player as a {@link Vector3d}
	 */
	public Vector3d getPlayerPosition() {
		EntityPlayerSP player = controller.getPlayer();
		if (player != null) {
			return new Vector3d(player.posX, player.posY, player.posZ);
		} else {
			return null;
		}
	}




	/**
	 * @return the current movement of the player.
	 */
	public Vector3d getMotionVector() {
		EntityPlayerSP player = controller.getPlayer();
		if (player != null) {
			return new Vector3d(player.motionX, player.motionY, player.motionZ);
		} else {
			return null;
		}
	}




	/**
	 * @return true, if the player is moving on any axis.
	 */
	public boolean isPlayerMoving() {
		return isPlayerMoving(false);
	}




	/**
	 * @param includeY true to include movement on the y-axis, false to ignore movement on the y-axis.
	 * @return true, if the player is moving on any axis.
	 */
	public boolean isPlayerMoving(boolean includeY) {
		return isPlayerMoving(0.0001, includeY);
	}




	/**
	 * @param threshold the threshold for the movement-speed
	 * @return true, if the player is moving on any axis.
	 */
	public boolean isPlayerMoving(double threshold) {
		return isPlayerMoving(threshold, false);
	}




	/**
	 * @param threshold the threshold for the movement-speed
	 * @param includeY  true to include movement on the y-axis, false to ignore movement on the y-axis.
	 * @return true, if the player is moving on any axis.
	 */
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




	/**
	 * @param pos     the target position as a {@link BaseBlockPos}
	 * @param ignoreY set to true to only check the x- and z-coordinates.
	 * @return true, if the player is currently at the given location (within a threshold defined by {@code PlayerUtils.AT_LOC_DIST_ERROR}).
	 */
	public boolean isAtLocation(BaseBlockPos pos, boolean ignoreY) {
		final double x = pos.getX() + 0.5;
		final double y = pos.getY();
		final double z = pos.getZ() + 0.5;
		if (ignoreY) {
			return isAtLocation(x, z);
		} else {
			return isAtLocation(x, y, z);
		}
	}




	/**
	 * @param x the target x-coordinate
	 * @param z the target z-coordinate
	 * @return true, if the player is currently at the given coordinates (within a threshold defined by {@code PlayerUtils.AT_LOC_DIST_ERROR}).
	 */
	public boolean isAtLocation(double x, double z) {
		final Vector3d current = getPlayerPosition();
		if (current.dist2(x, current.y, z) > AT_LOC_DIST_ERROR) {
			return false;
		} else {
			return true;
		}
	}




	/**
	 * @param x the target x-coordinate
	 * @param y the target y-coordinate
	 * @param z the target z-coordinate
	 * @return true, if the player is currently at the given coordinates (within a threshold defined by {@code PlayerUtils.AT_LOC_DIST_ERROR}).
	 */
	public boolean isAtLocation(double x, double y, double z) {
		final Vector3d current = getPlayerPosition();
		if (current.dist2(x, y, z) > AT_LOC_DIST_ERROR) {
			return false;
		} else {
			return true;
		}
	}


}
