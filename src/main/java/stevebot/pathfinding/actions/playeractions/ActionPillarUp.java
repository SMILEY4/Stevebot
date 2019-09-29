package stevebot.pathfinding.actions.playeractions;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import stevebot.Direction;
import stevebot.StateMachine;
import stevebot.Stevebot;
import stevebot.data.blockpos.FastBlockPos;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionUtils;
import stevebot.pathfinding.execution.PathExecutor;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.nodes.NodeCache;
import stevebot.player.PlayerController;

public class ActionPillarUp extends Action {


	private enum State {
		SLOWING_DOWN, JUMPING, LANDING;
	}






	private enum Transition {
		SLOW_ENOUGH, PLACED_BLOCK;
	}






	private StateMachine<State, Transition> stateMachine = new StateMachine<>();
	private final BlockChange[] change;




	private ActionPillarUp(Node from, Node to, double cost, BlockChange change) {
		super(from, to, cost);
		this.change = new BlockChange[]{change};
		stateMachine.defineTransition(State.SLOWING_DOWN, Transition.SLOW_ENOUGH, State.JUMPING);
		stateMachine.defineTransition(State.JUMPING, Transition.PLACED_BLOCK, State.LANDING);
	}




	@Override
	public void resetAction() {
		stateMachine.setState(State.SLOWING_DOWN);
	}




	@Override
	public PathExecutor.StateFollow tick(boolean fistTick) {

		final PlayerController controller = Stevebot.get().getPlayerController();

		switch (stateMachine.getState()) {

			case SLOWING_DOWN: {
				boolean slowEnough = controller.movement().slowDown(0.075);
				if (slowEnough) {
					stateMachine.fireTransition(Transition.SLOW_ENOUGH);
				} else {
					controller.camera().setLookAt(getTo().getPos().getX(), getTo().getPos().getY(), getTo().getPos().getZ(), true);
				}
				return PathExecutor.StateFollow.EXEC;
			}

			case JUMPING: {
				if (controller.utils().getPlayerBlockPos().equals(getFrom().getPos())) {
					controller.input().setJump(false);
				}
				if (controller.utils().getPlayerBlockPos().equals(getTo().getPos())) {
					Minecraft.getMinecraft().world.setBlockState(getFrom().getPos().copyAsMCBlockPos(), Blocks.GOLD_BLOCK.getDefaultState());
					stateMachine.fireTransition(Transition.PLACED_BLOCK);
				}
				return PathExecutor.StateFollow.EXEC;
			}


			case LANDING: {
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

			return Result.valid(Direction.UP, NodeCache.get(to), ActionCosts.COST_PILLAR_UP);
		}


	}


}
