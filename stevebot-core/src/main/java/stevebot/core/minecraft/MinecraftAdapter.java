package stevebot.core.minecraft;

import java.util.List;
import stevebot.core.data.blockpos.BaseBlockPos;
import stevebot.core.data.blocks.BlockWrapper;
import stevebot.core.data.items.wrapper.ItemBlockWrapper;
import stevebot.core.data.items.wrapper.ItemStackWrapper;
import stevebot.core.data.items.wrapper.ItemWrapper;
import stevebot.core.math.vectors.vec2.Vector2d;
import stevebot.core.math.vectors.vec3.Vector3d;
import stevebot.core.player.PlayerInputConfig;

public interface MinecraftAdapter {

    /**
     * @return whether a player-entity is currently loaded/available
     */
    boolean hasPlayer();

    /**
     * @return whether the player is currently in creative mode
     */
    boolean isPlayerCreativeMode();

    /**
     * @return the current position of the player head
     */
    Vector3d getPlayerHeadPosition();

    /**
     * @return the current position on the xz-plane of the player head
     */
    Vector2d getPlayerHeadPositionXZ();

    /**
     * @return the current player position as a {@link BaseBlockPos}
     */
    BaseBlockPos getPlayerBlockPosition();

    /**
     * @return the current player position
     */
    Vector3d getPlayerPosition();

    /**
     * @return the current placer motion vector
     */
    Vector3d getPlayerMotion();

    /**
     * @return the yaw-component of the player rotation (not camera rotation)
     */
    float getPlayerRotationYaw();

    /**
     * @return the pitch-component of the player rotation (not camera rotation)
     */
    float getPlayerRotationPitch();

    /**
     * Set the players rotation as pitch and yaw (different from camera rotation)
     *
     * @param yaw   the yaw value
     * @param pitch the pitch value
     */
    void setPlayerRotation(float yaw, float pitch);

    /**
     * Set the cameras rotation as pitch and yaw (different from player rotation)
     *
     * @param yaw   the yaw value
     * @param pitch the pitch value
     */
    void setCameraRotation(float yaw, float pitch);

    /**
     * @return the direction the player is looking in
     */
    Vector3d getLookDir();

    /**
     * @param interceptor set the {@link MouseChangeInterceptor} (to block or allow mouse movement)
     */
    void setMouseChangeInterceptor(MouseChangeInterceptor interceptor);

    /**
     * @return the configured mouse sensitivity
     */
    float getMouseSensitivity();

    /**
     * @return the amount of mouse movement on the x-axis since the last update
     */
    double getMouseDX();

    /**
     * @return the amount of mouse movement on the y-axis since the last update
     */
    double getMouseDY();

    /**
     * Set whether a given key/button is pressed down
     *
     * @param keyCode the code of the key/button to set
     * @param down    whether the button is pressed down
     */
    void setInput(final int keyCode, boolean down);

    /**
     * Set the sprinting-state of the player
     *
     * @param sprint whether the player is sprinting
     */
    void setPlayerSprinting(boolean sprint);

    /**
     * @return the configured {@link InputBinding}
     */
    InputBinding getKeyBinding(PlayerInputConfig.InputType inputType);

    /**
     * @return whether the player is on (solid) ground
     */
    boolean isPlayerOnGround();

    /**
     * @return the players current health
     */
    float getPlayerHealth();

    /**
     * Sends the player the given message. The message will be printed in the chat.
     *
     * @param msg the message.
     */
    void sendMessage(String msg);

    /**
     * @return a list of current items in the player hotbar
     */
    List<ItemStackWrapper> getHotbarItems();

    /**
     * Select the slot in the player hotbat
     *
     * @param slot the index of the slot [0,8]
     */
    void selectHotbarSlot(int slot);

    /**
     * @return a list of all registered blocks
     */
    List<BlockWrapper> getBlocks();

    /**
     * @return a list of all registered items
     */
    List<ItemWrapper> getItems();

    /**
     * @param pos the position of the block
     * @return the block at the given position
     */
    int getBlockId(BaseBlockPos pos);

    /**
     * @param chunkX the x position of the chunk
     * @param chunkZ the x position of the chunk
     * @return whether the chunk is currently loaded
     */
    boolean isChunkLoaded(int chunkX, int chunkZ);

    /**
     * @param block the block
     * @return the id of the block
     */
    int getItemIdFromBlock(BlockWrapper block);

    /**
     * @param item the block as an item
     * @return the id of the block
     */
    int getBlockIdFromItem(ItemBlockWrapper item);

    /**
     * @return the direction the block at the given position is facing -either "x", "y", or "z"
     */
    String getBlockFacing(BaseBlockPos position);

    /**
     * @param position the position of the door
     * @return whether the door is open or closed
     */
    boolean isDoorOpen(final BaseBlockPos position);

    /**
     * @param block the block to check
     * @param pos   the position of the block
     * @return whether the player can bass through the block
     */
    boolean isBlockPassable(BlockWrapper block, BaseBlockPos pos);

    /**
     * Calculate the duration it takes to break the given block using the given item
     *
     * @param item  the item to use (or null for using the hands)
     * @param block the block to break
     * @return the duration it takes to break the block
     */
    float getBreakDuration(ItemWrapper item, BlockWrapper block);

}
