package stevebot.rendering.renderables;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.util.math.BlockPos;
import stevebot.rendering.Color;
import stevebot.rendering.Renderable;
import stevebot.rendering.Renderer;

public class BoxCollectionRenderObject implements Renderable {


	private final Vector3d[] positions;
	private final Color[] colors;
	private float width;




	/**
	 * @param positions the positions of the boxes
	 * @param color     the color of all boxes
	 */
	public BoxCollectionRenderObject(BlockPos[] positions, Color color) {
		this(positions, DEFAULT_LINE_WIDTH, Renderable.fillColorArray(color, positions.length));
	}




	/**
	 * @param positions the positions of the boxes
	 * @param colors    the colors of the boxes
	 */
	public BoxCollectionRenderObject(BlockPos[] positions, Color[] colors) {
		this(positions, DEFAULT_LINE_WIDTH, colors);
	}




	/**
	 * @param positions the positions of the boxes
	 * @param width     the with in pixels of the outline of the boxes
	 * @param color     the color of all boxes
	 */
	public BoxCollectionRenderObject(BlockPos[] positions, float width, Color color) {
		this(Renderable.toVecArray(positions, 0, 0, 0), width, Renderable.fillColorArray(color, positions.length));
	}




	/**
	 * @param positions the positions of the boxes
	 * @param width     the with in pixels of the outline of the boxes
	 * @param colors    the colors of the boxes
	 */
	public BoxCollectionRenderObject(BlockPos[] positions, float width, Color[] colors) {
		this(Renderable.toVecArray(positions, 0, 0, 0), width, colors);
	}




	/**
	 * @param positions the positions of the boxes
	 * @param color     the color of all boxes
	 */
	public BoxCollectionRenderObject(Vector3d[] positions, Color color) {
		this(positions, DEFAULT_LINE_WIDTH, Renderable.fillColorArray(color, positions.length));
	}




	/**
	 * @param positions the positions of the boxes
	 * @param colors    the colors of the boxes
	 */
	public BoxCollectionRenderObject(Vector3d[] positions, Color[] colors) {
		this(positions, DEFAULT_LINE_WIDTH, colors);
	}




	/**
	 * @param positions the positions of the boxes
	 * @param width     the with in pixels of the outline of the boxes
	 * @param color     the colors of the boxes
	 */
	public BoxCollectionRenderObject(Vector3d[] positions, float width, Color color) {
		this(positions, width, Renderable.fillColorArray(color, positions.length));
	}




	/**
	 * @param positions the positions of the boxes
	 * @param width     the with in pixels of the outline of the boxes
	 * @param colors    the colors of the boxes
	 */
	public BoxCollectionRenderObject(Vector3d[] positions, float width, Color[] colors) {
		this.positions = positions;
		this.width = width;
		this.colors = colors;
	}




	@Override
	public void render(Renderer renderer) {
		renderer.beginBoxes(width);
		for (int i = 0, n = positions.length; i < n; i++) {
			final Vector3d pos = positions[i];
			final Color color = colors[i];
			renderer.drawBoxOpen(pos, color);
		}
		renderer.end();
	}




	/**
	 * @return the positions of the boxes
	 */
	public Vector3d[] getPositions() {
		return positions;
	}




	/**
	 * @return the colors of the boxes
	 */
	public Color[] getColors() {
		return colors;
	}




	/**
	 * @return the width of the outline of the boxes
	 */
	public float getWidth() {
		return width;
	}




	/**
	 * @param width the new width of the outline of the boxes
	 */
	public void setWidth(float width) {
		this.width = width;
	}


}
