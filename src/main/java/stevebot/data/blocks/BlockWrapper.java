package stevebot.data.blocks;

import net.minecraft.block.Block;

public class BlockWrapper {


	public final int id;
	public final String name;
	public final Block mcBlock;




	BlockWrapper(int id, String name, Block block) {
		this.id = id;
		this.name = name;
		this.mcBlock = block;
	}


}