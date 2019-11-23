package stevebot.pathfinding.actions;

public class ActionCosts extends ActionCostConstants {


	private static final ActionCosts INSTANCE = new ActionCosts();




	public static ActionCosts get() {
		return INSTANCE;
	}




	private final double[] VALUES_COST_FALL_N = calcCostFallN();




	public double COST_FALL_N(int blocks) {
		return VALUES_COST_FALL_N[Math.max(0, Math.min(VALUES_COST_FALL_N.length - 1, blocks - 1))];
	}




	private double[] calcCostFallN() {
		double[] costs = new double[255];
		for (int i = 0; i < costs.length; i++) {
			costs[i] = distanceToTicks(i + 1);
		}
		return costs;
	}




	private double distanceToTicks(double distance) {
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
