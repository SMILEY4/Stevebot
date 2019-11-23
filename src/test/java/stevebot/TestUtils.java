package stevebot;

import stevebot.data.blockpos.BaseBlockPos;
import stevebot.misc.Direction;
import stevebot.misc.ProcState;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.pathfinding.actions.playeractions.Action;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.path.CompletedPath;
import stevebot.pathfinding.path.EmptyPath;
import stevebot.pathfinding.path.PartialPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestUtils {


	public static CompletedPath completedPath(int nNodes) {
		List<Node> nodes = nodes(nNodes);
		final double cost = nodes.isEmpty() ? ActionCosts.get().COST_INFINITE : nodes.get(nodes.size() - 1).gcost();
		return new CompletedPath(cost, nodes);
	}




	public static PartialPath partialPath(int nNodes) {
		List<Node> nodes = nodes(nNodes);
		final double cost = nodes.isEmpty() ? ActionCosts.get().COST_INFINITE : nodes.get(nodes.size() - 1).gcost();
		return new PartialPath(cost, nodes);
	}




	public static EmptyPath emptyPath() {
		return new EmptyPath();
	}




	public static List<Node> nodes(int nNodes) {
		List<Node> nodes = new ArrayList<>();
		nodes.add(startNode());
		for (int i = 1; i < nNodes; i++) {
			nodes.add(node(nodes.get(i - 1)));
		}
		return nodes;
	}




	public static Node startNode() {
		final Random random = new Random();

		final Node node = new Node();
		node.setPos(new BaseBlockPos(random.nextInt(200) - 100, random.nextInt(200), random.nextInt(200) - 100));

		return node;
	}




	public static Node node(Node prevNode) {
		final Node node = new Node();
		node.setPos(prevNode.getPosCopy().add(Direction.CARDINALS[new Random().nextInt(8)]));
		node.setPrev(prevNode);

		final double cost = Math.random() * 18 + 2;
		final Action action = action(prevNode, node, cost);
		node.setAction(action);
		node.setGCost(prevNode.gcost() + cost);

		return node;
	}




	public static Action action(Node from, Node to, double cost) {
		return new Action(from, to, cost) {
			@Override
			public String getActionName() {
				return "test_action";
			}




			@Override
			public ProcState tick(boolean firstTick) {
				return ProcState.DONE;
			}




			@Override
			public boolean isOnPath(BaseBlockPos position) {
				return true;
			}
		};
	}


}
