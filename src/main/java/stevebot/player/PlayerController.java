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




	public Camera camera() {
		return camera;
	}




	public PlayerInput input() {
		return input;
	}




	public Movement movement() {
		return movement;
	}




	public PlayerUtils utils() {
		return utils;
	}




	public EntityPlayerSP getPlayer() {
		return Minecraft.getMinecraft().player;
	}


}
