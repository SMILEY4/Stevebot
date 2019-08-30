package stevebot.pathfinding.actions;

import stevebot.Direction;
import stevebot.pathfinding.Node;

import java.util.ArrayList;
import java.util.List;

public class ActionGenerator {


	public static List<Action> getActions(Node node) {
		List<Action> actions = new ArrayList<>();
		getActionsWalk(actions, node);
//		getActionsStepUp(actions, node);
//		getActionsStepDown(actions, node);
//		getActionsDropDownN(actions, node);
//		getActionsJump1Gap(actions, node);
		return actions;
	}




	private static void getActionsWalk(List<Action> actions, Node node) {
		for (Direction direction : Direction.CARDINALS) {
			ActionWalk action = ActionWalk.createValid(node, direction);
			if (action != null) {
				actions.add(action);
			}
		}
	}




	private static void getActionsStepUp(List<Action> actions, Node node) {

		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				if (x == 0 && z == 0) {
					continue;
				}
				ActionStepUp action = ActionStepUp.createValid(node, x, z);
				if (action != null) {
					actions.add(action);
				}
			}
		}
	}




	private static void getActionsStepDown(List<Action> actions, Node node) {
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				if (x == 0 && z == 0) {
					continue;
				}
				ActionStepDown action = ActionStepDown.createValid(node, x, z);
				if (action != null) {
					actions.add(action);
				}
			}
		}
	}




	public static void getActionsDropDownN(List<Action> actions, Node node) {
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				if (x == 0 && z == 0) {
					continue;
				}
				ActionDropDownN action = ActionDropDownN.createValid(node, x, z);
				if (action != null) {
					actions.add(action);
				}
			}
		}
	}




	public static void getActionsJump1Gap(List<Action> actions, Node node) {

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
