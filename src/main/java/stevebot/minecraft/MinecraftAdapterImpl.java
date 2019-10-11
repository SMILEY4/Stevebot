package stevebot.minecraft;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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

}
