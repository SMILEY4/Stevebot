package stevebot.events;

import net.minecraftforge.fml.common.eventhandler.Event;

public interface EventListener<E extends Event> {


	/**
	 * @return the class of the event this listener is listening to
	 */
	Class<E> getEventClass();

	/**
	 * @param event the event
	 */
	void onEvent(E event);


}
