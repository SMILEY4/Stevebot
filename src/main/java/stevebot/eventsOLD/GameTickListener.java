package stevebot.eventsOLD;

import net.minecraftforge.fml.common.gameevent.TickEvent;

public interface GameTickListener extends EventListener {


	/**
	 * Tick on client
	 */
	default void onClientTick(TickEvent.ClientTickEvent event) {
	}

	/**
	 * World tick
	 */
	default void onWorldTickEvent(TickEvent.WorldTickEvent event) {
	}

	/**
	 * Player tick
	 */
	default void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
	}

	/**
	 * Render tick
	 */
	default void onRenderTickEvent(TickEvent.RenderTickEvent event) {
	}

}
