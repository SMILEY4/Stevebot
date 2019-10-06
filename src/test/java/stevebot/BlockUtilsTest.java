package stevebot;

import org.junit.jupiter.api.Test;
import stevebot.data.blocks.BlockUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockUtilsTest {


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
