package stevebot;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import stevebot.player.PlayerController;
import stevebot.utils.GameEventListener;

import java.util.Random;

public class MovementTest implements GameEventListener {


	public static boolean running = false;

	private Random random = new Random();

	private static MovementInputFromOptions input;




	public MovementTest() {
		Stevebot.EVENT_HANDLER.addListener(this);
		input = new MovementInputFromOptions(Minecraft.getMinecraft().gameSettings);
	}




	BlockPos target = null;


	long nextSwitch = 0;




	@Override
	public void onClientTick(TickEvent.ClientTickEvent event) {


		PlayerController controller = Stevebot.PLAYER_CONTROLLER;
		if (controller.getPlayer() != null) {
//			Stevebot.LOGGER.info(controller.getPlayer().motionX + " " + controller.getPlayer().motionZ);
		}

//		if(!running) {
//			resetInput();
//			return;
//		}
//
//		PlayerController controller = Stevebot.PLAYER_CONTROLLER;
////		controller.stopAll();
//
//		boolean done = false;
//		if (target != null && controller.getPlayer() != null) {
////			done = controller.movement.moveTowardsFreeLook(target.getX(), target.getZ());
//
//			controller.getPlayer().movementInput = input;
//
//			resetInput();
//			moveTowardsCoords(target.getX(), target.getY(), target.getZ());
//
//			Stevebot.RENDERER.addObject(controller.getPlayerPosition());
//
//
//			if (controller.movement.isAtLocation(target.getX(), target.getZ())) {
//				done = true;
//			}
//
//		}
//
//		if (done || target == null || nextSwitch < System.currentTimeMillis() || new BlockPos(target.getX(), 0, target.getZ()).equals(new BlockPos(controller.getPlayerBlockPos().getX(), 0, controller.getPlayerBlockPos().getZ()))) {
//			nextSwitch = System.currentTimeMillis() + (random.nextInt(5) + 17) * 1000;
//			Stevebot.RENDERER.removeObject(target);
//			target = getNext(controller.getPlayerBlockPos());
//			Stevebot.RENDERER.addObject(target);
//			Stevebot.LOGGER.info("Switch target to " + target);
//
//			if(target != null) {
//				Stevebot.RENDERER.addObject(new ActionWalk(Node.create(controller.getPlayerBlockPos(), null), target));
//			}
//
//
//		}
	}




	private BlockPos getNext(BlockPos current) {
		if (current == null) {
			return null;
		}
		final int dx = random.nextInt(20) - 10;
		final int dz = random.nextInt(20) - 10;
		return current.add(dx, 0, dz);
	}




	private static boolean forward, backward, left, right = false;




	public static void resetInput() {
		forward = backward = left = right = false;
	}




	public static boolean moveTowardsCoords(double x, double z) {
		EntityPlayerSP thePlayer = Minecraft.getMinecraft().player;
		float currentYaw = thePlayer.rotationYaw;
		float yaw = (float) (Math.atan2(thePlayer.posX - x, -thePlayer.posZ + z) * 180 / Math.PI);
		float diff = yaw - currentYaw;
		if (diff < 0) {
			diff += 360;
		}
		float distanceToForward = Math.min(Math.abs(diff - 0), Math.abs(diff - 360)) % 360;
		float distanceToForwardRight = Math.abs(diff - 45) % 360;
		float distanceToRight = Math.abs(diff - 90) % 360;
		float distanceToBackwardRight = Math.abs(diff - 135) % 360;
		float distanceToBackward = Math.abs(diff - 180) % 360;
		float distanceToBackwardLeft = Math.abs(diff - 225) % 360;
		float distanceToLeft = Math.abs(diff - 270) % 360;
		float distanceToForwardLeft = Math.abs(diff - 315) % 360;
		float tmp = Math.round(diff / 45) * 45;
		if (tmp > 359) {
			tmp -= 360;
		}
		double t = 23;


		if (distanceToForward < t || distanceToForward > 360 - t) {
			forward = true;
			return true;
		}
		if (distanceToForwardLeft < t || distanceToForwardLeft > 360 - t) {
			forward = true;
			left = true;
			return true;
		}
		if (distanceToForwardRight < t || distanceToForwardRight > 360 - t) {
			forward = true;
			right = true;
			return true;
		}
		if (distanceToBackward < t || distanceToBackward > 360 - t) {
			backward = true;
			return true;
		}
		if (distanceToBackwardLeft < t || distanceToBackwardLeft > 360 - t) {
			backward = true;
			left = true;
			return true;
		}
		if (distanceToBackwardRight < t || distanceToBackwardRight > 360 - t) {
			backward = true;
			right = true;
			return true;
		}
		if (distanceToLeft < t || distanceToLeft > 360 - t) {
			left = true;
			return true;
		}
		if (distanceToRight < t || distanceToRight > 360 - t) {
			right = true;
			return true;
		}
		return false;
	}




	class MovementInputFromOptions extends MovementInput {


		private final GameSettings gameSettings;




		public MovementInputFromOptions(GameSettings gameSettingsIn) {
			this.gameSettings = gameSettingsIn;
		}




		public void updatePlayerMoveState() {
			this.moveStrafe = 0.0F;
			this.moveForward = 0.0F;
			if (this.gameSettings.keyBindForward.isKeyDown() || forward) {
				this.moveForward = 0.4f;
			}
			if (this.gameSettings.keyBindBack.isKeyDown() || backward) {
				this.moveForward = -0.4f;
			}
			if (this.gameSettings.keyBindLeft.isKeyDown() || left) {
				this.moveStrafe = 0.4f;
			}
			if (this.gameSettings.keyBindRight.isKeyDown() || right) {
				this.moveStrafe = -0.4f;
			}
			this.jump = this.gameSettings.keyBindJump.isKeyDown();// || jumping;
			this.sneak = this.gameSettings.keyBindSneak.isKeyDown();// || sneak;
			if (this.sneak) {
				this.moveStrafe = (float) ((double) this.moveStrafe * 0.3D);
				this.moveForward = (float) ((double) this.moveForward * 0.3D);
			}
		}

	}

}
