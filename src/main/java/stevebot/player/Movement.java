package stevebot.player;

import com.ruegnerlukas.simplemath.MathUtils;
import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.util.math.BlockPos;

public class Movement {


	private final MTPlayerController PLAYER_CONTROLLER;




	public Movement(MTPlayerController PLAYER_CONTROLLER) {
		this.PLAYER_CONTROLLER = PLAYER_CONTROLLER;
	}




	public boolean moveTowards(BlockPos pos, boolean ignoreY) {
		final double x = pos.getX() + 0.5;
		final double y = pos.getY();
		final double z = pos.getZ() + 0.5;
		if (ignoreY) {
			return moveTowards(x, z);
		} else {
			return moveTowards(x, y, z);
		}
	}




	public boolean moveTowards(double x, double y, double z) {
		if (!PLAYER_CONTROLLER.getUtils().isAtLocation(x, y, z)) {
			final BlockPos targetBlockPos = new BlockPos(x, (int) PLAYER_CONTROLLER.getPlayerPosition().y, z);
			PLAYER_CONTROLLER.getCamera().setLookAt(targetBlockPos, true);
			PLAYER_CONTROLLER.setMoveForward();
			return false;
		} else {
			return true;
		}
	}




	public boolean moveTowards(double x, double z) {
		if (!PLAYER_CONTROLLER.getUtils().isAtLocation(x, z)) {
			final BlockPos targetBlockPos = new BlockPos(x, (int) PLAYER_CONTROLLER.getPlayerPosition().y, z);
			PLAYER_CONTROLLER.getCamera().setLookAt(targetBlockPos, true);
			PLAYER_CONTROLLER.setMoveForward();
			return false;
		} else {
			return true;
		}
	}




	public boolean slowDown(double prefSpeed) {
		Vector3d motion = PLAYER_CONTROLLER.getMotionVector().mul(1, 0, 1);
		final double speed = motion.length();

		if (speed > prefSpeed) {
//			final double angle = Math.toDegrees(angleRad(view.x, view.y, -motion.x, -motion.y));
//			moveAngle(angle);
			return false;
		} else {
			return true;
		}
	}




	private void moveAngle(double angle) {

		if (MathUtils.inRange(angle, -22.5, 22.5)) {
			// f
			PLAYER_CONTROLLER.setMoveForward();

		} else if (MathUtils.inRange(angle, 22.5, 67.5)) {
			// f,r
			PLAYER_CONTROLLER.setMoveForward();
			PLAYER_CONTROLLER.setMoveRight();

		} else if (MathUtils.inRange(angle, -67.5, -22.5)) {
			// f,l
			PLAYER_CONTROLLER.setMoveForward();
			PLAYER_CONTROLLER.setMoveLeft();

		} else if (MathUtils.inRange(angle, 67, 112.5)) {
			// r
			PLAYER_CONTROLLER.setMoveRight();

		} else if (MathUtils.inRange(angle, -112.5, -67)) {
			// l
			PLAYER_CONTROLLER.setMoveLeft();


		} else if (MathUtils.inRange(angle, 112.5, 157.5)) {
			// b,r
			PLAYER_CONTROLLER.setMoveRight();
			PLAYER_CONTROLLER.setMoveBackward();

		} else if (MathUtils.inRange(angle, -157.5, -112.5)) {
			// b,l
			PLAYER_CONTROLLER.setMoveLeft();
			PLAYER_CONTROLLER.setMoveBackward();

		} else {
			// b
			PLAYER_CONTROLLER.setMoveBackward();
		}

	}




	private float angleRad(double x0, double y0, double x1, double y1) {
		final double cross = (x0 * y1) - (y0 * x1);
		final double dot = (x0 * x1) + (y0 * y1);
		return (float) Math.atan2(cross, dot);
	}


}
