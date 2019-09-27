package stevebot.rendering.renderables;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.util.math.BlockPos;
import stevebot.rendering.Color;
import stevebot.rendering.Renderable;
import stevebot.rendering.Renderer;

import java.util.ArrayList;
import java.util.List;

public class DynPointCollectionRenderObject implements Renderable {


	private final List<Vector3d> positions = new ArrayList<>();
	private final List<Color> colors = new ArrayList<>();
	private float size;




	/**
	 * A new empty renderable collections of points
	 */
	public DynPointCollectionRenderObject() {
		this(Renderable.DEFAULT_POINT_SIZE);
	}




	/**
	 * A new empty renderable collections of points
	 *
	 * @param size the size of this points in pixels
	 */
	public DynPointCollectionRenderObject(float size) {
		this.size = size;
	}




	/**
	 * Adds a new point to this renderable
	 *
	 * @param pos   the position of the point. The point will be in the center of the block.
	 * @param color the color of the point
	 */
	public void addPoint(BlockPos pos, Color color) {
		this.addPoint(new Vector3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), color);
	}




	/**
	 * Adds a new point to this renderable
	 *
	 * @param pos   the position of the point
	 * @param color the color of the point
	 */
	public void addPoint(Vector3d pos, Color color) {
		this.positions.add(pos);
		this.colors.add(color);
	}




	@Override
	public void render(Renderer renderer) {
		renderer.beginPoints(size);
		for (int i = 0, n = positions.size(); i < n; i++) {
			final Vector3d pos = positions.get(i);
			final Color color = colors.get(i);
			renderer.drawPointOpen(pos, color);
		}
		renderer.end();
	}




	/**
	 * @return the position of the points
	 */
	public List<Vector3d> getPositions() {
		return positions;
	}




	/**
	 * @return the color of the points
	 */
	public List<Color> getColors() {
		return colors;
	}




	/**
	 * @return the size in pixels of the points
	 */
	public float getSize() {
		return size;
	}




	/**
	 * @param size the new size in pixels of the points
	 */
	public void setSize(float size) {
		this.size = size;
	}

}
