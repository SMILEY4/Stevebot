package modtools.rendering.renderables;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import modtools.rendering.Color;
import modtools.rendering.MTRenderer;
import modtools.rendering.Renderable;
import net.minecraft.util.math.BlockPos;

public class LineRenderObject implements Renderable {


	private final Vector3d start, end;
	private float width;
	private final Color color;




	public LineRenderObject(BlockPos start, BlockPos end, Color color) {
		this(start, end, DEFAULT_LINE_WIDTH, color);
	}




	public LineRenderObject(BlockPos start, BlockPos end, float width, Color color) {
		this(
				new Vector3d(start.getX() + 0.5, start.getY() + 0.5, start.getZ() + 0.5),
				new Vector3d(end.getX() + 0.5, end.getY() + 0.5, end.getZ() + 0.5),
				width,
				color
		);
	}




	public LineRenderObject(Vector3d start, Vector3d end, Color color) {
		this(start, end, DEFAULT_LINE_WIDTH, color);
	}




	public LineRenderObject(Vector3d start, Vector3d end, float width, Color color) {
		this.start = start;
		this.end = end;
		this.width = width;
		this.color = color;
	}




	@Override
	public void render(MTRenderer renderer) {
		renderer.drawLine(getStart(), getEnd(), getWidth(), getColor());
	}




	public Vector3d getStart() {
		return start;
	}




	public Vector3d getEnd() {
		return end;
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
