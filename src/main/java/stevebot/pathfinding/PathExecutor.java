package stevebot.pathfinding;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import stevebot.Stevebot;
import stevebot.events.GameTickListener;
import stevebot.pathfinding.goal.Goal;
import stevebot.pathfinding.path.PathFactory;

public class PathExecutor implements GameTickListener {


	public enum State {
		EXEC, DONE, FAILED;
	}






	private PathFactory pathFactory;

	private int currentIndexFrom = 0;
	private Node currentNodeTo;

	private long timeStart = 0;
	private boolean fistTick = true;
	private boolean isFollowing = false;




	public PathExecutor(BlockPos posStart, Goal goal) {
		this.pathFactory = new PathFactory(posStart, goal);
		Stevebot.get().getEventHandler().addListener(this);
		pathFactory.prepareNextPath();
	}




	public void startFollowing() {
		if (pathFactory.hasPath()) {
			isFollowing = true;
			timeStart = System.currentTimeMillis();
			startPath();
		}
	}




	private void startPath() {
		currentIndexFrom = 0;
		currentNodeTo = pathFactory.getCurrentPath().getNodes().get(currentIndexFrom + 1);
	}




	public void stopFollowing() {
		isFollowing = false;
	}




	@Override
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (isFollowing && Stevebot.get().getPlayerController().getPlayer() != null) {

			if (pathFactory.getCurrentPath().getNodes().size() - currentIndexFrom < 20 && pathFactory.isCurrentLastSegment()) {
				pathFactory.prepareNextPath();
			}

			Stevebot.get().getPlayerController().input().stopAll();

			final State state = tick();

			if (state == State.FAILED) {
				stopFollowing();
				Stevebot.get().getPlayerController().utils().sendMessage("Failed to follow path.");
			}
			if (state == State.DONE) {
				pathFactory.removeCurrentPath();
				if (pathFactory.hasPath()) {
					Stevebot.get().getPlayerController().utils().sendMessage("Reached waypoint. (" + ((System.currentTimeMillis() - timeStart) / 1000.0) + "s");
					startPath();
				} else {
					stopFollowing();
					Stevebot.get().getPlayerController().utils().sendMessage("Reached destination. (" + ((System.currentTimeMillis() - timeStart) / 1000.0) + "s");
				}
			}

		}
	}




	private State tick() {

		State actionState = currentNodeTo.action.tick(fistTick);
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
		currentIndexFrom++;
		if (currentIndexFrom == pathFactory.getCurrentPath().getNodes().size() - 1) { // next is last node
			currentNodeTo = null;
			return false;
		} else {
			currentNodeTo = pathFactory.getCurrentPath().getNodes().get(currentIndexFrom + 1);
			currentNodeTo.action.resetAction();
			return true;
		}
	}


}
