package stevebot.pathfinding.execution;

import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.path.Path;

public class PathCrawler {


	private Path path;
	private int currentIndexFrom = 0;
	private Node currentNodeTo;




	/**
	 * Starts the given path(-segment)
	 *
	 * @param path the path(-segment)
	 */
	public void startPath(Path path) {
		this.path = path;
		currentIndexFrom = 0;
		currentNodeTo = path.getNodes().get(currentIndexFrom + 1);
	}




	/**
	 * Steps to the next action in the current path(-segment)
	 *
	 * @return false, if the action is the last action in the current path(-segment)
	 */
	public boolean nextAction() {
		currentIndexFrom++;
		if (currentIndexFrom == path.getNodes().size() - 1) { // next from is last node
			currentNodeTo = null;
			return false;
		} else {
			currentNodeTo = path.getNodes().get(currentIndexFrom + 1);
			return true;
		}
	}




	/**
	 * @return the starting node of the current action
	 */
	public Node getCurrentNodeFrom() {
		return path.getNodes().get(currentIndexFrom);
	}




	/**
	 * @return the destination node of the current action
	 */
	public Node getCurrentNodeTo() {
		return currentNodeTo;
	}


}
