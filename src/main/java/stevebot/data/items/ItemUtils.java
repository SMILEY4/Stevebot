package stevebot.data.items;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class ItemUtils {


	private static ItemLibrary itemLibrary;




	public static void initialize(ItemLibrary itemLibrary) {
		ItemUtils.itemLibrary = itemLibrary;
	}




	public static ItemLibrary getItemLibrary() {
		return ItemUtils.itemLibrary;
	}




	/**
	 * @param itemStack the used item
	 * @param state     the block to break
	 * @return the time (in ticks) it takes to break the given block with the given item
	 */
	public static float getBreakDuration(ItemStack itemStack, IBlockState state) {
		// sources:
		// https://greyminecraftcoder.blogspot.com/2015/01/calculating-rate-of-damage-when-mining.html
		// https://minecraft.gamepedia.com/Breaking
		final float playerBreakSpeed = getDigSpeed(itemStack, state);
		final int canHarvestMod = itemStack.canHarvestBlock(state) ? 30 : 100;
		final float dmgPerTick = ((playerBreakSpeed / state.getBlockHardness(null, null)) * (1f / canHarvestMod));
		return 1f / dmgPerTick;
	}




	private static float getDigSpeed(ItemStack itemStack, IBlockState state) {
		return getDigSpeed(itemStack, state, false, 0, false, 0, 0, false, false, true);
	}




	/**
	 * see {@link net.minecraft.entity.player.EntityPlayer#getDigSpeed(IBlockState, BlockPos)}
	 */
	private static float getDigSpeed(ItemStack itemStack, IBlockState state,
									 boolean hasEffectHaste, int effectHasteAmplifier,
									 boolean hasEffectMiningFatique, int effectMiningFatiqueAmplifier,
									 int effeciencyModifier, boolean aquaAffinityModifier,
									 boolean isInsideWater,
									 boolean isOnGround
	) {

		float f = getDestroySpeed(itemStack, state);

		if (f > 1.0F) {
			int i = effeciencyModifier;
			if (i > 0 && !itemStack.isEmpty()) {
				f += (float) (i * i + 1);
			}
		}

		if (hasEffectHaste) {
			f *= 1.0F + (float) (effectHasteAmplifier + 1) * 0.2F;
		}

		if (hasEffectMiningFatique) {
			float f1;
			switch (effectMiningFatiqueAmplifier) {
				case 0:
					f1 = 0.3F;
					break;
				case 1:
					f1 = 0.09F;
					break;
				case 2:
					f1 = 0.0027F;
					break;
				case 3:
				default:
					f1 = 8.1E-4F;
			}
			f *= f1;
		}

		if (isInsideWater && !aquaAffinityModifier) {
			f /= 5.0F;
		}

		if (!isOnGround) {
			f /= 5.0F;
		}

		return (f < 0 ? 0 : f);
	}




	private static float getDestroySpeed(ItemStack itemStack, IBlockState state) {
		float f = 1.0F;
		if (!itemStack.isEmpty()) {
			f *= itemStack.getDestroySpeed(state);
		}
		return f;
	}


}
