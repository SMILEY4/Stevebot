package astarviz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Path {


	public static Path buildPath(Map map, Node start, Node end) {

		List<Node> nodes = new ArrayList<>();
		Node current = end;
		while (current != start) {
			nodes.add(current);
			current = current.prev;
		}
		nodes.add(start);
		Collections.reverse(nodes);

		return new Path(nodes);
	}




	public final List<Node> nodes;
	public final double cost;




	public Path(List<Node> nodes) {
		this.nodes = nodes;
		this.cost = nodes.get(nodes.size() - 1).gcost;
	}


}
