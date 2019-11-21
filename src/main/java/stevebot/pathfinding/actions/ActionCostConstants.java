package stevebot.pathfinding.actions;

public abstract class ActionCostConstants {






	public final double COST_SPRINTING = 20.0 / 5.612; // 3.563
	public final double COST_WALKING = 20.0 / 4.317; // 4.632
	public final double COST_STEP_DOWN = COST_WALKING; // 4.632
	public final double COST_LADDER_DOWN = 20.0 / 3.0; // 6.666
	public final double COST_STEP_UP = 7.556;
	public final double COST_WALK_JUMP = COST_WALKING * 2 + 1; // = 9.264 + 1
	public final double COST_SPRINT_JUMP = COST_SPRINTING * 3 + 3;
	public final double COST_PILLAR_UP = 7.556;

	public final double COST_PLACE_BLOCK = 3;

	public final double COST_SWIM = 20 / 3.0; // 6.666
	public final double COST_ENTER_WATER = COST_WALKING * 0.5 + COST_SWIM * 0.5; // 4.815 * 2 = 7.315
	public final double COST_EXIST_WATER = COST_WALKING * 0.5 + COST_SWIM * 0.5;

	public final double COST_LADDER_UP = 20.0 / 2.35; // 8.510
	public final double COST_SNEAKING = 20.0 / 1.3; // 15.384

	public final double COST_MULT_TOUCHING = 1.6;
	public final double COST_MULT_DIAGONAL = 1.414;

	public final double COST_INFINITE = 99999999;


}
