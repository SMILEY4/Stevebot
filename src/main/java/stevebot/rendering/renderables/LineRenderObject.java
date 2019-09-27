package stevebot.rendering.renderables;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.util.math.BlockPos;
import stevebot.rendering.Color;
import stevebot.rendering.Renderable;
import stevebot.rendering.Renderer;

public class LineRenderObject implements Renderable {


	private final Vector3d start, end;
	private float width;
	private final Color color;




	/**
	 * @param start the start position of this line
	 * @param end   the end position of this line
	 * @param color the color of this line
	 */
	public LineRenderObject(BlockPos start, BlockPos end, Color color) {
		this(start, end, DEFAULT_LINE_WIDTH, color);
	}




	/**
	 * @param start the start position of this line
	 * @param end   the end position of this line
	 * @param width the with of this line
	 * @param color the color of this line
	 */
	public LineRenderObject(BlockPos start, BlockPos end, float width, Color color) {
		this(
				new Vector3d(start.getX() + 0.5, start.getY() + 0.5, start.getZ() + 0.5),
				new Vector3d(end.getX() + 0.5, end.getY() + 0.5, end.getZ() + 0.5),
				width,
				color
		);
	}




	/**
	 * @param start the start position of this line
	 * @param end   the end position of this line
	 * @param color the color of this line
	 */
	public LineRenderObject(Vector3d start, Vector3d end, Color color) {
		this(start, end, DEFAULT_LINE_WIDTH, color);
	}




	/**
	 * @param start the start position of this line
	 * @param end   the end position of this line
	 * @param width the with of this line
	 * @param color the color of this line
	 */
	public LineRenderObject(Vector3d start, Vector3d end, float width, Color color) {
		this.start = start;
		this.end = end;
		this.width = width;
		this.color = color;
	}




	@Override
	public void render(Renderer renderer) {
		renderer.drawLine(getStart(), getEnd(), getWidth(), getColor());
	}




	/**
	 * @return the start position of this line
	 */
	public Vector3d getStart() {
		return start;
	}




	/**
	 * @return the end position of this line
	 */
	public Vector3d getEnd() {
		return end;
	}




	/**
	 * @return the color of this line
	 */
	public Color getColor() {
		return color;
	}




	/**
	 * @return the with in pixels of this line
	 */
	public float getWidth() {
		return width;
	}




	/**
	 * @param width the new with in pixels of this line
	 */
	public void setWidth(float width) {
		this.width = width;
	}


}
