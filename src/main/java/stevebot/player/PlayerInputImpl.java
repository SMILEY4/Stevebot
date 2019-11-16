package stevebot.player;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import stevebot.events.EventListener;

public class PlayerInputImpl implements PlayerInput {


	private PlayerInputConfig inputConfig = null;
	private boolean muteUserInput = false;
	private boolean isHoldingJump = false;
	private boolean isHoldingSneak = false;

	private final EventListener listenerPlayerTick = new EventListener<TickEvent.PlayerTickEvent>() {
		@Override
		public Class<TickEvent.PlayerTickEvent> getEventClass() {
			return TickEvent.PlayerTickEvent.class;
		}




		@Override
		public void onEvent(TickEvent.PlayerTickEvent event) {
			if (event.phase == TickEvent.Phase.START) {
				if (muteUserInput) {
					stopAll();
				}
			}
		}
	};

	private final EventListener listenerConfigChanged = new EventListener<ConfigChangedEvent.PostConfigChangedEvent>() {
		@Override
		public Class<ConfigChangedEvent.PostConfigChangedEvent> getEventClass() {
			return ConfigChangedEvent.PostConfigChangedEvent.class;
		}




		@Override
		public void onEvent(ConfigChangedEvent.PostConfigChangedEvent event) {
			reloadConfig();
		}
	};




	public PlayerInputImpl() {
		reloadConfig();
	}




	@Override
	public EventListener getPlayerTickListener() {
		return listenerPlayerTick;
	}




	@Override
	public EventListener getConfigChangedListener() {
		return listenerConfigChanged;
	}




	@Override
	public void setMuteUserInput(boolean mute) {
		this.muteUserInput = mute;
	}




	@Override
	public boolean isUserMuted() {
		return this.muteUserInput;
	}




	@Override
	public void setMoveForward() {
		setMoveForward(true);
	}




	@Override
	public void setMoveForward(boolean move) {
		setInput(PlayerInputConfig.InputType.WALK_FORWARD, move);
	}




	@Override
	public void setMoveBackward() {
		setMoveBackward(true);
	}




	@Override
	public void setMoveBackward(boolean move) {
		setInput(PlayerInputConfig.InputType.WALK_BACKWARD, move);
	}




	@Override
	public void setMoveLeft() {
		setMoveLeft(true);
	}




	@Override
	public void setMoveLeft(boolean move) {
		setInput(PlayerInputConfig.InputType.WALK_LEFT, move);
	}




	@Override
	public void setMoveRight() {
		setMoveRight(true);
	}




	@Override
	public void setMoveRight(boolean move) {
		setInput(PlayerInputConfig.InputType.WALK_RIGHT, move);
	}




	@Override
	public void setJump() {
		setJump(true, false);
	}




	@Override
	public void setJump(boolean jump, boolean allowInAir) {
		if (jump) {
			if (PlayerUtils.getPlayer() == null) {
				return;
			}
			if (PlayerUtils.getPlayer().onGround) {
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




	@Override
	public void holdJump() {
		isHoldingJump = true;
		setJump(true, true);
	}




	@Override
	public void releaseJump() {
		isHoldingJump = false;
		setJump(false, false);
	}




	@Override
	public boolean isHoldingJump() {
		return isHoldingJump;
	}




	@Override
	public void setSprint() {
		setSprint(true);
	}




	@Override
	public void setSprint(boolean sprint) {
		if (PlayerUtils.getPlayer() != null) {
			PlayerUtils.getPlayer().setSprinting(sprint);
		}
	}




	@Override
	public void holdSneak() {
		isHoldingSneak = true;
		setSneak(true);
	}




	@Override
	public void releaseSneak() {
		isHoldingSneak = false;
		setSneak(false);
	}




	@Override
	public void setSneak() {
		setSneak(true);
	}




	@Override
	public void setSneak(boolean sneak) {
		setInput(PlayerInputConfig.InputType.SNEAK, sneak);
	}




	@Override
	public void setPlaceBlock() {
		setPlaceBlock(true);
	}




	@Override
	public void setPlaceBlock(boolean placeBlock) {
		setInput(PlayerInputConfig.InputType.PLACE_BLOCK, placeBlock);
	}




	@Override
	public void setBreakBlock() {
		setBreakBlock(true);
	}




	@Override
	public void setBreakBlock(boolean breakBlock) {
		setInput(PlayerInputConfig.InputType.BREAK_BLOCK, breakBlock);
	}




	@Override
	public void setInteract() {
		setInteract(true);
	}




	@Override
	public void setInteract(boolean interact) {
		setInput(PlayerInputConfig.InputType.INTERACT, interact);
	}




	@Override
	public void setInput(PlayerInputConfig.InputType type, boolean down) {
		KeyBinding binding = inputConfig.getBinding(type);
		if (binding.isKeyDown() == down) {
			return;
		}
		KeyBinding.setKeyBindState(binding.getKeyCode(), down);
		if (down) {
			KeyBinding.onTick(binding.getKeyCode());
		}
	}




	@Override
	public void stopAll() {
		setMoveForward(false);
		setMoveBackward(false);
		setMoveLeft(false);
		setMoveRight(false);
		if (!isHoldingJump()) {
			setJump(false, true);
		}
		setSprint(false);
		if (!isHoldingJump()) {
			setSneak(false);
		}
		setPlaceBlock(false);
		setBreakBlock(false);
		setInteract(false);
	}




	/**
	 * Reloads the {@link PlayerInputConfig} of this {@link PlayerInputImpl}.
	 */
	private void reloadConfig() {
		inputConfig = new PlayerInputConfig();
	}


}
