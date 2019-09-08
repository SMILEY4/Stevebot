package stevebot.player;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import stevebot.events.GameTickListener;
import stevebot.events.ModEventHandler;

public class PlayerInput {


	private PlayerController controller;

	private PlayerInputConfig inputConfig = null;
	private boolean muteUserInput = false;




	PlayerInput(PlayerController controller, ModEventHandler eventHandler) {
		this.controller = controller;
		GameTickListener tickListener = new GameTickListener() {
			@Override
			public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
				if (event.phase == TickEvent.Phase.START) {
					if (muteUserInput) {
						stopAll();
					}
				}
			}
		};
		eventHandler.addListener(tickListener);
		reloadConfig();
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
			if (controller.getPlayer() == null) {
				return;
			}
			if (controller.getPlayer().onGround) {
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
		controller.getPlayer().setSprinting(true);
	}




	public void setSprint(boolean sprint) {
		if (controller.getPlayer() != null) {
			controller.getPlayer().setSprinting(sprint);
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
