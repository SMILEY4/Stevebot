package stevebot.pathfinding.goal;

import net.minecraft.util.math.BlockPos;
import stevebot.rendering.Renderable;

public abstract class Goal {


	public abstract boolean reached(BlockPos pos);

	public abstract double calcHCost(BlockPos pos);

	public abstract String goalString();

	public abstract Renderable createRenderable();

}
