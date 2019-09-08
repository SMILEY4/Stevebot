package stevebot.rendering.renderables;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import stevebot.rendering.Color;
import stevebot.rendering.MTRenderer;
import stevebot.rendering.Renderable;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class DynPointCollectionRenderObject implements Renderable {


	private final List<Vector3d> positions = new ArrayList<>();
	private final List<Color> colors = new ArrayList<>();
	private float size;




	public DynPointCollectionRenderObject() {
		this(Renderable.DEFAULT_POINT_SIZE);
	}




	public DynPointCollectionRenderObject(float size) {
		this.size = size;
	}




	public void addPoint(BlockPos pos, Color color) {
		this.addPoint(new Vector3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), color);
	}




	public void addPoint(Vector3d pos, Color color) {
		this.positions.add(pos);
		this.colors.add(color);
	}




	@Override
	public void render(MTRenderer renderer) {
		renderer.beginPoints(size);
		for (int i = 0, n = positions.size(); i < n; i++) {
			final Vector3d pos = positions.get(i);
			final Color color = colors.get(i);
			renderer.drawPointOpen(pos, color);
		}
		renderer.end();
	}




	public List<Vector3d> getPositions() {
		return positions;
	}




	public List<Color> getColors() {
		return colors;
	}




	public float getSize() {
		return size;
	}




	public void setSize(float size) {
		this.size = size;
	}

}
