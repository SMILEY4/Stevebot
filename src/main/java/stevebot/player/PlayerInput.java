package stevebot.player;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import stevebot.events.EventListener;

public interface PlayerInput {


	/**
	 * The given listener listens for a {@link TickEvent.PlayerTickEvent}.
	 *
	 * @return the {@link EventListener} of this {@link PlayerInput} for the {@link TickEvent.PlayerTickEvent}.
	 */
	EventListener getPlayerTickListener();

	/**
	 * The given listener listens for a {@link ConfigChangedEvent.PostConfigChangedEvent}.
	 *
	 * @return the {@link EventListener} of this {@link PlayerInput} for the {@link ConfigChangedEvent.PostConfigChangedEvent}.
	 */
	EventListener getConfigChangedListener();

	/**
	 * @param mute set to true to ignore any input not coming from this {@link PlayerInputImpl}.
	 */
	void setMuteUserInput(boolean mute);

	/**
	 * @return true, if any input not coming from this {@link PlayerInputImpl} is ignored.
	 */
	boolean isUserMuted();

	/**
	 * Move forward until stopped (same as pressing 'w')
	 */
	void setMoveForward();

	/**
	 * @param move true to move forward (same as pressing 'w'), false to stop moving forward.
	 */
	void setMoveForward(boolean move);

	/**
	 * Move backward until stopped (same as pressing 's').
	 */
	void setMoveBackward();

	/**
	 * @param move true to move backward (same as pressing 's'), false to stop moving backward.
	 */
	void setMoveBackward(boolean move);

	/**
	 * Move left until stopped (same as pressing 'a').
	 */
	void setMoveLeft();

	/**
	 * @param move true to move left same as pressing 'a'), false to stop moving backward.
	 */
	void setMoveLeft(boolean move);

	/**
	 * Move right until stopped (same as pressing 'd').
	 */
	void setMoveRight();

	/**
	 * @param move true to move right same as pressing 'd'), false to stop moving backward.
	 */
	void setMoveRight(boolean move);

	/**
	 * Jump until stopped (same as pressing 'space').
	 */
	void setJump();

	/**
	 * @param jump       true to jump same as pressing 'space'), false to stop jumping.
	 * @param allowInAir whether or not to jump when the player is already in the air.
	 */
	void setJump(boolean jump, boolean allowInAir);

	/**
	 * Start to hold down jump. Until {@link PlayerInputImpl#releaseJump()} is called, the jump button will be held down.
	 * {@link PlayerInputImpl#stopAll()} will not reset affect the jump-button anymore
	 */
	void holdJump();

	/**
	 * Stop holding down the jump button. The "jump" will end and {@link PlayerInputImpl#stopAll()} has an effect again.
	 */
	void releaseJump();

	/**
	 * @return whether the jump button is continuously held down.
	 */
	boolean isHoldingJump();

	/**
	 * Sprint until stopped.
	 */
	void setSprint();

	/**
	 * @param sprint true to sprint, false to stop sprinting.
	 */
	void setSprint(boolean sprint);

	/**
	 * Start to hold down sneak. Until {@link PlayerInputImpl#releaseSneak()} is called, the sneak button will be held down.
	 * {@link PlayerInputImpl#stopAll()} will not reset affect the sneak-button anymore
	 */
	void holdSneak();

	/**
	 * Stop holding down the sneak button. The player will stop sneaking {@link PlayerInputImpl#stopAll()} has an effect again.
	 */
	void releaseSneak();

	/**
	 * Sneak until stopped.
	 */
	void setSneak();

	/**
	 * @param sneak true to sneak, false to stop sneaking.
	 */
	void setSneak(boolean sneak);

	/**
	 * Place blocks until stopped (same as pressing 'right mouse').
	 */
	void setPlaceBlock();

	/**
	 * @param placeBlock true to place blocks (same as pressing 'right mouse'), false to stop placing blocks.
	 */
	void setPlaceBlock(boolean placeBlock);

	/**
	 * Break blocks until stopped (same as pressing 'left mouse').
	 */
	void setBreakBlock();

	/**
	 * @param breakBlock true to break blocks (same as pressing 'left mouse'), false to stop break blocks.
	 */
	void setBreakBlock(boolean breakBlock);

	/**
	 * Interact with blocks/entities/... until stopped (same as pressing 'right mouse').
	 */
	void setInteract();

	/**
	 * @param interact true to interact with blocks/entities/... (same as pressing 'left mouse'), false to stop interacting.
	 */
	void setInteract(boolean interact);

	/**
	 * Set given input down to the given state.
	 *
	 * @param type the {@link PlayerInputConfig.InputType}
	 * @param down whether or not the corresponding button is pressed down or not.
	 */
	void setInput(PlayerInputConfig.InputType type, boolean down);

	/**
	 * Stops all inputs.
	 */
	void stopAll();


}
