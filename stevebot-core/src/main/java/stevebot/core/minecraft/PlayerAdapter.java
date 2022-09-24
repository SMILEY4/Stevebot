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

public interface PlayerAdapter {

    /**
     * @return the current position of the player head
     */
    Vector3d getPlayerHeadPosition();

    Vector2d getPlayerHeadPositionXZ();

    BaseBlockPos getPlayerBlockPosition();

    Vector3d getPlayerPosition();

    Vector3d getPlayerMotion();

    void setPlayerRotation(float yaw, float pitch);

    void setCameraRotation(float yaw, float pitch);

    void addCameraRotation(float yaw, float pitch);

    Vector3d getLookDir();

    void setMouseChangeInterceptor(MouseChangeInterceptor interceptor);

    float getMouseSensitivity();

    boolean hasPlayer();

    float getPlayerRotationYaw();

    float getPlayerRotationPitch();

    double getMouseDX();

    double getMouseDY();

    void setInput(final int keyCode, boolean down);

    /**
     * Sends the player the given message. The message will be printed in the chat.
     *
     * @param msg the message.
     */
    void sendMessage(String msg);

    List<ItemStackWrapper> getHotbarItems();

    void selectHotbarSlot(int slot);

    boolean isPlayerOnGround();

    float getPlayerHealth();

    void setPlayerSprinting(boolean sprint);

    InputBinding getKeyBinding(PlayerInputConfig.InputType inputType);

    boolean isPlayerCreativeMode();

}
