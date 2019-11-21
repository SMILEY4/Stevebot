package stevebot.pathfinding.goal;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.rendering.Color;
import stevebot.rendering.Renderable;

public class ExactGoal extends Goal {


	public final BaseBlockPos pos;




	public ExactGoal(BaseBlockPos pos) {
		this.pos = pos;
	}




	@Override
	public boolean reached(BaseBlockPos pos) {
		return this.pos.getX() == pos.getX() && this.pos.getY() == pos.getY() && this.pos.getZ() == pos.getZ();
	}




	@Override
	public double calcHCost(BaseBlockPos pos) {
		final int gx = this.pos.getX();
		final int gy = this.pos.getY();
		final int gz = this.pos.getZ();
		final int px = pos.getX();
		final int py = pos.getY();
		final int pz = pos.getZ();
		int dMax = Math.max(Math.abs(px - gx), Math.abs(pz - gz));
		int dMin = Math.min(Math.abs(px - gx), Math.abs(pz - gz));
		return (dMin * ActionCosts.get().COST_MULT_DIAGONAL + (dMax - dMin)) * ActionCosts.get().COST_WALKING + Math.abs(py - gy) * (py < gy ? ActionCosts.get().COST_STEP_DOWN : ActionCosts.get().COST_STEP_UP);
	}




	@Override
	public String goalString() {
		return pos.getX() + " " + pos.getY() + " " + pos.getZ();
	}




	@Override
	public Renderable createRenderable() {
		final Vector3d posMin = new Vector3d(pos.getX(), pos.getY(), pos.getZ());
		final Vector3d posMax = new Vector3d(pos.getX(), pos.getY(), pos.getZ()).add(1, 2, 1);
		return renderer -> {
			renderer.beginBoxes(3);
			renderer.drawBoxOpen(posMin, posMax, Color.GREEN);
			renderer.end();
		};
	}

}
