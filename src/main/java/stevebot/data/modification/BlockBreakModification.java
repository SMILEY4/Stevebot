package stevebot.data.modification;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.items.wrapper.ItemToolWrapper;

public class BlockBreakModification implements Modification {


	private final BaseBlockPos position;
	private final ItemToolWrapper tool;




	/**
	 * @param position the position of the broken block
	 */
	BlockBreakModification(BaseBlockPos position) {
		this(position, null);
	}




	/**
	 * @param position the position of the broken block
	 * @param tool     the tool used to break the block
	 */
	public BlockBreakModification(BaseBlockPos position, ItemToolWrapper tool) {
		this.position = position;
		this.tool = tool;
	}




	/**
	 * @return the position of the broken block
	 */
	public BaseBlockPos getPosition() {
		return position;
	}




	/**
	 * @return whether a specific tool was used
	 */
	public boolean usedTool() {
		return tool != null;
	}




	/**
	 * @return the tool used to break the block or null
	 */
	public ItemToolWrapper getTool() {
		return tool;
	}

}
