package stevebot.pathfinding.goal;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.player.PlayerUtils;
import stevebot.rendering.Color;
import stevebot.rendering.Renderable;

public class YGoal extends Goal {


	public final int y;




	public YGoal(int y) {
		this.y = Math.max(0, Math.min(y, 256));
	}




	@Override
	public boolean reached(BaseBlockPos pos) {
		return y == pos.getY();
	}




	@Override
	public double calcHCost(BaseBlockPos pos) {
		final int dist = Math.abs(pos.getY() - y);
		return dist * ActionCosts.get().WALK_SPRINT_STRAIGHT;
	}




	@Override
	public String goalString() {
		return String.valueOf(y);
	}




	@Override
	public Renderable createRenderable() {
		final Vector3d posMin = new Vector3d();
		final Vector3d posMax = new Vector3d();
		return renderer -> {
			final BaseBlockPos posPlayer = PlayerUtils.getPlayerBlockPos();
			if (posPlayer == null) {
				return;
			}
			posMin.set(posPlayer.getX(), y, posPlayer.getZ()).add(10, 1, 10);
			posMax.set(posPlayer.getX(), y, posPlayer.getZ()).sub(10, 0, 10);
			renderer.beginBoxes(3);
			renderer.drawBoxOpen(posMin, posMax, Color.GREEN);
			renderer.end();
		};
	}

}
