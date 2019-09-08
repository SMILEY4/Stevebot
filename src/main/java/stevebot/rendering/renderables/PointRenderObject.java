package stevebot.rendering.renderables;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import stevebot.rendering.Color;
import stevebot.rendering.RendererImpl;
import stevebot.rendering.Renderable;
import net.minecraft.util.math.BlockPos;

public class PointRenderObject implements Renderable {


	private final Vector3d pos;
	private float size;
	private final Color color;




	public PointRenderObject(BlockPos pos, Color color) {
		this(pos, DEFAULT_POINT_SIZE, color);
	}




	public PointRenderObject(BlockPos pos, float size, Color color) {
		this(new Vector3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), size, color);
	}




	public PointRenderObject(Vector3d pos, Color color) {
		this(pos, DEFAULT_POINT_SIZE, color);
	}




	public PointRenderObject(Vector3d pos, float size, Color color) {
		this.pos = pos;
		this.size = size;
		this.color = color;
	}




	@Override
	public void render(RendererImpl renderer) {
		renderer.drawPoint(pos, size, color);
	}




	public Vector3d getPos() {
		return pos;
	}




	public Color getColor() {
		return color;
	}




	public float getSize() {
		return size;
	}




	public void setSize(float size) {
		this.size = size;
	}


}
