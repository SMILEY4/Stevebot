package stevebot.data.modification;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blocks.BlockWrapper;

public class BlockPlaceModification implements Modification {


	private final BaseBlockPos position;
	private final BlockWrapper block;




	/**
	 * @param position the position of the placed block
	 * @param block    the places block
	 */
	BlockPlaceModification(BaseBlockPos position, BlockWrapper block) {
		this.position = position;
		this.block = block;
	}




	/**
	 * @return the position of the placed block
	 */
	public BaseBlockPos getPosition() {
		return position;
	}




	/**
	 * @return the placed block
	 */
	public BlockWrapper getBlock() {
		return block;
	}

}
