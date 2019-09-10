package stevebot.rendering.renderables;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.util.math.BlockPos;
import stevebot.rendering.Color;
import stevebot.rendering.Renderable;
import stevebot.rendering.Renderer;

public class PathRenderObject implements Renderable {


	private final Vector3d[] positions;
	private float width;
	private final Color color;




	public PathRenderObject(BlockPos[] positions, Color color) {
		this(positions, DEFAULT_LINE_WIDTH, color);
	}




	public PathRenderObject(BlockPos[] positions, float width, Color color) {
		this(Renderable.toVecArray(positions), width, color);
	}




	public PathRenderObject(Vector3d[] position, Color color) {
		this(position, DEFAULT_LINE_WIDTH, color);
	}




	public PathRenderObject(Vector3d[] position, float width, Color color) {
		this.positions = position;
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




	public Vector3d[] getPositions() {
		return positions;
	}




	public Color getColor() {
		return color;
	}




	public float getWidth() {
		return width;
	}




	public void setWidth(float width) {
		this.width = width;
	}


}
