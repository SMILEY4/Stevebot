package stevebot.pathfinding.actions;

import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.actions.playeractions.Action;

public interface ActionFactory {


	enum ResultType {
		VALID,
		INVALID,
		UNLOADED
	}






	class Result {


		private static final Result invalid = new Result();
		private static final Result unloaded = new Result();




		public static Result invalid() {
			if (invalid.type != ResultType.INVALID) {
				invalid.type = ResultType.INVALID;
			}
			return invalid;
		}




		public static Result unloaded() {
			if (unloaded.type != ResultType.UNLOADED) {
				unloaded.type = ResultType.UNLOADED;
			}
			return unloaded;
		}




		public static Result valid(Node to, double cost) {
			Result result = new Result();
			result.type = ResultType.VALID;
			result.estimatedCost = cost;
			result.to = to;
			return result;
		}




		public ResultType type = ResultType.INVALID;
		public double estimatedCost = ActionCosts.COST_INFINITE;
		public Node to = null;

	}


	Result check(Node node);

	Action createAction(Node node);

}
