package stevebot.events;

import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public interface GameRenderListener extends EventListener {


	/**
	 * Render tick.
	 */
	default void onRenderTickEvent(TickEvent.RenderTickEvent event) {
	}


	/**
	 * Fired last before the world finished drawing.
	 */
	default void onRenderWorldLast(RenderWorldLastEvent event) {
	}

}
