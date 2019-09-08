package stevebot.events;

import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public interface GameRenderListener extends EventListener {


	default void onRenderTickEvent(TickEvent.RenderTickEvent event) {
	}


	default void onRenderWorldLast(RenderWorldLastEvent event) {
	}

}
