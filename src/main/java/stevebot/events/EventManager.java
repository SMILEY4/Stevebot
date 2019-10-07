package stevebot.events;

import net.minecraftforge.fml.common.eventhandler.Event;

public interface EventManager {


	void addListener(EventListener listener);


	void removeListener(EventListener listener);


	void event(Event event);

}
