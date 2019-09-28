package stevebot.pathfinding.actions.playeractions;

import net.minecraft.block.Block;
import stevebot.data.blockpos.BaseBlockPos;

public class BlockChange {


	public static final BlockChange[] EMPTY = new BlockChange[]{};


	public final BaseBlockPos pos;
	public final Block newBlock;




	public BlockChange(BaseBlockPos pos, Block newBlock) {
		this.pos = pos;
		this.newBlock = newBlock;
	}

}
