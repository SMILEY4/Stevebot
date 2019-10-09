package stevebot.rendering;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.util.math.BlockPos;
import stevebot.data.blockpos.BaseBlockPos;

public interface Renderable {


	float DEFAULT_LINE_WIDTH = 3;
	float DEFAULT_POINT_SIZE = 4;


	/**
	 * Renders this renderable with the given {@link Renderer}
	 *
	 * @param renderer the {@link Renderer}
	 */
	void render(Renderer renderer);


	/**
	 * Creates an array of size n filled with the given color
	 *
	 * @param color the color
	 * @param n     the site of the resulting array
	 * @return the array
	 */
	static Color[] fillColorArray(Color color, int n) {
		Color[] array = new Color[n];
		for (int i = 0; i < n; i++) {
			array[i] = color;
		}
		return array;
	}


	/***
	 * Converts the array of {@link BlockPos} into an array of {@link Vector3d}. The resulting positions will be the centers of the blocks.
	 * @param bpArray the {@link BlockPos} array
	 * @return the {@link Vector3d} array
	 */
	static Vector3d[] toVecArray(BlockPos[] bpArray) {
		return toVecArray(bpArray, 0.5, 0.5, 0.5);
	}


	/***
	 * Converts the array of {@link BlockPos} into an array of {@link Vector3d}. The given offsets will be added to the {@link BlockPos}.
	 * @param bpArray the {@link BlockPos} array
	 * @param offX the x offset added to all {@link BlockPos}s
	 * @param offY the y offset added to all {@link BlockPos}s
	 * @param offZ the z offset added to all {@link BlockPos}s
	 * @return the {@link Vector3d} array
	 */
	static Vector3d[] toVecArray(BlockPos[] bpArray, double offX, double offY, double offZ) {
		Vector3d[] vecArray = new Vector3d[bpArray.length];
		for (int i = 0, n = bpArray.length; i < n; i++) {
			final BlockPos pos = bpArray[i];
			vecArray[i] = new Vector3d(pos.getX() + offX, pos.getY() + offY, pos.getZ() + offZ);
		}
		return vecArray;
	}


	/***
	 * Converts the array of {@link BaseBlockPos} into an array of {@link Vector3d}. The resulting positions will be the centers of the blocks.
	 * @param bpArray the {@link BaseBlockPos} array
	 * @return the {@link Vector3d} array
	 */
	static Vector3d[] toVecArray(BaseBlockPos[] bpArray) {
		return toVecArray(bpArray, 0.5, 0.5, 0.5);
	}


	/***
	 * Converts the array of {@link BaseBlockPos} into an array of {@link Vector3d}. The given offsets will be added to the {@link BaseBlockPos}.
	 * @param bpArray the {@link BaseBlockPos} array
	 * @param offX the x offset added to all {@link BaseBlockPos}s
	 * @param offY the y offset added to all {@link BaseBlockPos}s
	 * @param offZ the z offset added to all {@link BaseBlockPos}s
	 * @return the {@link Vector3d} array
	 */
	static Vector3d[] toVecArray(BaseBlockPos[] bpArray, double offX, double offY, double offZ) {
		Vector3d[] vecArray = new Vector3d[bpArray.length];
		for (int i = 0, n = bpArray.length; i < n; i++) {
			final BaseBlockPos pos = bpArray[i];
			vecArray[i] = new Vector3d(pos.getX() + offX, pos.getY() + offY, pos.getZ() + offZ);
		}
		return vecArray;
	}

}
