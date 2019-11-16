package stevebot.minecraft;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class MinecraftAdapterImpl extends MinecraftAdapter {


	/**
	 * Return the singleton {@link Minecraft} instance
	 */
	private Minecraft getMinecraft() {
		return Minecraft.getMinecraft();
	}




	@Override
	public World getWorld() {
		return getMinecraft().world;
	}




	@Override
	public EntityPlayerSP getPlayer() {
		return getMinecraft().player;
	}




	@Override
	public InventoryPlayer getPlayerInventory() {
		return getPlayer().inventory;
	}




	@Override
	public boolean isPlayerCreativeMode() {
		return getPlayer().isCreative();
	}




	@Override
	public Block getBlock(BlockPos pos) {
		return getWorld().getBlockState(pos).getBlock();
	}




	@Override
	public void setBlock(BlockPos pos, Block block) {
		getWorld().setBlockState(pos, block.getDefaultState());
	}




	@Override
	public boolean isChunkLoaded(int chunkX, int chunkZ) {
		return getWorld().getChunkFromChunkCoords(chunkX, chunkZ).isLoaded();
	}




	@Override
	public Entity getRenderViewEntity() {
		return getMinecraft().getRenderViewEntity();
	}




	@Override
	public GameSettings getGameSettings() {
		return getMinecraft().gameSettings;
	}




	@Override
	public void setMouseHelper(MouseHelper mouseHelper) {
		getMinecraft().mouseHelper = mouseHelper;
	}




	@Override
	public List<Block> getRegisteredBlocks() {
		List<Block> blocks = new ArrayList<>();
		Block.REGISTRY.forEach(blocks::add);
		return blocks;
	}




	@Override
	public int getBlockId(Block block) {
		return Block.REGISTRY.getIDForObject(block);
	}




	@Override
	public String getBlockName(Block block) {
		return Block.REGISTRY.getNameForObject(block).toString();
	}




	@Override
	public List<Item> getRegisteredItems() {
		List<Item> items = new ArrayList<>();
		Item.REGISTRY.forEach(items::add);
		return items;
	}




	@Override
	public int getItemId(Item item) {
		return Item.REGISTRY.getIDForObject(item);
	}




	@Override
	public String getItemName(Item item) {
		return Item.REGISTRY.getNameForObject(item).toString();
	}

}
