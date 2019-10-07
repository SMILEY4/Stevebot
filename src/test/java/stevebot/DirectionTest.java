package stevebot;

import org.junit.jupiter.api.Test;
import stevebot.misc.Direction;

import static org.assertj.core.api.Assertions.assertThat;

public class DirectionTest {


	@Test
	void testSplit() {
		assertThat(Direction.NORTH.split()).containsExactly(Direction.NORTH, Direction.NORTH);
		assertThat(Direction.EAST.split()).containsExactly(Direction.EAST, Direction.EAST);
		assertThat(Direction.SOUTH.split()).containsExactly(Direction.SOUTH, Direction.SOUTH);
		assertThat(Direction.WEST.split()).containsExactly(Direction.WEST, Direction.WEST);
		assertThat(Direction.NORTH_EAST.split()).containsExactly(Direction.NORTH, Direction.EAST);
		assertThat(Direction.SOUTH_WEST.split()).containsExactly(Direction.SOUTH, Direction.WEST);
		assertThat(Direction.NORTH_WEST.split()).containsExactly(Direction.NORTH, Direction.WEST);
		assertThat(Direction.SOUTH_EAST.split()).containsExactly(Direction.SOUTH, Direction.EAST);
	}




	@Test
	void testMergeWith() {
		assertThat(Direction.NORTH.merge(Direction.EAST)).isEqualTo(Direction.NORTH_EAST);
		assertThat(Direction.SOUTH.merge(Direction.EAST)).isEqualTo(Direction.SOUTH_EAST);
		assertThat(Direction.SOUTH.merge(Direction.WEST)).isEqualTo(Direction.SOUTH_WEST);
		assertThat(Direction.NORTH.merge(Direction.WEST)).isEqualTo(Direction.NORTH_WEST);
		assertThat(Direction.NORTH.merge(Direction.SOUTH)).isEqualTo(Direction.NONE);
		assertThat(Direction.EAST.merge(Direction.WEST)).isEqualTo(Direction.NONE);
		assertThat(Direction.UP.merge(Direction.DOWN)).isEqualTo(Direction.NONE);
	}




	@Test
	void testMergeTwo() {
		assertThat(Direction.merge(Direction.NORTH, Direction.EAST)).isEqualTo(Direction.NORTH_EAST);
		assertThat(Direction.merge(Direction.SOUTH, Direction.EAST)).isEqualTo(Direction.SOUTH_EAST);
		assertThat(Direction.merge(Direction.SOUTH, Direction.WEST)).isEqualTo(Direction.SOUTH_WEST);
		assertThat(Direction.merge(Direction.NORTH, Direction.WEST)).isEqualTo(Direction.NORTH_WEST);
		assertThat(Direction.merge(Direction.NORTH, Direction.SOUTH)).isEqualTo(Direction.NONE);
		assertThat(Direction.merge(Direction.EAST, Direction.WEST)).isEqualTo(Direction.NONE);
		assertThat(Direction.merge(Direction.UP, Direction.DOWN)).isEqualTo(Direction.NONE);
	}




	@Test
	void testOpposite() {
		assertThat(Direction.NORTH.opposite()).isEqualTo(Direction.SOUTH);
		assertThat(Direction.EAST.opposite()).isEqualTo(Direction.WEST);
		assertThat(Direction.SOUTH.opposite()).isEqualTo(Direction.NORTH);
		assertThat(Direction.WEST.opposite()).isEqualTo(Direction.EAST);
		assertThat(Direction.NORTH_EAST.opposite()).isEqualTo(Direction.SOUTH_WEST);
		assertThat(Direction.SOUTH_WEST.opposite()).isEqualTo(Direction.NORTH_EAST);
		assertThat(Direction.NORTH_WEST.opposite()).isEqualTo(Direction.SOUTH_EAST);
		assertThat(Direction.SOUTH_EAST.opposite()).isEqualTo(Direction.NORTH_WEST);
	}


}
