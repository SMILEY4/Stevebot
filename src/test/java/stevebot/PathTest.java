package stevebot;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.path.CompletedPath;
import stevebot.pathfinding.path.EmptyPath;
import stevebot.pathfinding.path.PartialPath;
import stevebot.pathfinding.path.Path;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PathTest {


	@Test
	void testCompletePath() {

		final int N_NODES = 10;
		final List<Node> nodes = TestUtils.nodes(N_NODES);
		final Path path = new CompletedPath(nodes.get(nodes.size() - 1).gcost(), nodes);

		assertThat(path.getFirstNode()).isEqualTo(nodes.get(0));
		assertThat(path.getLastNode()).isEqualTo(nodes.get(nodes.size() - 1));
		assertThat(path.getNodes().size()).isEqualTo(nodes.size());
		assertThat(path.reachedGoal()).isTrue();
		assertThat(path.getCost()).isCloseTo(nodes.get(nodes.size() - 1).gcost(), Percentage.withPercentage(0.01));
	}




	@Test
	void testPartialPath() {

		final int N_NODES = 10;
		final List<Node> nodes = TestUtils.nodes(N_NODES);
		final Path path = new PartialPath(nodes.get(nodes.size() - 1).gcost(), nodes);

		assertThat(path.getFirstNode()).isEqualTo(nodes.get(0));
		assertThat(path.getLastNode()).isEqualTo(nodes.get(nodes.size() - 1));
		assertThat(path.getNodes().size()).isEqualTo(nodes.size());
		assertThat(path.reachedGoal()).isFalse();
		assertThat(path.getCost()).isCloseTo(nodes.get(nodes.size() - 1).gcost(), Percentage.withPercentage(0.01));
	}




	@Test
	void testEmptyPath() {

		final Path path = new EmptyPath();

		assertThat(path.getFirstNode()).isNull();
		assertThat(path.getLastNode()).isNull();
		assertThat(path.getNodes().size()).isEqualTo(0);
		assertThat(path.reachedGoal()).isFalse();
		assertThat(path.getCost()).isCloseTo(ActionCosts.get().COST_INFINITE, Percentage.withPercentage(0.01));
	}

}
