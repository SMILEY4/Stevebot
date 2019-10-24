package astarviz;

public class Pathfinding {


	public static Map[] findPath(Map origin) {
		Map[] maps = new Map[10]; // TODO return array with the original map at first place and the rest is a map for each step
		maps[0] = origin;
		for (int i = 1; i < maps.length; i++) {
			maps[i] = new Map(origin.width, origin.height);
		}
		return maps;
	}

}
