package stevebot.player;

import com.ruegnerlukas.simplemath.vectors.vec2.Vector2d;
import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.eventsOLD.GameTickListener;
import stevebot.eventsOLD.ModEventHandler;

public class Camera implements GameTickListener {


	public enum CameraState {
		LOCKED,
		FREELOOK,
		DEFAULT
	}






	private final PlayerController controller;

	private CameraState state = CameraState.DEFAULT;
	private boolean isFreelook = false;




	Camera(PlayerController controller, ModEventHandler eventHandler) {
		this.controller = controller;
		eventHandler.addListener(this);
		setupLock();
	}




	/**
	 * Setup for the {@code  CameraState.LOCKED} mode.
	 */
	private void setupLock() {
		Minecraft.getMinecraft().mouseHelper = new MouseHelper() {
			@Override
			public void mouseXYChange() {
				if (getState() != CameraState.LOCKED) {
					super.mouseXYChange();
				}
			}
		};
	}




	/**
	 * Sets the state of the player-camera
	 *
	 * @param state the new state of the camera
	 */
	public void setState(CameraState state) {
		this.state = state;
	}




	/**
	 * @return the {@link CameraState} of the player-camera
	 */
	public CameraState getState() {
		return state;
	}




	@Override
	public void onRenderTickEvent(TickEvent.RenderTickEvent event) {

		EntityPlayerSP player = controller.getPlayer();
		if (player == null) {
			return;
		}

		if (getState() == CameraState.FREELOOK && !isFreelook) {
			startFreelook();
		}
		if (getState() == CameraState.FREELOOK && isFreelook) {
			updateFreelook(event.phase);
		}
		if (getState() != CameraState.FREELOOK && isFreelook) {
			stopFreelook();
		}

	}




	private float cameraYaw = 0;
	private float cameraPitch = 0;
	private float playerYaw = 0;
	private float playerPitch = 0;
	private float originalYaw = 0;
	private float originalPitch = 0;




	/**
	 * Starts the {@code CameraState.FREELOOK}-mode
	 */
	private void startFreelook() {
		final EntityPlayerSP player = controller.getPlayer();
		playerYaw = player.rotationYaw;
		playerPitch = player.rotationPitch;
		originalYaw = playerYaw;
		originalPitch = playerPitch;
		cameraYaw = playerYaw;
		cameraPitch = playerPitch;
		isFreelook = true;
	}




	/**
	 * Stops the {@code CameraState.FREELOOK}-mode
	 */
	private void stopFreelook() {
		cameraYaw = originalYaw;
		cameraPitch = originalPitch;
		playerYaw = originalYaw;
		playerPitch = originalPitch;
		isFreelook = false;
	}




	/**
	 * Update the camera in the {@code CameraState.FREELOOK}-mode
	 */
	private void updateFreelook(TickEvent.Phase phase) {

		final Entity player = Minecraft.getMinecraft().getRenderViewEntity();
		final EntityPlayerSP playerSP = controller.getPlayer();


		final float f = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6f + 0.2f;
		final float f1 = f * f * f * 8f;

		final double dx = Mouse.getDX() * f1 * 0.15;
		final double dy = -Mouse.getDY() * f1 * 0.15;

		cameraYaw += dx;
		cameraPitch += dy;
		cameraPitch = MathHelper.clamp(cameraPitch, -90f, 90f);

		if (phase == TickEvent.Phase.START) {
			player.rotationYaw = cameraYaw;
			player.rotationPitch = cameraPitch;
			player.prevRotationYaw = cameraYaw;
			player.prevRotationPitch = cameraPitch;

		} else {
			player.rotationYaw = playerSP.rotationYaw - cameraYaw + playerYaw;
			player.prevRotationYaw = playerSP.prevRotationYaw - cameraYaw + playerYaw;
			player.rotationPitch = playerSP.rotationPitch - cameraPitch + playerPitch;
			player.prevRotationPitch = playerSP.prevRotationPitch - cameraPitch + playerPitch;
		}

	}




	/**
	 * Checks if the player is looking at the given position
	 *
	 * @param pos           the position
	 * @param ignorePitch   set to true to ignore the pitch / up-down-axis
	 * @param rangeAngleDeg the threshold of the angle in degrees
	 * @return
	 */
	public boolean isLookingAt(BaseBlockPos pos, boolean ignorePitch, double rangeAngleDeg) {
		return isLookingAt(pos.getX(), pos.getY(), pos.getZ(), ignorePitch, rangeAngleDeg);
	}




	/**
	 * Checks if the player is looking at the given position
	 *
	 * @param x             the x position
	 * @param y             the y position
	 * @param y             the y position
	 * @param ignorePitch   set to true to ignore the pitch / up-down-axis
	 * @param rangeAngleDeg the threshold of the angle in degrees
	 * @return
	 */
	public boolean isLookingAt(int x, int y, int z, boolean ignorePitch, double rangeAngleDeg) {

		EntityPlayerSP player = controller.getPlayer();
		if (player != null) {

			if (ignorePitch) {
				final Vector2d posHead = new Vector2d(player.getPositionEyes(1.0F).x, player.getPositionEyes(1.0F).z);
				final Vector2d posBlock = new Vector2d(x + 0.5, z + 0.5);
				final Vector2d dirBlock = posBlock.copy().sub(posHead).normalize();
				final Vector2d lookDir = new Vector2d(getLookDir().x, getLookDir().z).normalize();
				final double angle = lookDir.angleDeg(dirBlock);
				return Math.abs(angle) <= rangeAngleDeg;

			} else {
				final Vector3d posHead = new Vector3d(player.getPositionEyes(1.0F).x, player.getPositionEyes(1.0F).y, player.getPositionEyes(1.0F).z);
				final Vector3d posBlock = new Vector3d(x + 0.5, y + 0.5, z + 0.5);
				final Vector3d dirBlock = posBlock.copy().sub(posHead).normalize();
				final Vector3d lookDir = getLookDir().normalize();
				final double angle = lookDir.angleDeg(dirBlock);
				return Math.abs(angle) <= rangeAngleDeg;
			}

		} else {
			return false;
		}
	}




	/**
	 * @return the direction the player is looking as a {@link Vector3d}
	 */
	public Vector3d getLookDir() {
		Vec3d v = getLookDirMC();
		if (v != null) {
			return new Vector3d(v.x, v.y, v.z);
		} else {
			return null;
		}
	}




	/**
	 * @return the direction the player is looking as a {@link Vec3d}
	 */
	private Vec3d getLookDirMC() {
		EntityPlayerSP player = controller.getPlayer();
		if (player != null) {
			return player.getLook(0f);
		} else {
			return null;
		}
	}




	/**
	 * Sets the view-direction of the player.
	 *
	 * @param pos the position to look at
	 */
	public void setLookAt(BaseBlockPos pos) {
		setLookAt(pos, false);
	}




	/**
	 * Sets the view-direction of the player.
	 *
	 * @param pos       the position to look at
	 * @param keepPitch set to true to keep the pitch of the current view-direction
	 */
	public void setLookAt(BaseBlockPos pos, boolean keepPitch) {
		setLookAt(pos.getX(), pos.getY(), pos.getZ(), keepPitch);
	}




	/**
	 * Sets the view-direction of the player.
	 *
	 * @param x         the x position to look at
	 * @param y         the y position to look at
	 * @param z         the z position to look at
	 * @param keepPitch set to true to keep the pitch of the current view-direction
	 */
	public void setLookAt(int x, int y, int z, boolean keepPitch) {
		EntityPlayerSP player = controller.getPlayer();
		if (player != null) {
			final Vector3d posBlock = new Vector3d(x + 0.5, y + 0.5, z + 0.5);
			final Vector3d posHead = new Vector3d(player.getPositionEyes(1.0F).x, player.getPositionEyes(1.0F).y, player.getPositionEyes(1.0F).z);
			final Vector3d dir = posBlock.copy().sub(posHead).normalize().scale(-1);
			if (keepPitch) {
				dir.y = 0;
			}
			setLook(dir);
		}
	}




	/**
	 * Sets the view-direction of the player.
	 *
	 * @param dir the new view-direction
	 */
	public void setLook(Vector3d dir) {
		double pitch = Math.asin(dir.y);
		double yaw = Math.atan2(dir.z, dir.x);
		pitch = pitch * 180.0 / Math.PI;
		yaw = yaw * 180.0 / Math.PI;
		yaw += 90f;
		setLook(pitch, yaw);
	}




	/**
	 * Sets the view-direction of the player.
	 *
	 * @param pitch the new pitch
	 * @param yaw   the new yaw
	 */
	public void setLook(double pitch, double yaw) {
		EntityPlayerSP player = controller.getPlayer();
		if (player != null) {
			player.rotationPitch = (float) pitch;
			player.rotationYaw = (float) yaw;
		}
	}


}