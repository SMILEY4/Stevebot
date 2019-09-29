package stevebot.data.blockpos;

import net.minecraft.util.math.BlockPos;

public class BaseBlockPos {


	protected int x, y, z;




	public BaseBlockPos() {
		this(0, 0, 0);
	}




	public BaseBlockPos(BaseBlockPos pos) {
		this(pos.getX(), pos.getY(), pos.getZ());
	}




	public BaseBlockPos(BlockPos pos) {
		this(pos.getX(), pos.getY(), pos.getZ());
	}




	public BaseBlockPos(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}




	public int getX() {
		return this.x;
	}




	public int getY() {
		return this.y;
	}




	public int getZ() {
		return this.z;
	}




	public double getCenterX() {
		return x + 0.5;
	}




	public double getCenterY() {
		return y + 0.5;
	}




	public double getCenterZ() {
		return z + 0.5;
	}




	public BlockPos copyAsMCBlockPos() {
		return new BlockPos(x, y, z);
	}




	public FastBlockPos copyAsFastBlockPos() {
		return new FastBlockPos(x, y, z);
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
