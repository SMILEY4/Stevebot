package stevebot.pathfinding;

import modtools.events.GameTickListener;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import stevebot.Stevebot;
import stevebot.pathfinding.actions.Action;

public class PathExecutor implements GameTickListener {


	public enum State {
		EXEC, DONE, FAILED;
	}






	public final Path path;
	private int indexFrom = 0;
	private Node from;
	private Node to;
	private Action action;

	private boolean fistTick = true;

	private boolean isFollowing = false;




	public PathExecutor(Path path) {
		Stevebot.get().getEventHandler().addListener(this);
		this.path = path;
	}




	public void start() {
		indexFrom = 0;
		from = path.nodes.get(indexFrom);
		to = path.nodes.get(indexFrom + 1);
		action = to.action;
		isFollowing = true;
	}




	public void stop() {
		isFollowing = false;
	}




	@Override
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (isFollowing && Stevebot.get().getPlayerController().getPlayer() != null) {

			Stevebot.get().getPlayerController().stopAll();

			final State state = tick();

			if (state == State.FAILED) {
				stop();
				Stevebot.get().getPlayerController().sendMessage("Failed to follow path.");
			}
			if (state == State.DONE) {
				stop();
				Stevebot.get().getPlayerController().sendMessage("Reached destination.");
			}

		}
	}




	private State tick() {

		State actionState = action.tick(fistTick);
		fistTick = false;

		if (actionState == State.FAILED) {
			return State.FAILED;
		}

		if (actionState == State.DONE) {
			fistTick = true;
			boolean hasNext = nextAction();
			if (!hasNext) {
				return State.DONE;
			}
		}

		return State.EXEC;

	}




	private boolean nextAction() {
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


}
