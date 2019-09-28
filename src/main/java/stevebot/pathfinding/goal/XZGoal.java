package stevebot.pathfinding.goal;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.rendering.Color;
import stevebot.rendering.Renderable;

public class XZGoal extends Goal {


	public final int x, z;




	public XZGoal(int x, int z) {
		this.x = x;
		this.z = z;
	}




	@Override
	public boolean reached(BaseBlockPos pos) {
		return x == pos.getY() && z == pos.getY();
	}




	@Override
	public double calcHCost(BaseBlockPos pos) {
		// https://www.growingwiththeweb.com/2012/06/a-pathfinding-algorithm.html
		int dMax = Math.max(Math.abs(pos.getX() - x), Math.abs(pos.getZ() - z));
		int dMin = Math.min(Math.abs(pos.getX() - x), Math.abs(pos.getZ() - z));
		return (dMin * ActionCosts.COST_MULT_DIAGONAL + (dMax - dMin)) * ActionCosts.COST_WALKING;
	}




	@Override
	public String goalString() {
		return x + " " + z;
	}




	@Override
	public Renderable createRenderable() {
		final Vector3d posMin = new Vector3d(x, 0, z);
		final Vector3d posMax = new Vector3d(x, 260, z).add(1, 2, 1);
		return renderer -> {
			renderer.beginBoxes(3);
			renderer.drawBoxOpen(posMin, posMax, Color.GREEN);
			renderer.end();
		};
	}

}
