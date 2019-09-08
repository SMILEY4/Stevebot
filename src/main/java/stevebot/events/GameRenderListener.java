package stevebot.events;

import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public interface GameRenderListener {


	default void onRenderTickEvent(TickEvent.RenderTickEvent event) {
	}


	default void onRenderWorldLast(RenderWorldLastEvent event) {
	}

}
