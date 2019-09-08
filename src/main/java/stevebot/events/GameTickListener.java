package stevebot.events;

import net.minecraftforge.fml.common.gameevent.TickEvent;

public interface GameTickListener extends EventListener {


	default void onClientTick(TickEvent.ClientTickEvent event) {
	}

	default void onWorldTickEvent(TickEvent.WorldTickEvent event) {
	}

	default void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
	}

	default void onRenderTickEvent(TickEvent.RenderTickEvent event) {
	}

}
