package stevebot.pathfinding;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blocks.BlockWrapper;

public class BlockChange {


	public static final BlockChange[] EMPTY = new BlockChange[]{};


	public final BaseBlockPos pos;
	public final BlockWrapper newBlock;




	public BlockChange(BaseBlockPos pos, BlockWrapper newBlock) {
		this.pos = pos;
		this.newBlock = newBlock;
	}

}
