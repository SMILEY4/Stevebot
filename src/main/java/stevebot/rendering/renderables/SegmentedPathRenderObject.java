package stevebot.rendering.renderables;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.util.math.BlockPos;
import stevebot.rendering.Color;
import stevebot.rendering.Renderable;
import stevebot.rendering.Renderer;

public class SegmentedPathRenderObject implements Renderable {


	private final Vector3d[] positions;
	private final Color[] colors;
	private float width;




	/**
	 * @param positions the position of the vertices of this path.
	 * @param colors    the colors of the vertices of this path.
	 */
	public SegmentedPathRenderObject(BlockPos[] positions, Color[] colors) {
		this(positions, DEFAULT_LINE_WIDTH, colors);
	}




	/**
	 * @param positions the position of the vertices of this path.
	 * @param width     the with of the path in pixels
	 * @param colors    the colors of the vertices of this path.
	 */
	public SegmentedPathRenderObject(BlockPos[] positions, float width, Color[] colors) {
		this(Renderable.toVecArray(positions), width, colors);
	}




	/**
	 * @param positions the position of the vertices of this path.
	 * @param colors    the colors of the vertices of this path.
	 */
	public SegmentedPathRenderObject(Vector3d[] positions, Color[] colors) {
		this(positions, DEFAULT_LINE_WIDTH, colors);
	}




	/**
	 * @param positions the position of the vertices of this path.
	 * @param width     the with of the path in pixels
	 * @param colors    the colors of the vertices of this path.
	 */
	public SegmentedPathRenderObject(Vector3d[] positions, float width, Color[] colors) {
		this.positions = positions;
		this.width = width;
		this.colors = colors;
	}




	@Override
	public void render(Renderer renderer) {
		renderer.beginLineStrip(width);
		for (int i = 0, n = positions.length; i < n - 1; i++) {
			final Color color = colors[i];
			final Vector3d p0 = positions[i];
			final Vector3d p1 = positions[i + 1];
			renderer.drawLineOpen(p0, p1, color);
		}
		renderer.end();
	}




	/**
	 * @return the position of the vertices of this path
	 */
	public Vector3d[] getPositions() {
		return positions;
	}




	/**
	 * @return the colors of the vertices of this path
	 */
	public Color[] getColors() {
		return colors;
	}




	/**
	 * @return the with in pixels of this path
	 */
	public float getWidth() {
		return width;
	}




	/**
	 * @param width the new with in pixels of this path
	 */
	public void setWidth(float width) {
		this.width = width;
	}


}
