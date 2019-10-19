package stevebot.data.blockpos;

import stevebot.misc.Direction;

public class FastBlockPos extends BaseBlockPos {


	/**
	 * Creates a position with the coordinates (0,0,0)
	 */
	public FastBlockPos() {
		super(0, 0, 0);
	}




	/**
	 * Creates a position with the coordinates (x,y,z)
	 */
	public FastBlockPos(int x, int y, int z) {
		super(x, y, z);
	}




	/**
	 * Creates a position with the same coordinates as the given position
	 */
	public FastBlockPos(BaseBlockPos pos) {
		super(pos.getX(), pos.getY(), pos.getZ());
	}




	/**
	 * Adds the given direction to this position
	 *
	 * @param direction the direction
	 * @return this {@link FastBlockPos} for chaining
	 */
	public FastBlockPos add(Direction direction) {
		return this.add(direction.dx, direction.dy, direction.dz);
	}




	/**
	 * Adds the given values to this position
	 *
	 * @return this {@link FastBlockPos} for chaining
	 */
	public FastBlockPos add(int x, int y, int z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}




	/**
	 * Sets this position to the coordinates of the given position
	 *
	 * @return this {@link FastBlockPos} for chaining
	 */
	public FastBlockPos set(BaseBlockPos pos) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		return this;
	}




	/**
	 * Sets this position to the given coordinates
	 *
	 * @return this {@link FastBlockPos} for chaining
	 */
	public FastBlockPos set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}




	/**
	 * @return a {@link FastBlockPos} with the same coordinates
	 */
	public FastBlockPos copy() {
		return new FastBlockPos(this);
	}


}
