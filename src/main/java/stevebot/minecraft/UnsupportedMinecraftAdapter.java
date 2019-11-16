package stevebot.minecraft;

import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class UnsupportedMinecraftAdapter extends MinecraftAdapter {


	@Override
	public World getWorld() {
		throw new UnsupportedOperationException();
	}




	@Override
	public EntityPlayerSP getPlayer() {
		throw new UnsupportedOperationException();
	}




	@Override
	public InventoryPlayer getPlayerInventory() {
		throw new UnsupportedOperationException();
	}




	@Override
	public boolean isPlayerCreativeMode() {
		throw new UnsupportedOperationException();
	}




	@Override
	public Block getBlock(BlockPos pos) {
		throw new UnsupportedOperationException();
	}




	@Override
	public void setBlock(BlockPos pos, Block block) {
		throw new UnsupportedOperationException();
	}




	@Override
	public boolean isChunkLoaded(int chunkX, int chunkZ) {
		throw new UnsupportedOperationException();
	}




	@Override
	public Entity getRenderViewEntity() {
		throw new UnsupportedOperationException();
	}




	@Override
	public GameSettings getGameSettings() {
		throw new UnsupportedOperationException();
	}




	@Override
	public void setMouseHelper(MouseHelper mouseHelper) {
		throw new UnsupportedOperationException();
	}




	@Override
	public List<Block> getRegisteredBlocks() {
		throw new UnsupportedOperationException();
	}




	@Override
	public int getBlockId(Block block) {
		throw new UnsupportedOperationException();
	}




	@Override
	public String getBlockName(Block block) {
		throw new UnsupportedOperationException();
	}




	@Override
	public List<Item> getRegisteredItems() {
		throw new UnsupportedOperationException();
	}




	@Override
	public int getItemId(Item item) {
		throw new UnsupportedOperationException();
	}




	@Override
	public String getItemName(Item item) {
		throw new UnsupportedOperationException();
	}


}
