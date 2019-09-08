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




	public void removeListener(EventListener listener) {
		if (listener instanceof GameInitListener) {
			initListeners.remove(listener);
		}
		if (listener instanceof GameTickListener) {
			tickListeners.remove(listener);
		}
		if (listener instanceof GameRenderListener) {
			renderListeners.remove(listener);
		}
		if (listener instanceof GameCommandListener) {
			commandListeners.remove(listener);
		}
	}




	public void onPreInit() {
		initListeners.forEach(GameInitListener::onPreInit);
	}




	public void onInit() {
		initListeners.forEach(GameInitListener::onInit);
	}




	public void onPostInit() {
		MinecraftForge.EVENT_BUS.register(this);
		initListeners.forEach(GameInitListener::onPostInit);
	}




	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		tickListeners.forEach(listener -> listener.onClientTick(event));
	}




	@SubscribeEvent
	public void onWorldTickEvent(TickEvent.WorldTickEvent event) {
		tickListeners.forEach(listener -> listener.onWorldTickEvent(event));
	}




	@SubscribeEvent
	public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
		tickListeners.forEach(listener -> listener.onPlayerTickEvent(event));
	}




	@SubscribeEvent
	public void onRenderTickEvent(TickEvent.RenderTickEvent event) {
		renderListeners.forEach(listener -> listener.onRenderTickEvent(event));
		tickListeners.forEach(listener -> listener.onRenderTickEvent(event));
	}




	@SubscribeEvent
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		renderListeners.forEach(listener -> listener.onRenderWorldLast(event));
	}




	@SubscribeEvent
	public void onCommand(CommandEvent event) {
		commandListeners.forEach(listener -> listener.onCommand(event));
	}

}
