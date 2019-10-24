package stevebot.player;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blocks.BlockUtils;
import stevebot.data.items.ItemUtils;
import stevebot.data.player.PlayerSnapshot;
import stevebot.minecraft.MinecraftAdapter;

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




	public static void initialize(PlayerInput playerInput, PlayerCamera playerCamera, PlayerMovement playerMovement, PlayerInventory playerInventory) {
		PlayerUtils.playerInput = playerInput;
		PlayerUtils.playerCamera = playerCamera;
		PlayerUtils.playerMovement = playerMovement;
		PlayerUtils.playerInventory = playerInventory;
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
		final InventoryPlayer inventory = PlayerUtils.getPlayer().inventory;
		final PlayerSnapshot snapshot = new PlayerSnapshot();
		for (int i = 0; i < 9; i++) {
			final ItemStack item = inventory.getStackInSlot(i);
			if (item != ItemStack.EMPTY) {
				snapshot.setHotbarItemStack(i, ItemUtils.getItemLibrary().getItemByMCItem(item.getItem()), item.getCount());
			}
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
	 * @return the {@link EntityPlayerSP}
	 */
	public static EntityPlayerSP getPlayer() {
		return MinecraftAdapter.get().getPlayer();
	}




	/**
	 * Sends the player the given message. The message will be printed in the chat.
	 *
	 * @param msg the message.
	 */
	public static void sendMessage(String msg) {
		if (getPlayer() != null) {
			getPlayer().sendMessage(new TextComponentString(msg));
		}
	}




	/**
	 * @return the current position of the player as a {@link BaseBlockPos}
	 */
	public static BaseBlockPos getPlayerBlockPos() {
		EntityPlayerSP player = getPlayer();
		if (player != null) {
			return BlockUtils.toBaseBlockPos(player.posX, player.posY, player.posZ);
		} else {
			return null;
		}
	}




	/**
	 * @return the exact current position of the player as a {@link Vector3d}
	 */
	public static Vector3d getPlayerPosition() {
		EntityPlayerSP player = getPlayer();
		if (player != null) {
			return new Vector3d(player.posX, player.posY, player.posZ);
		} else {
			return null;
		}
	}




	/**
	 * @return the exact current position of the player in the block as a {@link Vector3d}
	 */
	public static Vector3d getPlayerPositionInBlock() {
		EntityPlayerSP player = getPlayer();
		if (player != null) {
			final Vector3d playerPos = getPlayerPosition();
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
		EntityPlayerSP player = getPlayer();
		if (player != null) {
			return new Vector3d(player.motionX, player.motionY, player.motionZ);
		} else {
			return null;
		}
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
		if (current.dist2(x, current.y, z) > threshold) {
			return false;
		} else {
			return true;
		}
	}




	/**
	 * @param x the target x-coordinate
	 * @param y the target y-coordinate
	 * @param z the target z-coordinate
	 * @return true, if the player is currently at the given coordinates (within a threshold defined by {@code PlayerUtils.AT_LOC_DIST_ERROR}).
	 */
	public static boolean isAtLocation(double x, double y, double z) {
		final Vector3d current = getPlayerPosition();
		if (current.dist2(x, y, z) > AT_LOC_DIST_ERROR) {
			return false;
		} else {
			return true;
		}
	}


}
