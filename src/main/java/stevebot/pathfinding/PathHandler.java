package stevebot.pathfinding;

import modtools.rendering.Renderable;
import net.minecraft.util.math.BlockPos;
import stevebot.Stevebot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PathHandler {


	private ExecutorService service = Executors.newSingleThreadExecutor();

	private Path path;
	private Renderable pathRenderable;




	public void calculatePath(BlockPos from, BlockPos to, long timeout) {
		service.submit(() -> {
			Stevebot.get().getPlayerController().sendMessage("Calculating Path from " + from + " to " + to + "  (timeout@" + (timeout / 1000.0) + ")");
			Stevebot.get().getRenderer().removeRenderable(pathRenderable);

			long ts = System.currentTimeMillis();
			path = Pathfinding.calculatePath(from, to, timeout);

			if (path != null) {
				pathRenderable = path.toRenderable();

				Stevebot.get().getRenderer().addRenderable(pathRenderable);
				Stevebot.get().getPlayerController().sendMessage("Done:" + ((System.currentTimeMillis() - ts) / 1000.0) + "s, nodes=" + path.nodes.size() + ", cost=" + path.cost + ", explored:" + Node.nodeCache.size());
			} else {
				Stevebot.get().getPlayerController().sendMessage("Done:" + ((System.currentTimeMillis() - ts) / 1000.0) + "s, no path found!, explored:" + Node.nodeCache.size());
			}


		});
	}


}
