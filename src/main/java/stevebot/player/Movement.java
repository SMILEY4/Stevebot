package stevebot.player;

import com.ruegnerlukas.simplemath.MathUtils;
import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.util.math.BlockPos;

public class Movement {


	private final PlayerController controller;




	Movement(PlayerController controller) {
		this.controller = controller;
	}




	/**
	 * Moves the player one tick towards the given position
	 *
	 * @param pos     the target position
	 * @param ignoreY true to ignore the y-coordinate while checking if the player reached the target
	 * @return true, if the player reached the given target position
	 */
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




	/**
	 * Moves the player one tick towards the given position
	 *
	 * @param x the target x position
	 * @param y the target y position
	 * @param z the target z position
	 * @return true, if the player reached the given target position
	 */
	public boolean moveTowards(double x, double y, double z) {
		if (!controller.utils().isAtLocation(x, y, z)) {
			final BlockPos targetBlockPos = new BlockPos(x, (int) controller.utils().getPlayerPosition().y, z);
			controller.camera().setLookAt(targetBlockPos, true);
			controller.input().setMoveForward();
			return false;
		} else {
			return true;
		}
	}




	/**
	 * Moves the player one tick towards the given x and z coordinates
	 *
	 * @param x the target x position
	 * @param z the target z position
	 * @return true, if the player reached the given coordinates
	 */
	public boolean moveTowards(double x, double z) {
		if (!controller.utils().isAtLocation(x, z)) {
			final BlockPos targetBlockPos = new BlockPos(x, (int) controller.utils().getPlayerPosition().y, z);
			controller.camera().setLookAt(targetBlockPos, true);
			controller.input().setMoveForward();
			return false;
		} else {
			return true;
		}
	}




	/**
	 * Tries to slow down the player to the given target speed (ignores movement on the y-axis)
	 *
	 * @param prefSpeed the preferred target speed
	 * @return true, if the preferred speed was reached
	 */
	public boolean slowDown(double prefSpeed) {
		Vector3d motion = controller.utils().getMotionVector().mul(1, 0, 1);
		final double speed = motion.length();

		if (speed > prefSpeed) {
//			final double angle = Math.toDegrees(angleRad(view.x, view.y, -motion.x, -motion.y));
//			moveAngle(angle);
			return false;
		} else {
			return true;
		}
	}




	/**
	 * Tries to move the player in the given angle by walking forward,backward,left,right or any combination of these directions.
	 *
	 * @param angle the angle in degrees
	 */
	private void moveAngle(double angle) {

		if (MathUtils.inRange(angle, -22.5, 22.5)) {
			// f
			controller.input().setMoveForward();

		} else if (MathUtils.inRange(angle, 22.5, 67.5)) {
			// f,r
			controller.input().setMoveForward();
			controller.input().setMoveRight();

		} else if (MathUtils.inRange(angle, -67.5, -22.5)) {
			// f,l
			controller.input().setMoveForward();
			controller.input().setMoveLeft();

		} else if (MathUtils.inRange(angle, 67, 112.5)) {
			// r
			controller.input().setMoveRight();

		} else if (MathUtils.inRange(angle, -112.5, -67)) {
			// l
			controller.input().setMoveLeft();


		} else if (MathUtils.inRange(angle, 112.5, 157.5)) {
			// b,r
			controller.input().setMoveRight();
			controller.input().setMoveBackward();

		} else if (MathUtils.inRange(angle, -157.5, -112.5)) {
			// b,l
			controller.input().setMoveLeft();
			controller.input().setMoveBackward();

		} else {
			// b
			controller.input().setMoveBackward();
		}

	}




	/**
	 * @return the angle in radians between the two vectors (x0,y0) and (x1,y1)
	 */
	private float angleRad(double x0, double y0, double x1, double y1) {
		final double cross = (x0 * y1) - (y0 * x1);
		final double dot = (x0 * x1) + (y0 * y1);
		return (float) Math.atan2(cross, dot);
	}


}
