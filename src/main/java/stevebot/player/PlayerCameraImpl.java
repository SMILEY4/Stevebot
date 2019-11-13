package stevebot.player;

import com.ruegnerlukas.simplemath.vectors.vec2.Vector2d;
import com.ruegnerlukas.simplemath.vectors.vec2.Vector2f;
import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.events.EventListener;
import stevebot.minecraft.MinecraftAdapter;
import stevebot.misc.Direction;

public class PlayerCameraImpl implements PlayerCamera {


	private CameraState state = CameraState.DEFAULT;
	private boolean isFreelook = false;
	private CameraState preForcedState = state;
	private Vector2f lastFreeView = new Vector2f();

	private final EventListener listener = new EventListener<TickEvent.RenderTickEvent>() {
		@Override
		public Class<TickEvent.RenderTickEvent> getEventClass() {
			return TickEvent.RenderTickEvent.class;
		}




		@Override
		public void onEvent(TickEvent.RenderTickEvent event) {
			onRenderTickEvent(event);
		}
	};




	public PlayerCameraImpl() {
		setupLock();
	}




	/**
	 * Setup for the {@code  CameraState.LOCKED} mode.
	 */
	private void setupLock() {
		MinecraftAdapter.get().setMouseHelper(new MouseHelper() {
			@Override
			public void mouseXYChange() {
				if (getState() != CameraState.LOCKED) {
					super.mouseXYChange();
				}
			}
		});
	}




	@Override
	public EventListener getListener() {
		return listener;
	}




	@Override
	public void setState(CameraState state) {
		this.state = state;
	}




	@Override
	public CameraState getState() {
		return state;
	}




	/**
	 * Updates this camera
	 *
	 * @param event the {@link TickEvent.RenderTickEvent}
	 */
	public void onRenderTickEvent(TickEvent.RenderTickEvent event) {

		EntityPlayerSP player = PlayerUtils.getPlayer();
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
		final EntityPlayerSP player = PlayerUtils.getPlayer();
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

		final Entity player = MinecraftAdapter.get().getRenderViewEntity();
		final EntityPlayerSP playerSP = PlayerUtils.getPlayer();


		final float f = MinecraftAdapter.get().getGameSettings().mouseSensitivity * 0.6f + 0.2f;
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
			lastFreeView.set(player.rotationYaw, playerPitch);


		} else {
			player.rotationYaw = playerSP.rotationYaw - cameraYaw + playerYaw;
			player.prevRotationYaw = playerSP.prevRotationYaw - cameraYaw + playerYaw;
			player.rotationPitch = playerSP.rotationPitch - cameraPitch + playerPitch;
			player.prevRotationPitch = playerSP.prevRotationPitch - cameraPitch + playerPitch;
			lastFreeView.set(player.rotationYaw, playerPitch);
		}

	}




	@Override
	public boolean isLookingAt(BaseBlockPos pos, boolean ignorePitch, double rangeAngleDeg) {
		return isLookingAt(pos.getX(), pos.getY(), pos.getZ(), ignorePitch, rangeAngleDeg);
	}




	@Override
	public boolean isLookingAt(int x, int y, int z, boolean ignorePitch, double rangeAngleDeg) {

		EntityPlayerSP player = PlayerUtils.getPlayer();
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




	@Override
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
		EntityPlayerSP player = PlayerUtils.getPlayer();
		if (player != null) {
			return player.getLook(0f);
		} else {
			return null;
		}
	}




	@Override
	public void setLookAt(BaseBlockPos pos) {
		setLookAt(pos, false);
	}




	@Override
	public void setLookAt(BaseBlockPos pos, boolean keepPitch) {
		setLookAt(pos.getX(), pos.getY(), pos.getZ(), keepPitch);
	}




	@Override
	public void setLookAt(int x, int y, int z, boolean keepPitch) {
		EntityPlayerSP player = PlayerUtils.getPlayer();
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




	@Override
	public void setLookAtPoint(Vector3d point) {
		EntityPlayerSP player = PlayerUtils.getPlayer();
		if (player != null) {
			final Vector3d posHead = new Vector3d(player.getPositionEyes(1.0F).x, player.getPositionEyes(1.0F).y, player.getPositionEyes(1.0F).z);
			final Vector3d dir = point.copy().sub(posHead).normalize().scale(-1);
			setLook(dir);
		}
	}




	@Override
	public void setLookAtBlockSide(BaseBlockPos pos, Direction direction) {
		final PlayerCamera camera = PlayerUtils.getCamera();
		final Vector3d posLookAt = new Vector3d(pos.getCenterX(), pos.getCenterY(), pos.getCenterZ())
				.add(direction.dx * 0.5, direction.dy * 0.5, direction.dz * 0.5);
		camera.setLookAtPoint(posLookAt);
	}




	@Override
	public void setLook(Vector3d dir) {
		double pitch = Math.asin(dir.y);
		double yaw = Math.atan2(dir.z, dir.x);
		pitch = pitch * 180.0 / Math.PI;
		yaw = yaw * 180.0 / Math.PI;
		yaw += 90f;
		setLook(pitch, yaw);
	}




	@Override
	public void setLook(double pitch, double yaw) {
		final EntityPlayerSP player = PlayerUtils.getPlayer();
		if (player != null) {
			player.rotationPitch = (float) pitch;
			player.rotationYaw = (float) yaw;
		}
	}




	@Override
	public void enableForceCamera() {
		preForcedState = getState();
		setState(CameraState.LOCKED);
	}




	@Override
	public void disableForceCamera(boolean restoreFreelookView) {
		setState(preForcedState);
		if (restoreFreelookView) {
			final Entity player = MinecraftAdapter.get().getRenderViewEntity();
			player.rotationYaw = lastFreeView.x;
			player.prevRotationYaw = lastFreeView.x;
			player.rotationPitch = lastFreeView.y;
			player.prevRotationPitch = lastFreeView.y;
		}
	}




	@Override
	public boolean isForceEnabled() {
		return getState() == CameraState.LOCKED;
	}


}