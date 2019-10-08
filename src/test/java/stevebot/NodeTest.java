package stevebot;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.pathfinding.nodes.Node;

import java.util.PriorityQueue;

import static org.assertj.core.api.Assertions.assertThat;

public class NodeTest {


	@Test
	void testPosition() {

		final int nodeX = 32;
		final int nodeY = 0;
		final int nodeZ = -42;

		final Node node = new Node();

		node.setPos(new BaseBlockPos(nodeX, nodeY, nodeZ));

		assertThat(node.getPos().getX()).isEqualTo(nodeX);
		assertThat(node.getPos().getY()).isEqualTo(nodeY);
		assertThat(node.getPos().getZ()).isEqualTo(nodeZ);

		assertThat(node.getPosCopy().getX()).isEqualTo(nodeX);
		assertThat(node.getPosCopy().getY()).isEqualTo(nodeY);
		assertThat(node.getPosCopy().getZ()).isEqualTo(nodeZ);
	}




	@Test
	void testOpenClose() {

		final PriorityQueue<Node> openSet = new PriorityQueue<>();
		final Node node = new Node();

		node.open(openSet);

		assertThat(node.isOpen()).isTrue();
		assertThat(openSet.contains(node)).isTrue();

		node.close();
		assertThat(node.isOpen()).isFalse();
	}




	@Test
	void testCost() {

		final double G_COST = 9.5;
		final double H_COST = 6.5;

		final Node node = new Node();

		node.setGCost(G_COST);
		node.setHCost(H_COST);
		assertThat(node.fcost()).isCloseTo(G_COST + H_COST, Percentage.withPercentage(0.01));
	}

}
