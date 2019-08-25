package modtools.events;

import modtools.ModBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import modtools.ModModule;

import java.util.ArrayList;
import java.util.List;

public class MTEventHandler extends ModModule {


	private List<GameInitListener> initListeners = new ArrayList<>();
	private List<GameTickListener> tickListeners = new ArrayList<>();




	public MTEventHandler(ModBase modHandler) {
		super(modHandler);
	}




	public void addListener(GameInitListener listener) {
		initListeners.add(listener);
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
		System.out.println("PRE INIT");
		initListeners.forEach(GameInitListener::onPreInit);
	}




	public void onInit() {
		System.out.println("INIT");
		initListeners.forEach(GameInitListener::onInit);
	}




	public void onPostInit() {
		System.out.println("POST INIT");
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
		tickListeners.forEach(listener -> listener.onRenderTickEvent(event));
	}


}
