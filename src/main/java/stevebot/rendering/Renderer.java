package stevebot.rendering;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.util.math.BlockPos;

public interface Renderer {


	void addRenderable(Renderable renderable);

	void removeRenderable(Renderable renderable);

	void beginLines(float width);

	void beginLineStrip(float width);

	void beginBoxes(float width);

	void beginPoints(float size);

	void end();

	void drawLine(BlockPos start, BlockPos end, float width, Color color);

	void drawLine(Vector3d start, Vector3d end, float width, Color color);

	void drawLineOpen(Vector3d start, Vector3d end, Color color);

	void drawBox(BlockPos pos, float width, Color color);

	void drawBoxOpen(BlockPos pos, Color color);

	void drawBox(Vector3d pos, float width, Color color);

	void drawBoxOpen(Vector3d pos, Color color);

	void drawPoint(BlockPos pos, float size, Color color);

	void drawPoint(Vector3d pos, float size, Color color);

	void drawPointOpen(BlockPos pos, Color color);

	void drawPointOpen(Vector3d pos, Color color);

}
