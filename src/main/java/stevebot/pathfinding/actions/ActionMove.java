package stevebot.pathfinding.actions;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.Utils;

public class ActionMove implements IAction {


	public static boolean isValid(BlockPos from, BlockPos to) {

		// check if player fits in destination
		final BlockPos d0 = to.add(0, -1, 0);
		final BlockPos d1 = to;
		final BlockPos d2 = to.add(0, 1, 0);

		if (Utils.canWalkOn(d0) && Utils.canWalkThrough(d1) && Utils.canWalkThrough(d2)) { // does not need block below -> next action must be "ActionFall"

			final Vec3i dir = to.subtract(from);
			if (Math.abs(dir.getX()) + Math.abs(dir.getZ()) == 2) {

				// check if player can reach destination (if diagonal)
				final BlockPos a1 = from.add(dir.getX(), 0, 0);
				final BlockPos a2 = from.add(dir.getX(), 1, 0);
				final BlockPos b1 = from.add(0, 0, dir.getZ());
				final BlockPos b2 = from.add(0, 1, dir.getZ());

				final boolean aBlocked = !(Utils.canWalkThrough(a1) && Utils.canWalkThrough(a2));
				final boolean bBlocked = !(Utils.canWalkThrough(b1) && Utils.canWalkThrough(b2));

				if (aBlocked && bBlocked) {
					return false;
				} else {
					return true;
				}

			} else {
				return true;
			}


		} else {
			return false;
		}
	}




	private final Node from;
	private final Node to;
	private final double cost;




	public ActionMove(Node origin, int dx, int dz) {
		this.from = origin;
		this.to = Node.get(origin.pos.add(dx, 0, dz));
		this.cost = 1;
	}




	@Override
	public double getCost() {
		return this.cost;
	}




	@Override
	public Node getFrom() {
		return this.from;
	}




	@Override
	public Node getTo() {
		return this.to;
	}

}
