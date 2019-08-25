package stevebotOLD.pathfinding;

import stevebotOLD.pathfinding.actions.Action;

public class PathExecutor {


	public enum State {
		EXEC, DONE, FAILED;
	}






	public final Path path;
	private int indexFrom = 0;
	private Node from;
	private Node to;
	private Action action;




	public PathExecutor(Path path) {
		this.path = path;
		setup();
	}




	private void setup() {
		indexFrom = 0;
		from = path.nodes.get(indexFrom);
		to = path.nodes.get(indexFrom + 1);
		action = to.action;
	}




	private boolean step() {
		indexFrom++;
		if (indexFrom == path.nodes.size() - 1) { // next from is last node
			from = path.nodes.get(indexFrom);
			to = null;
			action = null;
			return false;
		} else {
			from = path.nodes.get(indexFrom);
			to = path.nodes.get(indexFrom + 1);
			action = to.action;
			return true;
		}
	}




	private boolean fistTick = true;




	public State tick() {

//		Stevebot.PLAYER_CONTROLLER.stopAll();

		State actionState = action.tick(fistTick);
		fistTick = false;

		if (actionState == State.FAILED) {
			return State.FAILED;
		}
		if (actionState == State.DONE) {
			fistTick = true;
			boolean hasNext = step();
			if (!hasNext) {
				return State.DONE;
			}
		}
		return State.EXEC;
	}


}
