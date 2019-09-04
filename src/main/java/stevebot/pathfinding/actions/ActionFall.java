package stevebot.pathfinding.actions;

import modtools.player.MTPlayerController;
import net.minecraft.util.math.BlockPos;
import stevebot.Stevebot;
import stevebot.pathfinding.BlockUtils;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.PathExecutor;

public class ActionFall extends Action {


	public static ActionFall createValid(Node node) {

		final BlockPos from = node.pos;

		int height = 0;
		BlockPos fallTo = from.add(0, -1, 0);
		while (BlockUtils.canWalkThrough(fallTo)) {
			fallTo = fallTo.add(0, -1, 0);
			height++;
		}
		fallTo = fallTo.add(0, 1, 0);

		final BlockPos d0 = fallTo.add(0, -1, 0);
		if (height <= 0 || !BlockUtils.canWalkOn(d0) || BlockUtils.isDangerous(d0)) {
			return null;
		}

		return new ActionFall(node, fallTo);
	}




	private final Node from;
	private final Node to;
	private final double cost;




	public ActionFall(Node from, BlockPos to) {
		this.from = from;
		this.to = Node.get(to);
		this.cost = ActionCosts.COST_FALL_N(from.pos.getY() - to.getY());

	}




	@Override
	public double getCost() {
		return cost;
	}




	@Override
	public Node getFrom() {
		return from;
	}




	@Override
	public Node getTo() {
		return to;
	}




	@Override
	public PathExecutor.State tick(boolean fistTick) {
		final MTPlayerController controller = Stevebot.get().getPlayerController();
		if (controller.getPlayer().onGround) {
			if (controller.getMovement().moveTowards(to.pos, false)) {
				return PathExecutor.State.DONE;
			} else {
				return PathExecutor.State.EXEC;
			}
		} else {
			return PathExecutor.State.EXEC;
		}
	}

}
