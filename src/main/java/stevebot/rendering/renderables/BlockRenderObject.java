package stevebot.rendering.renderables;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.util.math.BlockPos;
import stevebot.rendering.Color;
import stevebot.rendering.Renderable;
import stevebot.rendering.Renderer;

public class BlockRenderObject implements Renderable {


	private final Vector3d pos;
	private float width;
	private final Color color;




	public BlockRenderObject(BlockPos pos, Color color) {
		this(pos, DEFAULT_LINE_WIDTH, color);
	}




	public BlockRenderObject(BlockPos pos, float width, Color color) {
		this(new Vector3d(pos.getX(), pos.getY(), pos.getZ()), width, color);
	}




	public BlockRenderObject(Vector3d pos, Color color) {
		this(pos, DEFAULT_LINE_WIDTH, color);
	}




	public BlockRenderObject(Vector3d pos, float width, Color color) {
		this.pos = pos;
		this.width = width;
		this.color = color;
	}




	@Override
	public void render(Renderer renderer) {
		renderer.drawBox(pos, width, color);
	}




	public Vector3d getPos() {
		return pos;
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
