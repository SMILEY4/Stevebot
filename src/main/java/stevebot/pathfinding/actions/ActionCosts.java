package stevebot.pathfinding.actions;

public class ActionCosts {

	// https://minecraft.gamepedia.com/Transportation


	// ALL COSTS IN TICKS
	//    speed in m/s -> tick = (1/20)s -> cost = 20 / speed

	private static final double[] VALUES_COST_FALL_N = calcCostFallN();

	public static final double COST_SPRINTING = 20.0 / 5.612;
	public static final double COST_WALKING = 20.0 / 4.317;
	public static final double COST_STEP_DOWN = COST_WALKING;
	public static final double COST_STEP_UP = 20.0 / 4.474; // WALK 1 Block + JUMP 1 Block = sqrt(4.317^2 + 1.176^2)
	public static final double COST_LADDER_UP = 20.0 / 2.35;
	public static final double COST_LADDER_DOWN = 20.0 / 3.0;
	public static final double COST_SNEAKING = 20.0 / 1.3;

	public static final double COST_MULT_TOUCHING = 1.4; // COST_WALK * DIAGONAL * MULT_TOUCHING < STEP_UP * 2, otherwise player steps over block he would be touching, which is slower
	public static final double COST_MULT_DIAGONAL = 1.414;




	public static double COST_FALL_N(int blocks) {
		return VALUES_COST_FALL_N[Math.max(0, Math.min(VALUES_COST_FALL_N.length - 1, blocks - 1))];
	}




	private static double[] calcCostFallN() {
		double[] costs = new double[255];
		for (int i = 0; i < costs.length; i++) {
			costs[i] = distanceToTicks(i + 1);
		}
		return costs;
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


}
