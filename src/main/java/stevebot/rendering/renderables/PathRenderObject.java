package stevebot.rendering.renderables;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.util.math.BlockPos;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.rendering.Color;
import stevebot.rendering.Renderable;
import stevebot.rendering.Renderer;

public class PathRenderObject implements Renderable {


	private final Vector3d[] positions;
	private float width;
	private final Color color;




	/**
	 * @param positions the positions of the vertices of this path
	 * @param color     the color of this path
	 */
	public PathRenderObject(BaseBlockPos[] positions, Color color) {
		this(Renderable.toVecArray(positions), DEFAULT_LINE_WIDTH, color);
	}




	/**
	 * @param positions the positions of the vertices of this path
	 * @param color     the color of this path
	 */
	public PathRenderObject(BlockPos[] positions, Color color) {
		this(positions, DEFAULT_LINE_WIDTH, color);
	}




	/**
	 * @param positions the positions of the vertices of this path
	 * @param width     the with of this path in pixels
	 * @param color     the color of this path
	 */
	public PathRenderObject(BlockPos[] positions, float width, Color color) {
		this(Renderable.toVecArray(positions), width, color);
	}




	/**
	 * @param positions the positions of the vertices of this path
	 * @param color     the color of this path
	 */
	public PathRenderObject(Vector3d[] positions, Color color) {
		this(positions, DEFAULT_LINE_WIDTH, color);
	}




	/**
	 * @param positions the positions of the vertices of this path
	 * @param width     the with of this path in pixels
	 * @param color     the color of this path
	 */
	public PathRenderObject(Vector3d[] positions, float width, Color color) {
		this.positions = positions;
		this.width = width;
		this.color = color;
	}




	@Override
	public void render(Renderer renderer) {
		renderer.beginLineStrip(width);
		for (int i = 0, n = positions.length; i < n - 1; i++) {
			final Vector3d p0 = positions[i];
			final Vector3d p1 = positions[i + 1];
			renderer.drawLineOpen(p0, p1, color);
		}
		renderer.end();
	}




	/**
	 * @return the positions of the vertices of this path
	 */
	public Vector3d[] getPositions() {
		return positions;
	}




	/**
	 * @return the colors of the vertices of this path
	 */
	public Color getColor() {
		return color;
	}




	/**
	 * @return the with of this path in pixels
	 */
	public float getWidth() {
		return width;
	}




	/**
	 * Sets the width of this path in pixels
	 *
	 * @param width the width in pixels
	 */
	public void setWidth(float width) {
		this.width = width;
	}


}
