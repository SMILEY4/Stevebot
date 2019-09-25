package stevebot.pathfinding.execution;

import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.path.Path;

public class PathCrawler {


	private Path path;
	private int currentIndexFrom = 0;
	private Node currentNodeTo;




	public void startPath(Path path) {
		this.path = path;
		currentIndexFrom = 0;
		currentNodeTo = path.getNodes().get(currentIndexFrom + 1);
	}




	public boolean nextAction() {
		currentIndexFrom++;
		if (currentIndexFrom == path.getNodes().size() - 1) { // next is last node
			currentNodeTo = null;
			return false;
		} else {
			currentNodeTo = path.getNodes().get(currentIndexFrom + 1);
			currentNodeTo.action.resetAction();
			return true;
		}
	}




	public Node getCurrentNodeFrom() {
		return path.getNodes().get(currentIndexFrom);
	}




	public Node getCurrentNodeTo() {
		return currentNodeTo;
	}


}
