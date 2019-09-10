package stevebot.rendering.renderables;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.util.math.BlockPos;
import stevebot.rendering.Color;
import stevebot.rendering.Renderable;
import stevebot.rendering.Renderer;

public class BlockCollectionRenderObject implements Renderable {


	private final Vector3d[] positions;
	private final Color[] colors;
	private float width;




	public BlockCollectionRenderObject(BlockPos[] positions, Color color) {
		this(positions, DEFAULT_LINE_WIDTH, Renderable.fillColorArray(color, positions.length));
	}




	public BlockCollectionRenderObject(BlockPos[] positions, Color[] colors) {
		this(positions, DEFAULT_LINE_WIDTH, colors);
	}




	public BlockCollectionRenderObject(BlockPos[] positions, float width, Color color) {
		this(Renderable.toVecArray(positions, 0, 0, 0), width, Renderable.fillColorArray(color, positions.length));
	}




	public BlockCollectionRenderObject(BlockPos[] positions, float width, Color[] colors) {
		this(Renderable.toVecArray(positions, 0, 0, 0), width, colors);
	}




	public BlockCollectionRenderObject(Vector3d[] positions, Color color) {
		this(positions, DEFAULT_LINE_WIDTH, Renderable.fillColorArray(color, positions.length));
	}




	public BlockCollectionRenderObject(Vector3d[] positions, Color[] colors) {
		this(positions, DEFAULT_LINE_WIDTH, colors);
	}




	public BlockCollectionRenderObject(Vector3d[] positions, float width, Color color) {
		this(positions, width, Renderable.fillColorArray(color, positions.length));
	}




	public BlockCollectionRenderObject(Vector3d[] positions, float width, Color[] colors) {
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
