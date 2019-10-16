package stevebot.data.modification;

import net.minecraft.item.ItemTool;
import stevebot.data.blockpos.BaseBlockPos;

public class BlockBreakModification implements Modification {


	private final BaseBlockPos position;
	private final ItemTool tool;




	public BlockBreakModification(BaseBlockPos position) {
		this(position, null);
	}




	public BlockBreakModification(BaseBlockPos position, ItemTool tool) {
		this.position = position;
		this.tool = tool;
	}




	public BaseBlockPos getPosition() {
		return position;
	}




	public boolean usedTool() {
		return tool != null;
	}




	public ItemTool getTool() {
		return tool;
	}

}
