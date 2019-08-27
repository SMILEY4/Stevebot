package modtools.player;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import modtools.events.GameTickListener;

public class Camera implements GameTickListener {


	public enum CameraState {
		LOCKED,
		FREELOOK,
		DEFAULT
	}






	private final MTPlayerController PLAYER_CONTROLLER;

	private CameraState state = CameraState.DEFAULT;
	private boolean isFreelook = false;




	public Camera(MTPlayerController PLAYER_CONTROLLER) {
		this.PLAYER_CONTROLLER = PLAYER_CONTROLLER;
		PLAYER_CONTROLLER.getModHandler().getEventHandler().addListener(this);
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

		EntityPlayerSP player = PLAYER_CONTROLLER.getPlayer();
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
		final EntityPlayerSP player = PLAYER_CONTROLLER.getPlayer();
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
		final EntityPlayerSP playerSP = PLAYER_CONTROLLER.getPlayer();


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




	public Vec3d getLookDirMC() {
		EntityPlayerSP player = PLAYER_CONTROLLER.getPlayer();
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
		EntityPlayerSP player = PLAYER_CONTROLLER.getPlayer();
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
		EntityPlayerSP player = PLAYER_CONTROLLER.getPlayer();
		if (player != null) {
			player.rotationPitch = (float) pitch;
			player.rotationYaw = (float) yaw;
		}
	}


}