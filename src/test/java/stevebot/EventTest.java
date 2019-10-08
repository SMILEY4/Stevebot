package stevebot;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3i;
import org.junit.jupiter.api.Test;
import stevebot.events.*;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {


	@Test
	void testEventManager() {

		final Vector3i nCalls = new Vector3i(0);

		final EventManager eventManager = new EventManagerImpl();

		final EventListener<InitEvent> initListener1 = new EventListener<InitEvent>() {
			@Override
			public Class<InitEvent> getEventClass() {
				return InitEvent.class;
			}




			@Override
			public void onEvent(InitEvent event) {
				nCalls.x++;
			}
		};

		final EventListener<InitEvent> initListener2 = new EventListener<InitEvent>() {
			@Override
			public Class<InitEvent> getEventClass() {
				return InitEvent.class;
			}




			@Override
			public void onEvent(InitEvent event) {
				nCalls.y++;
			}
		};

		final EventListener<PostInitEvent> postInitListener = new EventListener<PostInitEvent>() {
			@Override
			public Class<PostInitEvent> getEventClass() {
				return PostInitEvent.class;
			}




			@Override
			public void onEvent(PostInitEvent event) {
				nCalls.z++;
			}
		};


		eventManager.addListener(initListener1);
		eventManager.addListener(initListener2);
		eventManager.addListener(postInitListener);

		nCalls.set(0);
		eventManager.event(new InitEvent());
		assertThat(nCalls.x).isEqualTo(1);
		assertThat(nCalls.y).isEqualTo(1);
		assertThat(nCalls.z).isEqualTo(0);

		nCalls.set(0);
		eventManager.event(new PostInitEvent());
		assertThat(nCalls.x).isEqualTo(0);
		assertThat(nCalls.y).isEqualTo(0);
		assertThat(nCalls.z).isEqualTo(1);

		eventManager.removeListener(initListener1);

		nCalls.set(0);
		eventManager.event(new InitEvent());
		assertThat(nCalls.x).isEqualTo(0);
		assertThat(nCalls.y).isEqualTo(1);
		assertThat(nCalls.z).isEqualTo(0);

	}


}
