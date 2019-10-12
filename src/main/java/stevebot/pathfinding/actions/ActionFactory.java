package stevebot.pathfinding.actions;

import stevebot.data.blocks.BlockCache;
import stevebot.misc.Direction;
import stevebot.pathfinding.actions.playeractions.Action;
import stevebot.pathfinding.actions.playeractions.BlockChange;
import stevebot.pathfinding.nodes.Node;
import stevebot.player.inventory.InventoryChange;

import java.util.Collections;
import java.util.List;

public interface ActionFactory {


	enum ResultType {

		/**
		 * The action is a valid action.
		 */
		VALID,

		/**
		 * The action is NOT a valid action.
		 */
		INVALID,

		/**
		 * The action is in a unloaded area and not valid.
		 */
		UNLOADED
	}






	class Result {


		private static final Result invalid = new Result();
		private static final Result unloaded = new Result();




		/**
		 * @return a new {@link Result} of the type {@link ResultType#INVALID}.
		 */
		public static Result invalid() {
			if (invalid.type != ResultType.INVALID) {
				invalid.type = ResultType.INVALID;
			}
			return invalid;
		}




		/**
		 * @return a new {@link Result} of the type {@link ResultType#UNLOADED}.
		 */
		public static Result unloaded() {
			if (unloaded.type != ResultType.UNLOADED) {
				unloaded.type = ResultType.UNLOADED;
			}
			return unloaded;
		}




		/**
		 * @param direction the direction of the action
		 * @param to        the target node of the action
		 * @param cost      the cost of the action
		 * @return a new {@link Result} of the type {@link ResultType#VALID}.
		 */
		public static Result valid(Direction direction, Node to, double cost) {
			Result result = new Result();
			result.type = ResultType.VALID;
			result.estimatedCost = cost;
			result.to = to;
			result.direction = direction;
			return result;
		}




		/**
		 * @param direction        the direction of the action
		 * @param to               the target node of the action
		 * @param cost             the cost of the action
		 * @param blockCaches      the list of {@link BlockCache}s
		 * @param inventoryChanges the list of {@link InventoryChange}s
		 * @return a new {@link Result} of the type {@link ResultType#VALID}.
		 */
		public static Result valid(Direction direction, Node to, double cost, BlockChange[] blockCaches, InventoryChange[] inventoryChanges) {
			Result result = valid(direction, to, cost);
			result.blockCaches = blockCaches;
			result.inventoryChanges = inventoryChanges;
			return result;
		}




		public ResultType type = ResultType.INVALID;
		public double estimatedCost = ActionCosts.COST_INFINITE;
		public Node to = null;
		public Direction direction = Direction.NONE;
		public BlockChange[] blockCaches;
		public InventoryChange[] inventoryChanges;

	}

	/**
	 * Check if a valid action can be created starting from the given node
	 *
	 * @param node the node
	 * @return the {@link Result} of the check
	 */
	Result check(Node node);

	/**
	 * Creates an action starting from the given node
	 *
	 * @param node   the node
	 * @param result the result of a previous valid check of the same node in the same step.
	 * @return the created action
	 */
	Action createAction(Node node, Result result);


	/**
	 * @param direction the direction
	 * @return a list of other {@link ActionFactory}s that are not possible for the same node when this factory creates a valid action.
	 */
	default List<Class<? extends ActionFactory>> makesImpossible(Direction direction) {
		return Collections.emptyList();
	}

}
