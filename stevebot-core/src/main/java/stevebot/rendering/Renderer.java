package stevebot.rendering;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import stevebot.data.blocks.BlockProvider;
import stevebot.data.blocks.ChunkCache;
import stevebot.math.vectors.vec3.Vector3d;
import stevebot.minecraft.MinecraftAdapter;
import stevebot.misc.Config;
import stevebot.pathfinding.nodes.NodeCache;
import stevebot.pathfinding.nodes.NodeRenderable;

public class Renderer {

    private final Tessellator TESSELLATOR = Tessellator.getInstance();
    private final BufferBuilder BUFFER = TESSELLATOR.getBuffer();
    private final List<Renderable> renderables = new ArrayList<>();

    private final MinecraftAdapter minecraftAdapter;

    public Renderer(MinecraftAdapter minecraftAdapter, BlockProvider blockProvider) {
        this.minecraftAdapter = minecraftAdapter;
        addRenderable(blockProvider.getBlockCache().getChunkCache().getChunkCacheRenderable());
        addRenderable(new NodeRenderable(NodeCache.getNodes()));
    }

    /**
     * Renders all {@link Renderable}s.
     */
    public void onEventRender() {
        EntityPlayerSP player = minecraftAdapter.getPlayer();
        if (player != null) {

            // setup
            Vec3d playerPos = player.getPositionVector();
            setup(playerPos);

            // draw
            for (int i = 0, n = renderables.size(); i < n; i++) {
                Renderable renderable = renderables.get(i);
                if (renderable instanceof ChunkCache.ChunkCacheRenderable && !Config.isShowChunkCache()) {
                    continue;
                }
                if (renderable instanceof NodeRenderable && !Config.isShowNodeCache()) {
                    continue;
                }
                renderable.render(this);
            }

            // reset
            reset();
        }
    }


    /**
     * Adds the given {@link Renderable} to this renderer.
     *
     * @param renderable the {@link Renderable}
     */
    public void addRenderable(Renderable renderable) {
        this.renderables.add(renderable);
    }


    /**
     * Removes the given {@link Renderable} from this renderer.
     *
     * @param renderable the {@link Renderable}
     */
    public void removeRenderable(Renderable renderable) {
        this.renderables.remove(renderable);
    }


    /**
     * Removes all renderables from this renderer
     */
    public void clearRenderables() {
        this.renderables.clear();
    }


    /**
     * Start drawing lines.
     *
     * @param width the with of the lines in pixels.
     */
    public void beginLines(float width) {
        GlStateManager.glLineWidth(width);
        BUFFER.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
    }


    /**
     * Start drawing line-strips.
     *
     * @param width the with of the line-strips in pixels.
     */
    public void beginLineStrip(float width) {
        GlStateManager.glLineWidth(width);
        BUFFER.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
    }


    /**
     * Start drawing boxes.
     *
     * @param width the with of the outline in pixels.
     */
    public void beginBoxes(float width) {
        GlStateManager.glLineWidth(width);
        BUFFER.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
    }


    /**
     * Start drawing points.
     *
     * @param size the size of the points in pixels.
     */
    public void beginPoints(float size) {
        GL11.glPointSize(size);
        BUFFER.begin(GL11.GL_POINTS, DefaultVertexFormats.POSITION_COLOR);
    }


    /**
     * Stop drawing and flush.
     */
    public void end() {
        TESSELLATOR.draw();
    }


    /**
     * Draws a single line.
     *
     * @param start the start position
     * @param end   the end position
     * @param width the with of the line in pixels
     * @param color the color of the line
     */
    public void drawLine(BlockPos start, BlockPos end, float width, Color color) {
        drawLine(
                new Vector3d(start.getX() + 0.5, start.getY() + 0.5, start.getZ() + 0.5),
                new Vector3d(end.getX() + 0.5, end.getY() + 0.5, end.getZ() + 0.5),
                width,
                color);
    }


    /**
     * Draws a single line.
     *
     * @param start the start position
     * @param end   the end position
     * @param width the with of the line in pixels
     * @param color the color of the line
     */
    public void drawLine(Vector3d start, Vector3d end, float width, Color color) {
        beginLines(width);
        drawLineOpen(start, end, color);
        end();
    }


    /**
     * Draws a line. {@code Renderer.beginLines(width)}/{@code Renderer.stop} must be called before/after.
     *
     * @param start the start position
     * @param end   the end position
     * @param color the color of the line
     */
    public void drawLineOpen(Vector3d start, Vector3d end, Color color) {
        addVertex(start, color);
        addVertex(end, color);
    }


    /**
     * Draws a single box
     *
     * @param pos   the position of the box
     * @param width the with of the outline in pixels
     * @param color the color of the box
     */
    public void drawBox(BlockPos pos, float width, Color color) {
        drawBox(new Vector3d(pos.getX(), pos.getY(), pos.getZ()), width, color);
    }


    /**
     * Draws a single box
     *
     * @param pos   the position of the box
     * @param width the with of the outline in pixels
     * @param color the color of the box
     */
    public void drawBox(Vector3d pos, float width, Color color) {
        beginLineStrip(width);
        drawBoxOpen(pos, color);
        end();
    }


    /**
     * Draws a box.  {@code Renderer.beginBoxes(width)}/{@code Renderer.stop} must be called before/after.
     *
     * @param pos   the position of the box
     * @param color the color of the box
     */
    public void drawBoxOpen(BlockPos pos, Color color) {
        drawBoxOpen(new Vector3d(pos.getX(), pos.getY(), pos.getZ()), color);
    }


    /**
     * Draws a box.  {@code Renderer.beginBoxes(width)}/{@code Renderer.stop} must be called before/after.
     *
     * @param pos   the position of the box
     * @param color the color of the box
     */
    public void drawBoxOpen(Vector3d pos, Color color) {
        drawBoxOpen(pos, new Vector3d(pos).add(1), color);
    }


    /**
     * Draws a box.  {@code Renderer.beginBoxes(width)}/{@code Renderer.stop} must be called before/after.
     *
     * @param posMin the position of the first corner of the box
     * @param posMax the position of the second/opposite corner of the box
     * @param color  the color of the box
     */
    public void drawBoxOpen(Vector3d posMin, Vector3d posMax, Color color) {

        final double minX = posMin.x;
        final double maxX = posMax.x;
        final double minY = posMin.y;
        final double maxY = posMax.y;
        final double minZ = posMin.z;
        final double maxZ = posMax.z;

        addVertex(maxX, minY, maxZ, color, 0f);

        addVertex(maxX, minY, maxZ, color, 1f);
        addVertex(minX, minY, maxZ, color, 1f);

        addVertex(maxX, maxY, minZ, color, 0f);

        addVertex(maxX, maxY, minZ, color, 1f);
        addVertex(minX, maxY, minZ, color, 1f);
        addVertex(minX, minY, minZ, color, 1f);
        addVertex(maxX, minY, minZ, color, 1f);
        addVertex(maxX, maxY, minZ, color, 1f);
        addVertex(maxX, maxY, maxZ, color, 1f);
        addVertex(maxX, minY, maxZ, color, 1f);
        addVertex(maxX, minY, minZ, color, 1f);

        addVertex(minX, maxY, maxZ, color, 0f);

        addVertex(minX, maxY, maxZ, color, 1f);
        addVertex(maxX, maxY, maxZ, color, 1f);
        addVertex(maxX, maxY, minZ, color, 1f);
        addVertex(minX, maxY, minZ, color, 1f);
        addVertex(minX, maxY, maxZ, color, 1f);
        addVertex(minX, minY, maxZ, color, 1f);
        addVertex(minX, minY, minZ, color, 1f);
        addVertex(minX, maxY, minZ, color, 1f);
        addVertex(minX, minY, minZ, color, 1f);

    }


    /**
     * Draws a single point.
     *
     * @param pos   the position of the point
     * @param size  the size of the point in pixels
     * @param color the color of the point
     */
    public void drawPoint(BlockPos pos, float size, Color color) {
        drawPoint(new Vector3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), size, color);
    }


    /**
     * Draws a single point.
     *
     * @param pos   the position of the point
     * @param size  the size of the point in pixels
     * @param color the color of the point
     */
    public void drawPoint(Vector3d pos, float size, Color color) {
        beginPoints(size);
        drawPointOpen(pos, color);
        end();
    }


    /**
     * Draws a point.  {@code Renderer.beginPoints(width)}/{@code Renderer.stop} must be called before/after.
     *
     * @param pos   the position of the point
     * @param color the color of the point
     */
    public void drawPointOpen(BlockPos pos, Color color) {
        drawPointOpen(new Vector3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), color);
    }


    /**
     * Draws a point.  {@code Renderer.beginPoints(width)}/{@code Renderer.stop} must be called before/after.
     *
     * @param pos   the position of the point
     * @param color the color of the point
     */
    public void drawPointOpen(Vector3d pos, Color color) {
        addVertex(pos, color);
    }


    /**
     * Prepare the opengl state for drawing shapes.
     *
     * @param playerPos the current position of the player/camera
     */
    private void setup(Vec3d playerPos) {
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.translate(-playerPos.x, -playerPos.y, -playerPos.z);
    }


    /**
     * Resets the opengl state to the default state.
     */
    private void reset() {
        GlStateManager.glLineWidth(1.0f);
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }


    /**
     * Adds a new vertex to the buffer.
     *
     * @param pos   the position of the vertex
     * @param color the color of the vertex
     */
    private void addVertex(Vector3d pos, Color color) {
        addVertex(pos.x, pos.y, pos.z, color.x, color.y, color.z, 1f);
    }


    /**
     * Adds a new vertex to the buffer.
     *
     * @param pos   the position of the vertex
     * @param color the color of the vertex
     */
    private void addVertex(Vector3d pos, Color color, float alpha) {
        addVertex(pos.x, pos.y, pos.z, color.x, color.y, color.z, alpha);

    }


    /**
     * @param x     the x-position of the vertex
     * @param y     the y-position of the vertex
     * @param z     the z-position of the vertex
     * @param color the color of the vertex
     */
    private void addVertex(double x, double y, double z, Color color) {
        addVertex(x, y, z, color.x, color.y, color.z, 1f);
    }


    /**
     * @param x     the x-position of the vertex
     * @param y     the y-position of the vertex
     * @param z     the z-position of the vertex
     * @param color the color of the vertex
     * @param alpha the alpha-component of the color of the vertex
     */
    private void addVertex(double x, double y, double z, Color color, float alpha) {
        addVertex(x, y, z, color.x, color.y, color.z, alpha);
    }


    /**
     * @param x     the x-position of the vertex
     * @param y     the y-position of the vertex
     * @param z     the z-position of the vertex
     * @param red   the red-component of the color of the vertex
     * @param green the green-component of the color of the vertex
     * @param blue  the blue-component of the color of the vertex
     */
    private void addVertex(double x, double y, double z, float red, float green, float blue) {
        addVertex(x, y, z, red, green, blue, 1f);
    }


    /**
     * @param x     the x-position of the vertex
     * @param y     the y-position of the vertex
     * @param z     the z-position of the vertex
     * @param red   the red-component of the color of the vertex
     * @param green the green-component of the color of the vertex
     * @param blue  the blue-component of the color of the vertex
     * @param alpha the alpha-component of the color of the vertex
     */
    private void addVertex(double x, double y, double z, float red, float green, float blue, float alpha) {
        BUFFER.pos(x, y, z).color(red, green, blue, alpha).endVertex();
    }

}
