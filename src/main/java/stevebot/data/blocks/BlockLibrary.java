package stevebot.data.blocks;

import net.minecraft.block.Block;

public interface BlockLibrary {


	int ID_UNLOADED_BOCK = -1;
	int ID_INVALID_BLOCK = -2;


	void initialize();

	Block getBlockById(int id);

	Block getBlockByName(String name);

	int getIdOfBlock(Block block);

	int getIdFromName(String name);

	String getNameOfBlock(Block block);

	String getNameFromId(int id);

}
