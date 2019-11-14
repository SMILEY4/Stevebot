package stevebot.pathfinding.actions;

public class ActionCosts {


	// https://minecraft.gamepedia.com/Transportation


	// ALL COSTS IN TICKS
	//    speed in m/s -> tick = (1/20)s -> cost = 20 / speed

	private static final double[] VALUES_COST_FALL_N = calcCostFallN();

	public static final double COST_SPRINTING = 20.0 / 5.612; // 3.563
	public static final double COST_WALKING = 20.0 / 4.317; // 4.632
	public static final double COST_STEP_DOWN = COST_WALKING; // 4.632
	public static final double COST_LADDER_DOWN = 20.0 / 3.0; // 6.666
	public static final double COST_STEP_UP = COST_WALKING * 0.5 + COST_FALL_N(1); // 7.556
	public static final double COST_WALK_JUMP = COST_WALKING * 2 + 1; // = 9.264 + 1
	public static final double COST_SPRINT_JUMP = COST_SPRINTING * 3 + 3;
	public static final double COST_PILLAR_UP = COST_FALL_N(1) + 10;

	public static final double COST_PLACE_BLOCK = 3;

	public static final double COST_SWIM = 20 / 3.0; // 6.666
	public static final double COST_ENTER_WATER = COST_WALKING * 0.5 + COST_SWIM * 0.5; // 4.815 * 2 = 7.315
	public static final double COST_EXIST_WATER = COST_WALKING * 0.5 + COST_SWIM * 0.5;

	public static final double COST_LADDER_UP = 20.0 / 2.35; // 8.510
	public static final double COST_SNEAKING = 20.0 / 1.3; // 15.384

	public static final double COST_MULT_TOUCHING = 1.6;
	public static final double COST_MULT_DIAGONAL = 1.414;

	public static final double COST_INFINITE = 99999999;




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
		// travelled_dist >= distance -> overshot target -> get percentage of real ticks travelled
		double p = distance / distTravelled;
		return ticks * p;
	}


}
