package stevebot.pathfinding;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import stevebot.Stevebot;
import stevebot.utils.GameEventListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PathHandler implements GameEventListener {


	private PathExecutor executor = null;
	private boolean followingPath = false;




	public PathHandler() {
		Stevebot.EVENT_HANDLER.addListener(this);
	}



	private ExecutorService service = Executors.newSingleThreadExecutor();

	public void onPlanPath(BlockPos from, BlockPos to) {
		if (executor != null) {
			Stevebot.RENDERER.removeObject(executor.path);
		}

		service.submit(() -> {
			Path path = Pathfinding.calculatePath(from, to, 30 * 1000);
			if (path != null && path.nodes.size() >= 2) {
				executor = new PathExecutor(path);
				Stevebot.RENDERER.addObject(path);
			}
		});

	}




	public boolean isFollowingPath() {
		return followingPath;
	}




	public void startFollowingLastPath() {
		if (executor != null) {
			followingPath = true;
		}
	}




	public void stopFollowingLastPath() {
		followingPath = false;
	}




	@Override
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (isFollowingPath() && executor != null && Stevebot.PLAYER_CONTROLLER.getPlayer() != null) {

			final PathExecutor.State state = executor.tick();

			if (state == PathExecutor.State.DONE) {
				stopFollowingLastPath();
				executor = null;
			}

		}
	}

}
