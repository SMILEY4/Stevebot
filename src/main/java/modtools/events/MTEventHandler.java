package modtools.events;

import modtools.ModBase;
import modtools.ModModule;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class MTEventHandler extends ModModule {


	private List<GameInitListener> initListeners = new ArrayList<>();
	private List<GameTickListener> tickListeners = new ArrayList<>();
	private List<GameRenderListener> renderListeners = new ArrayList<>();




	public MTEventHandler(ModBase modHandler) {
		super(modHandler);
	}




	public void addListener(GameInitListener listener) {
		initListeners.add(listener);
	}




	public void addListener(GameRenderListener listener) {
		renderListeners.add(listener);
	}




	public void removeListener(GameInitListener listener) {
		initListeners.remove(listener);
	}




	public void addListener(GameTickListener listener) {
		tickListeners.add(listener);
	}




	public void removeListener(GameTickListener listener) {
		tickListeners.remove(listener);
	}


	//=====  INITIALISATION




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


	//=====  TICKS




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


	//==== RENDERING




	@SubscribeEvent
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		renderListeners.forEach(listener -> listener.onRenderWorldLast(event));
	}


}
