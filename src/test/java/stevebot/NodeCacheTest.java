package stevebot;

import org.junit.jupiter.api.Test;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.pathfinding.nodes.Node;
import stevebot.pathfinding.nodes.NodeCache;

import static org.assertj.core.api.Assertions.assertThat;

public class NodeCacheTest {


	@Test
	void testCache() {

		final int nodeXa = 24;
		final int nodeYa = 5;
		final int nodeZa = -51;

		final int nodeXb = -15;
		final int nodeYb = 7;
		final int nodeZb = 42;

		final Node node1 = NodeCache.get(new BaseBlockPos(nodeXa, nodeYa, nodeZa));
		assertThat(node1).isNotNull();
		assertThat(node1.getPos().getX()).isEqualTo(nodeXa);
		assertThat(node1.getPos().getY()).isEqualTo(nodeYa);
		assertThat(node1.getPos().getZ()).isEqualTo(nodeZa);
		assertThat(NodeCache.getNodes().size()).isEqualTo(1);

		final Node node2 = NodeCache.get(new BaseBlockPos(nodeXa, nodeYa, nodeZa));
		assertThat(node2).isNotNull();
		assertThat(node2.getPos().getX()).isEqualTo(nodeXa);
		assertThat(node2.getPos().getY()).isEqualTo(nodeYa);
		assertThat(node2.getPos().getZ()).isEqualTo(nodeZa);
		assertThat(NodeCache.getNodes().size()).isEqualTo(1);

		assertThat(node1).isEqualTo(node2);

		final Node node3 = NodeCache.get(new BaseBlockPos(nodeXb, nodeYb, nodeZb));
		assertThat(node3).isNotEqualTo(node1);
		assertThat(NodeCache.getNodes().size()).isEqualTo(2);

		NodeCache.clear();
		assertThat(NodeCache.getNodes().size()).isEqualTo(0);
	}


}
