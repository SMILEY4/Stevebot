package stevebot.pathfinding.execution;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import stevebot.events.EventListener;

public interface PathExecutor {


	/**
	 * The given listener listens for a {@link TickEvent.ClientTickEvent}.
	 *
	 * @return the {@link EventListener} of this {@link PathExecutor}.
	 */
	EventListener getTickListener();

	/**
	 * @param listener the {@link PathExecutionListener} listening to this {@link PathExecutor}.
	 */
	void setPathListener(PathExecutionListener listener);

	/**
	 * Starts execution the specified path.
	 */
	void start();

	/**
	 * Stops the execution the specified path. It can not be restarted.
	 */
	void stop();

	/**
	 * Start following the path.
	 *
	 * @param enableFreelook if freelook should be enabled automatically
	 */
	void startFollowing(boolean enableFreelook);

}
