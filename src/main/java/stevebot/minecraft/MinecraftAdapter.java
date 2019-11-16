package stevebot.minecraft;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public abstract class MinecraftAdapter {


	private static MinecraftAdapter adapter = new UnsupportedMinecraftAdapter();




	/**
	 * Initializes the adapter with the given {@link MinecraftAdapter}-instance
	 *
	 * @param adapter the adapter-instance
	 */
	public static void initialize(MinecraftAdapter adapter) {
		MinecraftAdapter.adapter = adapter;
	}




	/**
	 * @return the singleton {@link MinecraftAdapter}-instance
	 */
	public static MinecraftAdapter get() {
		return adapter;
	}




	/**
	 * @return the minecraft world
	 */
	public abstract World getWorld();

	/**
	 * @return the entity representing the player in singleplayer
	 */
	public abstract EntityPlayerSP getPlayer();


	/**
	 * @return the inventory of the player
	 */
	public abstract InventoryPlayer getPlayerInventory();


	/**
	 * @return true, if the player is in creative mode
	 */
	public abstract boolean isPlayerCreativeMode();

	/**
	 * @param pos the position of the block
	 * @return the block at the given position
	 */
	public abstract Block getBlock(BlockPos pos);

	/**
	 * Sets the block at the given position to the given blocks default {@link IBlockState}
	 *
	 * @param pos   the position
	 * @param block the block
	 */
	public abstract void setBlock(BlockPos pos, Block block);

	/**
	 * @param chunkX the x position of the chunk
	 * @param chunkZ the x position of the chunk
	 * @return whether the chunk is currently loaded
	 */
	public abstract boolean isChunkLoaded(int chunkX, int chunkZ);

	/**
	 * @return the render view entity
	 */
	public abstract Entity getRenderViewEntity();

	/**
	 * @return the games settings
	 */
	public abstract GameSettings getGameSettings();

	/**
	 * Sets the games mouse-helper to the given helper
	 *
	 * @param mouseHelper the {@link MouseHelper}
	 */
	public abstract void setMouseHelper(MouseHelper mouseHelper);


	/**
	 * @return a list of all registered items
	 */
	public abstract List<Item> getRegisteredItems();

	/**
	 * @param item the item
	 * @return the id of the item
	 */
	public abstract int getItemId(Item item);

	/**
	 * @param item the item
	 * @return the name of the item
	 */
	public abstract String getItemName(Item item);

	/**
	 * @return a list of all registered blocks
	 */
	public abstract List<Block> getRegisteredBlocks();


	/**
	 * @param block the block
	 * @return the id of the block
	 */
	public abstract int getBlockId(Block block);

	/**
	 * @param block the block
	 * @return the name of the block
	 */
	public abstract String getBlockName(Block block);

}
