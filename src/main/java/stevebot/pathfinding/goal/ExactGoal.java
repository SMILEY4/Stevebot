package stevebot.pathfinding.goal;

import net.minecraft.util.math.BlockPos;

public class ExactGoal extends Goal {


	public final BlockPos pos;




	public ExactGoal(BlockPos pos) {
		this.pos = pos;
	}




	@Override
	public boolean reached(BlockPos pos) {
		return this.pos.getX() == pos.getX() && this.pos.getY() == pos.getY() && this.pos.getZ() == pos.getZ();
	}




	@Override
	public double calcHCost(BlockPos pos) {
		return Math.sqrt(pos.distanceSq(this.pos));
	}




	@Override
	public String goalString() {
		return pos.getX() + " " + pos.getY() + " " + pos.getZ();
	}

}
