package stevebot.pathfinding;

import stevebot.events.GameTickListener;
import stevebot.rendering.Color;
import stevebot.rendering.renderables.DynPointCollectionRenderObject;
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

	private long timeStart = 0;

	private boolean fistTick = true;
	private boolean isFollowing = false;

	private DynPointCollectionRenderObject points = new DynPointCollectionRenderObject();




	public PathExecutor(Path path) {
		Stevebot.get().getEventHandler().addListener(this);
		Stevebot.get().getRenderer().addRenderable(points);
		this.path = path;
	}




	public void start() {
		indexFrom = 0;
		from = path.nodes.get(indexFrom);
		to = path.nodes.get(indexFrom + 1);
		action = to.action;
		isFollowing = true;
		points.getPositions().clear();
		points.getColors().clear();
		timeStart = System.currentTimeMillis();
	}




	public void stop() {
		isFollowing = false;
	}




	@Override
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (isFollowing && Stevebot.get().getPlayerController().getPlayer() != null) {

			Stevebot.get().getPlayerController().input().stopAll();
			points.addPoint(Stevebot.get().getPlayerController().utils().getPlayerPosition(), Color.MAGENTA);

			final State state = tick();

			if (state == State.FAILED) {
				stop();
				Stevebot.get().getPlayerController().utils().sendMessage("Failed to follow path.");
			}
			if (state == State.DONE) {
				stop();
				Stevebot.get().getPlayerController().utils().sendMessage("Reached destination. (" + ((System.currentTimeMillis()-timeStart)/1000.0) + "s");
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
		if (indexFrom == path.nodes.size() - 1) { // next is last node
			from = path.nodes.get(indexFrom);
			to = null;
			action = null;
			return false;
		} else {
			from = path.nodes.get(indexFrom);
			to = path.nodes.get(indexFrom + 1);
			action = to.action;
			action.resetAction();
			return true;
		}
	}


}
