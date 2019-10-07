package stevebot.events;

import net.minecraftforge.fml.common.eventhandler.Event;

public interface EventListener<E extends Event> {


	Class<E> getEventClass();

	void onEvent(E event);


}
