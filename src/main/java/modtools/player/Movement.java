package modtools.player;

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


}
