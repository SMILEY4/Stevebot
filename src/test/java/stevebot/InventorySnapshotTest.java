package stevebot;

import org.junit.jupiter.api.Test;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blocks.BlockLibrary;
import stevebot.data.blocks.BlockWrapper;
import stevebot.data.items.wrapper.ItemBlockWrapper;
import stevebot.data.items.wrapper.ItemToolWrapper;
import stevebot.data.items.wrapper.ItemWrapper;
import stevebot.data.modification.Modification;
import stevebot.data.player.PlayerSnapshot;
import stevebot.minecraft.MinecraftAdapter;
import stevebot.minecraft.UnsupportedMinecraftAdapter;

import static org.assertj.core.api.Assertions.assertThat;

public class InventorySnapshotTest {


	private static final BlockWrapper BLOCK_STONE = new BlockWrapper(1, "minecraft:stone", null);
	private static final BlockWrapper BLOCK_DIRT = new BlockWrapper(3, "minecraft:dirt", null);
	private static final BlockWrapper BLOCK_SAND = new BlockWrapper(12, "minecraft:sand", null);

	private static final ItemWrapper ITEM_STONE = new ItemBlockWrapper(1, "minecraft:stone", null);
	private static final ItemWrapper ITEM_DIRT = new ItemBlockWrapper(3, "minecraft:dirt", null);
	private static final ItemWrapper ITEM_SAND = new ItemBlockWrapper(12, "minecraft:sand", null);
	private static final ItemWrapper ITEM_IRON_AXE = new ItemToolWrapper(258, "minecraft:iron_axe", null);
	private static final ItemWrapper ITEM_DIA_PICKAXE = new ItemToolWrapper(278, "minecraft:diamond_pickaxe", null);




	static {
		BLOCK_STONE.setItem(ITEM_STONE);
		BLOCK_DIRT.setItem(ITEM_DIRT);
		BLOCK_SAND.setItem(ITEM_SAND);
		((ItemBlockWrapper) ITEM_STONE).setBlockWrapper(BLOCK_STONE);
		((ItemBlockWrapper) ITEM_DIRT).setBlockWrapper(BLOCK_DIRT);
		((ItemBlockWrapper) ITEM_DIRT).setBlockWrapper(BLOCK_DIRT);
		((ItemBlockWrapper) ITEM_SAND).setBlockWrapper(BLOCK_SAND);
	}




	@Test
	void testApplyModificationPlace() {

		MinecraftAdapter.initialize(new UnsupportedMinecraftAdapter() {
			@Override
			public boolean isPlayerCreativeMode() {
				return false;
			}
		});

		final PlayerSnapshot snapshot = new PlayerSnapshot();
		snapshot.setHotbarItemStack(3, ITEM_DIRT, 2);
		snapshot.setHotbarItemStack(6, ITEM_DIA_PICKAXE, 1);
		snapshot.setHotbarItemStack(7, ITEM_STONE, 10);

		snapshot.applyModification(Modification.placeBlock(new BaseBlockPos(), BLOCK_STONE));
		snapshot.applyModification(Modification.placeBlock(new BaseBlockPos(), BLOCK_DIRT));

		assertThat(snapshot.getStackSize(3)).isEqualTo(1);
		assertThat(snapshot.getItem(3).getId()).isEqualTo(ITEM_DIRT.getId());

		assertThat(snapshot.getStackSize(7)).isEqualTo(9);
		assertThat(snapshot.getItem(7).getId()).isEqualTo(ITEM_STONE.getId());

		snapshot.applyModification(Modification.placeBlock(new BaseBlockPos(), BLOCK_DIRT));

		assertThat(snapshot.getStackSize(3)).isEqualTo(-1);
		assertThat(snapshot.getItem(3)).isNull();
	}




	@Test
	void testApplyModificationPlaceCreative() {

		MinecraftAdapter.initialize(new UnsupportedMinecraftAdapter() {
			@Override
			public boolean isPlayerCreativeMode() {
				return true;
			}
		});

		final PlayerSnapshot snapshot = new PlayerSnapshot();
		snapshot.setHotbarItemStack(3, ITEM_DIRT, 2);
		snapshot.setHotbarItemStack(6, ITEM_DIA_PICKAXE, 1);
		snapshot.setHotbarItemStack(7, ITEM_STONE, 10);

		snapshot.applyModification(Modification.placeBlock(new BaseBlockPos(), BLOCK_STONE));
		snapshot.applyModification(Modification.placeBlock(new BaseBlockPos(), BLOCK_DIRT));

		assertThat(snapshot.getStackSize(3)).isEqualTo(2);
		assertThat(snapshot.getItem(3).getId()).isEqualTo(ITEM_DIRT.getId());

		assertThat(snapshot.getStackSize(7)).isEqualTo(10);
		assertThat(snapshot.getItem(7).getId()).isEqualTo(ITEM_STONE.getId());

	}




	@Test
	void testFindThrowawayBlock() {

		final PlayerSnapshot snapshot = new PlayerSnapshot();
		snapshot.setHotbarItemStack(1, ITEM_IRON_AXE, 1);
		snapshot.setHotbarItemStack(3, ITEM_DIRT, 2);
		snapshot.setHotbarItemStack(6, ITEM_DIA_PICKAXE, 1);
		snapshot.setHotbarItemStack(7, ITEM_STONE, 10);

		assertThat(snapshot.hasThrowawayBlockInHotbar(true)).isTrue();
		assertThat(snapshot.findThrowawayBlock(true)).isEqualTo(3);

		snapshot.setHotbarItemStack(3, null, 0);

		assertThat(snapshot.hasThrowawayBlockInHotbar(true)).isTrue();
		assertThat(snapshot.findThrowawayBlock(true)).isEqualTo(7);

		snapshot.setHotbarItemStack(7, null, 0);

		assertThat(snapshot.hasThrowawayBlockInHotbar(true)).isFalse();
		assertThat(snapshot.findThrowawayBlock(true)).isEqualTo(-1);

	}




	@Test
	void testFindThrowawayBlockNoGravity() {

		final PlayerSnapshot snapshot = new PlayerSnapshot();
		snapshot.setHotbarItemStack(1, ITEM_IRON_AXE, 1);
		snapshot.setHotbarItemStack(2, ITEM_SAND, 2);
		snapshot.setHotbarItemStack(6, ITEM_DIA_PICKAXE, 1);
		snapshot.setHotbarItemStack(7, ITEM_STONE, 10);

		assertThat(snapshot.hasThrowawayBlockInHotbar(false)).isTrue();
		assertThat(snapshot.findThrowawayBlock(false)).isEqualTo(7);

		snapshot.setHotbarItemStack(7, null, 0);

		assertThat(snapshot.hasThrowawayBlockInHotbar(false)).isFalse();
		assertThat(snapshot.findThrowawayBlock(false)).isEqualTo(-1);


	}




	@Test
	void testFindSlotById() {

		final PlayerSnapshot snapshot = new PlayerSnapshot();
		snapshot.setHotbarItemStack(1, ITEM_IRON_AXE, 1);
		snapshot.setHotbarItemStack(3, ITEM_DIRT, 2);
		snapshot.setHotbarItemStack(6, ITEM_DIA_PICKAXE, 1);
		snapshot.setHotbarItemStack(7, ITEM_STONE, 10);

		assertThat(snapshot.findSlotById(ITEM_IRON_AXE.getId())).isEqualTo(1);
		assertThat(snapshot.findSlotById(ITEM_DIRT.getId())).isEqualTo(3);
		assertThat(snapshot.findSlotById(ITEM_DIA_PICKAXE.getId())).isEqualTo(6);
		assertThat(snapshot.findSlotById(ITEM_STONE.getId())).isEqualTo(7);

		assertThat(snapshot.findSlotById(487592)).isEqualTo(-1);

	}




	@Test
	void testGetAsBlock() {

		final PlayerSnapshot snapshot = new PlayerSnapshot();
		snapshot.setHotbarItemStack(1, ITEM_IRON_AXE, 1);
		snapshot.setHotbarItemStack(3, ITEM_DIRT, 2);
		snapshot.setHotbarItemStack(6, ITEM_DIA_PICKAXE, 1);
		snapshot.setHotbarItemStack(7, ITEM_STONE, 10);

		assertThat(snapshot.getAsBlock(1).getId()).isEqualTo(BlockLibrary.INVALID_BLOCK.getId());
		assertThat(snapshot.getAsBlock(3).getId()).isEqualTo(BLOCK_DIRT.getId());
		assertThat(snapshot.getAsBlock(6).getId()).isEqualTo(BlockLibrary.INVALID_BLOCK.getId());
		assertThat(snapshot.getAsBlock(7).getId()).isEqualTo(BLOCK_STONE.getId());

		assertThat(snapshot.getAsBlock(8).getId()).isEqualTo(BlockLibrary.INVALID_BLOCK.getId());

	}


}




