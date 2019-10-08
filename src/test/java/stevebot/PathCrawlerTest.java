package stevebot;

import org.junit.jupiter.api.Test;
import stevebot.pathfinding.execution.PathCrawler;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.path.CompletedPath;
import stevebot.pathfinding.path.PartialPath;

import static org.assertj.core.api.Assertions.assertThat;

public class PathCrawlerTest {


	@Test
	void testCrawlCompletedPathLong() {

		final int N_NODES = 10;

		final PathCrawler crawler = new PathCrawler();
		final CompletedPath path = TestUtils.completedPath(N_NODES);

		crawler.startPath(path);

		for (int i = 0; i < path.getNodes().size() - 1; i++) {
			final Node expectedFrom = path.getNodes().get(i);
			final Node expectedTo = path.getNodes().get(i + 1);

			assertThat(crawler.getCurrentNodeFrom()).isEqualTo(expectedFrom);
			assertThat(crawler.getCurrentNodeTo()).isEqualTo(expectedTo);

			final boolean isDone = !crawler.nextAction();
			final boolean expectedDone = expectedTo.equals(path.getLastNode());
			assertThat(isDone).isEqualTo(expectedDone);
		}
	}




	@Test
	void testCrawlCompletedPathShort() {

		final int N_NODES = 2;

		final PathCrawler crawler = new PathCrawler();
		final CompletedPath path = TestUtils.completedPath(N_NODES);

		crawler.startPath(path);

		for (int i = 0; i < path.getNodes().size() - 1; i++) {
			final Node expectedFrom = path.getNodes().get(i);
			final Node expectedTo = path.getNodes().get(i + 1);

			assertThat(crawler.getCurrentNodeFrom()).isEqualTo(expectedFrom);
			assertThat(crawler.getCurrentNodeTo()).isEqualTo(expectedTo);

			final boolean isDone = !crawler.nextAction();
			final boolean expectedDone = expectedTo.equals(path.getLastNode());
			assertThat(isDone).isEqualTo(expectedDone);
		}
	}




	@Test
	void testCrawlPartialPathLong() {

		final int N_NODES = 10;

		final PathCrawler crawler = new PathCrawler();
		final PartialPath path = TestUtils.partialPath(N_NODES);

		crawler.startPath(path);

		for (int i = 0; i < path.getNodes().size() - 1; i++) {
			final Node expectedFrom = path.getNodes().get(i);
			final Node expectedTo = path.getNodes().get(i + 1);

			assertThat(crawler.getCurrentNodeFrom()).isEqualTo(expectedFrom);
			assertThat(crawler.getCurrentNodeTo()).isEqualTo(expectedTo);

			final boolean isDone = !crawler.nextAction();
			final boolean expectedDone = expectedTo.equals(path.getLastNode());
			assertThat(isDone).isEqualTo(expectedDone);
		}
	}




	@Test
	void testCrawlPartialPathShort() {

		final int N_NODES = 2;

		final PathCrawler crawler = new PathCrawler();
		final PartialPath path = TestUtils.partialPath(N_NODES);

		crawler.startPath(path);

		for (int i = 0; i < path.getNodes().size() - 1; i++) {
			final Node expectedFrom = path.getNodes().get(i);
			final Node expectedTo = path.getNodes().get(i + 1);

			assertThat(crawler.getCurrentNodeFrom()).isEqualTo(expectedFrom);
			assertThat(crawler.getCurrentNodeTo()).isEqualTo(expectedTo);

			final boolean isDone = !crawler.nextAction();
			final boolean expectedDone = expectedTo.equals(path.getLastNode());
			assertThat(isDone).isEqualTo(expectedDone);
		}
	}


}
