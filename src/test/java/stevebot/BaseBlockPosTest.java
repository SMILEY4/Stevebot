package stevebot;

import net.minecraft.util.math.BlockPos;
import org.junit.jupiter.api.Test;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blockpos.FastBlockPos;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class BaseBlockPosTest {


	private static final Random random = new Random();




	@Test
	void testFromZero() {
		BaseBlockPos blockPos = new BaseBlockPos();
		assertThat(blockPos.getX()).isEqualTo(0);
		assertThat(blockPos.getY()).isEqualTo(0);
		assertThat(blockPos.getZ()).isEqualTo(0);
	}




	@Test
	void testFromXYZPosition() {
		final int x = random.nextInt();
		final int y = random.nextInt();
		final int z = random.nextInt();
		BaseBlockPos blockPos = new BaseBlockPos(x, y, z);
		assertThat(blockPos.getX()).isEqualTo(x);
		assertThat(blockPos.getY()).isEqualTo(y);
		assertThat(blockPos.getZ()).isEqualTo(z);
	}




	@Test
	void testFromBaseBlockPos() {
		final int x = random.nextInt();
		final int y = random.nextInt();
		final int z = random.nextInt();
		BaseBlockPos blockPos = new BaseBlockPos(new BaseBlockPos(x, y, z));
		assertThat(blockPos.getX()).isEqualTo(x);
		assertThat(blockPos.getY()).isEqualTo(y);
		assertThat(blockPos.getZ()).isEqualTo(z);
	}




	@Test
	void testFromMCBlockPos() {
		final int x = random.nextInt();
		final int y = random.nextInt();
		final int z = random.nextInt();
		BaseBlockPos blockPos = new BaseBlockPos(new BlockPos(x, y, z));
		assertThat(blockPos.getX()).isEqualTo(x);
		assertThat(blockPos.getY()).isEqualTo(y);
		assertThat(blockPos.getZ()).isEqualTo(z);
	}




	@Test
	void testGetCenter() {
		final int x = random.nextInt();
		final int y = random.nextInt();
		final int z = random.nextInt();
		BaseBlockPos blockPos = new BaseBlockPos(x, y, z);
		assertThat(blockPos.getCenterX()).isCloseTo(x + 0.5, within(0.001));
		assertThat(blockPos.getCenterY()).isCloseTo(y + 0.5, within(0.001));
		assertThat(blockPos.getCenterZ()).isCloseTo(z + 0.5, within(0.001));
	}




	@Test
	void copyAsMCBlockPos() {
		final int x = random.nextInt();
		final int y = random.nextInt();
		final int z = random.nextInt();
		BaseBlockPos blockPosSource = new BaseBlockPos(x, y, z);
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
		BaseBlockPos blockPosSource = new BaseBlockPos(x, y, z);
		FastBlockPos fastBlockPos = blockPosSource.copyAsFastBlockPos();
		assertThat(fastBlockPos.getX()).isEqualTo(x);
		assertThat(fastBlockPos.getY()).isEqualTo(y);
		assertThat(fastBlockPos.getZ()).isEqualTo(z);
	}




	@Test
	void testEqualsBaseBase() {
		final int x1 = random.nextInt();
		final int y1 = random.nextInt();
		final int z1 = random.nextInt();
		final int x2 = random.nextInt();
		final int y2 = random.nextInt();
		final int z2 = random.nextInt();
		BaseBlockPos blockPosA = new BaseBlockPos(x1, y1, z1);
		BaseBlockPos blockPosB = new BaseBlockPos(x1, y1, z1);
		BaseBlockPos blockPosC = new BaseBlockPos(x2, y2, z2);
		assertThat(blockPosA.equals(blockPosB)).isTrue();
		assertThat(blockPosA.equals(blockPosC)).isFalse();
	}




	@Test
	void testEqualsBaseFast() {
		final int x1 = random.nextInt();
		final int y1 = random.nextInt();
		final int z1 = random.nextInt();
		final int x2 = random.nextInt();
		final int y2 = random.nextInt();
		final int z2 = random.nextInt();
		BaseBlockPos blockPosA = new BaseBlockPos(x1, y1, z1);
		FastBlockPos blockPosB = new FastBlockPos(x1, y1, z1);
		FastBlockPos blockPosC = new FastBlockPos(x2, y2, z2);
		assertThat(blockPosA.equals(blockPosB)).isTrue();
		assertThat(blockPosA.equals(blockPosC)).isFalse();
	}


}
