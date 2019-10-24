package stevebot.data.modification;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blocks.BlockWrapper;
import stevebot.data.items.wrapper.ItemToolWrapper;

public interface Modification {


	Modification[] EMPTY = new Modification[]{};


	static Modification placeBlock(BaseBlockPos position, BlockWrapper block) {
		return new BlockPlaceModification(position, block);
	}


	static Modification breakBlock(BaseBlockPos position, ItemToolWrapper tool) {
		return new BlockBreakModification(position, tool);
	}


	static Modification breakBlock(BaseBlockPos position) {
		return new BlockBreakModification(position);
	}


	static Modification healthChange(int healthChange) {
		return new HealthChangeModification(healthChange);
	}


}
