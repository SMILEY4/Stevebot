package stevebot.pathfinding.goal;

import net.minecraft.util.math.BlockPos;

public class XZGoal extends Goal {


	public final int x, z;




	public XZGoal(int x, int z) {
		this.x = x;
		this.z = z;
	}




	@Override
	public boolean reached(BlockPos pos) {
		return x == pos.getX() && z == pos.getZ();
	}




	@Override
	public double calcHCost(BlockPos pos) {
		final int dx = pos.getX() - x;
		final int dz = pos.getZ() - z;
		return Math.sqrt(dx * dx + dz * dz);
	}




	@Override
	public String goalString() {
		return x + " " + z;
	}

}
