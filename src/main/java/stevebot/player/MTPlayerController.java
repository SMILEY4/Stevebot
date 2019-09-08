package stevebot.player;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import stevebot.ModBase;
import stevebot.ModModule;
import stevebot.events.GameTickListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import stevebot.pathfinding.BlockUtils;

public class MTPlayerController extends ModModule {


	private final Camera camera;
	private final Movement movement;
	private final PlayerUtils utils;

	private PlayerInputConfig inputConfig = null;
	private boolean muteUserInput = false;




	public MTPlayerController(ModBase modHandler) {
		super(modHandler);

		utils = new PlayerUtils(this);
		camera = new Camera(this);
		movement = new Movement(this);

		GameTickListener tickListener = new GameTickListener() {
			@Override
			public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
				if (event.phase == TickEvent.Phase.START) {
					if (muteUserInput) {
						MTPlayerController.this.stopAll();
					}
				}
			}
		};
		getModHandler().getEventHandler().addListener(tickListener);
		reloadConfig();
	}




	public Camera getCamera() {
		return camera;
	}




	public Movement getMovement() {
		return movement;
	}




	public PlayerUtils getUtils() {
		return utils;
	}




	public void sendMessage(String msg) {
		if (getPlayer() != null) {
			getPlayer().sendMessage(new TextComponentString(msg));
		}
	}




	public EntityPlayerSP getPlayer() {
		return Minecraft.getMinecraft().player;
	}




	public BlockPos getPlayerBlockPos() {
		EntityPlayerSP player = getPlayer();
		if (player != null) {
			return BlockUtils.toBlockPos(player.posX, player.posY, player.posZ);
		} else {
			return null;
		}
	}




	public Vector3d getPlayerPosition() {
		EntityPlayerSP player = getPlayer();
		if (player != null) {
			return new Vector3d(player.posX, player.posY, player.posZ);
		} else {
			return null;
		}
	}




	public Vector3d getMotionVector() {
		EntityPlayerSP player = getPlayer();
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
		EntityPlayerSP player = getPlayer();
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




	public void setMuteUserInput(boolean mute) {
		this.muteUserInput = mute;
	}




	public boolean isUserMuted() {
		return this.muteUserInput;
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




	public void setJump(boolean allowInAir) {
		setJump(true, allowInAir);
	}




	public void setJump(boolean jump, boolean allowInAir) {
		if (jump) {
			if (getPlayer() == null) {
				return;
			}
			if (getPlayer().onGround) {
				setInput(PlayerInputConfig.InputType.JUMP, true);
			} else {
				if (allowInAir) {
					setInput(PlayerInputConfig.InputType.JUMP, true);
				}
			}
		} else {
			setInput(PlayerInputConfig.InputType.JUMP, false);
		}
	}




	public void setSprint() {
		getPlayer().setSprinting(true);
	}




	public void setSprint(boolean sprint) {
		if (getPlayer() != null) {
			getPlayer().setSprinting(sprint);
		}
	}




	public void setSneak() {
		setSneak(true);
	}




	public void setSneak(boolean sneak) {
		setInput(PlayerInputConfig.InputType.SNEAK, sneak);
	}




	public void setPlaceBlock(boolean placeBlock) {
		setInput(PlayerInputConfig.InputType.PLACE_BLOCK, placeBlock);
	}




	public void setPlaceBlock() {
		setPlaceBlock(true);
	}




	public void setBreakBlock(boolean breakBlock) {
		setInput(PlayerInputConfig.InputType.BREAK_BLOCK, breakBlock);
	}




	public void setBreakBlock() {
		setBreakBlock(true);
	}




	public void setInteract(boolean interact) {
		setInput(PlayerInputConfig.InputType.INTERACT, interact);
	}




	public void setInteract() {
		setInteract(true);
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
		setJump(false, true);
		setSprint(false);
		setSneak(false);
//		setPlaceBlock(false);
//		setBreakBlock(false);
//		setInteract(false);
	}




	private void reloadConfig() {
		inputConfig = new PlayerInputConfig();
	}




	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.PostConfigChangedEvent event) {
		reloadConfig();
	}


}
