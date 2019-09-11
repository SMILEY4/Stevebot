package stevebot.pathfinding.goal;

import net.minecraft.util.math.BlockPos;
import stevebot.pathfinding.actions.ActionCosts;

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
		final int gx = this.pos.getX();
		final int gy = this.pos.getY();
		final int gz = this.pos.getZ();
		final int px = pos.getX();
		final int py = pos.getY();
		final int pz = pos.getZ();
		int dMax = Math.max(Math.abs(px - gx), Math.abs(pz - gz));
		int dMin = Math.min(Math.abs(px - gx), Math.abs(pz - gz));
		return (dMin * ActionCosts.COST_MULT_DIAGONAL + (dMax - dMin)) * ActionCosts.COST_WALKING + Math.abs(py - gy) * (py < gy ? ActionCosts.COST_STEP_DOWN : ActionCosts.COST_STEP_UP);
	}




	@Override
	public String goalString() {
		return pos.getX() + " " + pos.getY() + " " + pos.getZ();
	}

}
