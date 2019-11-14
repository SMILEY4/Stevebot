package stevebot.player;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.events.EventListener;
import stevebot.misc.Direction;

public interface PlayerCamera {


	enum CameraState {
		LOCKED,
		FREELOOK,
		DEFAULT
	}

	/**
	 * The given listener listens for a {@link TickEvent.RenderTickEvent}.
	 *
	 * @return the {@link EventListener} of this {@link PlayerCamera}.
	 */
	EventListener getListener();

	/**
	 * Sets the state of the player-camera
	 *
	 * @param state the new state of the camera
	 */
	void setState(PlayerCameraImpl.CameraState state);

	/**
	 * @return the {@link CameraState} of the player-camera
	 */
	PlayerCameraImpl.CameraState getState();

	/**
	 * Checks if the player is looking at the given {@link BaseBlockPos}
	 *
	 * @param pos           the position
	 * @param ignorePitch   set to true to ignore the pitch / up-down-axis
	 * @param rangeAngleDeg the threshold of the angle in degrees
	 * @return whether the player is looking at the given position
	 */
	boolean isLookingAt(BaseBlockPos pos, boolean ignorePitch, double rangeAngleDeg);

	/**
	 * Checks if the player is looking at the block at the given position
	 *
	 * @param x             the x position of the block
	 * @param y             the y position of the block
	 * @param z             the z position of the block
	 * @param ignorePitch   set to true to ignore the pitch / up-down-axis
	 * @param rangeAngleDeg the threshold of the angle in degrees
	 * @return whether the player is looking at the given position
	 */
	boolean isLookingAt(int x, int y, int z, boolean ignorePitch, double rangeAngleDeg);

	/**
	 * @return the direction the player is looking as a {@link Vector3d}
	 */
	Vector3d getLookDir();

	/**
	 * Sets the view-direction of the player to look at the given {@link BaseBlockPos}.
	 *
	 * @param pos the position to look at
	 */
	void setLookAt(BaseBlockPos pos);

	/**
	 * Sets the view-direction of the player to look at the given {@link BaseBlockPos}.
	 *
	 * @param pos       the {@link BaseBlockPos} to look at
	 * @param keepPitch set to true to keep the pitch of the current view-direction
	 */
	void setLookAt(BaseBlockPos pos, boolean keepPitch);

	/**
	 * Sets the view-direction of the player to look at a block at the given position.
	 *
	 * @param x         the x position of the block to look at
	 * @param y         the y position of the block to look at
	 * @param z         the z position of the block to look at
	 * @param keepPitch set to true to keep the pitch of the current view-direction
	 */
	void setLookAt(int x, int y, int z, boolean keepPitch);

	/**
	 * Sets the view-direction of the player to look at the given point
	 *
	 * @param point the position of the point
	 */
	void setLookAtPoint(Vector3d point);

	/**
	 * Makes the player look at the given side of the block at the given {@link BaseBlockPos}.
	 *
	 * @param pos       the position
	 * @param direction the direction of the side
	 */
	void setLookAtBlockSide(BaseBlockPos pos, Direction direction);

	/**
	 * Sets the view-direction of the player.
	 *
	 * @param dir the new view-direction
	 */
	void setLook(Vector3d dir);

	/**
	 * Sets the view-direction of the player.
	 *
	 * @param pitch the new pitch
	 * @param yaw   the new yaw
	 */
	void setLook(double pitch, double yaw);

	/**
	 * Forces the camera to look in specific directions, event when freelook is enabled.
	 */
	void enableForceCamera();

	/**
	 * Stops forcing the camera to look in specific directions, event when freelook is enabled.
	 *
	 * @param restoreFreelookView restores the view direction to the state before forceCamera was enabled
	 */
	void disableForceCamera(boolean restoreFreelookView);


	/**
	 * @return whether the camera is forced to look in a specific direction.
	 */
	boolean isForceEnabled();

}
