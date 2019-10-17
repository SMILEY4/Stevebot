package stevebot.data.modification;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.items.wrapper.ItemToolWrapper;

public class BlockBreakModification implements Modification {


	private final BaseBlockPos position;
	private final ItemToolWrapper tool;




	BlockBreakModification(BaseBlockPos position) {
		this(position, null);
	}




	public BlockBreakModification(BaseBlockPos position, ItemToolWrapper tool) {
		this.position = position;
		this.tool = tool;
	}




	public BaseBlockPos getPosition() {
		return position;
	}




	public boolean usedTool() {
		return tool != null;
	}




	public ItemToolWrapper getTool() {
		return tool;
	}

}
