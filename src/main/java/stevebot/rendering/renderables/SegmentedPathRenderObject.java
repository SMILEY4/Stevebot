package stevebot.rendering.renderables;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import stevebot.rendering.Color;
import stevebot.rendering.RendererImpl;
import stevebot.rendering.Renderable;
import net.minecraft.util.math.BlockPos;

public class SegmentedPathRenderObject implements Renderable {


	private final Vector3d[] positions;
	private final Color[] colors;
	private float width;




	public SegmentedPathRenderObject(BlockPos[] positions, Color[] colors) {
		this(positions, DEFAULT_LINE_WIDTH, colors);
	}




	public SegmentedPathRenderObject(BlockPos[] positions, float width, Color[] colors) {
		this(Renderable.toVecArray(positions), width, colors);
	}




	public SegmentedPathRenderObject(Vector3d[] position, Color[] colors) {
		this(position, DEFAULT_LINE_WIDTH, colors);
	}




	public SegmentedPathRenderObject(Vector3d[] position, float width, Color[] colors) {
		this.positions = position;
		this.width = width;
		this.colors = colors;
	}




	@Override
	public void render(RendererImpl renderer) {
		renderer.beginLineStrip(width);
		for (int i = 0, n = positions.length; i < n - 1; i++) {
			final Color color = colors[i];
			final Vector3d p0 = positions[i];
			final Vector3d p1 = positions[i + 1];
			renderer.drawLineOpen(p0, p1, color);
		}
		renderer.end();
	}




	public Vector3d[] getPositions() {
		return positions;
	}




	public Color[] getColors() {
		return colors;
	}




	public float getWidth() {
		return width;
	}




	public void setWidth(float width) {
		this.width = width;
	}


}
