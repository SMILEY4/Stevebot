package stevebot.player;

import com.ruegnerlukas.simplemath.MathUtils;
import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blocks.BlockUtils;

public class PlayerMovementImpl implements PlayerMovement {


	private final PlayerInput input;
	private final PlayerCamera camera;




	public PlayerMovementImpl(PlayerInput input, PlayerCamera camera) {
		this.input = input;
		this.camera = camera;
	}




	@Override
	public boolean moveTowards(BaseBlockPos pos, boolean ignoreY) {
		if (ignoreY) {
			return moveTowards(pos.getCenterX(), pos.getCenterZ());
		} else {
			return moveTowards(pos.getCenterX(), pos.getY(), pos.getCenterZ());
		}
	}




	@Override
	public boolean moveTowards(double x, double y, double z) {
		if (!PlayerUtils.isAtLocation(x, y, z)) {
			camera.setLookAt(BlockUtils.toBaseBlockPos(x, PlayerUtils.getPlayerPosition().y, z), true);
			input.setMoveForward();
			return false;
		} else {
			return true;
		}
	}




	@Override
	public boolean moveTowards(double x, double z) {
		if (PlayerUtils.isAtLocation(x, z)) {
			return true;
		} else {
			camera.setLookAt(BlockUtils.toBaseBlockPos(new Vector3d(x, PlayerUtils.getPlayerPosition().y, z)), true);
			input.setMoveForward();
			return false;
		}
	}




	@Override
	public boolean moveTowardsSpeed(double x, double y, double z, double maxHorSpeed) {
		if (!PlayerUtils.isAtLocation(x, y, z)) {
			final double currentSpeedSquared = PlayerUtils.getMotionVector().mul(1, 0, 1).length2();
			if (currentSpeedSquared < maxHorSpeed * maxHorSpeed) {
				camera.setLookAt(BlockUtils.toBaseBlockPos(new Vector3d(x, PlayerUtils.getPlayerPosition().y, z)), true);
				input.setMoveForward();
			}
			return false;
		} else {
			return true;
		}
	}




	@Override
	public boolean moveTowardsSpeed(double x, double z, double maxHorSpeed) {
		final double currentSpeedSquared = PlayerUtils.getMotionVector().mul(1, 0, 1).length2();
		if (currentSpeedSquared > maxHorSpeed * maxHorSpeed) {
			return false;
		} else {
			return moveTowards(x, z);
		}
	}




	@Override
	public boolean slowDown(double prefSpeed) {
		Vector3d motion = PlayerUtils.getMotionVector().mul(1, 0, 1);
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
			input.setMoveForward();

		} else if (MathUtils.inRange(angle, 22.5, 67.5)) {
			// f,r
			input.setMoveForward();
			input.setMoveRight();

		} else if (MathUtils.inRange(angle, -67.5, -22.5)) {
			// f,l
			input.setMoveForward();
			input.setMoveLeft();

		} else if (MathUtils.inRange(angle, 67, 112.5)) {
			// r
			input.setMoveRight();

		} else if (MathUtils.inRange(angle, -112.5, -67)) {
			// l
			input.setMoveLeft();


		} else if (MathUtils.inRange(angle, 112.5, 157.5)) {
			// b,r
			input.setMoveRight();
			input.setMoveBackward();

		} else if (MathUtils.inRange(angle, -157.5, -112.5)) {
			// b,l
			input.setMoveLeft();
			input.setMoveBackward();

		} else {
			// b
			input.setMoveBackward();
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
