package stevebotOLD.player;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class PlayerMovement {


	private final PlayerController controller;




	PlayerMovement(PlayerController controller) {
		this.controller = controller;
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
		if (!controller.utils.isAtLocation(x, y, z)) {
			final BlockPos targetBlockPos = new BlockPos(x, (int) controller.getPlayerPosition().y, z);
			controller.setLookAt(targetBlockPos);
			controller.setMoveForward();
			return false;
		} else {
			return true;
		}
	}




	public boolean moveTowards(double x, double z) {
		if (!controller.utils.isAtLocation(x, z)) {
			final BlockPos targetBlockPos = new BlockPos(x, (int) controller.getPlayerPosition().y, z);
			controller.setLookAt(targetBlockPos);
			controller.setMoveForward();
			return false;
		} else {
			return true;
		}
	}




	public boolean moveTowardsFreeLook(BlockPos pos, boolean ignoreY) {
		final double x = pos.getX() + 0.5;
		final double y = pos.getY();
		final double z = pos.getZ() + 0.5;
		if (ignoreY) {
			return moveTowardsFreeLook(x, z);
		} else {
			return moveTowardsFreeLook(x, y, z);
		}
	}




	public boolean moveTowardsFreeLook(double x, double y, double z) {
		if (!controller.utils.isAtLocation(x, y, z)) {
			moveTowardsFreeLook(x, z);
			return false;
		} else {
			return true;
		}
	}




	public boolean moveTowardsFreeLook(double x, double z) {
		if (!controller.utils.isAtLocation(x, z)) {
			Vec3d current = controller.getPlayerPosition();
			final Vec3d targetPos = new Vec3d(x, current.y, z);
			final Vec3d view2d = new Vec3d(controller.getLookDir().x, controller.getLookDir().z, 0).normalize();
			final Vec3d dir2d = new Vec3d(targetPos.x - current.x, targetPos.z - current.z, 0).normalize();
			final double angle = Math.toDegrees(angleRad(view2d.x, view2d.y, dir2d.x, dir2d.y));
			moveAngle(angle);
			return false;
		} else {
			return true;
		}
	}




	private void moveAngle(double angle) {

		if (inRange(angle, -22.5, 22.5)) {
			// f
			controller.setMoveForward();

		} else if (inRange(angle, 22.5, 67.5)) {
			// f,r
			controller.setMoveForward();
			controller.setMoveRight();

		} else if (inRange(angle, -67.5, -22.5)) {
			// f,l
			controller.setMoveForward();
			controller.setMoveLeft();

		} else if (inRange(angle, 67, 112.5)) {
			// r
			controller.setMoveRight();

		} else if (inRange(angle, -112.5, -67)) {
			// l
			controller.setMoveLeft();


		} else if (inRange(angle, 112.5, 157.5)) {
			// b,r
			controller.setMoveRight();
			controller.setMoveBackward();

		} else if (inRange(angle, -157.5, -112.5)) {
			// b,l
			controller.setMoveLeft();
			controller.setMoveBackward();

		} else {
			// b
			controller.setMoveBackward();
		}

	}




	private float angleRad(double x0, double y0, double x1, double y1) {
		final double cross = (x0 * y1) - (y0 * x1);
		final double dot = (x0 * x1) + (y0 * y1);
		return (float) Math.atan2(cross, dot);
	}




	private boolean inRange(double x, double a, double b) {
		return a <= x && x < b;
	}


}
