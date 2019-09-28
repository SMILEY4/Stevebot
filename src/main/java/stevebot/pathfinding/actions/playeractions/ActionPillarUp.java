package stevebot.pathfinding.actions.playeractions;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import stevebot.data.blockpos.FastBlockPos;
import stevebot.Stevebot;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionUtils;
import stevebot.pathfinding.execution.PathExecutor;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.nodes.NodeCache;
import stevebot.player.PlayerController;

public class ActionPillarUp extends StatefulAction {


	private static final String STATE_SLOW_DOWN = "SLOW_DOWN";
	private static final String STATE_JUMP = "JUMP";
	private static final String STATE_LAND = "LAND";

	private final BlockChange[] change;




	private ActionPillarUp(Node from, Node to, double cost, BlockChange change) {
		super(from, to, cost, STATE_SLOW_DOWN, STATE_JUMP, STATE_LAND);
		this.setState(STATE_JUMP);
		this.change = new BlockChange[]{change};
	}




	@Override
	public PathExecutor.StateFollow tick(boolean fistTick) {

		final PlayerController controller = Stevebot.get().getPlayerController();

//		if (controller.utils().getMotionVector().mul(1, 0, 1).length() < 0.075) {
//			setState(STATE_JUMP);
//		}

		switch (getCurrentState()) {

			case STATE_SLOW_DOWN: {
				boolean slowEnough = controller.movement().slowDown(0.075);
				if (slowEnough) {
					setState(STATE_JUMP);
				} else {
					controller.camera().setLookAt(getTo().getPos().getX(), getTo().getPos().getY(), getTo().getPos().getZ(), true);
				}
				return PathExecutor.StateFollow.EXEC;
			}

			case STATE_JUMP: {
				if (controller.utils().getPlayerBlockPos().equals(getFrom().getPos())) {
					controller.input().setJump(false);
				}
				if (controller.utils().getPlayerBlockPos().equals(getTo().getPos())) {
					Minecraft.getMinecraft().world.setBlockState(getFrom().getPos().copyAsMCBlockPos(), Blocks.GOLD_BLOCK.getDefaultState());
					setState(STATE_LAND);
				}
				return PathExecutor.StateFollow.EXEC;
			}


			case STATE_LAND: {
				if (controller.getPlayer().onGround && controller.utils().getPlayerBlockPos().equals(getTo().getPos())) {
					return PathExecutor.StateFollow.DONE;
				} else {
					return PathExecutor.StateFollow.EXEC;
				}
			}

			default: {
				return PathExecutor.StateFollow.FAILED;
			}
		}
	}




	public boolean changedBlocks() {
		return true;
	}




	public BlockChange[] getBlockChanges() {
		return this.change;
	}




	public static class PillarUpFactory implements ActionFactory {


		@Override
		public Action createAction(Node node, Result result) {
			// final Result result = check(node);
			return new ActionPillarUp(node, result.to, result.estimatedCost, new BlockChange(node.getPos(), Blocks.GOLD_BLOCK));
		}




		@Override
		public Result check(Node node) {

			// check to-position
			final FastBlockPos to = node.getPosCopy().add(0, 1, 0);
			if (!ActionUtils.canMoveThrough(to)) {
				return Result.invalid();
			}

			// check from-position
			if (!ActionUtils.canStandAt(node.getPos())) {
				return Result.invalid();
			}

			return Result.valid(NodeCache.get(to), ActionCosts.COST_PILLAR_UP);
		}


	}


}
