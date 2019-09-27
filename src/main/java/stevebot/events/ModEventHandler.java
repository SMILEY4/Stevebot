package stevebot.events;

import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class ModEventHandler {


	private List<GameInitListener> initListeners = new ArrayList<>();
	private List<GameTickListener> tickListeners = new ArrayList<>();
	private List<GameRenderListener> renderListeners = new ArrayList<>();
	private List<GameCommandListener> commandListeners = new ArrayList<>();

	private List<GameInitListener> initListenersToRemove = new ArrayList<>();
	private List<GameTickListener> tickListenersToRemove = new ArrayList<>();
	private List<GameRenderListener> renderListenersToRemove = new ArrayList<>();
	private List<GameCommandListener> commandListenersToRemove = new ArrayList<>();




	/**
	 * Adds the given {@link EventListener}.
	 *
	 * @param listener the listener
	 */
	public void addListener(EventListener listener) {
		if (listener instanceof GameInitListener) {
			initListeners.add((GameInitListener) listener);
		}
		if (listener instanceof GameTickListener) {
			tickListeners.add((GameTickListener) listener);
		}
		if (listener instanceof GameRenderListener) {
			renderListeners.add((GameRenderListener) listener);
		}
		if (listener instanceof GameCommandListener) {
			commandListeners.add((GameCommandListener) listener);
		}
	}




	/**
	 * Removes the given {@link EventListener}.
	 *
	 * @param listener the listener
	 */
	public void removeListener(EventListener listener) {
		if (listener instanceof GameInitListener) {
			initListenersToRemove.add((GameInitListener) listener);
		}
		if (listener instanceof GameTickListener) {
			tickListenersToRemove.add((GameTickListener) listener);
		}
		if (listener instanceof GameRenderListener) {
			renderListenersToRemove.add((GameRenderListener) listener);
		}
		if (listener instanceof GameCommandListener) {
			commandListenersToRemove.add((GameCommandListener) listener);
		}
	}




	public void onPreInit() {
		initListeners.removeAll(initListenersToRemove);
		initListenersToRemove.clear();
		initListeners.forEach(GameInitListener::onPreInit);
	}




	public void onInit() {
		initListeners.removeAll(initListenersToRemove);
		initListenersToRemove.clear();
		initListeners.forEach(GameInitListener::onInit);
	}




	public void onPostInit() {
		MinecraftForge.EVENT_BUS.register(this);
		initListeners.removeAll(initListenersToRemove);
		initListenersToRemove.clear();
		initListeners.forEach(GameInitListener::onPostInit);
	}




	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		tickListeners.removeAll(tickListenersToRemove);
		tickListenersToRemove.clear();
		tickListeners.forEach(listener -> listener.onClientTick(event));
	}




	@SubscribeEvent
	public void onWorldTickEvent(TickEvent.WorldTickEvent event) {
		tickListeners.removeAll(tickListenersToRemove);
		tickListenersToRemove.clear();
		tickListeners.forEach(listener -> listener.onWorldTickEvent(event));
	}




	@SubscribeEvent
	public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
		tickListeners.removeAll(tickListenersToRemove);
		tickListenersToRemove.clear();
		tickListeners.forEach(listener -> listener.onPlayerTickEvent(event));
	}




	@SubscribeEvent
	public void onRenderTickEvent(TickEvent.RenderTickEvent event) {
		tickListeners.removeAll(tickListenersToRemove);
		tickListenersToRemove.clear();
		renderListeners.forEach(listener -> listener.onRenderTickEvent(event));
		tickListeners.forEach(listener -> listener.onRenderTickEvent(event));
	}




	@SubscribeEvent
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		renderListeners.removeAll(renderListenersToRemove);
		renderListenersToRemove.clear();
		renderListeners.forEach(listener -> listener.onRenderWorldLast(event));
	}




	@SubscribeEvent
	public void onCommand(CommandEvent event) {
		commandListeners.removeAll(commandListenersToRemove);
		commandListenersToRemove.clear();
		commandListeners.forEach(listener -> listener.onCommand(event));
	}

}
