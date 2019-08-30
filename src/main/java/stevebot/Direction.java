package stevebot;

public enum Direction {



	/*
	 * X-AXIS:  (-)  west <--> east (+)
	 * Y-AXIS:  (-)  down <--> up (+)
	 * Z-AXIS:  (-) north <--> south (+)
	 * */

	NORTH(0, -1),
	EAST(+1, 0),
	SOUTH(0, +1),
	WEST(-1, 0),

	NORTH_EAST(+1, -1),
	NORTH_WEST(-1, -1),
	SOUTH_EAST(+1, +1),
	SOUTH_WEST(-1, +1),

	UP(+1),
	DOWN(-1),
	NONE(0);


	public static final Direction[] ALL = new Direction[]{NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST, UP, DOWN};
	public static final Direction[] CARDINALS = new Direction[]{NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST};
	public static final Direction[] CARDINALS_SIMPLE = new Direction[]{NORTH, EAST, SOUTH, WEST};
	public static final Direction[] VERTICAL = new Direction[]{UP, DOWN};

	public final int dx;
	public final int dy;
	public final int dz;
	public final boolean diagonal;




	Direction(int dy) {
		this(0, dy, 0);
	}




	Direction(int dx, int dz) {
		this(dx, 0, dz);
	}




	Direction(int dx, int dy, int dz) {
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		this.diagonal = dx != 0 && dz != 0;
	}




	public Direction[] split() {
		switch (this) {
			case NORTH_EAST:
				return new Direction[]{NORTH, EAST};
			case NORTH_WEST:
				return new Direction[]{NORTH, WEST};
			case SOUTH_EAST:
				return new Direction[]{SOUTH, EAST};
			case SOUTH_WEST:
				return new Direction[]{SOUTH, WEST};
			default:
				return new Direction[]{this, this};
		}
	}




	public Direction get(int dx, int dy, int dz) {
		if (dy < 0) {
			return DOWN;
		}
		if (dy > 0) {
			return UP;
		}
		if (dx < 0) {
			if (dz < 0) {
				return NORTH_WEST;
			}
			if (dz == 0) {
				return WEST;
			}
			if (dz > 0) {
				return SOUTH_WEST;
			}
		}
		if (dx == 0) {
			if (dz < 0) {
				return NORTH;
			}
			if (dz == 0) {
				return NONE;
			}
			if (dz > 0) {
				return SOUTH;
			}
		}
		if (dx > 0) {
			if (dz < 0) {
				return NORTH_EAST;
			}
			if (dz == 0) {
				return EAST;
			}
			if (dz > 0) {
				return SOUTH_EAST;
			}
		}
		return NONE;
	}




	public Direction merge(Direction a, Direction b) {
		return get(a.dx + b.dx, a.dy + b.dy, a.dz + b.dz);
	}

}