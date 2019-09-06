package stevebot.pathfinding.actions;

import stevebot.Direction;
import stevebot.pathfinding.Node;
import stevebot.pathfinding.actions.playeractions.*;

import java.util.ArrayList;
import java.util.List;

public class ActionGenerator {


	public static List<Action> getActions(Node node) {
		List<Action> actions = new ArrayList<>();
		getActionsWalk(actions, node);
		getActionsStepDown(actions, node);
		getActionsStepUp(actions, node);
		getActionsDropDownN(actions, node);
		getActionsFall(actions, node);
		getActionsWalkJump(actions, node);
		getActionsSprintJump(actions, node);
		return actions;
	}




	private static void getActionsWalk(List<Action> actions, Node node) {
		for (Direction direction : Direction.CARDINALS) {
			if (direction.diagonal) {
				ActionWalkDiagonal action = ActionWalkDiagonal.createValid(node, direction);
				if (action != null) {
					actions.add(action);
				}
			} else {
				ActionWalkStraight action = ActionWalkStraight.createValid(node, direction);
				if (action != null) {
					actions.add(action);
				}
			}
		}
	}




	private static void getActionsStepDown(List<Action> actions, Node node) {
		for (Direction direction : Direction.CARDINALS) {
			if (direction.diagonal) {
				ActionStepDownDiagonal action = ActionStepDownDiagonal.createValid(node, direction);
				if (action != null) {
					actions.add(action);
				}
			} else {
				ActionStepDownStraight action = ActionStepDownStraight.createValid(node, direction);
				if (action != null) {
					actions.add(action);
				}
			}
		}
	}




	private static void getActionsStepUp(List<Action> actions, Node node) {
		for (Direction direction : Direction.CARDINALS) {
			if (direction.diagonal) {
				ActionStepUpDiagonal action = ActionStepUpDiagonal.createValid(node, direction);
				if (action != null) {
					actions.add(action);
				}
			} else {
				ActionStepUpStraight action = ActionStepUpStraight.createValid(node, direction);
				if (action != null) {
					actions.add(action);
				}
			}
		}
	}




	private static void getActionsDropDownN(List<Action> actions, Node node) {
		for (Direction direction : Direction.CARDINALS) {
			if (direction.diagonal) {
				ActionDropDownDiagonal action = ActionDropDownDiagonal.createValid(node, direction);
				if (action != null) {
					actions.add(action);
				}
			} else {
				ActionDropDownStraight action = ActionDropDownStraight.createValid(node, direction);
				if (action != null) {
					actions.add(action);
				}
			}
		}
	}




	private static void getActionsFall(List<Action> actions, Node node) {
		ActionFall action = ActionFall.createValid(node);
		if (action != null) {
			actions.add(action);
		}
	}




	private static void getActionsWalkJump(List<Action> actions, Node node) {
		for (Direction direction : Direction.CARDINALS) {
			if (direction.diagonal) {
				ActionWalkJumpDiagonal action = ActionWalkJumpDiagonal.createValid(node, direction);
				if (action != null) actions.add(action);
			} else {
				ActionWalkJumpStraight action = ActionWalkJumpStraight.createValid(node, direction);
				if (action != null) actions.add(action);
			}
		}
	}




	private static void getActionsSprintJump(List<Action> actions, Node node) {
		for (Direction direction : Direction.CARDINALS_SIMPLE) {
			ActionSprintJumpStraight action = ActionSprintJumpStraight.createValid(node, direction);
			if (action != null) actions.add(action);
		}
	}

}
