package stevebot.data.blockpos;

import stevebot.Direction;

public class FastBlockPos extends BaseBlockPos {


	public FastBlockPos() {
		super(0, 0, 0);
	}




	public FastBlockPos(int x, int y, int z) {
		super(x, y, z);
	}




	public FastBlockPos(BaseBlockPos pos) {
		super(pos.getX(), pos.getY(), pos.getZ());
	}




	public FastBlockPos add(Direction direction) {
		return this.add(direction.dx, direction.dy, direction.dz);
	}




	public FastBlockPos add(int x, int y, int z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}




	public FastBlockPos set(BaseBlockPos pos) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		return this;
	}




	public FastBlockPos set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}




	public FastBlockPos copy() {
		return new FastBlockPos(this);
	}


}
