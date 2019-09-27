package stevebot.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import stevebot.events.ModEventHandler;

public class PlayerController {


	private final Camera camera;
	private final PlayerInput input;

	private final Movement movement;
	private final PlayerUtils utils;




	public PlayerController(ModEventHandler eventHandler) {
		camera = new Camera(this, eventHandler);
		input = new PlayerInput(this, eventHandler);
		movement = new Movement(this);
		utils = new PlayerUtils(this);
	}




	/**
	 * @return the object controlling the camera of the player.
	 */
	public Camera camera() {
		return camera;
	}




	/**
	 * @return the object controlling the input of the player.
	 */
	public PlayerInput input() {
		return input;
	}




	/**
	 * @return the object controlling the movement of the player.
	 */
	public Movement movement() {
		return movement;
	}




	/**
	 * @return player-utilities
	 */
	public PlayerUtils utils() {
		return utils;
	}




	/**
	 * @return the player entity.
	 */
	public EntityPlayerSP getPlayer() {
		return Minecraft.getMinecraft().player;
	}


}
