package stevebot.data.modification;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blocks.BlockWrapper;

public class BlockPlaceModification implements Modification {


	private final BaseBlockPos position;
	private final BlockWrapper block;




	BlockPlaceModification(BaseBlockPos position, BlockWrapper block) {
		this.position = position;
		this.block = block;
	}




	public BaseBlockPos getPosition() {
		return position;
	}




	public BlockWrapper getBlock() {
		return block;
	}

}
