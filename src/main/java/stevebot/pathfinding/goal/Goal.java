package stevebot.pathfinding.goal;

import net.minecraft.util.math.BlockPos;
import stevebot.rendering.Renderable;

public abstract class Goal {


	/**
	 * @param pos the position
	 * @return true, if the given position completes this goal
	 */
	public abstract boolean reached(BlockPos pos);

	/***
	 * @param pos the position
	 * @return the estimated cost from the given position to this goal
	 */
	public abstract double calcHCost(BlockPos pos);

	/**
	 * @return a readable string of this goal.
	 */
	public abstract String goalString();

	/**
	 * @return a {@link Renderable} representing this goal
	 */
	public abstract Renderable createRenderable();

}
