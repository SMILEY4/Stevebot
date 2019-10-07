package stevebot;

import net.minecraft.util.math.BlockPos;
import org.junit.jupiter.api.Test;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blockpos.FastBlockPos;
import stevebot.misc.Direction;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class FastBlockPosTest {


	private static final Random random = new Random();




	@Test
	void testFromZero() {
		FastBlockPos blockPos = new FastBlockPos();
		assertThat(blockPos.getX()).isEqualTo(0);
		assertThat(blockPos.getY()).isEqualTo(0);
		assertThat(blockPos.getZ()).isEqualTo(0);
	}




	@Test
	void testFromXYZPosition() {
		final int x = random.nextInt();
		final int y = random.nextInt();
		final int z = random.nextInt();
		FastBlockPos blockPos = new FastBlockPos(x, y, z);
		assertThat(blockPos.getX()).isEqualTo(x);
		assertThat(blockPos.getY()).isEqualTo(y);
		assertThat(blockPos.getZ()).isEqualTo(z);
	}




	@Test
	void testFromBaseBlockPos() {
		final int x = random.nextInt();
		final int y = random.nextInt();
		final int z = random.nextInt();
		FastBlockPos blockPos = new FastBlockPos(new BaseBlockPos(x, y, z));
		assertThat(blockPos.getX()).isEqualTo(x);
		assertThat(blockPos.getY()).isEqualTo(y);
		assertThat(blockPos.getZ()).isEqualTo(z);
	}




	@Test
	void testGetCenter() {
		final int x = random.nextInt();
		final int y = random.nextInt();
		final int z = random.nextInt();
		FastBlockPos blockPos = new FastBlockPos(x, y, z);
		assertThat(blockPos.getCenterX()).isCloseTo(x + 0.5, within(0.001));
		assertThat(blockPos.getCenterY()).isCloseTo(y + 0.5, within(0.001));
		assertThat(blockPos.getCenterZ()).isCloseTo(z + 0.5, within(0.001));
	}




	@Test
	void testSetXYZ() {
		final int x1 = random.nextInt();
		final int y1 = random.nextInt();
		final int z1 = random.nextInt();
		final int x2 = random.nextInt();
		final int y2 = random.nextInt();
		final int z2 = random.nextInt();
		FastBlockPos blockPos = new FastBlockPos(x1, y1, z1);
		blockPos.set(x2, y2, z2);
		assertThat(blockPos.getX()).isEqualTo(x2);
		assertThat(blockPos.getY()).isEqualTo(y2);
		assertThat(blockPos.getZ()).isEqualTo(z2);
	}




	@Test
	void testSetBaseBlockPos() {
		final int x1 = random.nextInt();
		final int y1 = random.nextInt();
		final int z1 = random.nextInt();
		final int x2 = random.nextInt();
		final int y2 = random.nextInt();
		final int z2 = random.nextInt();
		FastBlockPos blockPos = new FastBlockPos(x1, y1, z1);
		blockPos.set(new BaseBlockPos(x2, y2, z2));
		assertThat(blockPos.getX()).isEqualTo(x2);
		assertThat(blockPos.getY()).isEqualTo(y2);
		assertThat(blockPos.getZ()).isEqualTo(z2);
	}




	@Test
	void testAddXYZ() {
		final int x1 = random.nextInt();
		final int y1 = random.nextInt();
		final int z1 = random.nextInt();
		final int x2 = random.nextInt();
		final int y2 = random.nextInt();
		final int z2 = random.nextInt();
		FastBlockPos blockPos = new FastBlockPos(x1, y1, z1);
		blockPos.add(x2, y2, z2);
		assertThat(blockPos.getX()).isEqualTo(x1 + x2);
		assertThat(blockPos.getY()).isEqualTo(y1 + y2);
		assertThat(blockPos.getZ()).isEqualTo(z1 + z2);
	}




	@Test
	void testAddDirection() {
		final int x = random.nextInt();
		final int y = random.nextInt();
		final int z = random.nextInt();
		final Direction direction = Direction.values()[random.nextInt(Direction.values().length)];
		FastBlockPos blockPos = new FastBlockPos(x, y, z);
		blockPos.add(direction);
		assertThat(blockPos.getX()).isEqualTo(x + direction.dx);
		assertThat(blockPos.getY()).isEqualTo(y + direction.dy);
		assertThat(blockPos.getZ()).isEqualTo(z + direction.dz);
	}




	@Test
	void copyAsMCBlockPos() {
		final int x = random.nextInt();
		final int y = random.nextInt();
		final int z = random.nextInt();
		FastBlockPos blockPosSource = new FastBlockPos(x, y, z);
		BlockPos mcBlockPos = blockPosSource.copyAsMCBlockPos();
		assertThat(mcBlockPos.getX()).isEqualTo(x);
		assertThat(mcBlockPos.getY()).isEqualTo(y);
		assertThat(mcBlockPos.getZ()).isEqualTo(z);
	}




	@Test
	void copyAsFastBlockPos() {
		final int x = random.nextInt();
		final int y = random.nextInt();
		final int z = random.nextInt();
		FastBlockPos blockPosSource = new FastBlockPos(x, y, z);
		FastBlockPos fastBlockPos = blockPosSource.copyAsFastBlockPos();
		assertThat(fastBlockPos.getX()).isEqualTo(x);
		assertThat(fastBlockPos.getY()).isEqualTo(y);
		assertThat(fastBlockPos.getZ()).isEqualTo(z);
	}




	@Test
	void testEqualsFastFast() {
		final int x1 = random.nextInt();
		final int y1 = random.nextInt();
		final int z1 = random.nextInt();
		final int x2 = random.nextInt();
		final int y2 = random.nextInt();
		final int z2 = random.nextInt();
		FastBlockPos blockPosA = new FastBlockPos(x1, y1, z1);
		FastBlockPos blockPosB = new FastBlockPos(x1, y1, z1);
		FastBlockPos blockPosC = new FastBlockPos(x2, y2, z2);
		assertThat(blockPosA.equals(blockPosB)).isTrue();
		assertThat(blockPosA.equals(blockPosC)).isFalse();
	}




	@Test
	void testEqualsFastBase() {
		final int x1 = random.nextInt();
		final int y1 = random.nextInt();
		final int z1 = random.nextInt();
		final int x2 = random.nextInt();
		final int y2 = random.nextInt();
		final int z2 = random.nextInt();
		FastBlockPos blockPosA = new FastBlockPos(x1, y1, z1);
		BaseBlockPos blockPosB = new BaseBlockPos(x1, y1, z1);
		BaseBlockPos blockPosC = new BaseBlockPos(x2, y2, z2);
		assertThat(blockPosA.equals(blockPosB)).isTrue();
		assertThat(blockPosA.equals(blockPosC)).isFalse();
	}


}
