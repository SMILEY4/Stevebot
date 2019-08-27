package stevebot.pathfinding.actions;

import stevebot.pathfinding.Node;

import java.util.ArrayList;
import java.util.List;

public class ActionGenerator {


	public static List<Action> getActions(Node node) {
		List<Action> actions = new ArrayList<>();
		getActionsWalk(actions, node);
		return actions;
	}




	private static void getActionsWalk(List<Action> actions, Node node) {
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


}
