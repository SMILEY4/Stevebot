package stevebot;

import com.ruegnerlukas.simplemath.vectors.vec3.IVector3;
import com.ruegnerlukas.simplemath.vectors.vec3.Vector3i;
import net.minecraft.util.math.BlockPos;

public class FastBlockPos extends Vector3i {


	/**
	 * creates a {@link FastBlockPos} at (0,0,0)
	 */
	public FastBlockPos() {
		super(0);
	}




	/**
	 * creates a {@link FastBlockPos} with the same values as the given vector
	 */
	public FastBlockPos(IVector3 vec) {
		super(vec.getIntX(), vec.getIntY(), vec.getIntZ());
	}




	/**
	 * creates a {@link FastBlockPos} with the given x, y and z values
	 */
	public FastBlockPos(int x, int y, int z) {
		super(x, y, z);
	}




	/**
	 * creates a {@link FastBlockPos} at the same position as the given {@link BlockPos}
	 */
	public FastBlockPos(BlockPos blockPos) {
		super(blockPos.getX(), blockPos.getY(), blockPos.getZ());
	}




	/**
	 * Adds the given direction to this position
	 *
	 * @param direction the direction to add
	 * @return this FastBlockPos for chaining
	 */
	public FastBlockPos add(Direction direction) {
		this.add(direction.dx, direction.dy, direction.dz);
		return this;
	}




	/**
	 * Creates a {@link BlockPos} at the same position as this position.
	 *
	 * @return the created {@link BlockPos}
	 */
	public BlockPos toMCBlockPos() {
		return new BlockPos(x, y, z);
	}




	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		} else if (!(other instanceof FastBlockPos)) {
			return false;
		} else {
			final FastBlockPos otherBlockPos = (FastBlockPos) other;
			return otherBlockPos.x == x && otherBlockPos.y == y && otherBlockPos.z == z;
		}
	}




	@Override
	public int hashCode() {
		return (this.y + this.z * 31) * 31 + this.x;
	}

}
