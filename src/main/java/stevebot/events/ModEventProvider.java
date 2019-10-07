package stevebot.events;

import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ModEventProvider {


	private final EventManager eventManager;




	public ModEventProvider(EventManager eventManager) {
		this.eventManager = eventManager;
	}




	public void onPreInit() {
		eventManager.event(new PreInitEvent());
	}




	public void onInit() {
		eventManager.event(new InitEvent());
	}




	public void onPostInit() {
		MinecraftForge.EVENT_BUS.register(this);
		eventManager.event(new PostInitEvent());
	}




	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		eventManager.event(event);
	}




	@SubscribeEvent
	public void onWorldTickEvent(TickEvent.WorldTickEvent event) {
		eventManager.event(event);
	}




	@SubscribeEvent
	public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
		eventManager.event(event);
	}




	@SubscribeEvent
	public void onRenderTickEvent(TickEvent.RenderTickEvent event) {
		eventManager.event(event);
	}




	@SubscribeEvent
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		eventManager.event(event);
	}




	@SubscribeEvent
	public void onCommand(CommandEvent event) {
		eventManager.event(event);
	}

}
