package stevebot.rendering;

import com.ruegnerlukas.simplemath.vectors.vec3.Vector3d;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import stevebot.events.EventListener;

public interface Renderer {


	/**
	 * The given listener listens for a {@link RenderWorldLastEvent} and triggers the rendering of all {@link Renderable}s.
	 *
	 * @return the {@link EventListener} of this {@link Renderer}.
	 */
	EventListener getListener();

	/**
	 * Adds the given {@link Renderable} to this renderer.
	 *
	 * @param renderable the {@link Renderable}
	 */
	void addRenderable(Renderable renderable);

	/**
	 * Removes the given {@link Renderable} from this renderer.
	 *
	 * @param renderable the {@link Renderable}
	 */
	void removeRenderable(Renderable renderable);

	/**
	 * Removes all renderables from this renderer
	 */
	void clearRenderables();

	/**
	 * Start drawing lines.
	 *
	 * @param width the with of the lines in pixels.
	 */
	void beginLines(float width);

	/**
	 * Start drawing line-strips.
	 *
	 * @param width the with of the line-strips in pixels.
	 */
	void beginLineStrip(float width);


	/**
	 * Start drawing boxes.
	 *
	 * @param width the with of the outline in pixels.
	 */
	void beginBoxes(float width);

	/**
	 * Start drawing points.
	 *
	 * @param size the size of the points in pixels.
	 */
	void beginPoints(float size);

	/**
	 * Stop drawing and flush.
	 */
	void end();

	/**
	 * Draws a single line.
	 *
	 * @param start the start position
	 * @param end   the end position
	 * @param width the with of the line in pixels
	 * @param color the color of the line
	 */
	void drawLine(BlockPos start, BlockPos end, float width, Color color);

	/**
	 * Draws a single line.
	 *
	 * @param start the start position
	 * @param end   the end position
	 * @param width the with of the line in pixels
	 * @param color the color of the line
	 */
	void drawLine(Vector3d start, Vector3d end, float width, Color color);

	/**
	 * Draws a line. {@code Renderer.beginLines(width)}/{@code Renderer.stop} must be called before/after.
	 *
	 * @param start the start position
	 * @param end   the end position
	 * @param color the color of the line
	 */
	void drawLineOpen(Vector3d start, Vector3d end, Color color);

	/**
	 * Draws a single box
	 *
	 * @param pos   the position of the box
	 * @param width the with of the outline in pixels
	 * @param color the color of the box
	 */
	void drawBox(BlockPos pos, float width, Color color);

	/**
	 * Draws a single box
	 *
	 * @param pos   the position of the box
	 * @param width the with of the outline in pixels
	 * @param color the color of the box
	 */
	void drawBox(Vector3d pos, float width, Color color);

	/**
	 * Draws a box.  {@code Renderer.beginBoxes(width)}/{@code Renderer.stop} must be called before/after.
	 *
	 * @param pos   the position of the box
	 * @param color the color of the box
	 */
	void drawBoxOpen(BlockPos pos, Color color);

	/**
	 * Draws a box.  {@code Renderer.beginBoxes(width)}/{@code Renderer.stop} must be called before/after.
	 *
	 * @param pos   the position of the box
	 * @param color the color of the box
	 */
	void drawBoxOpen(Vector3d pos, Color color);

	/**
	 * Draws a box.  {@code Renderer.beginBoxes(width)}/{@code Renderer.stop} must be called before/after.
	 *
	 * @param posMin the position of the first corner of the box
	 * @param posMax the position of the second/opposite corner of the box
	 * @param color  the color of the box
	 */
	void drawBoxOpen(Vector3d posMin, Vector3d posMax, Color color);

	/**
	 * Draws a single point.
	 *
	 * @param pos   the position of the point
	 * @param size  the size of the point in pixels
	 * @param color the color of the point
	 */
	void drawPoint(BlockPos pos, float size, Color color);

	/**
	 * Draws a single point.
	 *
	 * @param pos   the position of the point
	 * @param size  the size of the point in pixels
	 * @param color the color of the point
	 */
	void drawPoint(Vector3d pos, float size, Color color);

	/**
	 * Draws a point.  {@code Renderer.beginPoints(width)}/{@code Renderer.stop} must be called before/after.
	 *
	 * @param pos   the position of the point
	 * @param color the color of the point
	 */
	void drawPointOpen(BlockPos pos, Color color);

	/**
	 * Draws a point.  {@code Renderer.beginPoints(width)}/{@code Renderer.stop} must be called before/after.
	 *
	 * @param pos   the position of the point
	 * @param color the color of the point
	 */
	void drawPointOpen(Vector3d pos, Color color);

}
