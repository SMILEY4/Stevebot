package stevebot.rendering.renderables;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.util.math.BlockPos;
import stevebot.rendering.Color;
import stevebot.rendering.Renderable;
import stevebot.rendering.Renderer;

public class PointCollectionRenderObject implements Renderable {


	private final Vector3d[] positions;
	private float size;
	private final Color[] colors;




	/**
	 * @param positions the positions of the points
	 * @param color     the colors of the points
	 */
	public PointCollectionRenderObject(BlockPos[] positions, Color color) {
		this(positions, DEFAULT_POINT_SIZE, Renderable.fillColorArray(color, positions.length));
	}




	/**
	 * @param positions the positions of the points
	 * @param colors    the colors of the points
	 */
	public PointCollectionRenderObject(BlockPos[] positions, Color[] colors) {
		this(positions, DEFAULT_POINT_SIZE, colors);
	}




	/**
	 * @param positions the positions of the points
	 * @param size      the size of the points in pixels
	 * @param color     the color of all points
	 */
	public PointCollectionRenderObject(BlockPos[] positions, float size, Color color) {
		this(Renderable.toVecArray(positions), size, Renderable.fillColorArray(color, positions.length));
	}




	/**
	 * @param positions the positions of the points
	 * @param size      the size of the points in pixels
	 * @param colors    the colors of the points
	 */
	public PointCollectionRenderObject(BlockPos[] positions, float size, Color[] colors) {
		this(Renderable.toVecArray(positions), size, colors);
	}




	/**
	 * @param positions the positions of the points
	 * @param color     the color of all points
	 */
	public PointCollectionRenderObject(Vector3d[] positions, Color color) {
		this(positions, DEFAULT_POINT_SIZE, Renderable.fillColorArray(color, positions.length));
	}




	/**
	 * @param positions the positions of the points
	 * @param colors    the colors of the points
	 */
	public PointCollectionRenderObject(Vector3d[] positions, Color[] colors) {
		this(positions, DEFAULT_POINT_SIZE, colors);
	}




	/**
	 * @param positions the positions of the points
	 * @param size      the size of the points in pixels
	 * @param color     the color of all points
	 */
	public PointCollectionRenderObject(Vector3d[] positions, float size, Color color) {
		this(positions, size, Renderable.fillColorArray(color, positions.length));
	}




	/**
	 * @param positions the positions of the points
	 * @param size      the size of the points in pixels
	 * @param colors    the colors of the points
	 */
	public PointCollectionRenderObject(Vector3d[] positions, float size, Color[] colors) {
		this.positions = positions;
		this.size = size;
		this.colors = colors;
	}




	@Override
	public void render(Renderer renderer) {
		renderer.beginPoints(size);
		for (int i = 0, n = positions.length; i < n; i++) {
			final Vector3d pos = positions[i];
			final Color color = colors[i];
			renderer.drawPointOpen(pos, color);
		}
		renderer.end();
	}




	/**
	 * @return the position of all points
	 */
	public Vector3d[] getPositions() {
		return positions;
	}




	/**
	 * @return the colors of all points
	 */
	public Color[] getColors() {
		return colors;
	}




	/**
	 * @return the size of the points in pixels
	 */
	public float getSize() {
		return size;
	}




	/**
	 * @param size the new size of the points in pixels
	 */
	public void setSize(float size) {
		this.size = size;
	}


}
