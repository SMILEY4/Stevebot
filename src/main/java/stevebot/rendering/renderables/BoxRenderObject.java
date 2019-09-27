package stevebot.rendering.renderables;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.util.math.BlockPos;
import stevebot.rendering.Color;
import stevebot.rendering.Renderable;
import stevebot.rendering.Renderer;

public class BoxRenderObject implements Renderable {


	private final Vector3d pos;
	private float width;
	private final Color color;




	/**
	 * @param pos   the position of the box
	 * @param color the color of the box
	 */
	public BoxRenderObject(BlockPos pos, Color color) {
		this(pos, DEFAULT_LINE_WIDTH, color);
	}




	/**
	 * @param pos   the position of the box
	 * @param width the with in pixels of the outline
	 * @param color the color of the box
	 */
	public BoxRenderObject(BlockPos pos, float width, Color color) {
		this(new Vector3d(pos.getX(), pos.getY(), pos.getZ()), width, color);
	}




	/**
	 * @param pos   the position of the box
	 * @param color the color of the box
	 */
	public BoxRenderObject(Vector3d pos, Color color) {
		this(pos, DEFAULT_LINE_WIDTH, color);
	}




	/**
	 * @param pos   the position of the box
	 * @param width the with in pixels of the outline
	 * @param color the color of the box
	 */
	public BoxRenderObject(Vector3d pos, float width, Color color) {
		this.pos = pos;
		this.width = width;
		this.color = color;
	}




	@Override
	public void render(Renderer renderer) {
		renderer.drawBox(pos, width, color);
	}




	/**
	 * @return the position of the box
	 */
	public Vector3d getPos() {
		return pos;
	}




	/**
	 * @return the color of the box
	 */
	public Color getColor() {
		return color;
	}




	/**
	 * @return the with in pixels of the outline of the box
	 */
	public float getWidth() {
		return width;
	}




	/**
	 * @param width the new with in pixels of the outline of the box
	 */
	public void setWidth(float width) {
		this.width = width;
	}


}
