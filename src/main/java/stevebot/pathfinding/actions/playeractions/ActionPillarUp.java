package stevebot.pathfinding.actions.playeractions;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import stevebot.data.blockpos.FastBlockPos;
import stevebot.data.blocks.BlockWrapper;
import stevebot.minecraft.MinecraftAdapter;
import stevebot.misc.Direction;
import stevebot.misc.StateMachine;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.ActionFactory;
import stevebot.pathfinding.actions.ActionUtils;
import stevebot.pathfinding.execution.PathExecutorImpl;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.nodes.NodeCache;
import stevebot.player.PlayerUtils;
import stevebot.player.inventory.InventoryChange;
import stevebot.player.inventory.InventorySlot;
import stevebot.player.inventory.ItemWrapper;

public class ActionPillarUp extends Action {


	private enum State {
		SLOWING_DOWN, JUMPING, LANDING;
	}






	private enum Transition {
		SLOW_ENOUGH, PLACED_BLOCK;
	}






	private StateMachine<State, Transition> stateMachine = new StateMachine<>();
	private final BlockChange[] blockChanges;




	private ActionPillarUp(Node from, Node to, double cost, BlockChange[] blockChanges) {
		super(from, to, cost);
		this.blockChanges = blockChanges;
		stateMachine.defineTransition(State.SLOWING_DOWN, Transition.SLOW_ENOUGH, State.JUMPING);
		stateMachine.defineTransition(State.JUMPING, Transition.PLACED_BLOCK, State.LANDING);
	}




	@Override
	public void resetAction() {
		stateMachine.setState(State.SLOWING_DOWN);
	}




	@Override
	public PathExecutorImpl.StateFollow tick(boolean fistTick) {

		switch (stateMachine.getState()) {

			case SLOWING_DOWN: {
				boolean slowEnough = PlayerUtils.getMovement().slowDown(0.075);
				if (slowEnough) {
					stateMachine.fireTransition(Transition.SLOW_ENOUGH);
				} else {
					PlayerUtils.getCamera().setLookAt(getTo().getPos().getX(), getTo().getPos().getY(), getTo().getPos().getZ(), true);
				}
				return PathExecutorImpl.StateFollow.EXEC;
			}

			case JUMPING: {
				if (PlayerUtils.getPlayerBlockPos().equals(getFrom().getPos())) {
					PlayerUtils.getInput().setJump(false);
				}
				if (PlayerUtils.getPlayerBlockPos().equals(getTo().getPos())) {
					MinecraftAdapter.get().setBlock(getFrom().getPos().copyAsMCBlockPos(), Blocks.GOLD_BLOCK);
					stateMachine.fireTransition(Transition.PLACED_BLOCK);
				}
				return PathExecutorImpl.StateFollow.EXEC;
			}


			case LANDING: {
				if (PlayerUtils.getPlayer().onGround && PlayerUtils.getPlayerBlockPos().equals(getTo().getPos())) {
					return PathExecutorImpl.StateFollow.DONE;
				} else {
					return PathExecutorImpl.StateFollow.EXEC;
				}
			}

			default: {
				return PathExecutorImpl.StateFollow.FAILED;
			}
		}
	}




	@Override
	public boolean changedBlocks() {
		return true;
	}




	@Override
	public BlockChange[] getBlockChanges() {
		return this.blockChanges;
	}




	public static class PillarUpFactory implements ActionFactory {


		static final BlockWrapper BLOCK_GOLD = new BlockWrapper(41, "minecraft:gold_block", Blocks.GOLD_BLOCK); // Todo




		@Override
		public Action createAction(Node node, Result result) {
			return new ActionPillarUp(node, result.to, result.estimatedCost, result.blockCaches);
		}




		@Override
		public Result check(Node node) {

			// check inventory
			final InventorySlot placableBlock = ActionUtils.getPlacableBlock();
			if (placableBlock == null) {
				return Result.invalid();
			}

			// check to-position
			final FastBlockPos to = node.getPosCopy().add(0, 1, 0);
			if (!ActionUtils.canMoveThrough(to)) {
				return Result.invalid();
			}

			// check from-position
			if (!ActionUtils.canStandAt(node.getPos())) {
				return Result.invalid();
			}

			return Result.valid(Direction.UP, NodeCache.get(to), ActionCosts.COST_PILLAR_UP,
					new BlockChange[]{new BlockChange(node.getPos(), placableBlock.getCurrentAsBlock())},
					new InventoryChange[]{ new InventoryChange(placableBlock, new ItemWrapper(new ItemStack(placableBlock.getItem().itemStack.getItem(), placableBlock.getItem().itemStack.getCount()-1)))}
			);
		}


	}


}
