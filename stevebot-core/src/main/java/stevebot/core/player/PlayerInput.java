package stevebot.core.player;

import stevebot.core.minecraft.MinecraftAdapter;

public class PlayerInput {

    private final MinecraftAdapter minecraftAdapter;
    private PlayerInputConfig inputConfig = null;
    private boolean muteUserInput = false;
    private boolean isHoldingJump = false;
    private boolean isHoldingSneak = false;


    public PlayerInput(final MinecraftAdapter minecraftAdapter) {
        this.minecraftAdapter = minecraftAdapter;
        reloadConfig();
    }


    public void onEventPlayerTick() {
        if (muteUserInput) {
            stopAll();
        }
    }


    public void onEventConfigChanged() {
        reloadConfig();
    }

    private void reloadConfig() {
        inputConfig = new PlayerInputConfig(minecraftAdapter);
    }

    /**
     * @param mute set to true to ignore any input not coming from this {@link PlayerInput}.
     */
    public void setMuteUserInput(boolean mute) {
        this.muteUserInput = mute;
    }


    /**
     * @return true, if any input not coming from this {@link PlayerInput} is ignored.
     */
    public boolean isUserMuted() {
        return this.muteUserInput;
    }


    /**
     * Move forward until stopped (same as pressing 'w')
     */
    public void setMoveForward() {
        setMoveForward(true);
    }


    /**
     * @param move true to move forward (same as pressing 'w'), false to stop moving forward.
     */
    public void setMoveForward(boolean move) {
        setInput(PlayerInputConfig.InputType.WALK_FORWARD, move);
    }


    /**
     * Move backward until stopped (same as pressing 's').
     */
    public void setMoveBackward() {
        setMoveBackward(true);
    }


    /**
     * @param move true to move backward (same as pressing 's'), false to stop moving backward.
     */
    public void setMoveBackward(boolean move) {
        setInput(PlayerInputConfig.InputType.WALK_BACKWARD, move);
    }


    /**
     * Move left until stopped (same as pressing 'a').
     */
    public void setMoveLeft() {
        setMoveLeft(true);
    }


    /**
     * @param move true to move left same as pressing 'a', false to stop moving backward.
     */
    public void setMoveLeft(boolean move) {
        setInput(PlayerInputConfig.InputType.WALK_LEFT, move);
    }


    /**
     * Move right until stopped (same as pressing 'd').
     */
    public void setMoveRight() {
        setMoveRight(true);
    }


    /**
     * @param move true to move right same as pressing 'd'), false to stop moving backward.
     */
    public void setMoveRight(boolean move) {
        setInput(PlayerInputConfig.InputType.WALK_RIGHT, move);
    }


    /**
     * Jump until stopped (same as pressing 'space').
     */
    public void setJump() {
        setJump(true, false);
    }


    /**
     * @param jump       true to jump same as pressing 'space'), false to stop jumping.
     * @param allowInAir whether or not to jump when the player is already in the air.
     */
    public void setJump(boolean jump, boolean allowInAir) {
        if (jump) {
            if (!PlayerUtils.hasPlayer()) {
                return;
            }
            if (PlayerUtils.isOnGround()) {
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


    /**
     * Start to hold down jump. Until {@link PlayerInput#releaseJump()} is called, the jump button will be held down.
     * {@link PlayerInput#stopAll()} will not reset affect the jump-button anymore
     */
    public void holdJump() {
        isHoldingJump = true;
        setJump(true, true);
    }


    /**
     * Stop holding down the jump button. The "jump" will end and {@link PlayerInput#stopAll()} has an effect again.
     */
    public void releaseJump() {
        isHoldingJump = false;
        setJump(false, false);
    }


    /**
     * @return whether the jump button is continuously held down.
     */
    public boolean isHoldingJump() {
        return isHoldingJump;
    }


    /**
     * Sprint until stopped.
     */
    public void setSprint() {
        setSprint(true);
    }


    /**
     * @param sprint true to sprint, false to stop sprinting.
     */
    public void setSprint(boolean sprint) {
        minecraftAdapter.setPlayerSprinting(sprint);
    }


    /**
     * Start to hold down sneak. Until {@link PlayerInput#releaseSneak()} is called, the sneak button will be held down.
     * {@link PlayerInput#stopAll()} will not reset affect the sneak-button anymore
     */
    public void holdSneak() {
        isHoldingSneak = true;
        setSneak(true);
    }


    /**
     * Stop holding down the sneak button. The player will stop sneaking {@link PlayerInput#stopAll()} has an effect again.
     */
    public void releaseSneak() {
        isHoldingSneak = false;
        setSneak(false);
    }


    /**
     * Sneak until stopped.
     */
    public void setSneak() {
        setSneak(true);
    }


    /**
     * @param sneak true to sneak, false to stop sneaking.
     */
    public void setSneak(boolean sneak) {
        setInput(PlayerInputConfig.InputType.SNEAK, sneak);
    }


    /**
     * Place blocks until stopped (same as pressing 'right mouse').
     */
    public void setPlaceBlock() {
        setPlaceBlock(true);
    }


    /**
     * @param placeBlock true to place blocks (same as pressing 'right mouse'), false to stop placing blocks.
     */
    public void setPlaceBlock(boolean placeBlock) {
        setInput(PlayerInputConfig.InputType.PLACE_BLOCK, placeBlock);
    }


    /**
     * Break blocks until stopped (same as pressing 'left mouse').
     */
    public void setBreakBlock() {
        setBreakBlock(true);
    }


    /**
     * @param breakBlock true to break blocks (same as pressing 'left mouse'), false to stop break blocks.
     */
    public void setBreakBlock(boolean breakBlock) {
        setInput(PlayerInputConfig.InputType.BREAK_BLOCK, breakBlock);
    }


    /**
     * Interact with blocks/entities/... until stopped (same as pressing 'right mouse').
     */
    public void setInteract() {
        setInteract(true);
    }


    /**
     * @param interact true to interact with blocks/entities/... (same as pressing 'left mouse'), false to stop interacting.
     */
    public void setInteract(boolean interact) {
        setInput(PlayerInputConfig.InputType.INTERACT, interact);
    }


    /**
     * Set given input down to the given state.
     *
     * @param type the {@link PlayerInputConfig.InputType}
     * @param down whether the corresponding button is pressed down or not.
     */
    public void setInput(PlayerInputConfig.InputType type, boolean down) {
        if (inputConfig.getBinding(type).isDown() == down) {
            return;
        }
        final int keyCode = inputConfig.getBinding(type).getKeyCode();
        minecraftAdapter.setInput(keyCode, down);
    }


    /**
     * Stops all inputs.
     */
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

}
