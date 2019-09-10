package stevebot.player;

import com.ruegnerlukas.simplemath.vectors.vec2.Vector2d;
import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import stevebot.events.GameTickListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import stevebot.events.ModEventHandler;

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




	public void setState(CameraState state) {
		this.state = state;
	}




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




	public boolean isLookingAt(BlockPos pos, boolean ignorePitch, double rangeAngleDeg) {

		EntityPlayerSP player = controller.getPlayer();
		if (player != null) {

			if (ignorePitch) {
				final Vector2d posHead = new Vector2d(player.getPositionEyes(1.0F).x, player.getPositionEyes(1.0F).z);
				final Vector2d posBlock = new Vector2d(pos.getX() + 0.5, pos.getZ() + 0.5);
				final Vector2d dirBlock = posBlock.copy().sub(posHead).normalize();
				final Vector2d lookDir = new Vector2d(getLookDir().x, getLookDir().z).normalize();
				final double angle = lookDir.angleDeg(dirBlock);
				return Math.abs(angle) <= rangeAngleDeg;

			} else {
				final Vector3d posHead = new Vector3d(player.getPositionEyes(1.0F).x, player.getPositionEyes(1.0F).y, player.getPositionEyes(1.0F).z);
				final Vector3d posBlock = new Vector3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
				final Vector3d dirBlock = posBlock.copy().sub(posHead).normalize();
				final Vector3d lookDir = getLookDir().normalize();
				final double angle = lookDir.angleDeg(dirBlock);
				return Math.abs(angle) <= rangeAngleDeg;
			}

		} else {
			return false;
		}
	}




	private void stopFreelook() {
		cameraYaw = originalYaw;
		cameraPitch = originalPitch;
		playerYaw = originalYaw;
		playerPitch = originalPitch;
		isFreelook = false;
	}




	public Vector3d getLookDir() {
		Vec3d v = getLookDirMC();
		if (v != null) {
			return new Vector3d(v.x, v.y, v.z);
		} else {
			return null;
		}
	}




	private Vec3d getLookDirMC() {
		EntityPlayerSP player = controller.getPlayer();
		if (player != null) {
			return player.getLook(0f);
		} else {
			return null;
		}
	}




	public void setLookAt(BlockPos pos) {
		setLookAt(pos, false);
	}




	public void setLookAt(BlockPos pos, boolean keepPitch) {
		EntityPlayerSP player = controller.getPlayer();
		if (player != null && pos != null) {
			final Vector3d posBlock = new Vector3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
			final Vector3d posHead = new Vector3d(player.getPositionEyes(1.0F).x, player.getPositionEyes(1.0F).y, player.getPositionEyes(1.0F).z);
			final Vector3d dir = posBlock.copy().sub(posHead).normalize().scale(-1);
			if (keepPitch) {
				dir.y = 0;
			}
			setLook(dir);
		}
	}




	public void setLook(Vector3d dir) {
		double pitch = Math.asin(dir.y);
		double yaw = Math.atan2(dir.z, dir.x);
		pitch = pitch * 180.0 / Math.PI;
		yaw = yaw * 180.0 / Math.PI;
		yaw += 90f;
		setLook(pitch, yaw);
	}




	public void setLook(double pitch, double yaw) {
		EntityPlayerSP player = controller.getPlayer();
		if (player != null) {
			player.rotationPitch = (float) pitch;
			player.rotationYaw = (float) yaw;
		}
	}


}