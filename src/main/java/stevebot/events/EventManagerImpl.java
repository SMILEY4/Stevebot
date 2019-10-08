package stevebot.events;

import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.ArrayList;
import java.util.List;

public class EventManagerImpl implements EventManager {


	private final List<EventListener> listeners = new ArrayList<>();
	private final List<EventListener> listenersToRemove = new ArrayList<>();




	@Override
	public void addListener(EventListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}




	@Override
	public void removeListener(EventListener listener) {
		synchronized (listenersToRemove) {
			listenersToRemove.add(listener);
		}
	}




	@Override
	public void event(Event event) {
		final Class classEvent = event.getClass();
		synchronized (listeners) {
			synchronized (listenersToRemove) {
				listeners.removeAll(listenersToRemove);
				listenersToRemove.clear();
			}
			for (int i = 0; i < listeners.size(); i++) {
				final EventListener listener = listeners.get(i);
				final Class classListener = listener.getEventClass();
				if (classEvent == classListener) {
					listener.onEvent(event);
				}
			}
		}
	}

}
