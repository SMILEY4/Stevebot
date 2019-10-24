package stevebot.data.blockpos;

import net.minecraft.util.math.BlockPos;

public class BaseBlockPos {


	protected int x, y, z;




	/**
	 * Creates a position with the coordinates (0,0,0)
	 */
	public BaseBlockPos() {
		this(0, 0, 0);
	}




	/**
	 * Creates a position with the same coordinates as the given position
	 */
	public BaseBlockPos(BaseBlockPos pos) {
		this(pos.getX(), pos.getY(), pos.getZ());
	}




	/**
	 * Creates a position with the same coordinates as the given position
	 */
	public BaseBlockPos(BlockPos pos) {
		this(pos.getX(), pos.getY(), pos.getZ());
	}




	/**
	 * Creates a position with the coordinates (x,y.z)
	 */
	public BaseBlockPos(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}




	/**
	 * @return the (block) x coordinate
	 */
	public int getX() {
		return this.x;
	}




	/**
	 * @return the (block) y coordinate
	 */
	public int getY() {
		return this.y;
	}




	/**
	 * @return the (block) z coordinate
	 */
	public int getZ() {
		return this.z;
	}




	/**
	 * @return the x coordinate of the center of the block
	 */
	public double getCenterX() {
		return x + 0.5;
	}




	/**
	 * @return the y coordinate of the center of the block
	 */
	public double getCenterY() {
		return y + 0.5;
	}




	/**
	 * @return the z coordinate of the center of the block
	 */
	public double getCenterZ() {
		return z + 0.5;
	}




	/**
	 * @return a {@link BlockPos} with the same coordinates
	 */
	public BlockPos copyAsMCBlockPos() {
		return new BlockPos(x, y, z);
	}




	/**
	 * @return a {@link FastBlockPos} with the same coordinates
	 */
	public FastBlockPos copyAsFastBlockPos() {
		return new FastBlockPos(x, y, z);
	}




	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		} else if (!(other instanceof BaseBlockPos)) {
			return false;
		} else {
			final BaseBlockPos otherBlockPos = (BaseBlockPos) other;
			return otherBlockPos.x == x && otherBlockPos.y == y && otherBlockPos.z == z;
		}
	}




	@Override
	public int hashCode() {
		return (this.y + this.z * 31) * 31 + this.x;
	}




	@Override
	public String toString() {
		return "BaseBlockPos[" + getX() + "," + getY() + "," + getZ() + "]";
	}




	public double dist(BaseBlockPos pos) {
		final int dx = pos.x - this.x;
		final int dy = pos.y - this.y;
		final int dz = pos.z - this.z;
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

}
