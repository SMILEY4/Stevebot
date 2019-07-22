package stevebot.pathfinding.actions;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;

public abstract class Action {


	// https://minecraft.gamepedia.com/Transportation

	public static final double COST_SNEAKING = 1.3;  // m/s
	public static final double COST_WALKING = 4.317;  // m/s
	public static final double COST_SPRINTING = 5.612;  // m/s

	public static final double COST_STEP_UP = 4.474; // m/s -> = WALK 1 Block + JUMP 1 Block = sqrt(4.317^2 + 1.176^2)

	public static final double COST_LADDER_UP = 2.35;  // m/s
	public static final double COST_LADDER_DOWN = 3.0;  // m/s

	private static final double[] VALUES_COST_FALL_N = calcCostFallN();




	public static double COST_FALL_N(int blocks) {
		return VALUES_COST_FALL_N[Math.max(0, Math.min(VALUES_COST_FALL_N.length - 1, blocks - 1))];
	}




	private static double[] calcCostFallN() {
		double[] costs = new double[255];
		for (int i = 0; i < costs.length; i++) {
			costs[i] = ticksToSeconds(distanceToTicks(i + 1));
		}
		return costs;
	}




	private static double ticksToSeconds(double ticks) {
		return ticks / 20.0;
	}




	private static double distanceToTicks(double distance) {
		int ticks = 0;
		double distTravelled = 0;
		while (distTravelled < distance) {
			double v = (Math.pow(0.98, ticks) - 1.0) * (-3.92);
			distTravelled += v;
			ticks++;
		}
		return ticks;
	}




	public static boolean isDiagonal(BlockPos from, BlockPos to) {
		final Vec3i dir = to.subtract(from);
		return Math.abs(dir.getX()) + Math.abs(dir.getZ()) == 2;
	}




	public abstract double getCost();

	public abstract Node getFrom();

	public abstract Node getTo();

	public abstract PathExecutor.State tick(boolean firstTick);

}
