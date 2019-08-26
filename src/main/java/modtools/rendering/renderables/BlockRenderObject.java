package modtools.rendering.renderables;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import modtools.rendering.Color;
import modtools.rendering.MTRenderer;
import modtools.rendering.Renderable;
import net.minecraft.util.math.BlockPos;

public class BlockRenderObject implements Renderable {


	private final Vector3d pos;
	private float width;
	private final Color color;




	public BlockRenderObject(BlockPos pos, Color color) {
		this(pos, DEFAULT_LINE_WIDTH, color);
	}




	public BlockRenderObject(BlockPos pos, float width, Color color) {
		this(new Vector3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), width, color);
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
	public void render(MTRenderer renderer) {
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
