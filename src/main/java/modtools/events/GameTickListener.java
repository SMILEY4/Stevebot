package modtools.events;

import net.minecraftforge.fml.common.gameevent.TickEvent;

public interface GameTickListener {


	default void onClientTick(TickEvent.ClientTickEvent event) {
	}

	default void onWorldTickEvent(TickEvent.WorldTickEvent event) {
	}

	default void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
	}

	default void onRenderTickEvent(TickEvent.RenderTickEvent event) {
	}

}
