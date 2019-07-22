package stevebot.utils;

import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public interface GameEventListener {


	default void onPreInit(FMLPreInitializationEvent event) {
	}

	default void onInit(FMLInitializationEvent event) {
	}

	default void onPostInit(FMLPostInitializationEvent event) {
	}

	default void onRenderWorld(RenderWorldLastEvent event) {
	}

	default void onClientTick(TickEvent.ClientTickEvent event) {
	}

	default void onOpenGUI(GuiOpenEvent event) {
	}

	default void onConfigChanged(ConfigChangedEvent.PostConfigChangedEvent event) {
	}

}
