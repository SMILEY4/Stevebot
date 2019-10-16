package stevebot.data.modification;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blocks.BlockWrapper;

public class BlockPlaceModification implements Modification {


	private final BaseBlockPos position;
	private final BlockWrapper block;




//	public BlockPlaceModification(BaseBlockPos position) {
//		this(position, null);
//	}




	public BlockPlaceModification(BaseBlockPos position, BlockWrapper block) {
		this.position = position;
		this.block = block;
	}




	public BaseBlockPos getPosition() {
		return position;
	}




	public boolean usedThrowaway() {
		return block == null;
	}




	public BlockWrapper getBlock() {
		return block;
	}

}
