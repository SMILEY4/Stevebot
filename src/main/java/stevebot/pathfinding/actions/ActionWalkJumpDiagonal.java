package stevebot.pathfinding.actions;

import modtools.player.MTPlayerController;
import net.minecraft.util.math.BlockPos;
import stevebot.Direction;
import stevebot.Stevebot;
import stevebot.pathfinding.BlockUtils;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;

public class ActionWalkJumpDiagonal extends Action {


	public static ActionWalkJumpDiagonal createValid(Node node, int x, int z, Direction direction) {
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

		Direction[] splitDirection = direction.split();
		final BlockPos p0 = node.pos.add(splitDirection[0].dx, 1, splitDirection[0].dz);
		final BlockPos p1 = node.pos.add(splitDirection[1].dx, 1, splitDirection[1].dz);

		boolean traversable0 = BlockUtils.canWalkThrough(p0) && BlockUtils.canWalkThrough(p0.add(0, 1, 0)) && BlockUtils.canWalkThrough(p0.add(0, 2, 0));
		boolean traversable1 = BlockUtils.canWalkThrough(p1) && BlockUtils.canWalkThrough(p1.add(0, 1, 0)) && BlockUtils.canWalkThrough(p1.add(0, 2, 0));

		if (!traversable0 || !traversable1) {
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
		return new ActionWalkJumpDiagonal(node, to);
	}




	private final Node from;
	private final Node to;
	private final double cost;




	public ActionWalkJumpDiagonal(Node from, BlockPos to) {
		this.from = from;
		this.to = Node.get(to);
		this.cost = ActionCosts.COST_WALK_JUMP;
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




	int currentState = 0;
	private final int PREPARE = 0;
	private final int JUMP = 1;
	private final int LAND = 2;




	@Override
	public void resetAction() {
		currentState = PREPARE;
	}




	@Override
	public PathExecutor.State tick(boolean firstTick) {

		MTPlayerController controller = Stevebot.get().getPlayerController();

		switch (currentState) {
			case PREPARE: {
				controller.getCamera().setLookAt(to.pos, true);
				boolean slowEnough = controller.getMovement().slowDown(0.055);
				if (slowEnough) {
					currentState = JUMP;
				}
				return PathExecutor.State.EXEC;
			}


			case JUMP: {
				controller.getMovement().moveTowards(to.pos, true);
				final double distToEdge = BlockUtils.distToCenter(controller.getPlayerPosition());
				if (distToEdge > 0.4) {
					controller.setJump(false);
				}
				if (controller.getPlayer().onGround && controller.getPlayerBlockPos().equals(to.pos)) {
					currentState = LAND;
				}
				return PathExecutor.State.EXEC;
			}


			case LAND: {
				if (controller.getMovement().moveTowards(to.pos, true)) {
					return PathExecutor.State.DONE;
				} else {
					return PathExecutor.State.EXEC;
				}
			}

		}

		return PathExecutor.State.EXEC;
	}


}
