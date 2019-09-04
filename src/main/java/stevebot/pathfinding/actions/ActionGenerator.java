package stevebot.pathfinding.actions;

import stevebot.Direction;
import stevebot.pathfinding.Node;

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
//		getActionsJump1Gap(actions, node);
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




	private static void getActionsJump1Gap(List<Action> actions, Node node) {

		ActionJump1Gap action0 = ActionJump1Gap.createValid(node, +2, 0);
		if (action0 != null) actions.add(action0);

		ActionJump1Gap action1 = ActionJump1Gap.createValid(node, -2, 0);
		if (action1 != null) actions.add(action1);

		ActionJump1Gap action2 = ActionJump1Gap.createValid(node, 0, +2);
		if (action2 != null) actions.add(action2);

		ActionJump1Gap action3 = ActionJump1Gap.createValid(node, 0, -2);
		if (action3 != null) actions.add(action3);

	}


}
