package stevebot.pathfinding.actions;

import stevebot.pathfinding.Node;

import java.util.ArrayList;
import java.util.List;

public class ActionGenerator {


	public static List<IAction> getActions(Node node) {
		List<IAction> actions = new ArrayList<>();
		/*
		 * - walk to one of 8 neighbors (if no ground at dest -> next action is fall)
		 * - walking may include breaking blocks
		 * - fall n blocks
		 * - pillar 1 block up
		 * - bridge 1 block to 4 (or 8 ?) neighbors
		 * */
		actions.addAll(getActionsMove(node));
		return actions;
	}




	public static List<ActionMove> getActionsMove(Node node) {
		List<ActionMove> actions = new ArrayList<>();
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				if (x == 0 && z == 0) {
					continue;
				}
				if (ActionMove.isValid(node.pos, node.pos.add(x, 0, z))) {
					actions.add(new ActionMove(node, x, z));
				}
			}
		}
		return actions;
	}


}
