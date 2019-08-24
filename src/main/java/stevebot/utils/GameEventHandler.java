package stevebot.utils;

import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class GameEventHandler {


	private final List<GameEventListener> listeners = new ArrayList<>();




	public void addListener(GameEventListener listener) {
		listeners.add(listener);
	}




	public void removeListener(GameEventListener listener) {
		listeners.remove(listener);
	}




	public void onPreInit(FMLPreInitializationEvent event) {
		listeners.forEach(listener -> listener.onPreInit(event));
	}




	public void onInit(FMLInitializationEvent event) {
		listeners.forEach(listener -> listener.onInit(event));
	}




	public void onPostInit(FMLPostInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		listeners.forEach(listener -> listener.onPostInit(event));
	}




	@SubscribeEvent
	public void onRenderWorld(RenderWorldLastEvent event) {
		listeners.forEach(listener -> listener.onRenderWorld(event));
	}

	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {

//		Minecraft mc = Minecraft.getMinecraft();
//		if(mc.player != null) {
//			mc.player.cameraYaw = mc.player.rotationYaw - 40;
//		}

	}




	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		listeners.forEach(listener -> listener.onClientTick(event));
	}




	@SubscribeEvent
	public void onOpenGUI(GuiOpenEvent event) {
		listeners.forEach(listener -> listener.onOpenGUI(event));
	}




	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.PostConfigChangedEvent event) {
		listeners.forEach(listener -> listener.onConfigChanged(event));
	}


}
