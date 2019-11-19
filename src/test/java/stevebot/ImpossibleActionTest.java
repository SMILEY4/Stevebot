package stevebot;

import org.junit.jupiter.api.Test;
import stevebot.misc.Direction;
import stevebot.pathfinding.actions.ImpossibleActionHandler;
import stevebot.pathfinding.actions.playeractions.ActionStepDown;
import stevebot.pathfinding.actions.playeractions.ActionStepUp;
import stevebot.pathfinding.actions.playeractions.ActionSwim;
import stevebot.pathfinding.actions.playeractions.ActionWalk;

import static org.assertj.core.api.Assertions.assertThat;

public class ImpossibleActionTest {


	@Test
	void testImpossibleActions() {

		final ImpossibleActionHandler handler = new ImpossibleActionHandler();

		handler.makesImpossible(ActionWalk.class, ActionSwim.class);
		handler.makesImpossible(ActionWalk.class, ActionStepUp.class);
		handler.makesImpossible(ActionWalk.class, ActionStepDown.class);

		handler.makesImpossible(ActionSwim.class, ActionWalk.class);
		handler.makesImpossible(ActionSwim.class, ActionStepUp.class);
		handler.makesImpossible(ActionSwim.class, ActionStepDown.class);

		handler.makesImpossible(ActionStepUp.class, ActionWalk.class);
		handler.makesImpossible(ActionStepUp.class, ActionStepDown.class);
		handler.makesImpossible(ActionStepUp.class, ActionSwim.class);

		handler.makesImpossible(ActionStepDown.class, ActionWalk.class);
		handler.makesImpossible(ActionStepDown.class, ActionStepUp.class);
		handler.makesImpossible(ActionStepDown.class, ActionSwim.class);

		assertActions(handler);

		handler.reset();

		assertActions(handler);
	}



	void assertActions(ImpossibleActionHandler handler) {

		assertThat(handler.isPossible(ActionSwim.class, Direction.NORTH)).isTrue();
		assertThat(handler.isPossible(ActionStepUp.class, Direction.NORTH)).isTrue();
		assertThat(handler.isPossible(ActionStepDown.class, Direction.NORTH)).isTrue();

		assertThat(handler.isPossible(ActionSwim.class, Direction.SOUTH)).isTrue();
		assertThat(handler.isPossible(ActionStepUp.class, Direction.SOUTH)).isTrue();
		assertThat(handler.isPossible(ActionStepDown.class, Direction.SOUTH)).isTrue();


		handler.addValid(ActionWalk.class, Direction.EAST);

		assertThat(handler.isPossible(ActionSwim.class, Direction.SOUTH)).isTrue();
		assertThat(handler.isPossible(ActionStepUp.class, Direction.SOUTH)).isTrue();
		assertThat(handler.isPossible(ActionStepDown.class, Direction.SOUTH)).isTrue();


		handler.addValid(ActionSwim.class, Direction.NORTH);

		assertThat(handler.isPossible(ActionWalk.class, Direction.SOUTH)).isTrue();
		assertThat(handler.isPossible(ActionStepUp.class, Direction.SOUTH)).isTrue();
		assertThat(handler.isPossible(ActionStepDown.class, Direction.SOUTH)).isTrue();

		assertThat(handler.isPossible(ActionWalk.class, Direction.NORTH)).isFalse();
		assertThat(handler.isPossible(ActionStepUp.class, Direction.NORTH)).isFalse();
		assertThat(handler.isPossible(ActionStepDown.class, Direction.NORTH)).isFalse();

	}

}
