package stevebot.pathfinding.actions;

import stevebot.pathfinding.Node;

import java.util.ArrayList;
import java.util.List;

public class ActionGenerator {



	/*
	 *
	 * OPTIMIZE
	 * --------
	 * - some valid actions make others impossible
	 *     e.g -> walk to (x,y) -> cant step up / drop down 1 / drop down n at (x,y)
	 * 			...
	 *
	 * ACTIONS:
	 * ----------
	 *
	 * WALK / WALK DIAGONAL
	 * - walk one block in any direction
	 * - status: DONE
	 *
	 * STEP UP
	 * - jump up to an adjacent block
	 * - status: DONE
	 *
	 * DROP 1
	 * - drop down one to an adjacent block
	 * - status: DONE
	 *
	 * DROP N
	 * - drown down n blocks next to the current block
	 * - status: DONE
	 * 		(todo: implement checks for health / max fall dist)
	 *
	 * JUMP N
	 * - jump to a block n blocks away
	 * - status: TODO wip
	 *
	 * DIG DOWN
	 * - destroy block below player + fall n blocks if air below block
	 * - status: TODO
	 *
	 * PILLAR UP
	 * - jump + place block below player
	 * - status: TODO
	 *
	 * BRIDGE
	 * - place block next to current block and step on placed block
	 * - status: TODO
	 *
	 */




	public static List<Action> getActions(Node node) {
		List<Action> actions = new ArrayList<>();
		getActionsWalk(actions, node);
		getActionsStepUp(actions, node);
		getActionsStepDown(actions, node);
		getActionsDropDown(actions, node);
//		getActionsJumpGap(actions, node);
		return actions;
	}




	public static void getActionsWalk(List<Action> actions, Node node) {
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				if (x == 0 && z == 0) {
					continue;
				}
				ActionWalk action = ActionWalk.createValid(node, x, z);
				if (action != null) {
					actions.add(action);
				}
			}
		}
	}




	public static void getActionsStepUp(List<Action> actions, Node node) {

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




	public static void getActionsStepDown(List<Action> actions, Node node) {
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




	public static void getActionsDropDown(List<Action> actions, Node node) {
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


//	public static void getActionsJumpGap(List<Action> actions, Node node) {
//
//		ActionJumpGap action0 = ActionJumpGap.createValid(node, +2, 0);
//		if(action0 != null) actions.add(action0);
//
//		ActionJumpGap action1 = ActionJumpGap.createValid(node, -2, 0);
//		if(action1 != null) actions.add(action1);
//
//		ActionJumpGap action2 = ActionJumpGap.createValid(node, 0, +2);
//		if(action2 != null) actions.add(action2);
//
//		ActionJumpGap action3 = ActionJumpGap.createValid(node, 0, -2);
//		if(action3 != null) actions.add(action3);
//
//	}


}
