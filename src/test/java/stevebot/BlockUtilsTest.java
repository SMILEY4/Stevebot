package stevebot;

import net.minecraft.init.Blocks;
import org.junit.jupiter.api.Test;
import stevebot.data.blocks.BlockUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockUtilsTest {


	@Test
	void testIsWater() {
		assertThat(BlockUtils.isWater(Blocks.WATER)).isTrue();
		assertThat(BlockUtils.isWater(Blocks.FLOWING_WATER)).isTrue();
		assertThat(BlockUtils.isWater(Blocks.LAVA)).isFalse();
		assertThat(BlockUtils.isWater(Blocks.FLOWING_LAVA)).isFalse();
		assertThat(BlockUtils.isWater(Blocks.STONE)).isFalse();
	}




	@Test
	void testIsLava() {
		assertThat(BlockUtils.isWater(Blocks.LAVA)).isTrue();
		assertThat(BlockUtils.isWater(Blocks.FLOWING_LAVA)).isTrue();
		assertThat(BlockUtils.isWater(Blocks.WATER)).isFalse();
		assertThat(BlockUtils.isWater(Blocks.FLOWING_WATER)).isFalse();
		assertThat(BlockUtils.isWater(Blocks.STONE)).isFalse();
	}




	@Test
	void testIsFlowingLiquid() {
		assertThat(BlockUtils.isWater(Blocks.FLOWING_WATER)).isTrue();
		assertThat(BlockUtils.isWater(Blocks.FLOWING_LAVA)).isTrue();
		assertThat(BlockUtils.isWater(Blocks.WATER)).isFalse();
		assertThat(BlockUtils.isWater(Blocks.LAVA)).isFalse();
		assertThat(BlockUtils.isWater(Blocks.STONE)).isFalse();
	}




	@Test
	void testIsLiquid() {
		assertThat(BlockUtils.isWater(Blocks.FLOWING_WATER)).isTrue();
		assertThat(BlockUtils.isWater(Blocks.FLOWING_LAVA)).isTrue();
		assertThat(BlockUtils.isWater(Blocks.WATER)).isTrue();
		assertThat(BlockUtils.isWater(Blocks.LAVA)).isTrue();
		assertThat(BlockUtils.isWater(Blocks.STONE)).isFalse();
	}




	@Test
	void testConvertXYZToFastBlockPos() {
		assertThat(BlockUtils.toFastBlockPos(1, 2.4, 3.7).getX()).isEqualTo(1);
		assertThat(BlockUtils.toFastBlockPos(1, 2.4, 3.7).getY()).isEqualTo(2);
		assertThat(BlockUtils.toFastBlockPos(1, 2.4, 3.7).getZ()).isEqualTo(3);
		assertThat(BlockUtils.toFastBlockPos(-1, -2.4, -3.7).getX()).isEqualTo(-2);
		assertThat(BlockUtils.toFastBlockPos(-1, -2.4, -3.7).getY()).isEqualTo(-3);
		assertThat(BlockUtils.toFastBlockPos(-1, -2.4, -3.7).getZ()).isEqualTo(-4);
	}




	@Test
	void testConvertXYZToBaseBlockPos() {
		assertThat(BlockUtils.toBaseBlockPos(1, 2.4, 3.7).getX()).isEqualTo(1);
		assertThat(BlockUtils.toBaseBlockPos(1, 2.4, 3.7).getY()).isEqualTo(2);
		assertThat(BlockUtils.toBaseBlockPos(1, 2.4, 3.7).getZ()).isEqualTo(3);
		assertThat(BlockUtils.toBaseBlockPos(-1, -2.4, -3.7).getX()).isEqualTo(-2);
		assertThat(BlockUtils.toBaseBlockPos(-1, -2.4, -3.7).getY()).isEqualTo(-3);
		assertThat(BlockUtils.toBaseBlockPos(-1, -2.4, -3.7).getZ()).isEqualTo(-4);
	}




	@Test
	void testConvertXYZToMCBlockPos() {
		assertThat(BlockUtils.toMCBlockPos(1, 2.4, 3.7).getX()).isEqualTo(1);
		assertThat(BlockUtils.toMCBlockPos(1, 2.4, 3.7).getY()).isEqualTo(2);
		assertThat(BlockUtils.toMCBlockPos(1, 2.4, 3.7).getZ()).isEqualTo(3);
		assertThat(BlockUtils.toMCBlockPos(-1, -2.4, -3.7).getX()).isEqualTo(-2);
		assertThat(BlockUtils.toMCBlockPos(-1, -2.4, -3.7).getY()).isEqualTo(-3);
		assertThat(BlockUtils.toMCBlockPos(-1, -2.4, -3.7).getZ()).isEqualTo(-4);
	}

}
