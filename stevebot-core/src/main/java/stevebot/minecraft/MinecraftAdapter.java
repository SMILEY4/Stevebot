package stevebot.minecraft;

import java.util.List;
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

public interface MinecraftAdapter {


    /**
     * @return the minecraft world
     */
    World getWorld();

    /**
     * @return the entity representing the player in singleplayer
     */
    EntityPlayerSP getPlayer();


    /**
     * @return the inventory of the player
     */
    InventoryPlayer getPlayerInventory();


    /**
     * @return true, if the player is in creative mode
     */
    boolean isPlayerCreativeMode();

    /**
     * @param pos the position of the block
     * @return the block at the given position
     */
    Block getBlock(BlockPos pos);

    /**
     * Sets the block at the given position to the given blocks default {@link IBlockState}
     *
     * @param pos   the position
     * @param block the block
     */
    void setBlock(BlockPos pos, Block block);

    /**
     * @param chunkX the x position of the chunk
     * @param chunkZ the x position of the chunk
     * @return whether the chunk is currently loaded
     */
    boolean isChunkLoaded(int chunkX, int chunkZ);

    /**
     * @return the render view entity
     */
    Entity getRenderViewEntity();

    /**
     * @return the games settings
     */
    GameSettings getGameSettings();

    /**
     * Sets the games mouse-helper to the given helper
     *
     * @param mouseHelper the {@link MouseHelper}
     */
    void setMouseHelper(MouseHelper mouseHelper);


    /**
     * @return a list of all registered items
     */
    List<Item> getRegisteredItems();

    /**
     * @param item the item
     * @return the id of the item
     */
    int getItemId(Item item);

    /**
     * @param item the item
     * @return the name of the item
     */
    String getItemName(Item item);

    /**
     * @return a list of all registered blocks
     */
    List<Block> getRegisteredBlocks();


    /**
     * @param block the block
     * @return the id of the block
     */
    int getBlockId(Block block);

    /**
     * @param block the block
     * @return the name of the block
     */
    String getBlockName(Block block);

}
