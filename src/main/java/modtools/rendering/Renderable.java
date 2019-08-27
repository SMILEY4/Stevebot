package modtools.rendering;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.util.math.BlockPos;

public interface Renderable {


	float DEFAULT_LINE_WIDTH = 3;
	float DEFAULT_POINT_SIZE = 4;

	void render(MTRenderer renderer);


	static Color[] fillColorArray(Color color, int n) {
		Color[] array = new Color[n];
		for (int i = 0; i < n; i++) {
			array[i] = color;
		}
		return array;
	}


	static Vector3d[] toVecArray(BlockPos[] bpArray) {
		Vector3d[] vecArray = new Vector3d[bpArray.length];
		for (int i = 0, n = bpArray.length; i < n; i++) {
			final BlockPos pos = bpArray[i];
			vecArray[i] = new Vector3d(pos.getX(), pos.getY(), pos.getZ());
		}
		return vecArray;
	}

}
