package stevebotOLD.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import tools.listener.GameMiscListener;

public class PlayerController implements GameMiscListener {


	private PlayerInputConfig inputConfig = null;

	public final PlayerMovement movement;
	public final PlayerUtils utils;




	public PlayerController() {
//		Stevebot.EVENT_HANDLER.addListener(this);
		movement = new PlayerMovement(this);
		utils = new PlayerUtils(this);
		reloadConfig();
	}




	public void sendMessage(String msg) {
		if(getPlayer() != null) {
			getPlayer().sendMessage(new TextComponentString(msg));
		}
	}



	public EntityPlayerSP getPlayer() {
		return Minecraft.getMinecraft().player;
	}




	public BlockPos getPlayerBlockPos() {
		EntityPlayerSP player = getPlayer();
		if (player != null) {
			return new BlockPos((int) (getPlayerPosition().x), (int) (getPlayerPosition().y), (int) (getPlayerPosition().z));
		} else {
			return null;
		}
	}




	public Vec3d getPlayerPosition() {
		EntityPlayerSP player = getPlayer();
		if (player != null) {
			return player.getPositionVector();
		} else {
			return null;
		}
	}




	public Vec3d getMotionVector() {
		EntityPlayerSP player = getPlayer();
		if (player != null) {
			return new Vec3d(player.motionX, player.motionY, player.motionZ);
		} else {
			return null;
		}
	}




	public boolean isPlayerMoving() {
		return isPlayerMoving(0.0001);
	}




	public boolean isPlayerMoving(double threshhold) {
		EntityPlayerSP player = getPlayer();
		if (player != null) {
			return player.motionX < threshhold && player.motionY < threshhold && player.motionZ < threshhold;
		} else {
			return false;
		}
	}




	public Vec3d getLookDir() {
		EntityPlayerSP player = getPlayer();
		if (player != null) {
			return player.getLook(0f);
		} else {
			return null;
		}
	}




	public void setLookAt(BlockPos pos) {
		EntityPlayerSP player = getPlayer();
		if (player != null) {
			final Vec3d posBlock = new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
			final Vec3d posHead = new Vec3d(player.getPositionEyes(1.0F).x, player.getPositionEyes(1.0F).y, player.getPositionEyes(1.0F).z);
			final Vec3d dir = posBlock.subtract(posHead).normalize().scale(-1);
			setLook(dir);
		}
	}




	public void setLook(Vec3d dir) {
		double pitch = Math.asin(dir.y);
		double yaw = Math.atan2(dir.z, dir.x);
		pitch = pitch * 180.0 / Math.PI;
		yaw = yaw * 180.0 / Math.PI;
		yaw += 90f;
		setLook(pitch, yaw);
	}




	public void setLook(double pitch, double yaw) {
		EntityPlayerSP player = getPlayer();
		if (player != null) {
			player.rotationPitch = (float) pitch;
			player.rotationYaw = (float) yaw;
		}
	}




	public void setMoveForward() {
		setMoveForward(true);
	}




	public void setMoveForward(boolean move) {
		setInput(PlayerInputConfig.InputType.WALK_FORWARD, move);
	}




	public void setMoveBackward() {
		setMoveBackward(true);
	}




	public void setMoveBackward(boolean move) {
		setInput(PlayerInputConfig.InputType.WALK_BACKWARD, move);
	}




	public void setMoveLeft() {
		setMoveLeft(true);
	}




	public void setMoveLeft(boolean move) {
		setInput(PlayerInputConfig.InputType.WALK_LEFT, move);
	}




	public void setMoveRight() {
		setMoveRight(true);
	}




	public void setMoveRight(boolean move) {
		setInput(PlayerInputConfig.InputType.WALK_RIGHT, move);
	}




	public void setJump() {
		setJump(true);
	}




	public void setJump(boolean jump) {
		setInput(PlayerInputConfig.InputType.JUMP, jump);
	}




	public void setInput(PlayerInputConfig.InputType type, boolean down) {
		KeyBinding binding = inputConfig.getBinding(type);
		KeyBinding.setKeyBindState(binding.getKeyCode(), down);
		KeyBinding.onTick(binding.getKeyCode());
	}




	public void stopAll() {
		setMoveForward(false);
		setMoveBackward(false);
		setMoveLeft(false);
		setMoveRight(false);
		setJump(false);
	}




	private void reloadConfig() {
		inputConfig = new PlayerInputConfig();
	}




//	@Override
//	public void onConfigChanged(ConfigChangedEvent.PostConfigChangedEvent event) {
//		reloadConfig();
//	}


}
