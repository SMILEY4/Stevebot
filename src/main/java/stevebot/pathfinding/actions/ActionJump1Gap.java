package stevebot.pathfinding.actions;

import modtools.player.MTPlayerController;
import net.minecraft.util.math.BlockPos;
import stevebot.Stevebot;
import stevebot.pathfinding.BlockUtils;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;

public class ActionJump1Gap extends Action {


	private final Node from;
	private final Node to;
	private final double cost;




	public ActionJump1Gap(Node from, int dx, int dz) {
		this(from, from.pos.add(dx, 0, dz));
	}




	public ActionJump1Gap(Node from, BlockPos to) {
		this.from = from;
		this.to = Node.get(to);
		this.cost = ActionCosts.COST_JUMP_GAP_1;
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




	int state = 0;
	long time = 0;




	@SuppressWarnings ("Duplicates")
	@Override
	public PathExecutor.State tick(boolean firstTick) {

		MTPlayerController controller = Stevebot.get().getPlayerController();


		if (state == 0) {
			controller.getCamera().setLookAt(to.pos, true);
			time = System.currentTimeMillis();
			state = 1;

		} else if (state == 1) {
			if (System.currentTimeMillis() - time >= 200) {
				state = 2;
			}
			return PathExecutor.State.EXEC;

		} else if (state == 2) {
			if (!controller.getMovement().moveTowards(to.pos, true)) {
				if (controller.getPlayerBlockPos().equals(from.pos)) {
					controller.setJump();
				}
				return PathExecutor.State.EXEC;

			} else {
				return PathExecutor.State.DONE;
			}
		}

		return PathExecutor.State.EXEC;

	}




	public static ActionJump1Gap createValid(Node node, int x, int z) {
		final BlockPos from = node.pos;
		final BlockPos to = node.pos.add(x, 0, z);

		// check start position
		final BlockPos s0 = from.add(0, -1, 0); // standing on = walk on
		final BlockPos s3 = from.add(0, +2, 0); // above = walk through

		if (!BlockUtils.canWalkOn(s0) || !BlockUtils.canWalkThrough(s3)) {
			return null;
		}


		// check gap
		final BlockPos g0 = from.add(x / 2, -1, z / 2); // gap ground = !walk on
		final BlockPos g1 = from.add(x / 2, 0, z / 2); // feet = walk through
		final BlockPos g2 = from.add(x / 2, +1, z / 2); // head = walk through
		final BlockPos g3 = from.add(x / 2, +2, z / 2); // above = walk through

		if (BlockUtils.canWalkOn(g0) || !BlockUtils.canWalkThrough(g1) || !BlockUtils.canWalkThrough(g2) || !BlockUtils.canWalkThrough(g3)) {
			return null;
		}


		// check destination
		final BlockPos d0 = from.add(x, -1, z); // landing on = walk on
		final BlockPos d1 = from.add(x, 0, z); // feet = walk through
		final BlockPos d2 = from.add(x, +1, z); // head = walk through
		final BlockPos d3 = from.add(x, +2, z); // above = walk through

		if (!BlockUtils.canWalkOn(d0) || !BlockUtils.canWalkThrough(d1) || !BlockUtils.canWalkThrough(d2) || !BlockUtils.canWalkThrough(d3)) {
			return null;
		}


		// valid movement -> create action
		return new ActionJump1Gap(node, to);
	}


}
