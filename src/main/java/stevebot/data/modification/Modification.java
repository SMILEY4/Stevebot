package stevebot.data.modification;

import net.minecraft.item.ItemTool;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blocks.BlockWrapper;

public interface Modification {


	Modification[] EMPTY = new Modification[]{};


//	static Modification placeBlock(BaseBlockPos position) {
//		return new BlockPlaceModification(position);
//	}


	static Modification placeBlock(BaseBlockPos position, BlockWrapper block) {
		return new BlockPlaceModification(position, block);
	}


	static Modification breakBlock(BaseBlockPos position, ItemTool tool) {
		return new BlockBreakModification(position, tool);
	}


	static Modification breakBlock(BaseBlockPos position) {
		return new BlockBreakModification(position);
	}


}
