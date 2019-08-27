package stevebot.pathfinding.actions;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import stevebot.pathfinding.Node;

public abstract class Action {


	static boolean isDiagonal(BlockPos from, BlockPos to) {
		final Vec3i dir = to.subtract(from);
		return Math.abs(dir.getX()) + Math.abs(dir.getZ()) == 2;
	}




	public abstract double getCost();

	public abstract Node getFrom();

	public abstract Node getTo();


}
