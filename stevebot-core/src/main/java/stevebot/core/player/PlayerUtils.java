package stevebot.core.player;

import stevebot.core.data.blockpos.BaseBlockPos;
import stevebot.core.data.items.wrapper.ItemStackWrapper;
import stevebot.core.data.player.PlayerSnapshot;
import stevebot.core.math.vectors.vec3.Vector3d;
import stevebot.core.minecraft.MinecraftAdapter;

public class PlayerUtils {


    // player-radius p = 0.3; half block size = 0.5; wanted radius around center x = ?
    // player is at/near the center of the block when
    // =>    p + x < 0.5
    // 	   0.3 + x < 0.5
    //           x < 0.2  =>  0.2*0.2 = 0.04
    public static final double AT_LOC_DIST_ERROR = 0.04;

    private static PlayerInput playerInput;
    private static PlayerCamera playerCamera;
    private static PlayerMovement playerMovement;
    private static PlayerInventory playerInventory;

    private static PlayerSnapshot activeSnapshot;

    private static MinecraftAdapter minecraftAdapter;

    public static void initialize(
            MinecraftAdapter minecraftAdapter,
            PlayerInput playerInput,
            PlayerCamera playerCamera,
            PlayerMovement playerMovement,
            PlayerInventory playerInventory
    ) {
        PlayerUtils.minecraftAdapter = minecraftAdapter;
        PlayerUtils.playerInput = playerInput;
        PlayerUtils.playerCamera = playerCamera;
        PlayerUtils.playerMovement = playerMovement;
        PlayerUtils.playerInventory = playerInventory;
    }

    public static boolean hasPlayer() {
        return minecraftAdapter.hasPlayer();
    }

    /**
     * @return the {@link PlayerInput}-singleton
     */
    public static PlayerInput getInput() {
        return playerInput;
    }


    /**
     * @return the {@link PlayerCamera}-singleton
     */
    public static PlayerCamera getCamera() {
        return playerCamera;
    }


    /**
     * @return the {@link PlayerMovement}-singleton
     */
    public static PlayerMovement getMovement() {
        return playerMovement;
    }


    /**
     * @return the {@link PlayerInventory}-singleton
     */
    public static PlayerInventory getInventory() {
        return playerInventory;
    }


    /**
     * @return a new {@link PlayerSnapshot} of the current state of the player
     */
    public static PlayerSnapshot createSnapshot() {
        final PlayerSnapshot snapshot = new PlayerSnapshot();
        for (ItemStackWrapper itemStackWrapper : minecraftAdapter.getHotbarItems()) {
            snapshot.setHotbarItemStack(itemStackWrapper);
        }
        return snapshot;
    }


    /**
     * Sets the active snapshot to the given {@link PlayerSnapshot}
     *
     * @param snapshot the new active snapshot
     */
    public static void setActiveSnapshot(PlayerSnapshot snapshot) {
        PlayerUtils.activeSnapshot = snapshot;
    }


    /**
     * @return the currently active {@link PlayerSnapshot}
     */
    public static PlayerSnapshot getActiveSnapshot() {
        return PlayerUtils.activeSnapshot;
    }


    /**
     * Sends the player the given message. The message will be printed in the chat.
     *
     * @param msg the message.
     */
    public static void sendMessage(String msg) {
        minecraftAdapter.sendMessage(msg);
    }


    /**
     * get the current amount of player-health
     * @return the current health
     */
    public static float getHealth() {
        return minecraftAdapter.getPlayerHealth();
    }


    /**
     * Check if the player is standing on solid ground
     *
     * @return whether the player is on solid ground
     */
    public static boolean isOnGround() {
        return minecraftAdapter.isPlayerOnGround();
    }


    /**
     * @return the current position of the player as a {@link BaseBlockPos}
     */
    public static BaseBlockPos getPlayerBlockPos() {
        return minecraftAdapter.getPlayerBlockPosition();
    }


    /**
     * @return the exact current position of the player as a {@link Vector3d}
     */
    public static Vector3d getPlayerPosition() {
        return minecraftAdapter.getPlayerPosition();
    }


    /**
     * @return the exact current position of the player in the block as a {@link Vector3d}
     */
    public static Vector3d getPlayerPositionInBlock() {
        final Vector3d playerPos = getPlayerPosition();
        if (playerPos != null) {
            playerPos.x = playerPos.x - Math.floor(playerPos.x);
            playerPos.y = playerPos.y - Math.floor(playerPos.y);
            playerPos.z = playerPos.z - Math.floor(playerPos.z);
            return playerPos;
        } else {
            return null;
        }
    }


    /**
     * @return the current movement of the player.
     */
    public static Vector3d getMotionVector() {
        return minecraftAdapter.getPlayerMotion();
    }


    /**
     * @return true, if the player is moving on any axis.
     */
    public static boolean isPlayerMoving() {
        return isPlayerMoving(false);
    }


    /**
     * @param includeY true to include movement on the y-axis, false to ignore movement on the y-axis.
     * @return true, if the player is moving on any axis.
     */
    public static boolean isPlayerMoving(boolean includeY) {
        return isPlayerMoving(0.0001, includeY);
    }


    /**
     * @param threshold the threshold for the movement-speed
     * @return true, if the player is moving on any axis.
     */
    public static boolean isPlayerMoving(double threshold) {
        return isPlayerMoving(threshold, false);
    }


    /**
     * @param threshold the threshold for the movement-speed
     * @param includeY  true to include movement on the y-axis, false to ignore movement on the y-axis.
     * @return true, if the player is moving on any axis.
     */
    public static boolean isPlayerMoving(double threshold, boolean includeY) {
        final Vector3d motion = getMotionVector();
        if (motion != null) {
            if (includeY) {
                return motion.x > threshold || motion.y > threshold || motion.z > threshold;
            } else {
                return motion.x > threshold || motion.z > threshold;
            }
        } else {
            return false;
        }
    }


    /**
     * @param pos     the target position as a {@link BaseBlockPos}
     * @param ignoreY set to true to only check the x- and z-coordinates.
     * @return true, if the player is currently at the given location (within a threshold defined by {@code PlayerUtils.AT_LOC_DIST_ERROR}).
     */
    public static boolean isAtLocation(BaseBlockPos pos, boolean ignoreY) {
        final double x = pos.getX() + 0.5;
        final double y = pos.getY();
        final double z = pos.getZ() + 0.5;
        if (ignoreY) {
            return isAtLocation(x, z);
        } else {
            return isAtLocation(x, y, z);
        }
    }


    /**
     * @param x the target x-coordinate
     * @param z the target z-coordinate
     * @return true, if the player is currently at the given coordinates (within a threshold defined by {@code PlayerUtils.AT_LOC_DIST_ERROR}).
     */
    public static boolean isAtLocation(double x, double z) {
        return isAtLocationThreshold(x, z, AT_LOC_DIST_ERROR);
    }


    /**
     * @param x         the target x-coordinate
     * @param z         the target z-coordinate
     * @param threshold the threshold
     * @return true, if the player is currently at the given coordinates (within the given threshold).
     */
    public static boolean isAtLocationThreshold(double x, double z, double threshold) {
        final Vector3d current = getPlayerPosition();
        return !(current.dist2(x, current.y, z) > threshold);
    }


    /**
     * @param x the target x-coordinate
     * @param y the target y-coordinate
     * @param z the target z-coordinate
     * @return true, if the player is currently at the given coordinates (within a threshold defined by {@code PlayerUtils.AT_LOC_DIST_ERROR}).
     */
    public static boolean isAtLocation(double x, double y, double z) {
        final Vector3d current = getPlayerPosition();
        return !(current.dist2(x, y, z) > AT_LOC_DIST_ERROR);
    }

}
