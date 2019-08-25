package stevebotOLD.rendering;

import net.minecraft.util.math.Vec3d;
import stevebotOLD.pathfinding.actions.Action;

public class Marker {


	public Vec3d pos;
	public Color color;
	public Action action;




	public Marker(Vec3d pos, Action action) {
		this(pos, action, null);
	}




	public Marker(Vec3d pos, Action action, Color color) {
		this.action = action;
		this.pos = pos;
		this.color = color;
	}

}
