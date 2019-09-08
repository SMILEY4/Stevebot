package stevebot.data.blocks;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public interface BlockProvider {


	Block getBlockAt(BlockPos pos);

	Block getBlockAt(int x, int y, int z);

	int getBlockIdAt(BlockPos pos);


	int getBlockIdAt(int x, int y, int z);

}
