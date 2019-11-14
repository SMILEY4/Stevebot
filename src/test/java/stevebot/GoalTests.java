package stevebot;

import org.junit.jupiter.api.Test;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.pathfinding.goal.ExactGoal;
import stevebot.pathfinding.goal.XZGoal;
import stevebot.pathfinding.goal.YGoal;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class GoalTests {


	private static final Random random = new Random();




	@Test
	void testXZGoal() {
		final int x = random.nextInt();
		final int z = random.nextInt();
		XZGoal goal = new XZGoal(x, z);
		assertThat(goal.x).isEqualTo(x);
		assertThat(goal.z).isEqualTo(z);
	}




	@Test
	void testYGoal() {
		final int y = random.nextInt(200);
		YGoal goal = new YGoal(y);
		assertThat(goal.y).isEqualTo(y);
	}




	@Test
	void testYGoalNegative() {
		final int y = -random.nextInt(200);
		YGoal goal = new YGoal(y);
		assertThat(goal.y).isEqualTo(0);
	}




	@Test
	void testReachedXZGoal() {
		final int x0 = random.nextInt();
		final int y0 = random.nextInt();
		final int z0 = random.nextInt();
		final int x1 = random.nextInt();
		final int y1 = random.nextInt();
		final int z1 = random.nextInt();
		XZGoal goal = new XZGoal(x0, z0);
		BaseBlockPos blockPos0 = new BaseBlockPos(x0, y0, z0);
		BaseBlockPos blockPos1 = new BaseBlockPos(x1, y1, z1);
		assertThat(goal.reached(blockPos0)).isTrue();
		assertThat(goal.reached(blockPos1)).isFalse();
	}




	@Test
	void testExactGoal() {
		final int x = random.nextInt();
		final int y = random.nextInt();
		final int z = random.nextInt();
		ExactGoal goal = new ExactGoal(new BaseBlockPos(x, y, z));
		assertThat(goal.pos.getX()).isEqualTo(x);
		assertThat(goal.pos.getY()).isEqualTo(y);
		assertThat(goal.pos.getZ()).isEqualTo(z);
	}




	@Test
	void testReachedExactGoal() {
		final int x0 = random.nextInt();
		final int y0 = random.nextInt();
		final int z0 = random.nextInt();
		final int x1 = random.nextInt();
		final int y1 = random.nextInt();
		final int z1 = random.nextInt();
		ExactGoal goal = new ExactGoal(new BaseBlockPos(x0, y0, z0));
		BaseBlockPos blockPos0 = new BaseBlockPos(x0, y0, z0);
		BaseBlockPos blockPos1 = new BaseBlockPos(x1, y1, z1);
		assertThat(goal.reached(blockPos0)).isTrue();
		assertThat(goal.reached(blockPos1)).isFalse();
	}

}
