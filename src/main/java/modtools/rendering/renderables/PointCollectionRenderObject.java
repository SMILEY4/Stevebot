package modtools.rendering.renderables;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import modtools.rendering.Color;
import modtools.rendering.MTRenderer;
import modtools.rendering.Renderable;
import net.minecraft.util.math.BlockPos;

public class PointCollectionRenderObject implements Renderable {


	private final Vector3d[] positions;
	private float size;
	private final Color[] colors;




	public PointCollectionRenderObject(BlockPos[] positions, Color color) {
		this(positions, DEFAULT_POINT_SIZE, Renderable.fillColorArray(color, positions.length));
	}




	public PointCollectionRenderObject(BlockPos[] positions, Color[] colors) {
		this(positions, DEFAULT_POINT_SIZE, colors);
	}




	public PointCollectionRenderObject(BlockPos[] positions, float size, Color color) {
		this(Renderable.toVecArray(positions), size, Renderable.fillColorArray(color, positions.length));
	}




	public PointCollectionRenderObject(BlockPos[] positions, float size, Color[] colors) {
		this(Renderable.toVecArray(positions), size, colors);
	}




	public PointCollectionRenderObject(Vector3d[] positions, Color color) {
		this(positions, DEFAULT_POINT_SIZE, Renderable.fillColorArray(color, positions.length));
	}




	public PointCollectionRenderObject(Vector3d[] positions, Color[] colors) {
		this(positions, DEFAULT_POINT_SIZE, colors);
	}




	public PointCollectionRenderObject(Vector3d[] positions, float size, Color color) {
		this(positions, size, Renderable.fillColorArray(color, positions.length));
	}




	public PointCollectionRenderObject(Vector3d[] positions, float size, Color[] colors) {
		this.positions = positions;
		this.size = size;
		this.colors = colors;
	}




	@Override
	public void render(MTRenderer renderer) {
		renderer.beginPoints(size);
		for (int i = 0, n = positions.length; i < n; i++) {
			final Vector3d pos = positions[i];
			final Color color = colors[i];
			renderer.drawPointOpen(pos, color);
		}
		renderer.end();
	}




	public Vector3d[] getPositions() {
		return positions;
	}




	public Color[] getColors() {
		return colors;
	}




	public float getSize() {
		return size;
	}




	public void setSize(float size) {
		this.size = size;
	}


}
