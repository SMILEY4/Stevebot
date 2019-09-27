package stevebot.pathfinding.actions.playeractions;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class BlockChange {


	public static final BlockChange[] EMPTY = new BlockChange[]{};


	public final BlockPos pos;
	public final Block newBlock;




	public BlockChange(BlockPos pos, Block newBlock) {
		this.pos = pos;
		this.newBlock = newBlock;
	}

}
